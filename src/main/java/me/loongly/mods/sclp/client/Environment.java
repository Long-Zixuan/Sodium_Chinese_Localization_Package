package me.loongly.mods.sclp.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.lang.String;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.Option;

public class Environment 
{
    #if BEFORE_18_1
    public static Class<?> textCompClazz_1_16 = null;
    public static Constructor<?> textCompClazz_1_16_Constructor = null;
    public static Class<?> iTextCompClazz_1_16 = null;
    public static Constructor<?> cyclingControlClz_1_16_ConOCI = null;
    #else
    public static Class<?> textCompClazz_1_19 = null;
    public static Method textCompClazz_1_19_Constructor = null;
    #endif
    public static Class<?> optionPageClazz = null;
    public static Constructor<?> optionPageConstructor = null;
    public static Constructor<?> cyclingControlClzCon = null;

    static
    {    
        #if BEFORE_18_1
        try 
        {
            textCompClazz_1_16 = Class.forName("net.minecraft.util.text.TranslationTextComponent");//这玩意继承了ITextComponent
        } 
        catch (ClassNotFoundException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            throw new RuntimeException("[SCLP]" + e.toString());
        }
        
        try 
        {
            textCompClazz_1_16_Constructor = textCompClazz_1_16.getConstructor(String.class, Object[].class);
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            throw new RuntimeException("[SCLP]" + e.toString());
        }
        try 
        {
            iTextCompClazz_1_16 = Class.forName("net.minecraft.util.text.ITextComponent");
        } 
        catch (ClassNotFoundException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            throw new RuntimeException("[SCLP]" + e.toString());
        }
        #else
        try
        {
            textCompClazz_1_19 = Class.forName("net.minecraft.network.chat.Component");
        }
        catch (ClassNotFoundException e)
        {
            SCLPClientMod.logger().error("[SCLP] If you game version is 1.18,Please ignore this error:",e);//warn
        }
        
        try
        {
            if(textCompClazz_1_19 != null)
            {
                textCompClazz_1_19_Constructor = textCompClazz_1_19.getMethod("m_237115_",String.class);//m_237115_是translatable的方法名
            }
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
        }
        #endif
       
        try
        {
            optionPageClazz = Class.forName("me.jellysquid.mods.sodium.client.gui.options.OptionPage");
        }
        catch (ClassNotFoundException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
        }

        try 
        {
            #if BEFORE_18_1
            optionPageConstructor = optionPageClazz.getConstructor(String.class, com.google.common.collect.ImmutableList.class);
            #else
            optionPageConstructor = optionPageClazz.getConstructor(textCompClazz_1_19, com.google.common.collect.ImmutableList.class);
            #endif
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] If you game version is 1.18,Please ignore this error :",e);//warn
        }
        try
        {
            #if BEFORE_18_1
            cyclingControlClzCon = CyclingControl.class.getConstructor(
                Option.class,      // 对应Option<T>
                Class.class,       // 对应Class<T>
                String[].class     // 对应String[]
            );
            #else
            if(textCompClazz_1_19 != null)
            {
                Class<?> textCompArrayClazz = textCompClazz_1_19.arrayType();
                cyclingControlClzCon = CyclingControl.class.getConstructor(
                    Option.class,      // 对应Option<T>
                    Class.class,       // 对应Class<T>
                    textCompArrayClazz     // 对应Component[]
                );
            }
            #endif
        }
        catch (NoSuchMethodException | IllegalArgumentException e)
        {
            SCLPClientMod.logger().error("[SCLP] If you are using rubiddium and the version is 0.2.13 please ignore this error:",e);//warn
        }

        #if BEFORE_18_1
        if(cyclingControlClzCon == null)
        {
            Class<?> iTextCmpClazzArr = iTextCompClazz_1_16.arrayType();
            try 
            {
                cyclingControlClz_1_16_ConOCI = CyclingControl.class.getConstructor(
                    Option.class,      // 对应Option<T>
                    Class.class,       // 对应Class<T>
                    iTextCmpClazzArr
                );
            } 
            catch ( NoSuchMethodException | SecurityException e) 
            {
                // TODO Auto-generated catch block
                SCLPClientMod.logger().error("[SCLP] :",e);//warn
                throw new RuntimeException("[SCLP]" + e.toString());
            }
        }
        #endif
    }
}
