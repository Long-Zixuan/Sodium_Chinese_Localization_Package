package me.loongly.mods.sclp.neoforge.services;

import net.neoforged.fml.loading.FMLPaths;

import me.loongly.mods.sclp.common.services.IPlatformHelper;

import java.nio.file.Path;


public class NeoForgePlatformHelper implements IPlatformHelper 
{

    @Override
    public Path getConfigDirectory() 
    {
        return FMLPaths.CONFIGDIR.get();
    }
}
