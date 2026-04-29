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
import me.loongly.mods.sclp.language.I18NLanguage;
import me.loongly.mods.sclp.language.LangFile;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

import org.lwjgl.glfw.GLFW;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SCLPGameOptions 
{
    private File file_;
    private Map<String, String> optionMap_;

    public SCLPGameOptions(File file)
    {
        file_ = file;
        if(file.exists())
        {
            try
            {   
                InputStream inputStream = new FileInputStream(file);
                optionMap_ = parseOptionFile(inputStream);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                file.createNewFile();
                optionMap_ = new HashMap<>();
                optionMap_.put("isTranModName","true");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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
        StringBuilder builder = new StringBuilder();
        builder.append("# LoongLy Software \n# This is a configuration file for Sodium Chinese Localization Pack.\n");
        for (Map.Entry<String, String> entry : optionMap_.entrySet()) 
        {
            builder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_))) 
        {
            writer.write(builder.toString());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void setIsTransModNameVal(boolean isTransModName) 
    {
        if(isTransModName)
        {
            optionMap_.put("isTranModName","true");
        }
        else
        {
            optionMap_.put("isTranModName","false");
        }
    }

    public boolean getIsTransModNameVal()
    {
        return optionMap_.get("isTranModName").equals("true");
    }

    public static HashMap<String, String> parseOptionFile(InputStream inputStream) 
    {
        HashMap<String, String> map = new HashMap<>();
        if (inputStream == null) 
        {
            throw new IllegalArgumentException("InputStream cannot be null");
        }

        try (java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(inputStream, java.nio.charset.StandardCharsets.UTF_8))) 
        {
            
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) 
            {
                lineNumber++;
                // 去除首尾空白
                line = line.trim();
                
                // 跳过空行和注释行
                if (line.isEmpty() || line.startsWith("#")) 
                {
                    continue;
                }

                // 查找第一个 ':' 的位置
                int separatorIndex = line.indexOf(':');
                if (separatorIndex == -1) 
                {
                    System.out.println("Invalid line: " + line + " in lang file, line number: " + lineNumber);
                    // 如果没有 ':'，则该行格式不正确，跳过
                    continue;
                }

                // 提取 Key 和 Value
                String key = line.substring(0, separatorIndex).trim();
                String value = line.substring(separatorIndex + 1);
                value = value.trim();

                if (!key.isEmpty()) 
                {
                    map.put(key, value);
                }
            }
        }
        catch (Exception e) 
        {
            System.err.println("Error parsing lang file stream: " + e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(inputStream != null)
                {
                    inputStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return map;
    }

}
