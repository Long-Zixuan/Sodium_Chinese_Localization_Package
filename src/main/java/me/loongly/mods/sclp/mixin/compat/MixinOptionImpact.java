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

import me.jellysquid.mods.sodium.client.gui.options.OptionImpact;

import net.minecraft.text.Text;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.ViaOpt;

@Mixin(value = OptionImpact.class, remap = false)
public class MixinOptionImpact 
{
    #if BEFORE_18_1
    @Inject(method = "<init>", at = @At("RETURN"),cancellable = true)
    private void injectInit(CallbackInfo c)
    {
        if(!SCLPClientMod.options().sclpOn)
        {
            return;
        }
        try
        {
            Field field = OptionImpact.class.getDeclaredField("text");
            field.setAccessible(true);
            Object t = field.get((OptionImpact)(Object)this);
            if(t instanceof String)
            {
                field.set((OptionImpact)(Object)this, I18N.trans((String)t));
            }
        }
        catch (NoSuchFieldException | IllegalAccessException e) 
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
        }
    }
    #endif
}    
