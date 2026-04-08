package me.loongly.mods.sclp.mixin.compat;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
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

@Mixin(value = PromptScreen.class) 
class MixinScreenPrompt extends Screen
{
    protected MixinScreenPrompt(Text title) 
    {
        super(title);
        //TODO Auto-generated constructor stub
    }

    @Final @Shadow(remap = false)
    private Screen prevScreen;

    @Final @Shadow(remap = false)
    int promptWidth, promptHeight;
   
    @Shadow(remap = false)
    private FlatButtonWidget closeButton;

    @Inject(method = "init", at = @At(value = "RETURN"))
    public void injectInit(CallbackInfo c)
    {
        var scrIns = (PromptScreen)((Object)this);
        this.closeButton.setVisible(false);
        int boxX = (prevScreen.width / 2) - (promptWidth / 2);
        int boxY = (prevScreen.height / 2) - (promptHeight / 2);

        this.closeButton = new FlatButtonWidget(new Dim2i((boxX + promptWidth) - 84, (boxY + promptHeight) - 24, 80, 20), Text.literal("关闭"), this::onClose);
        this.closeButton.setStyle(createButtonStyle());

        this.addDrawableChild(this.closeButton);
    }

    public void onClose()
    {
        MinecraftClient.getInstance().setScreen(this.prevScreen);
    }

    private static FlatButtonWidget.Style createButtonStyle() 
    {
        var style = new FlatButtonWidget.Style();
        style.bgDefault = 0xff2b2b2b;
        style.bgHovered = 0xff393939;
        style.bgDisabled = style.bgDefault;

        style.textDefault = 0xFFFFFFFF;
        style.textDisabled = style.textDefault;

        return style;
    }
}
