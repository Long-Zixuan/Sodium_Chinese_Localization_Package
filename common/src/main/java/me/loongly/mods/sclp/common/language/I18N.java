package me.loongly.mods.sclp.common.language;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;

import me.loongly.mods.sclp.common.services.IPlatformHelper;
import net.minecraft.client.resources.language.I18n;

public class I18N
{
    public static String trans(String key, Object... args)
    {
        String languageCode = I18n.get("sclp.cur_languagecode");
        Map<String, String> language = I18NLanguage.getInstance().getLanguage(languageCode);
        String string = key;
        if(language != null && language.containsKey(key))
        {
            string = language.get(key);
        }
        else
        {
            return I18n.get(key, args);
        }
        try 
        {
            return String.format(string, args);
        }
        catch (IllegalFormatException var4) 
        {
            return I18n.get("sclp.format_error") + string;
        }
    }

    static public int hadTrans(String key)
    {
        String languageCode = I18n.get("sclp.cur_languagecode");
        Map<?, ?> language = I18NLanguage.getInstance().getLanguage(languageCode);
        Map<?, ?> fallbackLanguage = I18NLanguage.getInstance().getLanguage("en_us");
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
