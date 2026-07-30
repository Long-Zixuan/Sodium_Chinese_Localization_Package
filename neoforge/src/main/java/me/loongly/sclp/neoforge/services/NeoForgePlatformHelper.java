package me.loongly.sclp.neoforge.services;

import net.neoforged.fml.loading.FMLPaths;

import me.loongly.sclp.common.services.IPlatformHelper;

import java.nio.file.Path;


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
        return "zh_cn";
    }

}
