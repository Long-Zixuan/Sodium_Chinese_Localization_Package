package me.loongly.mods.sclp.client.gui;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.loongly.mods.sclp.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.language.I18N;
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;

public enum ViaOpt 
{
    VIA;

    public static OptionImpl<SCLPGameOptions, ViaOpt> create(String nameKey, String tooltipKey, SCLPOptionsStorage sclpOpts)
    {
        return SCLPViaOptCreater.create(nameKey, tooltipKey, sclpOpts);
    }

    private static class SCLPViaOptCreater
    {
        public static OptionImpl<SCLPGameOptions, ViaOpt> create(String nameKey,String tooltipKey, SCLPOptionsStorage lsdcOpts)
        {//setId不可用，此为Embeddium的函数，其包名类名与Sodium完全相同
            return OptionImpl.createBuilder(ViaOpt.class, lsdcOpts)
                        .setName(new LiteralText (I18N.trans(nameKey)))
                        .setTooltip(new LiteralText(I18N.trans(tooltipKey)))
                        .setControl(opt -> new CyclingControl<>(opt, ViaOpt.class, new Text[] { new LiteralText(I18N.trans("sclp.options.open_external_page_button") + " ➤").setStyle(Style.EMPTY.withUnderline(true))}))
                        .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build();
        }
    }
}
