package me.loongly.mods.sclp.language;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;

public class I18N
{
    public static String trans(String key, Object... args)
    {
        String languageCode = MinecraftClient.getInstance().getLanguageManager().getLanguage();
        Map<String, String> language = I18NLanguage.getInstance().getLanguage(languageCode);
        String string = key;
        //key = key.replace("\n", "<br>");//lang文件中换行统一用<br>
        if(language != null && language.containsKey(key))
        {
            string = language.get(key);
        }
        else
        {
            return I18n.translate(key, args);
        }
        //string = string.replace("<br>", "\n");//lang文件中换行统一用<br>
        try 
        {
            return String.format(string, args);
        }
        catch (IllegalFormatException var4) 
        {
            return "Format error: " + string;
        }
    }

    static public int hadTrans(String key)
    {
        String languageCode = MinecraftClient.getInstance().getLanguageManager().getLanguage();
        Map<?, ?> language = I18NLanguage.getInstance().getLanguage(languageCode);
        Map<?, ?> fallbackLanguage = I18NLanguage.getInstance().getLanguage("en_us");
        //key = key.replace("\n", "<br>");//键值的换行统一用<br>代替
        if(language != null && language.containsKey(key))
        {
            return 2;
        }
        if(fallbackLanguage != null && fallbackLanguage.containsKey(key))
        {
            return 1;
        }
        return 0;
    }
}
