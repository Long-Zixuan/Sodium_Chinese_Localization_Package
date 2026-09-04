package me.loongly.mods.sclp.client.gui;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.loongly.mods.sclp.client.SCLPClientMod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;


public class SCLPGameOptions 
{

    private static final String DEFAULT_FILE_NAME = "sodium-chinese-pack-options.json";
    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .excludeFieldsWithModifiers(Modifier.PRIVATE)
            .create();
    private Path configPath;

    public boolean shouldTransModName = true;
    public boolean shouldShowSupportPage = true;
    public boolean sclpOn = true;

    public SCLPGameOptions()
    {

    }

    public static SCLPGameOptions load() 
    {
        Path path = FMLPaths.CONFIGDIR.get().resolve(DEFAULT_FILE_NAME);
        SCLPGameOptions config;

        if (Files.exists(path)) 
        {
            try (FileReader reader = new FileReader(path.toFile())) 
            {
                config = GSON.fromJson(reader, SCLPGameOptions.class);
            }
            catch (IOException e) 
            {
                throw new RuntimeException("Could not parse SCLP config", e);
            }
        }
        else 
        {
            config = new SCLPGameOptions();
        }

        config.configPath = path;

        try 
        {
            config.writeChanges();
        }
        catch (IOException e) 
        {
            throw new RuntimeException("Couldn't update SCLP config", e);
        }

        return config;
    }

    public void writeChanges() throws IOException 
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
