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
#if BEFORE_18_1
#else
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
#endif

public class Builder
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
        Class<?> textCompClazz;
        try
        {
            textCompClazz = Class.forName("net.minecraft.network.chat.Component");
        }
        catch (ClassNotFoundException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return;
        }
        Method constructor;
        try
        {
            constructor = textCompClazz.getMethod("m_237115_",String.class);//m_237115_是translatable的方法名
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return;
        }
        Object textComp;
        try
        {
            textComp = constructor.invoke(null,name);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return;
        }
        try
        {
            Method setName = builder.getClass().getMethod("setName", textCompClazz);
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
        Class<?> textCompClazz;
        try
        {
            textCompClazz = Class.forName("net.minecraft.network.chat.Component");
        }
        catch (ClassNotFoundException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return;
        }
        Method constructor;
        try
        {
            constructor = textCompClazz.getMethod("m_237115_",String.class);//m_237115_是translatable的方法名
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return;
        }
        Object textComp;
        try
        {
            textComp = constructor.invoke(null,tooltip);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return;
        }
        try
        {
            Method setName = builder.getClass().getMethod("setTooltip", textCompClazz);
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
        Class<?> optionPageClazz;
        try
        {
            optionPageClazz = Class.forName("me.jellysquid.mods.sodium.client.gui.options.OptionPage");
        }
        catch (ClassNotFoundException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        Constructor<?> optionPageConstructor;
        try 
        {
            optionPageConstructor = optionPageClazz.getConstructor(String.class, com.google.common.collect.ImmutableList.class);
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        try
        {
            return (OptionPage) optionPageConstructor.newInstance(text, ImmutableList.copyOf(groups));
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            //SCLPClientMod.logger().error("[SCLP] :",e);//warn
            //return null;
        }

        Class<?> clazz;
        try 
        {
            clazz = Class.forName("net.minecraft.util.text.TranslationTextComponent");//这玩意继承了ITextComponent
        } 
        catch (ClassNotFoundException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        Constructor<?> transTextConstructor;
        try 
        {
            transTextConstructor = clazz.getConstructor(String.class, Object[].class);
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        Object translationTextComponent;
        try 
        {
            translationTextComponent = transTextConstructor.newInstance(text, null);
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        Class<?> iTextCompClazz;
        try
        {
            iTextCompClazz = Class.forName("net.minecraft.util.text.ITextComponent");
        } 
        catch (ClassNotFoundException e) 
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        try 
        {
            optionPageConstructor = clazz.getConstructor(iTextCompClazz, com.google.common.collect.ImmutableList.class);
        } 
        catch (NoSuchMethodException | SecurityException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        try
        {
            return (OptionPage)optionPageConstructor.newInstance(translationTextComponent, ImmutableList.copyOf(groups));
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
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
        Class<?> textCompClazz;
        try
        {
            textCompClazz = Class.forName("net.minecraft.network.chat.Component");
        }
        catch (ClassNotFoundException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        Method constructor;
        try
        {
            constructor = textCompClazz.getMethod("m_237115_",String.class);//m_237115_是translatable的方法名
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        Object textComp;
        try
        {
            textComp = constructor.invoke(null,text);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        Class<?> optionPageClazz;
        try
        {
            optionPageClazz = Class.forName("me.jellysquid.mods.sodium.client.gui.options.OptionPage");
        }
        catch (ClassNotFoundException e)
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        Constructor<?> optionPageConstructor;
        try 
        {
            optionPageConstructor = optionPageClazz.getConstructor(textCompClazz, com.google.common.collect.ImmutableList.class);
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
            return null;
        }
        try
        {
            return (OptionPage) optionPageConstructor.newInstance(textComp, ImmutableList.copyOf(groups));
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
}
