package loongly.sclp.language;

import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;

public class I18NLanguage 
{
    static public String NO_FABRIC_API_WARM_ZH_CN = Formatting.BOLD +"请安装Fabric API，否则"+Formatting.UNDERLINE+"汉化包将无法正常工作！";
    static public String NO_FABRIC_API_WARM_ZH_TW = Formatting.BOLD +"請安裝Fabric API，否則"+Formatting.UNDERLINE+"漢化包將無法正常工作！";
    //static public String NO_FABRIC_API_WARM_EN_US = Formatting.BOLD +"Please install the Fabric API, otherwise SCLP will "+Formatting.UNDERLINE+"not work properly!";

    static public String NO_FABRIC_API_WARM_1_EN_US = Formatting.BOLD +"Please install the Fabric API, ";
    static public String NO_FABRIC_API_WARM_2_EN_US = Formatting.BOLD +"otherwise SCLP will"+Formatting.UNDERLINE+" not work properly!";
    static public String NO_FABRIC_API_WARM_JP_JP = Formatting.BOLD + "Fabric APIをインストールしてください";

    private static I18NLanguage instance_s;

    public static I18NLanguage getInstance()
    {
        if(instance_s == null)
        {
            instance_s = new I18NLanguage();
        }
        return instance_s;
    }

    public synchronized Map<String, String> getLanguage(String lanCode)
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
        String languageCode = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
        reloadLanguage(languageCode);
        reloadLanguage("en_us");
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
