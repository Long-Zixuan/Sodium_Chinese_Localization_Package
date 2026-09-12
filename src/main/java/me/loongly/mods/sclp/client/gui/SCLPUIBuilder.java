package me.loongly.mods.sclp.client.gui;

import com.google.common.collect.ImmutableList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.lang.String;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.language.I18N;
import net.minecraft.client.MinecraftClient;
import me.loongly.mods.sclp.client.Environment;
#if BEFORE_18_1
#else
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
#endif

public class SCLPUIBuilder
{
    public static void setImplBuilderName(OptionImpl.Builder builder, String name)
    {
        #if BEFORE_18_1
        try
        {
            Method setName = builder.getClass().getMethod("setName", String.class);
            setName.invoke(builder, name);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
        }
        #else
        try
        {
            Class.forName("net.minecraft.network.chat.TextComponent");
            builder.setName(new TranslatableText(name));
            return;
        }
        catch (ClassNotFoundException e)
        {}
        //1.19
        Object textComp = create1_19TextComponent(name);
        if(textComp == null)
        {
            return;
        }
        try
        {
            Method setName = builder.getClass().getMethod("setName", Environment.textCompClazz_1_19);
            setName.invoke(builder, textComp);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return;
        }
        #endif
    }

    public static void setImplBuilderTooltip(OptionImpl.Builder builder, String tooltip)
    {
        #if BEFORE_18_1
        try
        {
            Method setName = builder.getClass().getMethod("setTooltip", String.class);
            setName.invoke(builder, tooltip);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
        }
        #else
        try
        {
            Class.forName("net.minecraft.network.chat.TextComponent");
            builder.setTooltip(new TranslatableText(tooltip));
            return;
        }
        catch (ClassNotFoundException e)
        {}
        //1.19
        Object textComp = create1_19TextComponent(tooltip);
        if(textComp == null)
        {
            return;
        }
        try
        {
            Method setName = builder.getClass().getMethod("setTooltip", Environment.textCompClazz_1_19);
            setName.invoke(builder, textComp);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return;
        }
        #endif
    }

    public static OptionPage createOptionPage(String text, List<OptionGroup> groups)
    {
        #if BEFORE_18_1
        try
        {
            return (OptionPage) Environment.optionPageConstructor.newInstance(text, ImmutableList.copyOf(groups));
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            //SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        #else
        try
        {
            Class.forName("net.minecraft.network.chat.TextComponent");
            return new OptionPage(new LiteralText(text), ImmutableList.copyOf(groups));
        }
        catch (ClassNotFoundException e)
        {}
        //1.19
        Object textComp = create1_19TextComponent(text);
        if(textComp == null)
        {
            return null;
        }
        try
        {
            return (OptionPage) Environment.optionPageConstructor.newInstance(textComp, ImmutableList.copyOf(groups));
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        #endif
        //end 1.19
    }
    
    #if BEFORE_18_1
    public static Object create1_16TextComponent(String text, Object... args)
    {
        Object translationTextComponent;
        try 
        {
            translationTextComponent = Environment.textCompClazz_1_16_Constructor.newInstance(text, args);
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        return translationTextComponent;
    }
    #else
    public static Object create1_19TextComponent(String text)
    {
        Object textComp;
        try
        {
            textComp =  Environment.textCompClazz_1_19_Constructor.invoke(null,text);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        return textComp;
    }
    #endif
}
