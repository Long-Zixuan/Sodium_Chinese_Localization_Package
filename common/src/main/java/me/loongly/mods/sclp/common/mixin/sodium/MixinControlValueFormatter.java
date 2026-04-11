package me.loongly.mods.sclp.common.mixin.sodium;
import me.loongly.mods.sclp.common.client.SCLPClientMod;

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
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlValueFormatterImpls;
import net.caffeinemc.mods.sodium.api.config.option.ControlValueFormatter;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Overwrite;


@Mixin(value = ControlValueFormatterImpls.class, remap = false)
class MixinControlValueFormatter 
{
    /**
     * @author LoongLy
     * @reason 为 multiplier 添加 I18n 翻译支持
     */
    @Overwrite
    public static ControlValueFormatter multiplier() 
    {
        return (v) -> Component.translatable("sclp.multiplier", v);
    }

     /**
     * @author LoongLy
     * @reason 为 guiScale 添加 I18n 翻译支持
     */
    @Overwrite
    public static ControlValueFormatter guiScale() 
    {
        return (v) -> (v == 0) ? Component.translatable("options.guiScale.auto") : Component.translatable("sclp.multiplier", v);
    }
}

//LZX-2026-04-11-002