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
import me.loongly.mods.sclp.language.I18N;
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
    private BooleanValue isTransModName_;
    private File file_;

    private final ForgeConfigSpec SPECS;

    public SCLPGameOptions(File file)
    {
        var BUILDER = new ForgeConfigSpec.Builder();

        // sclp ->
        BUILDER.push("sclp");

        // sclp -> settings ->
        BUILDER.push("settings");
        isTransModName_ = BUILDER
                .comment(I18N.trans("sclp.options.trans_mod_name"))
                .define("isTransModName", true);
       
        BUILDER.pop();

        file_ = file;

        SPECS = BUILDER.build();

        final var configData = CommentedFileConfig.builder(file).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        SPECS.setConfig(configData);
    }

    public boolean isLoaded() 
    {
        return SPECS.isLoaded();
    }

    public static SCLPGameOptions load(File file)
    {
        SCLPGameOptions config;
        config = new SCLPGameOptions(file);
        config.writeChanges();

        return config;
    }

    public void writeChanges() 
    {
        SPECS.save();
    }

    public void setIsTransModNameVal(boolean isTransModName) 
    {
        isTransModName_.set(isTransModName); 
    }

    public boolean getIsTransModNameVal()
    {
        return isTransModName_.get();
    }

}
