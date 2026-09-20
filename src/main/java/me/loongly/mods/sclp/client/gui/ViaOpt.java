package me.loongly.mods.sclp.client.gui;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl.Builder;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.loongly.mods.sclp.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.language.I18N;

public enum ViaOpt
{
    VIA;

    public static OptionImpl<SCLPGameOptions,ViaOpt> build(SCLPOptionsStorage sclpOpts,String nameKey, String tooltipKey)
    {
        return OptionImpl.createBuilder(ViaOpt.class, sclpOpts)
                    .setName(nameKey)
                    .setTooltip(tooltipKey)
                    .setControl(opt -> new CyclingControl<>(opt, ViaOpt.class, new String[] { I18N.trans("sclp.options.open_external_page_button")}))
                    .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                    .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                    .build();
    }
}
