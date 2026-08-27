package me.loongly.mods.sclp.mixin;

import net.caffeinemc.caffeineconfig.AbstractCaffeineConfigMixinPlugin;
import net.caffeinemc.caffeineconfig.CaffeineConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class SCLPMixinConfigPlugin extends AbstractCaffeineConfigMixinPlugin 
{

    private static final String MIXIN_PACKAGE_ROOT = "me.loongly.mods.sclp.mixin.";

    @Override
    protected CaffeineConfig createConfig() 
    {
        return CaffeineConfig.builder("Sodium Chinese Localization Package").withSettingsKey("sodium-chinese-localized-pack:options")
                .addMixinOption("compat", true) // Should not allow users to turn this off
                .build(FMLPaths.CONFIGDIR.get().resolve("sodium-chinese-localized-pack.properties"));
    }

    @Override
    protected String mixinPackageRoot() 
    {
        return MIXIN_PACKAGE_ROOT;
    }
}