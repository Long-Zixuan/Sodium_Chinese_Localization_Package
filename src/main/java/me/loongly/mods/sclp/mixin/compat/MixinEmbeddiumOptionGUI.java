package me.loongly.mods.sclp.mixin.compat;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.prompt.ScreenPrompt;

import org.antlr.v4.parse.ANTLRParser.throwsSpec_return;
import org.apache.logging.log4j.core.config.builder.api.Component;
import org.embeddedt.embeddium.gui.screen.PromptScreen;

import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget.Style;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.SCLPGameOptionPages;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;
import net.minecraft.util.Util;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.OrderedText;
import net.minecraft.client.MinecraftClient;
import me.jellysquid.mods.sodium.client.gui.prompt.ScreenPromptable;

import java.io.IOException;
import java.util.List;

import org.embeddedt.embeddium.gui.EmbeddiumVideoOptionsScreen;

@Mixin(value = EmbeddiumVideoOptionsScreen.class) 
class MixinEmbeddiumOptionGUI
{
    @Overwrite(remap = false)
    private void openDonationPrompt() 
    {
        var srcIns = (EmbeddiumVideoOptionsScreen)((Object) this);
        var prompt = new PromptScreen(srcIns, SodiumOptionsGUI.DONATION_PROMPT_MESSAGE, 320, 190,
                new PromptScreen.Action(Text.translatable("Support Sodium"), this::openDonationPage));
        MinecraftClient.getInstance().setScreen(prompt);
    }
    
    void openDonationPage()
	{
        net.minecraft.util.Util.getOperatingSystem().open("https://caffeinemc.net/donate");
	}
}
