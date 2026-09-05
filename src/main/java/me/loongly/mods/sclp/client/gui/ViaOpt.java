package me.loongly.mods.sclp.client.gui;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Array;
import java.util.Arrays;

import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.loongly.mods.sclp.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.language.I18N;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.loongly.mods.sclp.client.SCLPClientMod;
#if BEFORE_18_1
#else
import net.minecraft.text.Text;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
#endif
import me.jellysquid.mods.sodium.client.gui.options.Option;
import java.lang.reflect.InvocationTargetException;

public enum ViaOpt 
{
    VIA;

    public static OptionImpl<SCLPGameOptions, ViaOpt> create(String nameKey, String tooltipKey, SCLPOptionsStorage sclpOpts)
    {
        return SCLPViaOptCreater.create(nameKey, tooltipKey, sclpOpts);
    }

    private static class SCLPViaOptCreater
    {
        public static OptionImpl<SCLPGameOptions, ViaOpt> create(String nameKey,String tooltipKey, SCLPOptionsStorage lsdcOpts)
        {
            #if BEFORE_18_1
            var builder = OptionImpl.createBuilder(ViaOpt.class, lsdcOpts)
                .setControl(opt -> {
                    try
                    {
                        Constructor<?> constructor = CyclingControl.class.getConstructor(
                            Option.class,      // 对应Option<T>
                            Class.class,       // 对应Class<T>
                            String[].class     // 对应String[]
                        );
                        return (CyclingControl<ViaOpt>) constructor.newInstance(opt, ViaOpt.class, new String[]{I18N.trans("sclp.options.open_external_page_button") + " ➤"});
                    }
                    catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                    {
                        SCLPClientMod.logger().error("[SCLP] :",e);//warn
                        return null;
                    }
                })
                .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD);
            Builder.setImplBuilderName(builder, I18N.trans(nameKey));
            Builder.setImplBuilderTooltip(builder, I18N.trans(tooltipKey));
            return builder.build();
            #else
            try
            {
                Class.forName("net.minecraft.network.chat.TextComponent");
                var builder = OptionImpl.createBuilder(ViaOpt.class, lsdcOpts)
                    .setControl(opt -> {
                        return new CyclingControl<>(opt, ViaOpt.class, new Text[] { new LiteralText(I18N.trans("sclp.options.open_external_page_button") + " ➤").setStyle(Style.EMPTY.withUnderline(true))});
                    })
                    .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                    .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD);
                Builder.setImplBuilderName(builder, I18N.trans(nameKey));
                Builder.setImplBuilderTooltip(builder, I18N.trans(tooltipKey));
                return builder.build();
            }
            catch (ClassNotFoundException e)
            {}
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
            Method textConstructor;
            try
            {
                textConstructor = textCompClazz.getMethod("m_237115_",String.class);//m_237115_是translatable的方法名
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
                textComp = textConstructor.invoke(null,I18N.trans("sclp.options.open_external_page_button") + " ➤");
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
            {
                // TODO Auto-generated catch block
                SCLPClientMod.logger().error("[SCLP] :",e);//warn
                return null;
            }
            Class<?> textCompArrayClazz = textCompClazz.arrayType();
            Object textCompArray = Array.newInstance(textCompClazz,1);
            Array.set(textCompArray,0,textComp);
            var builder = OptionImpl.createBuilder(ViaOpt.class, lsdcOpts)
                .setControl(opt -> {
                    try
                    {
                        Constructor<?> constructor = CyclingControl.class.getConstructor(
                            Option.class,      // 对应Option<T>
                            Class.class,       // 对应Class<T>
                            textCompArrayClazz     // 对应Component[]
                        );
                        return (CyclingControl<ViaOpt>) constructor.newInstance(opt, ViaOpt.class, textCompArray);
                    }
                    catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                    {
                        SCLPClientMod.logger().error("[SCLP] :",e);//warn
                        return null;
                    }
                })
                .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD);
            Builder.setImplBuilderName(builder, I18N.trans(nameKey));
            Builder.setImplBuilderTooltip(builder, I18N.trans(tooltipKey));
            return builder.build();
            #endif
        }
    }
}
