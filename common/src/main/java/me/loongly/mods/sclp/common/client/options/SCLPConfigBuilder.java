package me.loongly.mods.sclp.common.client.options;

import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.option.OptionFlag;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionGroupBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionPageBuilder;
import net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.lang.reflect.Method;
import java.time.LocalDate;

import com.mojang.authlib.minecraft.client.MinecraftClient;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import me.loongly.mods.sclp.common.services.IPlatformHelper;


public class SCLPConfigBuilder implements ConfigEntryPoint 
{
    private static final SCLPOptions sclpOpts = SCLPClientMod.options();

    @Override
    public void registerConfigLate(ConfigBuilder configBuilder) 
    {
        String version = IPlatformHelper.INSTANCE.curVersion().split("-")[0];
        var modOpts = configBuilder.registerOwnModOptions()
                .setColorTheme(configBuilder.createColorTheme()
                        .setBaseThemeRGB(0xed65ff)
                )
                .setIcon(Identifier.parse("sclp:texture/icon.png"))
                .setVersion(version);
        var page = createOptionsPage(configBuilder);
        page.addOptionGroup(createGeneralPage(configBuilder));
        if(sclpOpts.getShouldShowSupportPage())
        {
                page.addOptionGroup(createSupportPage(configBuilder));
        }
        var today = LocalDate.now();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(isMyBirthday(month, day))
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
                                        .setBinding(value -> {
                                            sclpOpts.setShoudTransModName(value); 
                                            rebuildSodiumScr(); 
                                            SCLPClientMod.caiDan();
                                        }, () -> sclpOpts.getShouldTransModName())
                                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                                        .setStorageHandler(sclpOpts::save)
                                        .setDefaultValue(SCLPOptions.DEFAULT_SHOULD_TRANS_MOD_NAME)
                                );
    }

    private OptionGroupBuilder createSupportPage(ConfigBuilder configBuilder)
    {
        var group = configBuilder.createOptionGroup()
                .setName(Component.translatable("sclp.options.group.support"));
        group.addOption(configBuilder.createExternalButtonOption(this.optionId("support_project"))
                .setName(Component.translatable("sclp.options.support_project.name"))
                .setTooltip(Component.translatable("sclp.options.support_project.tooltip"))
                .setScreenConsumer(SCLPClientMod::openSupportWeb));
        
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

    private OptionGroupBuilder createBirthPage(ConfigBuilder configBuilder)
    {
        var today = LocalDate.now();
        int year = today.getYear();
        var group = configBuilder.createOptionGroup()
                .setName(Component.literal("🎂"))
                .addOption(configBuilder.createExternalButtonOption(this.optionId("birth_caidan"))
                        .setName(Component.literal("🎂:" + (year - 2004)))
                        .setTooltip(Component.literal("🎂:" + (year - 2004)))
                        .setScreenConsumer(SCLPClientMod::birthCaiDan));
        return group;
    }

    static boolean isMyBirthday(int month, int day)
    {
        return month == 4 && day == 4;
    }

    private Identifier optionId(String path) 
    {
        return Identifier.fromNamespaceAndPath(SCLPClientMod.MOD_ID, path);
    }

    static boolean canGetScreen()
    {
        var clazz = Minecraft.class;
        try 
        {
            clazz.getDeclaredField("screen");
            return true;
        } 
        catch (NoSuchFieldException e) 
        {
            return false;
        }
    }

    static void rebuildSodiumScr()//26.2 MC的API改了，故不支持该函数
    {
        if(canGetScreen())
        {
            try
            {
                var curScreen = Minecraft.getInstance().screen;
                if(curScreen instanceof VideoSettingsScreen)
                {
                    Class<?> clazz = VideoSettingsScreen.class;
                    Method method = clazz.getDeclaredMethod("rebuild");
                    method.setAccessible(true);
                    method.invoke(curScreen);
                }
            }
            catch(Exception e)
            {
                SCLPClientMod.logger().error("[SCLP] close Sodium Screen Error:", e);
            }
        }
    }

    static void closeSodiumScreen()//26.2 MC的API改了，故不支持该函数
    {
        if(canGetScreen())
        {
            try
            {
                var curScreen = Minecraft.getInstance().screen;
                if(curScreen instanceof VideoSettingsScreen)
                {
                    ((VideoSettingsScreen)curScreen).onClose();
                }
            }
            catch(Exception e)
            {
                SCLPClientMod.logger().error("[SCLP] close Sodium Screen Error:", e);
            }
        }
    }
}
