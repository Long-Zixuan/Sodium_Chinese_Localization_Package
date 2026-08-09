package me.loongly.mods.sclp.common.client.gui;

import com.google.common.collect.ImmutableList;

import org.embeddedt.embeddium.api.options.control.CyclingControl;
import org.embeddedt.embeddium.api.options.control.SliderControl;
import org.embeddedt.embeddium.api.options.structure.Option;
import org.embeddedt.embeddium.api.options.structure.OptionGroup;
import org.embeddedt.embeddium.api.options.structure.OptionImpact;
import org.embeddedt.embeddium.api.options.structure.OptionImpl;
import org.embeddedt.embeddium.api.options.structure.OptionStorage;
import org.embeddedt.embeddium.impl.gui.EmbeddiumGameOptionPages;
import org.embeddedt.embeddium.api.options.structure.OptionPage;

import org.embeddedt.embeddium.api.options.structure.OptionFlag;
import org.embeddedt.embeddium.api.options.control.TickBoxControl;
import org.embeddedt.embeddium.api.options.OptionIdentifier;

import org.embeddedt.embeddium.api.OptionGroupConstructionEvent;
import org.embeddedt.embeddium.api.OptionPageConstructionEvent;

import org.embeddedt.embeddium.impl.gui.EmbeddiumVideoOptionsScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import me.loongly.mods.sclp.common.client.gui.options.storage.ECLPOptionsStorage;
import me.loongly.mods.sclp.common.language.I18N;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import me.loongly.mods.sclp.common.client.SCLPClientMod;


public class ECLPGameOptionPages 
{

    private static final ECLPOptionsStorage lsdcOpts = new ECLPOptionsStorage();

    public static OptionPage buildPage()
    {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        List<OptionGroup> groups = new ArrayList<>();
        if(SCLPClientMod.isMyBirthday(year, month, day))
        {
            groups.add(OptionGroup.createBuilder()
                .setId(OptionIdentifier.create(SCLPClientMod.MOD_ID, "birth_group"))
                .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
                    .setId(ResourceLocation.fromNamespaceAndPath(SCLPClientMod.MOD_ID, "birth_cai_dan"))
                    .setName(Component.literal("🎂:" + (year -2004)))
                    .setTooltip(Component.literal("🎂"))
                    .setControl(TickBoxControl::new)
                    .setBinding((opts, value) -> SCLPClientMod.birthCaiDan(), opts -> true)
                    //.setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build())
            .build());
        }
        groups.add(OptionGroup.createBuilder()
        .setId(OptionIdentifier.create(SCLPClientMod.MOD_ID, "trans_mod_name_group"))
            .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
                .setId(ResourceLocation.fromNamespaceAndPath(SCLPClientMod.MOD_ID, "should_trans_mod_name"))
                .setName(Component.translatable("sclp.options.shoud_trans_mod_name"))
                .setTooltip(Component.translatable("sclp.options.shoud_trans_mod_name.tooltip"))
                .setControl(TickBoxControl::new)
                .setBinding((opts, value) -> {opts.shouldTransModName = value;rebuildEmbScreenUI();SCLPClientMod.caiDan();}, opts -> opts.shouldTransModName)
                //.setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build())
            .build());
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            groups.add(OptionGroup.createBuilder()
            .setId(OptionIdentifier.create(SCLPClientMod.MOD_ID, "sclp_support_group"))
                .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
                    .setId(ResourceLocation.fromNamespaceAndPath(SCLPClientMod.MOD_ID, "should_close_support"))
                    .setName(Component.translatable("sclp.options.close_support_page.name"))
                    .setTooltip(Component.translatable("sclp.options.close_support_page.tooltip"))
                    .setControl(TickBoxControl::new)
                    .setBinding((opts, value) -> {opts.shouldShowSupportPage = !value;rebuildEmbScreenUI();}, opts -> !opts.shouldShowSupportPage)
                    //.setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                    .build())
                .add(OptionImpl.createBuilder(ViaOpt.class, lsdcOpts)
                    .setId(ResourceLocation.fromNamespaceAndPath(SCLPClientMod.MOD_ID, "sclp_support"))
                    .setName(Component.translatable("sclp.options.support_project.name"))
                    .setTooltip(Component.translatable("sclp.options.support_project.tooltip"))
                    .setControl(opt -> new CyclingControl<>(opt, ViaOpt.class, new Component[] { Component.literal(I18N.trans("sclp.options.open_external_page_button"))}))
                    .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                    //.setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                    .build())
                .build());
        }
        
        return new OptionPage(OptionIdentifier.create(SCLPClientMod.MOD_ID, "sclp_settings"),Component.translatable("sclp.page"), ImmutableList.copyOf(groups));
    }

    private static void rebuildEmbScreenUI()
    {
        var embScreen = Minecraft.getInstance().screen;
        try
        {
            if(embScreen instanceof EmbeddiumVideoOptionsScreen)
            {
                ((EmbeddiumVideoOptionsScreen) embScreen).rebuildUI();
            }
        }
        catch (Exception e)
        {
            SCLPClientMod.LOGGER.error("[SCLP] Error when rebuild Embeddium Video Options Screen UI", e);
        }
    }
}


/*
      ____                                    ____ 
     /   /                                   /   /   
    /   /    ____________  ______  _____    /   /    ___ ___ 
   /   /___ /  _  /  _  / /     / /  _  \  /   /___ |  //  /
  /_______/ \____/\____/ /  /  /  \__   / /_______/  \    /
 ___________________________________/  /______________/  /
/___LoongLy Software 2026_______________________________/
 */
//LoongLy Software Update 2026/08/01
