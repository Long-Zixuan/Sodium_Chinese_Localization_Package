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

import net.minecraft.client.resource.language.I18n;

import loongly.sclp.client.gui.options.storage.SCLPOptionsStorage;
import loongly.sclp.language.I18N;
import loongly.sclp.client.SclpClientMod;

import java.util.ArrayList;
import java.util.List;
public class SCLPGameOptionPages
{

    private static final SCLPOptionsStorage sclpOpts = new SCLPOptionsStorage();

    public static OptionPage sclpPage()
    {
        List<OptionGroup> groups = new ArrayList<>();
        buildSclpPages(groups);
        return new OptionPage(I18N.trans("sclp.page_name"), ImmutableList.copyOf(groups));
    }

    static void buildSclpPages(List<OptionGroup> groups)
    {
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sclpOpts)
                        .setName(I18N.trans("sclp.is_enable_sclp"))
                        .setTooltip(I18N.trans("sclp.is_enable_sclp_tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> opts.isEnableSclp = value, opts -> opts.isEnableSclp)
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
                        .setBinding((opts, value) -> opts.isDisableSclpNoInternetWarn = value, opts -> opts.isDisableSclpNoInternetWarn)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
        
    }
}
