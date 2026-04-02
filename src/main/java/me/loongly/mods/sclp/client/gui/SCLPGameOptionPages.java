package me.loongly.mods.sclp.client.gui;

import com.google.common.collect.ImmutableList;

import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.options.storage.SCLPOptionsStorage;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public class SCLPGameOptionPages 
{
    public static final SCLPOptionsStorage sodiumExtraOpts = new SCLPOptionsStorage();
    public static final MinecraftOptionsStorage vanillaOpts = new MinecraftOptionsStorage();

    public static OptionPage SCLPPage()
    {
        List<OptionGroup> groups = new ArrayList<>();
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, sodiumExtraOpts)
                        .setName(Text.literal("是否翻译模组名"))
                        .setTooltip(Text.translatable("如果启用，模组名将会被翻译。\\n该选项将在重开此页面时生效"))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> {
                            options.isTransModName = value; 
                            try
                            {
                                SCLPClientMod.caiDan();
                            }
                            catch (Exception e){}
                        }, options -> options.isTransModName)
                        .build())
                .build());
        return new OptionPage(Text.literal("汉化包设置"), ImmutableList.copyOf(groups));
    }
}
