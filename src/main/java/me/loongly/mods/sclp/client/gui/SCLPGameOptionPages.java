package me.loongly.mods.sclp.client.gui;

import com.google.common.collect.ImmutableList;



import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.language.I18N;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;


public class SCLPGameOptionPages 
{

    private static final SCLPOptionsStorage lsdcOpts = new SCLPOptionsStorage();

    @SuppressWarnings("unchecked")
    public static OptionPage sclpPage()
    {
        List<OptionGroup> groups = new ArrayList<>();
        groups.add(OptionGroup.createBuilder()
        .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
                        .setName(Text.translatable("sclp.options.shoud_trans_mod_name"))
                        .setTooltip(Text.translatable("sclp.options.shoud_trans_mod_name.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> {
                            opts.shouldTransModName = value; 
                            if(SCLPClientMod.isSOA())
                            {
                                rebuildRSOSodiumScr();
                            }
                            if(SCLPClientMod.isEmb())
                            {
                                rebuildEmbScr();
                            }
                        }, opts -> opts.shouldTransModName)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .setEnabled(SCLPClientMod.isSOA() || SCLPClientMod.isEmb())
                        .build())
                .build());
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
                    .setName(Text.translatable("sclp.options.close_support_page.name"))
                    .setTooltip(Text.translatable("sclp.options.close_support_page.tooltip"))
                    .setControl(TickBoxControl::new)
                    .setBinding((opts, value) -> {opts.shouldShowSupportPage = !value;}, opts -> !opts.shouldShowSupportPage)
                    .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                    .build())
                .add(ViaOpt.create("sclp.options.support_project.name", "sclp.options.support_project.tooltip", lsdcOpts))
                .build());
        }
        return new OptionPage(Text.literal(I18N.trans("sclp.page")), ImmutableList.copyOf(groups));
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
        //                 .setName(Text.literal("🎂:" + (year -2004)))
        //                 .setTooltip(Text.literal("🎂"))
        //                 .setControl(TickBoxControl::new)
        //                 .setBinding((opts, value) -> SCLPClientMod.birthCaiDan(), opts -> true)
        //                 .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
        //                 .build())
        //         .build());
        // }
        return new OptionPage(Text.literal("🎂:" + (year -2004)), ImmutableList.copyOf(groups));
    }

    private static void rebuildRSOSodiumScr()//环境里面没有RSO
    {
        var curSrc = MinecraftClient.getInstance().currentScreen;
        try
        {
            Class<?> rsoSrcClass = Class.forName("me.flashyreese.mods.reeses_sodium_options.client.gui.SodiumVideoOptionsScreen");
            if(rsoSrcClass.isInstance(curSrc))
            {
                Method rebuildMeth = rsoSrcClass.getMethod("rebuildUI");
                rebuildMeth.setAccessible(true);
                rebuildMeth.invoke(curSrc);
            }
        }
        catch (Exception e)
        {
            SCLPClientMod.LOGGER.error("[SCLP] Failed to rebuild RSOSodium Screen",e);
        }
    }

    private static void rebuildEmbScr()//避免老版本Emb，所以用反射解耦
    {
        var curSrc = MinecraftClient.getInstance().currentScreen;
        try
        {
            Class<?> embSrcClass = Class.forName("org.embeddedt.embeddium.gui.EmbeddiumVideoOptionsScreen");
            if(embSrcClass.isInstance(curSrc))
            {
                Method rebuildMeth = embSrcClass.getMethod("rebuildUI");
                rebuildMeth.setAccessible(true);
                rebuildMeth.invoke(curSrc);
            }
        }
        catch (Exception e)
        {
            SCLPClientMod.LOGGER.error("[SCLP] Failed to rebuild EmbScreen",e);
        }
    }

}


//LoongLy Software Update 2026/04/05
