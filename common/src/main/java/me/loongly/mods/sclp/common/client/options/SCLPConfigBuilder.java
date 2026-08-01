package me.loongly.mods.sclp.common.client.options;

import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.option.OptionFlag;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionGroupBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionPageBuilder;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlValueFormatterImpls;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.time.LocalDate;

import me.loongly.mods.sclp.common.client.SCLPClientMod;


public class SCLPConfigBuilder implements ConfigEntryPoint 
{
    private static final SCLPOptions sclpOpts = SCLPClientMod.options();

    @Override
    public void registerConfigLate(ConfigBuilder configBuilder) 
    {
        var modOpts = configBuilder.registerOwnModOptions()
                .setColorTheme(configBuilder.createColorTheme()
                        .setBaseThemeRGB(0xed65ff)
                )
                .setIcon(Identifier.parse("sclp:texture/icon.png"))
                .setVersion("4.4.2");
        var page = createOptionsPage(configBuilder);
        page.addOptionGroup(createGeneralPage(configBuilder));
        if(sclpOpts.shouldShowSCLPSupportPage)
        {
                page.addOptionGroup(createSupportPage(configBuilder));
        }

        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(isBirthday(month, day))
        {
            page.addOptionGroup(createBirthPage(configBuilder));
        }

        modOpts.addPage(page);
    }

    private OptionPageBuilder createOptionsPage(ConfigBuilder configBuilder) 
    {
        return configBuilder.createOptionPage()
                        .setName(Component.translatable("sclp.pages.sclp_page.name"));
    }

    private OptionGroupBuilder createGeneralPage(ConfigBuilder configBuilder)
    {
        return configBuilder.createOptionGroup()
                                .setName(Component.translatable("stat.generalButton"))
                                .addOption(configBuilder.createBooleanOption(this.optionId("should_trans_mod_name"))//Builder(boolean.class, sclpOpts)
                                        .setName(Component.translatable("sclp.options.should_trans_mod_name.name"))
                                        .setTooltip(Component.translatable("sclp.options.should_trans_mod_name.tooltip"))
                                        .setBinding(value -> {sclpOpts.shouldTransModName = value; SCLPClientMod.caiDan();}, () -> sclpOpts.shouldTransModName)
                                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                                        .setStorageHandler(sclpOpts::save)
                                        .setDefaultValue(SCLPOptions.DEFAULT_SHOULD_TRANS_MOD_NAME)
                                );
    }

    private OptionGroupBuilder createSupportPage(ConfigBuilder configBuilder)
    {
        var group = configBuilder.createOptionGroup()
                                .setName(Component.translatable("sclp.options.group.support"))
                                .addOption(configBuilder.createExternalButtonOption(this.optionId("support_project"))
                                        .setName(Component.translatable("sclp.options.support_project.name"))
                                        .setTooltip(Component.translatable("sclp.options.support_project.tooltip"))
                                        .setScreenConsumer(SCLPClientMod::openSupportWeb));
        
        group.addOption(configBuilder.createBooleanOption(this.optionId("close_support_page"))//Builder(boolean.class, sclpOpts)
                .setName(Component.translatable("sclp.options.close_support_page.name"))
                .setTooltip(Component.translatable("sclp.options.close_support_page.tooltip"))
                .setBinding(value -> sclpOpts.shouldShowSCLPSupportPage = !value, () -> !sclpOpts.shouldShowSCLPSupportPage)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .setStorageHandler(sclpOpts::save)
                .setDefaultValue(SCLPOptions.DEFAULT_SHOULD_SHOW_SCLP_SUPPORT_PAGE)
        );
        
        return group;
    }

    private OptionGroupBuilder createBirthPage(ConfigBuilder configBuilder)
    {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        var group = configBuilder.createOptionGroup()
                                .setName(Component.literal("🎂"))
                                .addOption(configBuilder.createExternalButtonOption(this.optionId("birth_caidan"))
                                        .setName(Component.literal("🎂:" + (year - 2004)))
                                        .setTooltip(Component.literal("🎂:" + (year - 2004)))
                                        .setScreenConsumer(SCLPClientMod::birthCaiDan));
        return group;
    }

    static boolean isBirthday(int month, int day)
    {
        return month == 4 && day == 4;
    }

    private Identifier optionId(String path) 
    {
        return Identifier.fromNamespaceAndPath(SCLPClientMod.MOD_ID, path);
    }
}
