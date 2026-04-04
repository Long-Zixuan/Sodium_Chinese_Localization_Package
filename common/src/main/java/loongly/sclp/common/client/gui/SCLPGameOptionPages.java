package loongly.sclp.common.client.gui;

import com.google.common.collect.ImmutableList;

import net.caffeinemc.mods.sodium.client.gui.options.OptionFlag;
import net.caffeinemc.mods.sodium.client.gui.options.OptionGroup;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlValueFormatter;
import net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl;
import net.caffeinemc.mods.sodium.client.gui.options.control.TickBoxControl;

import net.minecraft.network.chat.Component;

import loongly.sclp.common.client.gui.options.storage.SCLPOptionsStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import loongly.sclp.common.client.SCLPClientMod;


public class SCLPGameOptionPages 
{

    private static final SCLPOptionsStorage lsdcOpts = new SCLPOptionsStorage();

    public static OptionPage birth()
    {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        List<OptionGroup> groups = new ArrayList<>();
        groups.add(OptionGroup.createBuilder()
        .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
                        .setName(Component.literal("🎂:" + (year -2004)))
                        .setTooltip(Component.literal("🎂"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> SCLPClientMod.birthCaiDan(), opts -> true)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
        return new OptionPage(Component.literal("🎂"), ImmutableList.copyOf(groups));
    }
}

//LoongLy Software Update 2026/04/05
