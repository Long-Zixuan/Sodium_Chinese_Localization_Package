package loongly.sclp.client.gui;


import com.google.common.collect.ImmutableList;

import com.google.common.collect.ImmutableList;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Util;
import loongly.sclp.client.gui.options.storage.SCLPOptionsStorage;
import loongly.sclp.language.I18N;
import loongly.sclp.client.SclpClientMod;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
public class SCLPGameOptionPages
{

    private static final SCLPOptionsStorage sclpOpts = new SCLPOptionsStorage();

    public static boolean isChangeNotShowPage = false;

    public static OptionPage sclpPage()
    {
        List<OptionGroup> groups = new ArrayList<>();
        buildSclpPages(groups);
        return new OptionPage("sclp.page_name", ImmutableList.copyOf(groups));
    }

     public static OptionPage birthPage()
     {
        List<OptionGroup> groups = new ArrayList<>();
        //buildSclpPages(groups);
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        return new OptionPage("ᗜᴗᗜ:" + (year - 2004), ImmutableList.copyOf(groups));
     }

    static void buildSclpPages(List<OptionGroup> groups)
    {
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                        .setName("sclp.is_enable_sclp")
                        .setTooltip("sclp.is_enable_sclp_tooltip")
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> {opts.isEnableSclp = value;rebuildSodiumScr();SclpClientMod.caidan();}, opts -> opts.isEnableSclp)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                        .setName("sclp.is_disable_fabric_api_warn")
                        .setTooltip("sclp.is_disable_fabric_api_warn_tooltip")
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.isDisableSclpFabricApiWarn = value, opts -> opts.isDisableSclpFabricApiWarn)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                        .setName("sclp.is_disable_no_internet_warn")
                        .setTooltip("sclp.is_disable_no_internet_warn_tooltip")
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> {opts.isDisableSclpNoInternetWarn = value;rebuildSodiumScr();}, opts -> opts.isDisableSclpNoInternetWarn)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                        .setName("sclp.not_show_page")
                        .setTooltip("sclp.not_show_page_tooltip")
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> {opts.notShowPage = value;rebuildSodiumScr();isChangeNotShowPage = true;}, opts -> opts.notShowPage)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
        if(SclpClientMod.options().shouldShowSupportBtn)
        {
                groups.add(OptionGroup.createBuilder()
                        .add(ViaOpt.build(sclpOpts, "sclp.support", "sclp.support_tooltip"))
                        .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                                .setName("sclp.support_close")
                                .setTooltip("sclp.support_close_tooltip")
                                .setControl(TickBoxControl::new)
                                .setBinding((opts, value) -> {opts.shouldShowSupportBtn = !value;}, opts -> !opts.shouldShowSupportBtn)
                                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                                .build())
                        .build());
        }
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(SclpClientMod.isMyBirthday(year, month, day))
        {
                groups.add(OptionGroup.createBuilder()
                .add(ViaOpt.build(sclpOpts, "sclp.birth", "ᗜᴗᗜ:" + (year - 2004)))
                .build());
        }
    }

   

    static void rebuildSodiumScr()
    {
        try
        {
                MinecraftClient client = MinecraftClient.getInstance();
                Screen curScreen = client.currentScreen;
                if(curScreen instanceof SodiumOptionsGUI)
                {
                        Class<?> clazz = SodiumOptionsGUI.class;
                        Method method = clazz.getDeclaredMethod("rebuildGUI");
                        method.setAccessible(true);
                        method.invoke(curScreen);
                }
        }
        catch(Exception e)
        {
                SclpClientMod.LOGGER.error("[SCLP] close Sodium Screen Error:", e);
        }
    }

    static void closeVideoSettingsPage()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        client.currentScreen.onClose();
    }
}
