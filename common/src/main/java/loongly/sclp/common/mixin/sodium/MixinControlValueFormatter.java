package loongly.sclp.common.mixin.sodium;
import loongly.sclp.common.client.SCLPClientMod;

import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPrompt;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPromptable;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlValueFormatter;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Overwrite;


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
        String i18nName = I18n.get(name);
        String i18NDisableText = I18n.get(disableText);
        return (v) -> Component.literal(v == 0 ? i18NDisableText : v + " " + i18nName);
    }

     /**
     * @author LoongLy
     * @reason 为 brightness 添加 I18n 翻译支持
     */
    // @Overwrite
    // static ControlValueFormatter brightness() 
    // {
    //     return (v) -> {
    //         if (v == 0) 
    //         {
    //             return Component.translatable("options.gamma.min");
    //         } 
    //         else if (v == 100) 
    //         {
    //             return Component.translatable("options.gamma.max");
    //         } 
    //         else
    //         {
    //             return Component.translatable("sclp.percent", v);
    //         }
    //     };
    // }

    /**
     * @author LoongLy
     * @reason 为 percentage 添加 I18n 翻译支持
     */
    // @Overwrite
    // static ControlValueFormatter percentage() 
    // {
    //     return (v) -> Component.translatable("sclp.percent", v);
    // }

    /**
     * @author LoongLy
     * @reason 为 multiplier 添加 I18n 翻译支持
     */
    @Overwrite
    static ControlValueFormatter multiplier() 
    {
        return (v) -> Component.translatable("sclp.multiplier", v);
    }

     /**
     * @author LoongLy
     * @reason 为 guiScale 添加 I18n 翻译支持
     */
    @Overwrite
    static ControlValueFormatter guiScale() 
    {
        return (v) -> (v == 0) ? Component.translatable("options.guiScale.auto") : Component.translatable("sclp.multiplier", v);
    }
}

//LZX-2026-04-11-002