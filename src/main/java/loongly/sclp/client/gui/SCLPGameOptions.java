package loongly.sclp.client.gui;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import loongly.sclp.client.SclpClientMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
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

    public boolean isEnableSclp;
    public boolean isDisableSclpFabricApiWarn;
    public boolean isDisableSclpNoInternetWarn;
    public boolean notShowPage;

    public SCLPGameOptions()
    {
        isEnableSclp = true;
        isDisableSclpFabricApiWarn = false;
        isDisableSclpNoInternetWarn = false;
        notShowPage = false;
    }



    public static SCLPGameOptions load()
    {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(DEFAULT_FILE_NAME);
        SCLPGameOptions config;

        if (Files.exists(path))
        {
            try (FileReader reader = new FileReader(path.toFile()))
            {
                config = GSON.fromJson(reader, SCLPGameOptions.class);
            }
            catch (IOException e)
            {
                SclpClientMod.LOGGER.error("[SCLP]Could not parse SCLP config", e);
                throw new RuntimeException("[SCLP]Could not parse SCLP config", e);
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
            //throw new RuntimeException("[SCLP]Couldn't update SCLP config", e);
            SclpClientMod.LOGGER.error("[SCLP]Couldn't update SCLP config", e);
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
            //throw new IOException("[SCLP]Not a directory: " + dir);
            SclpClientMod.LOGGER.error("[SCLP]Not a directory: " + dir);
        }

        Files.write(this.configPath, GSON.toJson(this).getBytes(StandardCharsets.UTF_8));
    }
}
