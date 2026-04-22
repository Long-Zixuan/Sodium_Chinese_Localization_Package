package me.loongly.mods.sclp.neoforge.services;

import net.neoforged.fml.loading.FMLPaths;

import me.loongly.mods.sclp.common.services.IPlatformHelper;

import java.nio.file.Path;
import java.util.Locale;

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
        Locale locale = Locale.getDefault();
        return locale.getLanguage();
    }
}
