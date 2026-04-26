package me.loongly.mods.sclp.client.gui;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import me.loongly.mods.sclp.client.SCLPClientMod;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class SCLPGameOptions 
{
    public static BooleanValue isTransModName;
    private File file;

    public static final ForgeConfigSpec SPECS;

    static 
    {
        var BUILDER = new ForgeConfigSpec.Builder();

        // sclp ->
        BUILDER.push("sclp");

        // sclp -> settings ->
        BUILDER.push("settings");
        isTransModName = BUILDER
                .comment("是否翻译模组名")
                .define("isTransModName", true);
       
        BUILDER.pop();

        SPECS = BUILDER.build();
    }

    public static boolean isLoaded() 
    {
        return SPECS.isLoaded();
    }

    public static SCLPGameOptions load(File file)
    {
        SCLPGameOptions config;

        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (Exception e) 
            {
                SCLPClientMod.LOGGER.error("Could not create config file!", e);
            }
        }

        if (file.exists()) 
        {
            try 
            {
                final var configData = CommentedFileConfig.builder(file).sync().autosave().writingMode(WritingMode.REPLACE).build();
                configData.load();
                SPECS.setConfig(configData);
                isTransModName.set(SPECS.get("sclp.settings.isTransModName"));
            } 
            catch (Exception e) 
            {
                SCLPClientMod.LOGGER.error("Could not parse config, falling back to defaults!", e);
            }
        }
        config = new SCLPGameOptions();
        config.file = file;
        config.writeChanges();

        return config;
    }

    public void writeChanges() 
    {
        SPECS.save();
    }

    public void setIsTransModNameVal(boolean isTransModName_) 
    {
        isTransModName.set(isTransModName_); 
    }

    public boolean getIsTransModNameVal()
    {
        return isTransModName.get();
    }

}
