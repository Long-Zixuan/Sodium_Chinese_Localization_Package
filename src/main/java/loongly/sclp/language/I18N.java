package loongly.sclp.language;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;

import loongly.sclp.client.SclpClientMod;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.MinecraftClient;

public class I18N
{
    public static String trans(String key, Object... args)
    {
        if(!SclpClientMod.options().isEnableSclp)
        {
            if(!key.startsWith("sclp") || key.equals("sclp.performance_impact"))
            {
                if(key.equals("sclp.performance_impact"))
                {
                    return "Performance Impact:";
                }
                return String.format(key, args);
            }
        }
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
        if(args.length == 0)
        {
            return string; //防止换行出现Format Error，比较大部分换行不会有格式化字符串
        }
        try 
        {
            return String.format(string, args);
        }
        catch (IllegalFormatException var4) 
        {
            return I18n.translate("sclp.format_error") + string; //为什么不用I18N.trans呢，因为万一格式一直错误就无限递归了
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
