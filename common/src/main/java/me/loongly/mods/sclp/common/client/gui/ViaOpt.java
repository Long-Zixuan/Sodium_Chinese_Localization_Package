package me.loongly.mods.sclp.common.client.gui;

import me.loongly.mods.sclp.common.client.gui.options.storage.ECLPOptionsStorage;
import me.loongly.mods.sclp.common.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.common.language.I18N;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public enum ViaOpt 
{
    VIA;

    public static class ECLPViaOptCreater
    {
        public static org.embeddedt.embeddium.api.options.structure.OptionImpl<SCLPGameOptions, ViaOpt> create(String nameKey,String tooltipKey,ResourceLocation id, ECLPOptionsStorage lsdcOpts)
        {
            return org.embeddedt.embeddium.api.options.structure.OptionImpl.createBuilder(ViaOpt.class, lsdcOpts)
                        .setId(id)
                        .setName(Component.translatable(nameKey))
                        .setTooltip(Component.translatable(tooltipKey))
                        .setControl(opt -> new org.embeddedt.embeddium.api.options.control.CyclingControl<>(opt, ViaOpt.class, new Component[] { Component.literal(I18N.trans("sclp.options.open_external_page_button")).withStyle(s -> s.withUnderlined(true))}))
                        .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                        .setFlags(org.embeddedt.embeddium.api.options.structure.OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build();
        }
    }

    public static class SCLPViaOptCreater
    {
        public static net.caffeinemc.mods.sodium.client.gui.options.OptionImpl<SCLPGameOptions, ViaOpt> create(String nameKey,String tooltipKey, SCLPOptionsStorage lsdcOpts)
        {
            return net.caffeinemc.mods.sodium.client.gui.options.OptionImpl.createBuilder(ViaOpt.class, lsdcOpts)
                        .setName(Component.translatable(nameKey))
                        .setTooltip(Component.translatable(tooltipKey))
                        .setControl(opt -> new net.caffeinemc.mods.sodium.client.gui.options.control.CyclingControl<>(opt, ViaOpt.class, new Component[] { Component.literal(I18N.trans("sclp.options.open_external_page_button")).withStyle(s -> s.withUnderlined(true))}))
                        .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                        .setFlags(net.caffeinemc.mods.sodium.client.gui.options.OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build();
        }
    }
}
