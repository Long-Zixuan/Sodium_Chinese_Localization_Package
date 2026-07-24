package me.loongly.mods.sclp.common.client.options;

import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.option.OptionFlag;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionGroupBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionPageBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

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
                .setVersion("5.4.3");
        var page = createOptionsPage(configBuilder);
        page.addOptionGroup(createGeneralPage(configBuilder));
        if(sclpOpts.getShouldShowSupportPage())
        {
                page.addOptionGroup(createSupportPage(configBuilder));
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
                                        .setBinding(value -> {sclpOpts.setShoudTransModName(value); SCLPClientMod.caiDan();}, () -> sclpOpts.getShouldTransModName())
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
                                        .setScreenConsumer(screen -> Util.getPlatform().openUri("https://ifdian.net/a/loongly")));
        
        group.addOption(configBuilder.createBooleanOption(this.optionId("close_support_page"))//Builder(boolean.class, sclpOpts)
                .setName(Component.translatable("sclp.options.close_support_page.name"))
                .setTooltip(Component.translatable("sclp.options.close_support_page.tooltip"))
                .setBinding(value -> sclpOpts.setShouldShowSupportPage(!value), () -> !sclpOpts.getShouldShowSupportPage())
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .setStorageHandler(sclpOpts::save)
                .setDefaultValue(SCLPOptions.DEFAULT_SHOULD_SHOW_SCLP_SUPPORT_PAGE)
        );
        
        return group;
    }

    private Identifier optionId(String path) 
    {
        return Identifier.fromNamespaceAndPath(SCLPClientMod.MOD_ID, path);
    }
}
