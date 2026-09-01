package me.loongly.mods.sclp.mixin.compat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Field;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;

import me.loongly.mods.sclp.language.I18N;

import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl.Builder;

import net.minecraft.text.Text;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.ViaOpt;

@Mixin(value = OptionImpl.class, remap = false)
public class MixinOptionImpl 
{
    #if BEFORE_18_1
    @Inject(method = "getName", at = @At("RETURN"),cancellable = true)
    private void ibjectSetName(CallbackInfoReturnable<String> cir)
    {
        String name = cir.getReturnValue();
        name = I18N.trans(name);
        cir.setReturnValue(name);
    }
    /*#else
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
    }*/
    #endif
}    
