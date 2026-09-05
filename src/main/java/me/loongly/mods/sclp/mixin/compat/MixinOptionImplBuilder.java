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

import me.loongly.mods.sclp.language.I18N;

import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl.Builder;
import me.loongly.mods.sclp.client.SCLPClientMod;

@Mixin(value = Builder.class, remap = false)
public class MixinOptionImplBuilder
{
    #if BEFORE_18_1
    @Inject(method = "setTooltip(Ljava/lang/String;)Lme/jellysquid/mods/sodium/client/gui/options/OptionImpl$Builder;", at = @At("RETURN"),cancellable = true)
    private void injectSetTooltip(String tooltip, CallbackInfoReturnable<Builder> cir)
    {
        if(!SCLPClientMod.options().sclpOn)
        {
            return;
        }
        try 
        {
            Field field = Builder.class.getDeclaredField("tooltip");
            field.setAccessible(true);
            Object l = field.get((Builder)(Object)this);
            if(l instanceof String)
            {
                field.set((Builder)(Object)this, I18N.trans(tooltip));
            }
            else
            {
                Class<?> clazz;
            try 
            {
                clazz = Class.forName("net.minecraft.util.text.TranslationTextComponent");//这玩意继承了ITextComponent
            } 
            catch (ClassNotFoundException e) 
            {
                // TODO Auto-generated catch block
                SCLPClientMod.logger().error("[SCLP] :",e);//warn
                return;
            }
            Constructor<?> constructor;
            try 
            {
                constructor = clazz.getConstructor(String.class, Object[].class);
            } 
            catch (NoSuchMethodException | SecurityException e) 
            {
                // TODO Auto-generated catch block
                SCLPClientMod.logger().error("[SCLP] :",e);//warn
                    return;
            }
            Object translationTextComponent;
            try 
            {
                translationTextComponent = constructor.newInstance(I18N.trans(tooltip), null);
            } 
            catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) 
            {
                // TODO Auto-generated catch block
                SCLPClientMod.logger().error("[SCLP] :",e);//warn
                    return;
            }
                field.set((Builder)(Object)this, translationTextComponent);
            }
        }
        catch (NoSuchFieldException | IllegalAccessException e) 
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
        }
    }
    #endif
}    
