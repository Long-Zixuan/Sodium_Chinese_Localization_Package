package me.loongly.mods.sclp.client.gui;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.loongly.mods.sclp.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.language.I18N;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;

public enum ViaOpt 
{
    VIA;

    public static OptionImpl<SCLPGameOptions, Boolean> create(String nameKey, String tooltipKey, SCLPOptionsStorage sclpOpts)
    {
        return SCLPViaOptCreater.create(nameKey, tooltipKey, sclpOpts);
    }

    private static class SCLPViaOptCreater
    {
        public static OptionImpl<SCLPGameOptions, Boolean> create(String nameKey,String tooltipKey, SCLPOptionsStorage lsdcOpts)
        {
            var builder = OptionImpl.createBuilder(boolean.class, lsdcOpts)
                .setControl(TickBoxControl::new)//先这样吧
                .setBinding((opts, value) -> {}, opts -> true)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD);
            Builder.setImplBuilderName(builder, I18N.trans(nameKey));
            builder.setTooltip(new LiteralText(I18N.trans(tooltipKey)));
            return builder.build();
        }
    }
}
