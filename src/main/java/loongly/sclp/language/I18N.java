package loongly.sclp.language;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.MinecraftClient;

public class I18N
{
    public static String trans(String key, Object... args)
    {
        String languageCode = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
        Map<String, String> language = I18NLanguage.getInstance().getLanguage(languageCode);
        Map<String, String> fallbackLanguage = I18NLanguage.getInstance().getLanguage("en_us");
        String string = key;
        if(language != null && language.containsKey(key))
        {
            string = language.get(key);
        }
        else
        {
            if(I18n.hasTranslation(key))
            {
                return I18n.translate(key, args);
            }
            if(fallbackLanguage != null && fallbackLanguage.containsKey(key))
            {
                string = fallbackLanguage.get(key);
            }
        }
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
        String languageCode = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
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
