package me.loongly.mods.sclp.mixin.compat;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.language.I18N;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(value = ControlValueFormatter.class, remap = false)
public interface MixinControlValueFormatter 
{
    /**
     * @author LoongLy
     * @reason 为 quantityOrDisabled 添加 I18n 翻译支持
     */
    @Overwrite
    static ControlValueFormatter quantityOrDisabled(String name, String disableText) 
    {
        if(SCLPClientMod.isOldRubVersion())
        {
            return v -> {
                Class<?> clazz;
                try 
                {
                    clazz = Class.forName("net.minecraft.util.text.TranslationTextComponent");
                } 
                catch (ClassNotFoundException e) 
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "error";
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
                     return "error";
                }
                Object translationTextComponent;
                try 
                {
                    translationTextComponent = constructor.newInstance((v == 0) ? disableText : name, new Object[] { Integer.valueOf(v) });
                } 
                catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) 
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                     return "error";
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
                    return "error";
                }
                try 
                {
                    return (String) getStringMeth.invoke(translationTextComponent);
                } 
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "error";
                }
            };
            //上面这一坨等价于下面这行，说白了就是通过反射实现1.16里面的函数
            //return v -> (new TranslationTextComponent((v == 0) ? disableText : name, new Object[] { Integer.valueOf(v) })).getString();
        }
        String i18nName = I18N.trans(name);
        String i18NDisableText = I18N.trans(disableText);
        return (v) -> (v == 0 ? i18NDisableText : v + " " + i18nName);
    }
}

//LZX-2026-08-27-003