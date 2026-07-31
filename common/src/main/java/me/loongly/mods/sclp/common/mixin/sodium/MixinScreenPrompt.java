package me.loongly.mods.sclp.common.mixin.sodium;
import net.caffeinemc.mods.sodium.client.SodiumClientMod;

import net.caffeinemc.mods.sodium.client.config.builder.PageBuilderImpl;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen;
import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.Util;
import net.caffeinemc.mods.sodium.client.gui.ButtonTheme;
import net.caffeinemc.mods.sodium.client.gui.Colors;
import net.caffeinemc.mods.sodium.client.gui.SodiumOptions;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPrompt;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPromptable;


@Mixin(ScreenPrompt.class)
public class MixinScreenPrompt
{
    @Final
    @Shadow
    private int width, height;

    @Final
    @Shadow
    private ScreenPromptable parent;
   
    @Final
    @Shadow
    FlatButtonWidget closeButton;

    private static final ButtonTheme PROMPT_THEME = new ButtonTheme(Colors.FOREGROUND, Colors.FOREGROUND, Colors.FOREGROUND, 0xff393939, 0xff2b2b2b, 0xff2b2b2b);

    @Inject(method = "init", at = @At(value = "RETURN"))
    public void injectInit(CallbackInfo c)
    {
        var scrIns = ((ScreenPrompt)(Object)this);

        var parentDimensions = this.parent.getDimensions();

        int boxX = parentDimensions.getCenterX() - (this.width / 2);
        int boxY = parentDimensions.getCenterY() - (this.height / 2);
       
        this.closeButton = new FlatButtonWidget(new Dim2i((boxX + this.width) - 84, (boxY + this.height) - 24, 80, 20), Component.translatable("sclp.close"), this::close);
    }

    private void close() 
    {
        this.parent.setPrompt(null);
    }
}

//LZX-2026-04-03-001