package me.loongly.mods.sclp.common.mixin.reeses_sodium_options;
import java.time.LocalDate;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.minecraft.client.MinecraftClient;

import me.flashyreese.mods.reeses_sodium_options.client.gui.SodiumVideoOptionsScreen;
import me.flashyreese.mods.reeses_sodium_options.client.gui.frame.BasicFrame;
import me.flashyreese.mods.reeses_sodium_options.client.gui.frame.OptionPageFrame;
import me.loongly.mods.sclp.common.api.ISCLPScreen;
import me.loongly.mods.sclp.common.client.SCLPClientMod;
import me.loongly.mods.sclp.common.language.I18N;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Mixin(OptionPageFrame.class)
public class MixinOptionPageFrame
{
    @Shadow @Final
    OptionPage page;
    @Inject(method = "render", at = @At("TAIL"))
    void injectRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo c) 
    {
        if(page.getName().getString().equals(I18N.trans("sclp.page")))//emmm 有点不太保险
        {
            SodiumVideoOptionsScreen embScreen = getSodiumScreen();
            if(embScreen != null)
            {
                ISCLPScreen sclpScreen = (ISCLPScreen) embScreen;
                sclpScreen.open();
            }
            return;
        }
        SodiumVideoOptionsScreen embScreen = getSodiumScreen();
        if(embScreen != null)
        {
            ISCLPScreen sclpScreen = (ISCLPScreen) embScreen;
            sclpScreen.close();
        }
    }

    private static SodiumVideoOptionsScreen getSodiumScreen()
    {
        Screen sodiumScr = Minecraft.getInstance().screen;
        try
        {
            if(sodiumScr instanceof SodiumVideoOptionsScreen)
            {
                return ((SodiumVideoOptionsScreen) sodiumScr);
            }
        }
        catch (Exception e)
        {
            SCLPClientMod.LOGGER.error("[SCLP] Error when rebuild Embeddium Video Options Screen UI", e);
        }
        return null;
    }
}
