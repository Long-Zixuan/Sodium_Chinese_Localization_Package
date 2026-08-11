package me.loongly.mods.sclp.common.client.gui;

import com.google.common.collect.ImmutableList;

import net.caffeinemc.mods.sodium.client.gui.options.OptionFlag;
import net.caffeinemc.mods.sodium.client.gui.options.OptionGroup;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlValueFormatter;
import net.caffeinemc.mods.sodium.client.gui.options.control.CyclingControl;
import net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;

import net.minecraft.network.chat.Component;

import me.loongly.mods.sclp.common.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.common.language.I18N;
import me.loongly.mods.sclp.common.services.IPlatformHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import me.loongly.mods.sclp.common.client.SCLPClientMod;


public class SCLPGameOptionPages 
{

    private static final SCLPOptionsStorage lsdcOpts = new SCLPOptionsStorage();

    @SuppressWarnings("unchecked")
    public static OptionPage sclpPage()
    {
        BooleanSupplier shoudEnableTransModName = () -> IPlatformHelper.INSTANCE.isSOA();
        List<OptionGroup> groups = new ArrayList<>();
        groups.add(OptionGroup.createBuilder()
        .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
                        .setName(Component.translatable("sclp.options.shoud_trans_mod_name"))
                        .setTooltip(Component.translatable("sclp.options.shoud_trans_mod_name.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.shouldTransModName = value, opts -> opts.shouldTransModName)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .setEnabled(shoudEnableTransModName)
                        .build())
                .build());
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
                    .setName(Component.translatable("sclp.options.close_support_page.name"))
                    .setTooltip(Component.translatable("sclp.options.close_support_page.tooltip"))
                    .setControl(TickBoxControl::new)
                    .setBinding((opts, value) -> {opts.shouldShowSupportPage = !value;}, opts -> !opts.shouldShowSupportPage)
                    .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                    .build())
                .add((OptionImpl<SCLPGameOptions, ViaOpt>)ViaOpt.create("sodium","sclp.options.support_project.name", "sclp.options.support_project.tooltip", null, (Object)lsdcOpts))
                .build());
        }
        return new OptionPage(Component.literal(I18N.trans("sclp.page")), ImmutableList.copyOf(groups));
    }

    public static OptionPage birthPage()
    {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        List<OptionGroup> groups = new ArrayList<>();
        // if(SCLPClientMod.isMyBirthday(year, month, day))
        // {
        //     groups.add(OptionGroup.createBuilder()
        //     .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
        //                 .setName(Component.literal("🎂:" + (year -2004)))
        //                 .setTooltip(Component.literal("🎂"))
        //                 .setControl(TickBoxControl::new)
        //                 .setBinding((opts, value) -> SCLPClientMod.birthCaiDan(), opts -> true)
        //                 .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
        //                 .build())
        //         .build());
        // }
        return new OptionPage(Component.literal("🎂:" + (year -2004)), ImmutableList.copyOf(groups));
    }

    public static net.caffeinemc.mods.sodium.client.gui.options.OptionImpl<SCLPGameOptions, ViaOpt> sclpCreate(String nameKey,String tooltipKey, SCLPOptionsStorage lsdcOpts)
    {
        return net.caffeinemc.mods.sodium.client.gui.options.OptionImpl.createBuilder(ViaOpt.class, lsdcOpts)
                    .setName(Component.translatable(nameKey))
                    .setTooltip(Component.translatable(tooltipKey))
                    .setControl(opt -> new net.caffeinemc.mods.sodium.client.gui.options.control.CyclingControl<>(opt, ViaOpt.class, new Component[] { Component.literal(I18N.trans("sclp.options.open_external_page_button"))}))
                    .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                    .setFlags(net.caffeinemc.mods.sodium.client.gui.options.OptionFlag.REQUIRES_RENDERER_RELOAD)
                    .build();
    }

}


//LoongLy Software Update 2026/04/05
