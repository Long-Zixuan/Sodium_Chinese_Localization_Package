package me.loongly.mods.sclp.common.client.options;

import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.option.OptionFlag;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import me.loongly.mods.sclp.common.client.SCLPClientMod;


public class SCLPConfigBuilder implements ConfigEntryPoint 
{
    private static final SCLPOptions sclpOpts = SCLPClientMod.options();
    private static final String MOD_ID = "sclp";

    @Override
    public void registerConfigLate(ConfigBuilder configBuilder) 
    {
        configBuilder.registerOwnModOptions()
                .setColorTheme(configBuilder.createColorTheme()
                        .setBaseThemeRGB(0xed65ff)
                )
                .setIcon(Identifier.parse("sclp:texture/icon.png"))
                .setVersion("5.4.2.1")
                .addPage(configBuilder.createOptionPage()
                        .setName(Component.translatable("sclp.pages.sclp_page.name"))
                        .addOptionGroup(configBuilder.createOptionGroup()
                                .setName(Component.translatable("stat.generalButton"))
                                .addOption(configBuilder.createBooleanOption(Identifier.parse("sclp:should_trans_mod_name"))//Builder(boolean.class, sclpOpts)
                                        .setName(Component.translatable("sclp.options.should_trans_mod_name.name"))
                                        .setTooltip(Component.translatable("sclp.options.should_trans_mod_name.tooltip"))
                                        .setBinding(value -> {sclpOpts.setShoudTransModName(value); SCLPClientMod.caiDan();}, () -> sclpOpts.getShouldTransModName())
                                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                                        .setStorageHandler(sclpOpts::save)
                                        .setDefaultValue(SCLPOptions.DEFAULT_SHOULD_TRANS_MOD_NAME)
                                )
                        )
                        .addOptionGroup(configBuilder.createOptionGroup()
                                .setName(Component.translatable("sclp.options.group.support"))
                                .addOption(configBuilder.createExternalButtonOption(this.optionId("support_project"))
                                        .setName(Component.translatable("sclp.options.support_project.name"))
                                        .setTooltip(Component.translatable("sclp.options.support_project.tooltip"))
                                        .setScreenConsumer(screen -> Util.getPlatform().openUri("https://www.loongly.me/html/support_me_old.html"))))
                );
    }

    private Identifier optionId(String path) 
    {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
