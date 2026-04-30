package me.loongly.mods.sclp.neoforge.services;

import net.neoforged.fml.loading.FMLPaths;

import me.loongly.mods.sclp.common.services.IPlatformHelper;

import java.nio.file.Path;
import java.util.Locale;
import net.minecraft.client.Minecraft;
//import net.neoforged.fml.i18n.I18nManager;

public class NeoForgePlatformHelper implements IPlatformHelper 
{

    @Override
    public Path getConfigDirectory() 
    {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public String getLanguageCode()
    {
        if(Minecraft.getInstance().getLanguageManager() != null)
        {
            return Minecraft.getInstance().getLanguageManager().getSelected();
        }
        if(Minecraft.getInstance().options != null)
        {
            return Minecraft.getInstance().options.languageCode;
        }
        return "en_us";
    }
}
