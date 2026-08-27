package me.loongly.mods.sclp.mixin.compat;

import java.util.HashMap;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.ViaOpt;
import me.loongly.mods.sclp.language.I18N;

import net.minecraft.text.Text;


@Mixin(value = OptionImpl.class,remap = false)
public class MixinOptionImpl 
{
    @Shadow @Final
    Text name;
    @Inject(method = "setValue", at = @At("HEAD"), cancellable = true)
    private void injectSetValue(Object value, CallbackInfo ci) 
    {
        if(value instanceof ViaOpt)
        {
            if(name.getString().equals(I18N.trans("sclp.options.support_project.name")))
            {
                SCLPClientMod.openSupportPage();
                ci.cancel();
            }
        }
    }
}
