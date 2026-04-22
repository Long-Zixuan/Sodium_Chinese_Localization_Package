package me.loongly.mods.sclp.mixin.compat;

import org.apache.logging.log4j.core.config.builder.api.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.text.Text;
import net.minecraft.client.resource.language.I18n;
import me.loongly.mods.sclp.language.I18N;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;


@Mixin(value = ControlValueFormatter.class,remap = false) 
public interface MixinControlValueFormatter
{
    @Overwrite
    static ControlValueFormatter quantityOrDisabled(String name, String disableText) 
    {
        String i18nName = I18N.trans(name);
        String i18nDisableText = I18N.trans(disableText);
        return (v) -> Text.literal(v == 0 ? i18nDisableText : v + " " + i18nName);
    }

    @Overwrite
    static ControlValueFormatter multiplier() 
    {
        return (v) -> Text.literal(I18N.trans("sclp.multiplier",v));
    }

    @Overwrite
    static ControlValueFormatter guiScale() 
    {
        return (v) -> (v == 0) ? Text.literal(I18N.trans("options.guiScale.auto")) : Text.literal(I18N.trans("sclp.multiplier",v));
    }
}
