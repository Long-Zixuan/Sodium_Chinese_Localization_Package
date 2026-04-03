package me.loongly.mods.sclp.common.mixin.embeddium;

import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.loongly.mods.sclp.common.client.gui.ECLPGameOptionPages;
import me.loongly.mods.sclp.common.client.gui.SCLPGameOptionPages;
import me.loongly.mods.sclp.common.interfac.ISCLPScreen;
import me.loongly.mods.sclp.common.client.SCLPClientMod;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import net.minecraft.resources.ResourceLocation;
import org.embeddedt.embeddium.api.options.OptionIdentifier;
import org.embeddedt.embeddium.impl.gui.frame.AbstractFrame;
import org.embeddedt.embeddium.impl.gui.frame.BasicFrame;
import org.embeddedt.embeddium.impl.gui.frame.OptionPageFrame;
import org.embeddedt.embeddium.impl.gui.frame.ScrollableFrame;
import org.embeddedt.embeddium.impl.gui.frame.components.SearchTextFieldComponent;
import org.embeddedt.embeddium.impl.gui.frame.components.SearchTextFieldModel;
import org.embeddedt.embeddium.impl.gui.frame.tab.Tab;
import org.embeddedt.embeddium.impl.gui.frame.tab.TabFrame;
import org.embeddedt.embeddium.impl.gui.screen.PromptScreen;
import org.embeddedt.embeddium.impl.gui.theme.DefaultColors;
import org.embeddedt.embeddium.impl.render.ShaderModBridge;
import org.embeddedt.embeddium.impl.util.PlatformUtil;

import org.embeddedt.embeddium.impl.Embeddium;
import org.embeddedt.embeddium.api.OptionGUIConstructionEvent;
import org.embeddedt.embeddium.api.math.Dim2i;
import org.embeddedt.embeddium.impl.data.fingerprint.HashedFingerprint;
import org.embeddedt.embeddium.impl.gui.console.Console;
import org.embeddedt.embeddium.impl.gui.console.message.MessageLevel;
import org.embeddedt.embeddium.api.options.structure.Option;
import org.embeddedt.embeddium.api.options.structure.OptionFlag;
import org.embeddedt.embeddium.api.options.structure.OptionGroup;
import org.embeddedt.embeddium.api.options.structure.OptionPage;
import org.embeddedt.embeddium.api.options.structure.OptionStorage;
import org.embeddedt.embeddium.impl.gui.widgets.FlatButtonWidget;
import org.embeddedt.embeddium.impl.gui.EmbeddiumVideoOptionsScreen;

import net.minecraft.network.chat.FormattedText;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import java.lang.reflect.Field;

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;

@Mixin(ScrollableFrame.Builder.class)
class ScrollableFrameMixin
{
    @Inject(method = "setFrame", at = @At("TAIL"),remap = false, cancellable = true)
    void injectSetFrame(AbstractFrame frame, CallbackInfoReturnable<Void> cir) 
    {
        if(frame instanceof OptionPageFrame)
        {
            OptionPageFrame optionPageFrame = (OptionPageFrame)frame;
            Class<?> optionPageFrameClass = OptionPageFrame.class;
            try
            {
                // 获取私有属性 "page"
                Field pageField = optionPageFrameClass.getDeclaredField("page");

                // 设置可访问性为 true（绕过访问权限限制）
                pageField.setAccessible(true);
                OptionPage page = (OptionPage) pageField.get(optionPageFrame);
                if(page.getId().getModId().equals(SCLPClientMod.MOD_ID))
                {
                    var embScreen = getEmbScreen();
                    if(embScreen != null)
                    {
                        ISCLPScreen sclpScreen = (ISCLPScreen) embScreen;
                        sclpScreen.setBirthBtnVis(true);
                    }
                    return;
                }
            }
            catch (Exception e)
            {
                SCLPClientMod.LOGGER.error("[SCLP] Failed to get page field from OptionPageFrame", e);
            }
        }
        var embScreen = getEmbScreen();
        if(embScreen != null)
        {
            ISCLPScreen sclpScreen = (ISCLPScreen) embScreen;
            sclpScreen.setBirthBtnVis(false);
        }
    }

    private static EmbeddiumVideoOptionsScreen getEmbScreen()
    {
        var embScreen = Minecraft.getInstance().screen;
        try
        {
            if(embScreen instanceof EmbeddiumVideoOptionsScreen)
            {
                return ((EmbeddiumVideoOptionsScreen) embScreen);
            }
        }
        catch (Exception e)
        {
            SCLPClientMod.LOGGER.error("[SCLP] Error when rebuild Embeddium Video Options Screen UI", e);
        }
        return null;
    }
}
