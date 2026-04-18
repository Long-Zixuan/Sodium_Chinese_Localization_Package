package loongly.sclp.language;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
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
            if(fallbackLanguage.containsKey(key))
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

    static public boolean hadTrans(String key)
    {
        String languageCode = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
        Map<?, ?> language = I18NLanguage.getInstance().getLanguage(languageCode);
        if(language != null && language.containsKey(key))
        {
            return true;
        }
        return false;
    }
}
