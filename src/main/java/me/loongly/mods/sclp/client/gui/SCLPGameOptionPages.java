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


public class SCLPGameOptionPages 
{

    private static final SCLPOptionsStorage lsdcOpts = new SCLPOptionsStorage();

    #if BEFORE_18_1
    #else
    @SuppressWarnings("unchecked")
    public static OptionPage sclpPage()
    {
        List<OptionGroup> groups = new ArrayList<>();
        groups.add(OptionGroup.createBuilder()
        .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
                        .setName(new TranslatableText("sclp.options.shoud_trans_mod_name"))
                        .setTooltip(new TranslatableText("sclp.options.shoud_trans_mod_name.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setBinding((opts, value) -> {
                            opts.shouldTransModName = value;
                            SCLPClientMod.caiDan();
                        }, opts -> opts.shouldTransModName)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .setEnabled(false)
                        .build())
                .build());
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
                    .setName(new TranslatableText("sclp.options.close_support_page.name"))
                    .setTooltip(new TranslatableText("sclp.options.close_support_page.tooltip"))
                    .setControl(TickBoxControl::new)
                    .setBinding((opts, value) -> {opts.shouldShowSupportPage = !value;}, opts -> !opts.shouldShowSupportPage)
                    .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                    .build())
                .add(ViaOpt.create("sclp.options.support_project.name", "sclp.options.support_project.tooltip", lsdcOpts))
                .build());
        }
        return new OptionPage(new LiteralText(I18N.trans("sclp.page")), ImmutableList.copyOf(groups));
    }
    #endif

    public static OptionPage birthPage()
    {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        List<OptionGroup> groups = new ArrayList<>();
        // if(SCLPClientMod.isMyBirthday(year, month, day))
        // {
        //     groups.add(OptionGroup.createBuilder()
        //     .add(OptionImpl.createBuilder(boolean.class, lsdcOpts)
        //                 .setName(Text.literal("🎂:" + (year -2004)))
        //                 .setTooltip(Text.literal("🎂"))
        //                 .setControl(TickBoxControl::new)
        //                 .setBinding((opts, value) -> SCLPClientMod.birthCaiDan(), opts -> true)
        //                 .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
        //                 .build())
        //         .build());
        // }
        #if BEFORE_18_1
        Class<?> optionPageClazz;
        try
        {
            optionPageClazz = Class.forName("me.jellysquid.mods.sodium.client.gui.options.OptionPage");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
        try
        {
            return (OptionPage) optionPageConstructor.newInstance("🎂:" + (year -2004), ImmutableList.copyOf(groups));
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            //e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
        Object translationTextComponent;
        try 
        {
            translationTextComponent = transTextConstructor.newInstance("🎂:" + (year -2004), null);
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        Class<?> iTextCompClazz;
        try
        {
            iTextCompClazz = Class.forName("net.minecraft.util.text.ITextComponent");
        } 
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
            return null;
        }
        try 
        {
            optionPageConstructor = clazz.getConstructor(iTextCompClazz, com.google.common.collect.ImmutableList.class);
        } 
        catch (NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
            return null;
        }
        try
        {
            return (OptionPage)optionPageConstructor.newInstance(translationTextComponent, ImmutableList.copyOf(groups));
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
        #else
        try
        {
            Class.forName("net.minecraft.network.chat.TextComponent");
            return new OptionPage(new LiteralText("🎂:" + (year -2004)), ImmutableList.copyOf(groups));
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
            e.printStackTrace();
            return null;
        }
        Method constructor;
        try
        {
            constructor = textCompClazz.getMethod("m_237115_",String.class);
        } 
        catch (NoSuchMethodException | SecurityException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        Object textComp;
        try
        {
            textComp = constructor.invoke(null,"🎂:" + (year -2004));
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        Class<?> optionPageClazz;
        try
        {
            optionPageClazz = Class.forName("me.jellysquid.mods.sodium.client.gui.options.OptionPage");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
        try
        {
            return (OptionPage) optionPageConstructor.newInstance(textComp, ImmutableList.copyOf(groups));
        } 
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        #endif
        //end 1.19
    }
}


//LoongLy Software Update 2026/04/05
