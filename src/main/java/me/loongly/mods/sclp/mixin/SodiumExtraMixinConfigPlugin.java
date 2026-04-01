package me.loongly.mods.sclp.mixin;

import me.loongly.mods.sclp.client.SCLPClientMod;
import net.caffeinemc.caffeineconfig.AbstractCaffeineConfigMixinPlugin;
import net.caffeinemc.caffeineconfig.CaffeineConfig;

public class SodiumExtraMixinConfigPlugin extends AbstractCaffeineConfigMixinPlugin 
{

    private static final String MIXIN_PACKAGE_ROOT = "me.loongly.mods.sclp.mixin.";

    @Override
    protected CaffeineConfig createConfig() {
        return SCLPClientMod.mixinConfig();
    }

    @Override
    protected String mixinPackageRoot() {
        return MIXIN_PACKAGE_ROOT;
    }
}
