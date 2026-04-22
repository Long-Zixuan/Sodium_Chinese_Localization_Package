package me.loongly.mods.sclp.language;

import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;

public class I18NLanguage 
{
    private static I18NLanguage instance_s = null;

    static
    {
        if(instance_s == null)
        {
            instance_s = new I18NLanguage();
        }
    }

    public static I18NLanguage getInstance()
    {
        if(instance_s == null)
        {
            System.err.println("[sclp]I18NLanguage used before init");
        }
        return instance_s;
    }

    public static void init()
    {
        if(instance_s == null)
        {
            System.out.println("[sclp]I18NLanguage init");
        }
        //由static代码段中的代码初始化instance_s
    }

    public Map<String, String> getLanguage(String lanCode)
    {
        if(LANGUAGES.containsKey(lanCode))
        {
            return Collections.unmodifiableMap(LANGUAGES.get(lanCode).toMap());
        }
        else
        {
            if(reloadLanguage(lanCode))
            {
                return Collections.unmodifiableMap(LANGUAGES.get(lanCode).toMap());
            }
        }
        return Collections.unmodifiableMap(LANGUAGES.get("en_us").toMap());
    }

    final HashMap<String,LangFile> LANGUAGES = new HashMap<String,LangFile>();

    private I18NLanguage()
    {
        String[] languages = new String[]{"en_us","zh_cn","zh_tw","zh_hk","ja_jp"};
        for(String languageCode : languages)
        {
            reloadLanguage(languageCode);
        }
    }

    boolean reloadLanguage(String languageCode)
    {
        LangFile langFile = new LangFile(languageCode);
        LANGUAGES.put(languageCode, langFile);
        if(langFile.toMap().size() > 0)
        {
            return true;
        }
        return false;
    }
}
