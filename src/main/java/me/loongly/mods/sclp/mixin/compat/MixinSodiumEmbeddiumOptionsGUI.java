package me.loongly.mods.sclp.mixin.compat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.client.resource.language.I18n;
import org.embeddedt.embeddium.util.PlatformUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import me.loongly.mods.sclp.language.I18N;
import me.loongly.mods.sclp.client.SCLPClientMod;


@Mixin(value = PlatformUtil.class, remap = false)
public class MixinSodiumEmbeddiumOptionsGUI
{
    @Inject(method = "getModName", at = @At(value = "RETURN"), cancellable = true)
    private static void mixinGetModName(String modId, CallbackInfoReturnable<String> cir) {
        String modName = cir.getReturnValue();
        if(modName.equals("Embeddium Chinese Localized Pack")) //因为这个模组名字太长了，影响显示了，但是我也不想其他页面显示ECLP这种缩写
        {
            modName = "ECLP";
        }
        if(SCLPClientMod.options().getIsTransModNameVal())
        {
            modName = I18N.trans(modName);
        }
        cir.setReturnValue(modName);
    }
}
