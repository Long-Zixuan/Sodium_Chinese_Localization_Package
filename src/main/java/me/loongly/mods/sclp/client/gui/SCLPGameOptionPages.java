package me.loongly.mods.sclp.client.gui;

import com.google.common.collect.ImmutableList;

import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.options.storage.SCLPOptionsStorage;
import me.loongly.mods.sclp.language.I18N;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Identifier;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SCLPGameOptionPages 
{
    public static final SCLPOptionsStorage sodiumExtraOpts = new SCLPOptionsStorage();
    public static final MinecraftOptionsStorage vanillaOpts = new MinecraftOptionsStorage();

    public static OptionPage SCLPPage()
    {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        /*
        为什么Xenon隐藏该选项呢？因为Xenon在设置UI不会显示模组名字，
        唯一会显示模组名字的地方是兼容性警告，但是有兼容性问题的模组肯定是没有收入翻译的。
        为了避免玩家开了个寂寞的感觉，故在Xenon关闭该选项 */
        if(SCLPClientMod.isXenon())
        {
            sodiumExtraOpts.getData().setIsTransModNameVal(false);
            sodiumExtraOpts.save();
        }
        
        List<OptionGroup> groups = new ArrayList<>();
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sodiumExtraOpts)
                        .setName(Text.literal(I18N.trans("sclp.options.trans_mod_name")))
                        .setTooltip(Text.literal(I18N.trans("sclp.options.trans_mod_name.tooltip")))
                        .setControl(TickBoxControl::new)
                        .setEnabled(!SCLPClientMod.isXenon())//同上
                        .setBinding((options, value) -> {
                            options.setIsTransModNameVal(value); 
                            try
                            {
                                SCLPClientMod.caiDan();
                            }
                            catch (Exception e){}
                        }, options -> options.getIsTransModNameVal())
                        .build())
                .build());
        if(isMyBirthday(year, month, day))
        {
            groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sodiumExtraOpts)
                        .setName(Text.translatable("🎂:" + (year - 2004)))
                        .setTooltip(Text.translatable("🎂:" + (year - 2004)))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> {
                            try
                            {
                                SCLPClientMod.birthCaiDan();
                            }
                            catch (Exception e){}
                        }, options -> options.getIsTransModNameVal())
                        .build())
                .build());
        }
        return new OptionPage(Text.translatable("sclp.options.pages.settings"), ImmutableList.copyOf(groups));
    }

    static boolean isMyBirthday(int year, int month, int day)
    {
        return month == 4 && day == 4;
    }
}
