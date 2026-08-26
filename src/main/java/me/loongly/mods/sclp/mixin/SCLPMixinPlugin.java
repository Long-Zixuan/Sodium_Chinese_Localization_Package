package me.loongly.mods.sclp.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

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
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) //日后再写成函数吧
    {
        if(mixinClassName.equals("me.loongly.mods.sclp.mixin.sodium.MixinControlValueFormatter"))//0.5.0 开始Sodium的ValueFormatter类里面的函数返回值都是ControlValueFormatter，正确都是String，再开发一个版本意义不大，所以直接屏蔽
        {
            var modContainer = FabricLoader.getInstance().getModContainer("sodium");
            if(modContainer.isPresent())
            {
                var mod = modContainer.get();
                var version = mod.getMetadata().getVersion();
                try 
                {
                    if(version.compareTo(Version.parse("0.5.0")) >= 0)
                    {
                        return true;
                    }
                } 
                catch (VersionParsingException e) 
                {
                    e.printStackTrace();
                }
                return false;
            }
        }
        if(mixinClassName.equals("me.loongly.mods.sclp.mixin.sodium.MixinControlValueFormatter_Old"))//0.5.0 开始Sodium的ValueFormatter类里面的函数返回值都是ControlValueFormatter，正确都是String，再开发一个版本意义不大，所以直接屏蔽
        {
            var modContainer = FabricLoader.getInstance().getModContainer("sodium");
            if(modContainer.isPresent())
            {
                var mod = modContainer.get();
                var version = mod.getMetadata().getVersion();
                try 
                {
                    if(version.compareTo(Version.parse("0.5.0")) >= 0)
                    {
                        return false;
                    }
                } 
                catch (VersionParsingException e) 
                {
                    e.printStackTrace();
                }
            }
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
}
