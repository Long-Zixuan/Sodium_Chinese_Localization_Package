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
import me.loongly.mods.sclp.client.Environment;
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
                    if(Environment.cyclingControlClzCon != null)
                    {
                        try
                        {
                            return (CyclingControl<ViaOpt>) Environment.cyclingControlClzCon.newInstance(opt, ViaOpt.class, new String[]{I18N.trans("sclp.options.open_external_page_button") + " ➤"});
                        }
                        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
                        {
                            SCLPClientMod.logger().error("[SCLP] :",e);//warn
                            return null;
                        }
                    }
                    if(Environment.cyclingControlClz_1_16_ConOCI != null)
                    {
                        Object textCompArray = Array.newInstance(Environment.textCompClazz_1_16,1);
                        Object textComp = SCLPUIBuilder.create1_16TextComponent(I18N.trans("sclp.options.open_external_page_button") + " ➤");
                        Array.set(textCompArray,0,textComp);
                        try
                        {
                            return (CyclingControl<ViaOpt>) Environment.cyclingControlClz_1_16_ConOCI.newInstance(opt, ViaOpt.class, textCompArray);
                        }
                        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
                        {
                            SCLPClientMod.logger().error("[SCLP] :",e);//warn
                            return null;
                        }
                    }
                    return null;
                })
                .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD);
            SCLPUIBuilder.setImplBuilderName(builder, I18N.trans(nameKey));
            SCLPUIBuilder.setImplBuilderTooltip(builder, I18N.trans(tooltipKey));
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
                SCLPUIBuilder.setImplBuilderName(builder, I18N.trans(nameKey));
                SCLPUIBuilder.setImplBuilderTooltip(builder, I18N.trans(tooltipKey));
                return builder.build();
            }
            catch (ClassNotFoundException e)
            {}
            
            Object textComp = SCLPUIBuilder.create1_19TextComponent(I18N.trans("sclp.options.open_external_page_button") + " ➤");
            Object textCompArray = Array.newInstance(Environment.textCompClazz_1_19,1);
            Array.set(textCompArray,0,textComp);
            var builder = OptionImpl.createBuilder(ViaOpt.class, lsdcOpts)
                .setControl(opt -> {
                    if(Environment.cyclingControlClzCon != null)
                    {
                        try 
                        {
                            return (CyclingControl<ViaOpt>) Environment.cyclingControlClzCon.newInstance(opt, ViaOpt.class, textCompArray);
                        }
                        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
                        {
                            SCLPClientMod.logger().error("[SCLP] :",e);//warn
                        }
                    }
                    return null;
                })
                .setBinding((opts, value) -> {}, opts -> ViaOpt.VIA)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD);
            SCLPUIBuilder.setImplBuilderName(builder, I18N.trans(nameKey));
            SCLPUIBuilder.setImplBuilderTooltip(builder, I18N.trans(tooltipKey));
            return builder.build();
            #endif
        }
    }
}
