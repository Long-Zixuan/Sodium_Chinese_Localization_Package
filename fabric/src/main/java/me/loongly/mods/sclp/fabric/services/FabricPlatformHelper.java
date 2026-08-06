package me.loongly.mods.sclp.fabric.services;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import me.loongly.mods.sclp.common.services.IPlatformHelper;

import java.nio.file.Path;


public class FabricPlatformHelper implements IPlatformHelper 
{

    @Override
    public Path getConfigDirectory() 
    {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public String curVersion()
    {
        return FabricLoader.getInstance().getModContainer("sclp").get().getMetadata().getVersion().getFriendlyString();
    }

    @Override
    public boolean isNewSodium()
    {
        var modList = FabricLoader.getInstance().getModContainer("sodium");
        if(modList.isPresent())
        {
            var mod = modList.get();
            var version = mod.getMetadata().getVersion();
            try
            {
                return version.compareTo(Version.parse("0.8.0")) >= 0; // >= 0.8.0
            }
            catch (VersionParsingException e)
            {
                return false;
            }
        }
        else
        {
            return false;
        }
        
    }
}
