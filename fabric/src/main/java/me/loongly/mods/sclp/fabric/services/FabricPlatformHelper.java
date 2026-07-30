package me.loongly.mods.sclp.fabric.services;

import net.fabricmc.loader.api.FabricLoader;

import me.loongly.mods.sclp.common.services.IPlatformHelper;

import java.nio.file.Path;


public class FabricPlatformHelper implements IPlatformHelper 
{

    @Override
    public Path getConfigDirectory() 
    {
        return FabricLoader.getInstance().getConfigDir();
    }
}
