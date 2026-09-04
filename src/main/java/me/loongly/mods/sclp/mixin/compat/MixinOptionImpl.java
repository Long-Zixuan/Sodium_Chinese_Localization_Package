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
        if(!SCLPClientMod.options().sclpOn)
        {
            return;
        }
        String name = cir.getReturnValue();
        name = I18N.trans(name);
        cir.setReturnValue(name);
    }
   
    @Inject(method = "setValue", at = @At("HEAD"), cancellable = true)
    private void injectSetValue(Object value, CallbackInfo ci) 
    {
        if(value instanceof ViaOpt)
        {
            
        }
        else
        {
            return;
        }
        Object name;
        try
        {
            Field field = OptionImpl.class.getDeclaredField("name");
            field.setAccessible(true);
            name = field.get((OptionImpl)(Object)this);
        }
        catch (NoSuchFieldException | IllegalAccessException e) 
        {
            e.printStackTrace();
            return;
        }
        if(name instanceof String)
        {
            if(name.equals(I18N.trans("sclp.options.support_project.name")))
            {
                SCLPClientMod.openSupportPage();
                ci.cancel();
            }
            return;
        }
        try
        {
            Class<?> clazz = Class.forName("net.minecraft.util.text.ITextComponent");
            Method method = clazz.getDeclaredMethod("getString");
            method.setAccessible(true);
            String nameStr = (String)method.invoke(name);
            if(nameStr.equals(I18N.trans("sclp.options.support_project.name")))
            {
                SCLPClientMod.openSupportPage();
                ci.cancel();
            }
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) 
        {
            e.printStackTrace();
        }
    }
    #else
    @Inject(method = "setValue", at = @At("HEAD"), cancellable = true)
    private void injectSetValue(Object value, CallbackInfo ci) 
    {
        if(value instanceof ViaOpt)
        {
            
        }
        else
        {
            return;
        }
        Object name;
        try
        {
            Field field = OptionImpl.class.getDeclaredField("name");
            field.setAccessible(true);
            name = field.get((OptionImpl)(Object)this);
        }
        catch (NoSuchFieldException | IllegalAccessException e) 
        {
            e.printStackTrace();
            return;
        }

        try
        {
            Class.forName("net.minecraft.network.chat.TextComponent");
            if(name instanceof Text)
            {
                String nameStr = ((Text)name).getString();
                if(nameStr.equals(I18N.trans("sclp.options.support_project.name")))
                {
                    SCLPClientMod.openSupportPage();
                    ci.cancel();
                }
                return;
            }
        }
        catch (ClassNotFoundException e)
        {}
    
        try
        {
            Class<?> textCompClazz = Class.forName("net.minecraft.network.chat.Component");
            Method method = textCompClazz.getDeclaredMethod("getString");
            method.setAccessible(true);
            String nameStr = (String)method.invoke(name);
            if(nameStr.equals(I18N.trans("sclp.options.support_project.name")))
            {
                SCLPClientMod.openSupportPage();
                ci.cancel();
            }
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) 
        {
            e.printStackTrace();
        }
    }
    #endif
}    
