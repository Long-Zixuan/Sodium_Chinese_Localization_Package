package me.loongly.mods.sclp.common.client.gui;

import com.google.common.collect.ImmutableList;

import net.caffeinemc.mods.sodium.client.gui.options.OptionFlag;
import net.caffeinemc.mods.sodium.client.gui.options.OptionGroup;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlValueFormatter;
import net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;

import net.minecraft.network.chat.Component;

import me.loongly.mods.sclp.common.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.common.language.I18N;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import me.loongly.mods.sclp.common.client.SCLPClientMod;


public class SCLPGameOptionPages 
{

    private static final SCLPOptionsStorage lsdcOpts = new SCLPOptionsStorage();

    public static OptionPage sclpPage()
    {
        BooleanSupplier shoudEnableTransModName = () -> false;//用这个page builder的钠版本根本没有显示模组名字的功能，一律false
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
}

//LoongLy Software Update 2026/04/05
