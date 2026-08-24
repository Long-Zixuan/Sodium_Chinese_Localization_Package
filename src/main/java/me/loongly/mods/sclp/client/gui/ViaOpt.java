package me.loongly.mods.sclp.client.gui;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.loongly.mods.sclp.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.language.I18N;
import net.minecraft.text.Text;
import net.minecraft.text.Style;

public enum ViaOpt 
{
    VIA;

    public static Object create(String nameKey, String tooltipKey, Object sclpOpts)
    {
        return SCLPViaOptCreater.create(nameKey, tooltipKey, sclpOpts);
    }

    private static class SCLPViaOptCreater
    {
        public static OptionImpl<SCLPGameOptions, ViaOpt> create(String nameKey,String tooltipKey, Object lsdcOpts)
        {
            return OptionImpl.createBuilder(ViaOpt.class, (SCLPOptionsStorage)lsdcOpts)
                        .setName(Text.translatable(nameKey))
                        .setTooltip(Text.translatable(tooltipKey))
                        .setControl(opt -> new CyclingControl<>(opt, ViaOpt.class, new Text[] { Text.literal(I18N.trans("sclp.options.open_external_page_button")).setStyle(Style.EMPTY.withUnderline(true))}))
                        .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build();
        }
    }
}
