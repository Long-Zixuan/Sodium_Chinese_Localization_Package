package me.loongly.mods.sclp.client.gui;

import com.google.common.collect.ImmutableList;

import me.loongly.mods.sclp.client.gui.Builder;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.lang.String;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.language.I18N;
import net.minecraft.client.MinecraftClient;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
#if BEFORE_18_1
#else
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
#endif


public class SCLPGameOptionPages 
{

    private static final SCLPOptionsStorage lsdcOpts = new SCLPOptionsStorage();
    public static boolean isChangePageVis = false;

    public static OptionPage sclpPage()
    {
        List<OptionGroup> groups = new ArrayList<>();
        var builder = OptionImpl.createBuilder(boolean.class, lsdcOpts);
        Builder.setImplBuilderName(builder, I18N.trans("sclp.options.sclp_on"));
        Builder.setImplBuilderTooltip(builder, I18N.trans("sclp.options.sclp_on.tooltip"));
        builder.setControl(TickBoxControl::new)
                    .setBinding((opts, value) -> {
                        opts.sclpOn = value;
                        SCLPClientMod.caiDan();
                    }, opts -> opts.sclpOn)
                    .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD);
        groups.add(OptionGroup.createBuilder()
        .add(builder.build())
                .build());

        var builder2 = OptionImpl.createBuilder(boolean.class, lsdcOpts);
        Builder.setImplBuilderName(builder2, I18N.trans("sclp.options.sclp_page_off"));
        Builder.setImplBuilderTooltip(builder2, I18N.trans("sclp.options.sclp_page_off.tooltip"));
        builder2.setControl(TickBoxControl::new)
                    .setBinding((opts, value) -> {
                        opts.sclpPageOff = value;
                        SCLPClientMod.caiDan();
                        SCLPGameOptionPages.isChangePageVis = true;
                    }, opts -> opts.sclpPageOff)
                    .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD);
        groups.add(OptionGroup.createBuilder()
        .add(builder2.build())
                .build());
            
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            var closeSupBuilder = OptionImpl.createBuilder(boolean.class, lsdcOpts)
                    .setControl(TickBoxControl::new)
                    .setBinding((opts, value) -> {opts.shouldShowSupportPage = !value;}, opts -> !opts.shouldShowSupportPage)
                    .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD);
            Builder.setImplBuilderName(closeSupBuilder, I18N.trans("sclp.options.close_support_page.name"));
            Builder.setImplBuilderTooltip(closeSupBuilder, I18N.trans("sclp.options.close_support_page.tooltip"));
            groups.add(OptionGroup.createBuilder()
                .add(closeSupBuilder.build())
                .add(ViaOpt.create("sclp.options.support_project.name", "sclp.options.support_project.tooltip", lsdcOpts))
                .build());
        }
        return Builder.createOptionPage(I18N.trans("sclp.page"), groups);
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
        //                 .setName(Text.literal("ᗜᴗᗜ:" + (year -2004)))
        //                 .setTooltip(Text.literal("ᗜᴗᗜ"))
        //                 .setControl(TickBoxControl::new)
        //                 .setBinding((opts, value) -> SCLPClientMod.birthCaiDan(), opts -> true)
        //                 .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
        //                 .build())
        //         .build());
        // }
        return Builder.createOptionPage("ᗜᴗᗜ:" + (year -2004), groups);
    }
}


//LoongLy Software Update 2026/04/05
