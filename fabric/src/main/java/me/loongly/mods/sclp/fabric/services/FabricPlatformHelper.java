package me.loongly.mods.sclp.fabric.services;

import net.fabricmc.loader.api.FabricLoader;

import me.loongly.mods.sclp.common.services.IPlatformHelper;

import java.nio.file.Path;
import net.minecraft.client.Minecraft;


public class FabricPlatformHelper implements IPlatformHelper 
{

    @Override
    public Path getConfigDirectory() 
    {
        return FabricLoader.getInstance().getConfigDir();
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
