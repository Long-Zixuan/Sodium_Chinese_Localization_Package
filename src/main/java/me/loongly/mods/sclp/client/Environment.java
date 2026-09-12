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
    public static final Class<?> textCompClazz_1_16;
    public static final Constructor<?> textCompClazz_1_16_Constructor;
    public static final Class<?> iTextCompClazz_1_16;
    public static final Constructor<?> cyclingControlClz_1_16_ConOCI;
    #else
    public static final Class<?> textCompClazz_1_19;
    public static final Method textCompClazz_1_19_Constructor;
    #endif
    public static final Class<?> optionPageClazz;
    public static final Constructor<?> optionPageConstructor;
    public static final Constructor<?> cyclingControlClzCon;

    static
    {    
        #if BEFORE_18_1
        Class<?> tempTextCompClazz_1_16 = null;
        Constructor<?> tempTextCompClazz_1_16_Constructor = null;
        Class<?> tempITextCompClazz_1_16 = null;
        Constructor<?> tempCyclingControlClz_1_16_ConOCI = null;
        
        try 
        {
            tempTextCompClazz_1_16 = Class.forName("net.minecraft.util.text.TranslationTextComponent");
        } 
        catch (ClassNotFoundException e) 
        {
            SCLPClientMod.logger().error("[SCLP] :",e);
            throw new RuntimeException("[SCLP]" + e.toString());
        }
        
        try 
        {
            tempTextCompClazz_1_16_Constructor = tempTextCompClazz_1_16.getConstructor(String.class, Object[].class);
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            SCLPClientMod.logger().error("[SCLP] :",e);
            throw new RuntimeException("[SCLP]" + e.toString());
        }
        try 
        {
            tempITextCompClazz_1_16 = Class.forName("net.minecraft.util.text.ITextComponent");
        } 
        catch (ClassNotFoundException e) 
        {
            SCLPClientMod.logger().error("[SCLP] :",e);
            throw new RuntimeException("[SCLP]" + e.toString());
        }
        
        #else
        Class<?> tempTextCompClazz_1_19 = null;
        Method tempTextCompClazz_1_19_Constructor = null;
        
        try
        {
            tempTextCompClazz_1_19 = Class.forName("net.minecraft.network.chat.Component");
        }
        catch (ClassNotFoundException e)
        {
            SCLPClientMod.logger().error("[SCLP] If you game version is 1.18,Please ignore this error:",e);
        }
        
        try
        {
            if(tempTextCompClazz_1_19 != null)
            {
                tempTextCompClazz_1_19_Constructor = tempTextCompClazz_1_19.getMethod("m_237115_",String.class);
            }
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            SCLPClientMod.logger().error("[SCLP] :",e);
        }
        
        textCompClazz_1_19 = tempTextCompClazz_1_19;
        textCompClazz_1_19_Constructor = tempTextCompClazz_1_19_Constructor;
        #endif
       
        Class<?> tempOptionPageClazz = null;
        Constructor<?> tempOptionPageConstructor = null;
        Constructor<?> tempCyclingControlClzCon = null;
        
        try
        {
            tempOptionPageClazz = Class.forName("me.jellysquid.mods.sodium.client.gui.options.OptionPage");
        }
        catch (ClassNotFoundException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);
        }

        try 
        {
            #if BEFORE_18_1
            if(tempOptionPageClazz != null) {
                tempOptionPageConstructor = tempOptionPageClazz.getConstructor(String.class, com.google.common.collect.ImmutableList.class);
            }
            #else
            if(tempOptionPageClazz != null && textCompClazz_1_19 != null) {
                tempOptionPageConstructor = tempOptionPageClazz.getConstructor(textCompClazz_1_19, com.google.common.collect.ImmutableList.class);
            }
            #endif
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            SCLPClientMod.logger().error("[SCLP] If you game version is 1.18,Please ignore this error :",e);
        }
        
        try
        {
            #if BEFORE_18_1
            tempCyclingControlClzCon = CyclingControl.class.getConstructor(
                Option.class,      
                Class.class,       
                String[].class     
            );
            #else
            if(textCompClazz_1_19 != null)
            {
                Class<?> textCompArrayClazz = textCompClazz_1_19.arrayType();
                tempCyclingControlClzCon = CyclingControl.class.getConstructor(
                    Option.class,      
                    Class.class,       
                    textCompArrayClazz     
                );
            }
            #endif
        }
        catch (NoSuchMethodException | IllegalArgumentException e)
        {
            SCLPClientMod.logger().error("[SCLP] If you are using rubiddium and the version is 0.2.13 please ignore this error:",e);
        }

        #if BEFORE_18_1
        if(tempCyclingControlClzCon == null && tempITextCompClazz_1_16 != null)
        {
            Class<?> iTextCmpClazzArr = tempITextCompClazz_1_16.arrayType();
            try 
            {
                tempCyclingControlClz_1_16_ConOCI = CyclingControl.class.getConstructor(
                    Option.class,      
                    Class.class,       
                    iTextCmpClazzArr
                );
            } 
            catch ( NoSuchMethodException | SecurityException e) 
            {
                SCLPClientMod.logger().error("[SCLP] :",e);
                throw new RuntimeException("[SCLP]" + e.toString());
            }
        }
        textCompClazz_1_16 = tempTextCompClazz_1_16;
        textCompClazz_1_16_Constructor = tempTextCompClazz_1_16_Constructor;
        iTextCompClazz_1_16 = tempITextCompClazz_1_16;
        cyclingControlClz_1_16_ConOCI = tempCyclingControlClz_1_16_ConOCI;
        #endif
        
        optionPageClazz = tempOptionPageClazz;
        optionPageConstructor = tempOptionPageConstructor;
        cyclingControlClzCon = tempCyclingControlClzCon;
    }
}
