package me.loongly.mods.sclp.mixin.compat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.text.Text;
import org.embeddedt.embeddium.util.PlatformUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.loongly.mods.sclp.client.SCLPClientMod;


@Mixin(value = PlatformUtil.class, remap = false)
public class MixinSodiumEmbeddiumOptionsGUI
{
    @Inject(method = "getModName", at = @At(value = "RETURN",target = "Lnet/minecraft/text/Text/;translatable(Ljava/lang/String;)V"), cancellable = true)
    private static void mixinGetModName(String modId, CallbackInfoReturnable<String> cir) {
        String modName = cir.getReturnValue();
        if(SCLPClientMod.options().isTransModName)
        {
            modName = Text.translatable(modName).getString().replaceAll("§.", "");
        }
        cir.setReturnValue(modName);
    }
}
