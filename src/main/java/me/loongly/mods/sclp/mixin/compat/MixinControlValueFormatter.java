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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String i18nName = I18N.trans(name);
        String i18NDisableText = I18N.trans(disableText);
        return (v) -> (v == 0 ? i18NDisableText : v + " " + i18nName);
    }

    #if BEFORE_18_1
    @Overwrite
    static ControlValueFormatter fpsLimit() 
    {
        return v -> (v == 260) ? I18N.trans("options.framerateLimit.max") : I18N.trans("options.framerate", new Object[] { Integer.valueOf(v) });
    }

    @Overwrite
    static ControlValueFormatter quantity(String name) 
    {
        return v -> v + " " +  I18N.trans(name);
    }

    @Overwrite
    static ControlValueFormatter guiScale() 
    {
        return v -> (v == 0) ? I18N.trans("options.guiScale.auto") : I18N.trans("sclp.multiplier", v);
    }
    
    @Overwrite
    static ControlValueFormatter brightness() 
    {
        return v -> (v == 0) ? I18N.trans("options.gamma.min") : (v == 100) ? I18N.trans("options.gamma.max") : I18N.trans("sclp.percent", v);
    }
    #endif
}

//LZX-2026-08-27-003
