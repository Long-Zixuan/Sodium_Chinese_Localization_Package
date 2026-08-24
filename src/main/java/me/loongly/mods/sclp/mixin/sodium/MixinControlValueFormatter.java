package me.loongly.mods.sclp.mixin.sodium;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.loongly.mods.sclp.client.SCLPClientMod;

import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.spongepowered.asm.mixin.Overwrite;
import me.loongly.mods.sclp.language.I18N;


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
        return (v) -> Text.literal(v == 0 ? i18NDisableText : v + " " + i18nName);
    }

     /**
     * @author LoongLy
     * @reason 为 brightness 添加 I18n 翻译支持
     */
    @Overwrite
    static ControlValueFormatter brightness() 
    {
        return (v) -> {
            if (v == 0) 
            {
                return Text.translatable("options.gamma.min");
            } 
            else if (v == 100) 
            {
                return Text.translatable("options.gamma.max");
            } 
            else
            {
                return Text.translatable("sclp.percent", v);
            }
        };
    }

    /**
     * @author LoongLy
     * @reason 为 percentage 添加 I18n 翻译支持
     */
    @Overwrite
    static ControlValueFormatter percentage() 
    {
        return (v) -> Text.translatable("sclp.percent", v);
    }

    /**
     * @author LoongLy
     * @reason 为 multiplier 添加 I18n 翻译支持
     */
    @Overwrite
    static ControlValueFormatter multiplier() 
    {
        return (v) -> Text.translatable("sclp.multiplier", v);
    }

     /**
     * @author LoongLy
     * @reason 为 guiScale 添加 I18n 翻译支持
     */
    @Overwrite
    static ControlValueFormatter guiScale() 
    {
        return (v) -> (v == 0) ? Text.translatable("options.guiScale.auto") : Text.translatable("sclp.multiplier", v);
    }
}

//LZX-2026-04-11-002