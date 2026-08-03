package me.loongly.mods.sclp.neoforge.services;

import net.neoforged.fml.ModList;
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

    @Override
    public String curVersion()
    {
        var modList = ModList.get();
        var sclpModCont = modList.getModContainerById("sclp").orElse(null);
        if (sclpModCont != null)
        {
            return sclpModCont.getModInfo().getVersion().toString();
        }
        return "Unknown";
    }
}
