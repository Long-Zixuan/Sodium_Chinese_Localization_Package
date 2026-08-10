package me.loongly.mods.sclp.common.mixin.sodium;

import java.util.HashMap;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import me.loongly.mods.sclp.common.client.gui.ViaOpt;
import me.loongly.mods.sclp.common.language.I18N;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;


@Mixin(value = OptionImpl.class,remap = false)
public class MixinOptionImpl 
{
    @Shadow @Final
    Component name;
    @Inject(method = "setValue", at = @At("HEAD"), cancellable = true)
    private void injectSetValue(Object value, CallbackInfo ci) 
    {
        if(value instanceof ViaOpt)
        {
            if(name.getString().equals(I18N.trans("sclp.options.support_project.name")))
            {
                openSupportPage();
                ci.cancel();
            }
        }
    }

    void openSupportPage()
	{
		SCLPClientMod.LOGGER.info("[SCLP] Open Support Page.");
		Util.getPlatform()
                .openUri("https://ifdian.net/a/loongly");
	}
}
