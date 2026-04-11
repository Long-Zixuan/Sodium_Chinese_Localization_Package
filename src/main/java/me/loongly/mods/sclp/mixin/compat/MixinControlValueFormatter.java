package me.loongly.mods.sclp.mixin.compat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.text.Text;
import net.minecraft.client.resource.language.I18n;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;


@Mixin(value = ControlValueFormatter.class,remap = false) 
public interface MixinControlValueFormatter
{
    @Overwrite
    static ControlValueFormatter quantityOrDisabled(String name, String disableText) 
    {
        String i18nName = I18n.translate(name);
        String i18nDisableText = I18n.translate(disableText);
        return (v) -> Text.literal(v == 0 ? i18nDisableText : v + " " + i18nName);
    }

    @Overwrite
    static ControlValueFormatter multiplier() 
    {
        return (v) -> Text.translatable("sclp.multiplier",v);
    }
}
