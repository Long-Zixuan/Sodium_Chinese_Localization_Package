package me.loongly.mods.sclp.language;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.loongly.mods.sclp.client.SCLPClientMod;

import net.minecraft.client.resource.language.I18n;

public class I18N
{
    #if BEFORE_18_1
    static Class<?> textCompClazz_1_16;
    static Constructor<?> textCompClazz_1_16_Con;
    static Method textCompClazz_1_16_GetStringMeth;
    static
    {
        try 
        {
            textCompClazz_1_16 = Class.forName("net.minecraft.util.text.TranslationTextComponent");
        } 
        catch (ClassNotFoundException e) 
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
        }
        
        try 
        {
            if(textCompClazz_1_16 != null)
            {
                textCompClazz_1_16_Con = textCompClazz_1_16.getConstructor(String.class, Object[].class);
            }
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
        }
        
        try 
        {
            if(textCompClazz_1_16 != null)
            {
                textCompClazz_1_16_GetStringMeth = textCompClazz_1_16.getMethod("getString");
            }
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
        }
    }
    #endif

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
        if(textCompClazz_1_16 == null || textCompClazz_1_16_Con == null || textCompClazz_1_16_GetStringMeth == null)
        {
            return "error:" + key;
        }
        Object translationTextComponent;
        try 
        {
            translationTextComponent = textCompClazz_1_16_Con.newInstance(key, args);
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return "error:" + key;
        }
        
        try 
        {
            String finalStr = (String) textCompClazz_1_16_GetStringMeth.invoke(translationTextComponent);
            return finalStr;
        } 
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return "error:" + key;
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
