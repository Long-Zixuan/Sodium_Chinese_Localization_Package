package loongly.sclp.client.gui;


import com.google.common.collect.ImmutableList;

import com.google.common.collect.ImmutableList;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Util;
import loongly.sclp.client.gui.options.storage.SCLPOptionsStorage;
import loongly.sclp.language.I18N;
import loongly.sclp.client.SclpClientMod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class SCLPGameOptionPages
{

    private static final SCLPOptionsStorage sclpOpts = new SCLPOptionsStorage();

    public static boolean isChangeNotShowPage = false;

    public static OptionPage sclpPage()
    {
        List<OptionGroup> groups = new ArrayList<>();
        buildSclpPages(groups);
        return new OptionPage(I18N.trans("sclp.page_name"), ImmutableList.copyOf(groups));
    }

     public static OptionPage birthPage()
     {
        List<OptionGroup> groups = new ArrayList<>();
        buildSclpPages(groups);
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        return new OptionPage(I18N.trans("ᗜᴗᗜ:" + (year - 2004)), ImmutableList.copyOf(groups));
     }

    static void buildSclpPages(List<OptionGroup> groups)
    {
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                        .setName(I18N.trans("sclp.is_enable_sclp"))
                        .setTooltip(I18N.trans("sclp.is_enable_sclp_tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> {opts.isEnableSclp = value;closeVideoSettingsPage();SclpClientMod.caidan();}, opts -> opts.isEnableSclp)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                        .setName(I18N.trans("sclp.is_disable_fabric_api_warn"))
                        .setTooltip(I18N.trans("sclp.is_disable_fabric_api_warn_tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.isDisableSclpFabricApiWarn = value, opts -> opts.isDisableSclpFabricApiWarn)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                        .setName(I18N.trans("sclp.is_disable_no_internet_warn"))
                        .setTooltip(I18N.trans("sclp.is_disable_no_internet_warn_tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> {opts.isDisableSclpNoInternetWarn = value;closeVideoSettingsPage();}, opts -> opts.isDisableSclpNoInternetWarn)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                        .setName(I18N.trans("sclp.not_show_page"))
                        .setTooltip(I18N.trans("sclp.not_show_page_tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> {opts.notShowPage = value;closeVideoSettingsPage();isChangeNotShowPage = true;}, opts -> opts.notShowPage)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
         LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(SclpClientMod.isMyBirthday(year, month, day))
        {
                groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                        .setName("ᗜᴗᗜ:" + (year - 2004))
                        .setTooltip("ᗜᴗᗜ:" + (year - 2004))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> { SclpClientMod.birthCaidan();}, opts -> true)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
        }
    }

    static void closeVideoSettingsPage()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        client.currentScreen.onClose();
    }
}
