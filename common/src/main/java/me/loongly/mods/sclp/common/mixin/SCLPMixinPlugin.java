package me.loongly.mods.sclp.common.mixin;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import me.loongly.mods.sclp.common.services.IPlatformHelper;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public class SCLPMixinPlugin implements IMixinConfigPlugin 
{
    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {return null;}

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) 
    {
        if(mixinClassName.startsWith("me.loongly.mods.sclp.common.mixin.reeses_sodium_options"))
        {
            return !isNewRSO();
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {return null;}

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    boolean isNewSodium()
    {
        try
        {
            Class.forName("net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen");
            return true;
        }
        catch (ClassNotFoundException e)
        {
            return false;
        }
    }

    boolean isNewRSO()
    {
        try
        {
            Class.forName("me.flashreese.mods.reese_sodium_options.client.config.ReeseSodiumOptionsConfig");
            return true;
        }
        catch (ClassNotFoundException e)
        {
            return false;
        }
    }
}
