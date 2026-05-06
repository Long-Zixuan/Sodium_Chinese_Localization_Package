package me.loongly.mods.sclp.mixin;

import me.loongly.mods.sclp.client.SCLPClientMod;
import net.caffeinemc.caffeineconfig.AbstractCaffeineConfigMixinPlugin;
import net.caffeinemc.caffeineconfig.CaffeineConfig;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class SodiumExtraMixinConfigPlugin extends AbstractCaffeineConfigMixinPlugin 
{

    private static final String MIXIN_PACKAGE_ROOT = "me.loongly.mods.sclp.mixin.";

    static boolean hadShowError = false;

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
        try
        { 
            Class.forName("com.netease.mc.coremod.CoreModManager");
            return true;
        }
        catch(Exception e)
        {
            CompletableFuture.runAsync
            (
                ()->
                {
                    if(!hadShowError)
                    {
                        SCLPClientMod.openErrorHtml();
                        hadShowError = true;
                    }
                }
            );
            //SCLPClientMod.openErrorHtml();
            return false;
            //throw new IllegalArgumentException("该版本的 Embeddium-汉化模组 仅可以在网易我的世界运行，如果需要在国际版运行，请前往国际版社区下载：https://modrinth.com/mod/mc1.16.5-sodium-chinese-localization-pack");
        }
    }
}
