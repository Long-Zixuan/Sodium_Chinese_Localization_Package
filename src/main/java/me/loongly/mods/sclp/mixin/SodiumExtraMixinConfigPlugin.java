package me.loongly.mods.sclp.mixin;

import me.loongly.mods.sclp.client.SCLPClientMod;
import net.caffeinemc.caffeineconfig.AbstractCaffeineConfigMixinPlugin;
import net.caffeinemc.caffeineconfig.CaffeineConfig;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;

import java.util.Optional;

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

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) 
    {
        /*if(mixinClassName.equals("me.loongly.mods.sclp.mixin.compat.MixinSodiumOptionsAPIGUI")) 
        {
            try
            {
                if(ModList.get().getModContainerById("sodiumoptionsapi").isPresent())
                {
                    return true;
                }
                else 
                {
                    return false;
                }
            } 
            catch (Exception e)
            {
                return false;
            }
        }*/
        return true;
    }
}
