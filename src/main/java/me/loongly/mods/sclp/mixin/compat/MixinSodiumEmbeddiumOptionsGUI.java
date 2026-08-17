package me.loongly.mods.sclp.mixin.compat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.loongly.mods.sclp.language.I18N;
import me.loongly.mods.sclp.client.SCLPClientMod;

import org.apache.logging.log4j.core.config.builder.api.Component;
import org.embeddedt.embeddium.gui.frame.tab.Tab;
import org.embeddedt.embeddium.gui.frame.tab.TabHeaderWidget;
import org.embeddedt.embeddium.util.PlatformUtil;


@Mixin(value = TabHeaderWidget.class, remap = false)
public class MixinSodiumEmbeddiumOptionsGUI
{
    @Overwrite
    public static MutableText getLabel(String modId) 
    {
        return idComponent(modId).setStyle(Style.EMPTY.withUnderline(true));
    }

    private static MutableText idComponent(String namespace) 
    {
        
        var modOriName = PlatformUtil.getModName(namespace);

        switch(modOriName) 
        {
            case "Sodium Shadowy Path Blocks" -> modOriName = "SSPB";//这个不可能，但是原本逻辑写了，所有这里也写
            case "Embeddium Chinese Localized Pack" -> modOriName = "ECLP";
        }

        var modDisName = modOriName;
        if(SCLPClientMod.options().getIsTransModNameVal())
        {
            modDisName = I18N.trans(modOriName);
        }
        
        return Text.literal(modDisName);
    }

}
