package me.loongly.mods.sclp.mixin;

import me.loongly.mods.sclp.client.SCLPClientMod;
import net.caffeinemc.caffeineconfig.AbstractCaffeineConfigMixinPlugin;
import net.caffeinemc.caffeineconfig.CaffeineConfig;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;

import java.util.Arrays;
import java.util.Optional;

import org.apache.maven.artifact.versioning.ArtifactVersion;

public class SCLPMixinConfigPlugin extends AbstractCaffeineConfigMixinPlugin 
{

    private static final String MIXIN_PACKAGE_ROOT = "me.loongly.mods.sclp.mixin.";

    @Override
    protected CaffeineConfig createConfig() 
    {
        return SCLPClientMod.mixinConfig();
    }

    @Override
    protected String mixinPackageRoot() 
    {
        return MIXIN_PACKAGE_ROOT;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) 
    {
        if(mixinClassName.equals("me.loongly.mods.sclp.mixin.compat.MixinSodiumOptionsGUI") || 
        mixinClassName.equals("me.loongly.mods.sclp.mixin.compat.MixinOptionImpl"))
        {
            return SCLPClientMod.isNewEmbeddium();
        }
        return true;
    }

    boolean isInArr(String[] arr, String str)
    {
        return Arrays.stream(arr).anyMatch(str::equals);
    }
}
