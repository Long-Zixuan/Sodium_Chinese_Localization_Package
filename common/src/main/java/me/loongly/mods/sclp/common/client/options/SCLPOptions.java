package me.loongly.mods.sclp.common.client.options;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import me.loongly.mods.sclp.common.services.IPlatformHelper;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;


public class SCLPOptions 
{
    private static final String DEFAULT_FILE_NAME = "sodium-chinese-localized-pack-options.json";
    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .excludeFieldsWithModifiers(Modifier.PRIVATE, Modifier.STATIC)
            .create();
    private Path configPath;

    public static final boolean DEFAULT_SHOULD_TRANS_MOD_NAME = true;

    public boolean shouldTransModName;

    public void setShoudTransModName(boolean val)
    {
        shouldTransModName = val;
    }

    public boolean getShouldTransModName()
    {
        return shouldTransModName;
    }

    public SCLPOptions()
    {
        shouldTransModName = DEFAULT_SHOULD_TRANS_MOD_NAME;
    }

    public void save() 
    {
        try 
        {
            writeChanges();
        }
        catch (IOException e) 
        {
            throw new RuntimeException("Couldn't save SCLP options changes", e);
        }

        SCLPClientMod.LOGGER.info("[SCLP] Saved changes to SCLP options");
    }

    public static SCLPOptions load() 
    {
        Path path = IPlatformHelper.INSTANCE.getConfigDirectory().resolve(DEFAULT_FILE_NAME);
        SCLPOptions config;

        if (Files.exists(path)) 
        {
            try (FileReader reader = new FileReader(path.toFile())) 
            {
                config = GSON.fromJson(reader, SCLPOptions.class);
            }
            catch (IOException e) 
            {
                throw new RuntimeException("Could not parse SCLP options", e);
            }
        }
        else 
        {
            config = new SCLPOptions();
        }

        config.configPath = path;

        try 
        {
            config.writeChanges();
        }
        catch (IOException e) 
        {
            throw new RuntimeException("Couldn't update SCLP options", e);
        }
        return config;
    }

    private void writeChanges() throws IOException 
    {
        Path dir = this.configPath.getParent();

        if (!Files.exists(dir)) 
        {
            Files.createDirectories(dir);
        }
        else if (!Files.isDirectory(dir)) 
        {
            throw new IOException("Not a directory: " + dir);
        }

        Files.writeString(this.configPath, GSON.toJson(this));
    }

}
