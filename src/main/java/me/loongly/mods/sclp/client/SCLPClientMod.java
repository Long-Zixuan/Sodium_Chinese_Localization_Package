package me.loongly.mods.sclp.client;

import net.caffeinemc.caffeineconfig.CaffeineConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Logger;

import me.loongly.mods.sclp.client.gui.SCLPGameOptions;

import org.apache.logging.log4j.LogManager;

public class SCLPClientMod 
{
    public static final Logger LOGGER = LogManager.getLogger("Sodium Chinese Localization Pack");
    private static SCLPGameOptions CONFIG;
    private static CaffeineConfig MIXIN_CONFIG;

    public static SCLPGameOptions options() 
    {
        if (CONFIG == null) 
        {
            CONFIG = loadConfig();
        }

        return CONFIG;
    }

    public static void caiDan(){}

    public static CaffeineConfig mixinConfig() 
    {
        if (MIXIN_CONFIG == null) 
        {
            MIXIN_CONFIG = CaffeineConfig.builder("Sodium Chinese Localization Pack").withSettingsKey("sclp:options")

                    .withLogger(SCLPClientMod.LOGGER)
                    .build(FMLPaths.CONFIGDIR.get().resolve("sclp.properties"));
        }
        return MIXIN_CONFIG;
    }

    private static SCLPGameOptions loadConfig() 
    {
        return SCLPGameOptions.load(FMLPaths.CONFIGDIR.get().resolve("sodium-chinese-pack.json").toFile());
    }

    public SCLPClientMod() 
    {
    }
}
