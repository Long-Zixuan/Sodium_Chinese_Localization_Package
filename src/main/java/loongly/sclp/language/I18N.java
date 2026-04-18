package loongly.sclp.language;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;

import net.minecraft.client.MinecraftClient;

public class I18N
{
    public static String trans(String key, Object... args)
    {
        String languageCode = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
        Map<String, String> language = I18NLanguage.getInstance().getLanguage(languageCode);
        Map<String, String> fallbackLanguage = I18NLanguage.getInstance().getLanguage("en_us");
        String string = key;
        key = key.replace("\n", "<br>");//lang文件中换行统一用<br>
        if(language != null && language.containsKey(key))
        {
            string = language.get(key);
        }
        else
        {
            if(fallbackLanguage.containsKey(key))
            {
                string = fallbackLanguage.get(key);
            }
        }
        string = string.replace("<br>", "\n");//lang文件中换行统一用<br>
        try 
        {
            return String.format(string, args);
        }
        catch (IllegalFormatException var4) 
        {
            return "Format error: " + string;
        }
    }

    static public boolean hadTrans(String key)
    {
        String languageCode = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
        Map<?, ?> language = I18NLanguage.getInstance().getLanguage(languageCode);
        key = key.replace("\n", "<br>");//键值的换行统一用<br>代替
        if(language != null && language.containsKey(key))
        {
            return true;
        }
        return false;
    }
}
