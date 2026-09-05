package me.loongly.mods.sclp.language;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.resource.language.I18n;

public class I18N
{
    public static String trans(String key, Object... args)
    {
        #if BEFORE_18_1
        String languageCode = oldVersionTrans("sclp.cur_languagecode");
        #else
        String languageCode = I18n.translate("sclp.cur_languagecode");
        #endif
        Map<String, String> language = I18NLanguage.getInstance().getLanguage(languageCode);
        String string = key;
        if(language != null && language.containsKey(key))
        {
            string = language.get(key);
        }
        else
        {
            #if BEFORE_18_1
            return oldVersionTrans(key, args);
            #else
            return I18n.translate(key, args);
            #endif
        }
        try 
        {
            return String.format(string, args);
        }
        catch (IllegalFormatException var4) 
        {
            #if BEFORE_18_1
            return "Format Error:" + string;
            #else
            return I18n.translate("sclp.format_error") + string;
            #endif
        }
    }

    #if BEFORE_18_1
    public static String oldVersionTrans(String key, Object... args)
    {
        Class<?> clazz;
        try 
        {
            clazz = Class.forName("net.minecraft.util.text.TranslationTextComponent");
        } 
        catch (ClassNotFoundException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return "error";
        }
        Constructor<?> constructor;
        try 
        {
            constructor = clazz.getConstructor(String.class, Object[].class);
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
                return "error";
        }
        Object translationTextComponent;
        try 
        {
            translationTextComponent = constructor.newInstance(key, args);
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
                return "error";
        }
        Method getStringMeth;
        try 
        {
            getStringMeth = clazz.getMethod("getString");
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return "error";
        }
        try 
        {
            String finalStr = (String) getStringMeth.invoke(translationTextComponent);
            return finalStr;
        } 
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return "error";
        }
    }
    #endif

    static public int hadTrans(String key)
    {
        #if BEFORE_18_1
        String languageCode = oldVersionTrans("sclp.cur_languagecode");
        #else
        String languageCode = I18n.translate("sclp.cur_languagecode");
        #endif
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
