package me.loongly.mods.sclp.common.mixin.embeddium;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.embeddedt.embeddium.impl.util.PlatformUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import net.minecraft.client.resources.language.I18n;
import me.loongly.mods.sclp.common.language.I18N;


@Mixin(value = PlatformUtil.class, remap = false)
public class MixinEmbeddiumPlatformUtil
{
    @Inject(method = "getModName", at = @At(value = "RETURN"), cancellable = true, remap = false)
    private static void mixinGetModName(String modId, CallbackInfoReturnable<String> cir) 
    {
        String modName = cir.getReturnValue();
        if(modName.equals("Sodium/Embeddium Chinese Localized Package")) //因为这个模组名字太长了，影响显示了，但是我也不想其他页面显示ECLP这种缩写
        {
            modName = "ECLP";
        }
        if(SCLPClientMod.options().shouldTransModName)
        {
            modName = I18N.trans(modName);
        }
        cir.setReturnValue(modName);
    }
}
