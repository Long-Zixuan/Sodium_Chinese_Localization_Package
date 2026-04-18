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

    private static I18NLanguage instance;

    public static I18NLanguage getInstance()
    {
        if(instance == null)
        {
            instance = new I18NLanguage();
        }
        return instance;
    }

    public synchronized Map<String, String> getLanguage(String lanCode)
    {
        if(LANGUAGES.containsKey(lanCode))
        {
            return Collections.unmodifiableMap(LANGUAGES.get(lanCode));
        }
        else
        {
            if(loadLanguage(lanCode))
            {
                return Collections.unmodifiableMap(LANGUAGES.get(lanCode));
            }
        }
        return Collections.unmodifiableMap(LANGUAGES.get("en_us"));
    }

    final HashMap<String,HashMap<String,String>> LANGUAGES = new HashMap<String,HashMap<String,String>>();

    private I18NLanguage()
    {
        String languageCode = MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode();
        String langFilePath = LangFile.getLangFilePath(languageCode);
        try (InputStream inputStream = I18NLanguage.class.getResourceAsStream(langFilePath)) 
        {
            if (inputStream == null) 
            {

            }
            else
            {
                LANGUAGES.put(languageCode, LangFile.parseLangFile(inputStream));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try (InputStream inputStream = I18NLanguage.class.getResourceAsStream(LangFile.getLangFilePath("en_us"))) 
        {
            if (inputStream == null) 
            {
                
                System.err.println("en_us.lang file not found");
            }
            else
            {
                LANGUAGES.put("en_us", LangFile.parseLangFile(inputStream));
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    boolean loadLanguage(String languageCode)
    {
        String langFilePath = LangFile.getLangFilePath(languageCode);
        try (InputStream inputStream = I18NLanguage.class.getResourceAsStream(langFilePath)) 
        {
            if (inputStream == null) 
            {
                
                System.out.println(languageCode + ".lang file not found");
                return false;
            }
            else
            {
                LANGUAGES.put(languageCode, LangFile.parseLangFile(inputStream));
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
