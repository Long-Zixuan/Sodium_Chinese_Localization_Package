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

import me.jellysquid.mods.sodium.client.gui.options.Option;

import me.loongly.mods.sclp.language.I18N;

import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;

@Mixin(value = CyclingControl.class, remap = false)
public class MixinCyclingControl<T>
{
    #if BEFORE_18_1
    /*@Inject( //为了兼容性，只好牺牲你了呵呵呵
        method = "<init>(Lme/jellysquid/mods/sodium/client/gui/options/Option;Ljava/lang/Class;[Ljava/lang/String;)V",
        at = @At("RETURN")
    )
    private void injectInit(Option<T> option, Class<T> enumType, String[] names,CallbackInfo ci)
    {
        try 
        {
            Field namesField = CyclingControl.class.getDeclaredField("names");
            namesField.setAccessible(true);
            String[] originalNames = (String[]) namesField.get(this);
            
            // 翻译所有名称
            for (int i = 0; i < originalNames.length; i++) 
            {
                originalNames[i] = I18N.trans(originalNames[i]);
            }
        } 
        catch (NoSuchFieldException | IllegalAccessException e) 
        {
            e.printStackTrace();
        }
    }*/

     @Inject(
        method = "<init>(Lme/jellysquid/mods/sodium/client/gui/options/Option;Ljava/lang/Class;)V",
        at = @At("RETURN")
    )
    private void injectInit2(Option<T> option, Class<T> enumType,CallbackInfo ci)
    {
        try 
        {
            Field namesField = CyclingControl.class.getDeclaredField("names");
            namesField.setAccessible(true);
            Object tmpName = namesField.get(this);
            if(tmpName instanceof String[])
            {
                
            }
            else
            {
                return;//如果是ITextComponent，肯定是现代化I18n了，不需要我翻译
            }
            String[] originalNames = (String[]) tmpName;
            
            // 翻译所有名称
            for (int i = 0; i < originalNames.length; i++) 
            {
                originalNames[i] = I18N.trans(originalNames[i]);
            }
        } 
        catch (NoSuchFieldException | IllegalAccessException e) 
        {
            e.printStackTrace();
        }
    }
    #endif
}    
