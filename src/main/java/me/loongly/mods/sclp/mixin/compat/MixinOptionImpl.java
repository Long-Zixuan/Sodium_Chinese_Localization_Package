package me.loongly.mods.sclp.mixin.compat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;

@Mixin(value = OptionImpl.class, remap = false)
public class MixinOptionImpl 
{
    #if BEFORE_18_1
    @Inject(method = "getName", at = @At("RETURN"),cancellable = true)
    private void ibjectSetName(CallbackInfoReturnable<String> cir)
    {
        String name = cir.getReturnValue();
        if(name instanceof String)
        {
            Class<?> clazz;
            try 
            {
                clazz = Class.forName("net.minecraft.util.text.TranslationTextComponent");
            } 
            catch (ClassNotFoundException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
                e.printStackTrace();
                return;
            }
            Object translationTextComponent;
            try 
            {
                translationTextComponent = constructor.newInstance(name,null);
            } 
            catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
            Method getStringMeth;
            try 
            {
                getStringMeth = clazz.getMethod("getString");
            } 
            catch (NoSuchMethodException | SecurityException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
            try 
            {
                String finalStr = (String) getStringMeth.invoke(translationTextComponent);
                name = finalStr;
            } 
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        cir.setReturnValue(name);
    }
    #endif
}    
