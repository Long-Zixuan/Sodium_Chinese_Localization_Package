package me.loongly.mods.sclp.mixin.compat;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.prompt.ScreenPrompt;

import org.antlr.v4.parse.ANTLRParser.throwsSpec_return;
import org.embeddedt.embeddium.gui.screen.PromptScreen;

import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget.Style;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import me.loongly.mods.sclp.api.ISCLPScreen;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.SCLPGameOptionPages;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
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
import me.loongly.mods.sclp.language.I18N;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.embeddedt.embeddium.gui.EmbeddiumVideoOptionsScreen;
import org.embeddedt.embeddium.gui.frame.BasicFrame;

@Mixin(value = EmbeddiumVideoOptionsScreen.class,remap = false) 
class MixinEmbeddiumOptionGUI implements ISCLPScreen
{
    @Overwrite(remap = false)
    private void openDonationPrompt() 
    {
        var srcIns = (EmbeddiumVideoOptionsScreen)((Object) this);
        var prompt = new PromptScreen(srcIns, SodiumOptionsGUI.DONATION_PROMPT_MESSAGE, 320, 190,
                new PromptScreen.Action(Text.literal(I18N.trans("sclp.donation.9")), this::openDonationPage));
        MinecraftClient.getInstance().setScreen(prompt);
    }
    
    void openDonationPage()
	{
        net.minecraft.util.Util.getOperatingSystem().open("https://caffeinemc.net/donate");
	}

    @Unique
    private FlatButtonWidget birthBtn_;

    @Inject(method = "parentFrameBuilder", at = @At("RETURN"),cancellable = true)
    void injectParentFrameBuilder(CallbackInfoReturnable<BasicFrame.Builder> c)
    {
        int width = ((EmbeddiumVideoOptionsScreen)((Object)this)).width;
        int height = ((EmbeddiumVideoOptionsScreen)((Object)this)).height;
        int newWidth = width;
        if (newWidth > 550 && (float) width / (float) height > (5f / 4f)) 
        {
            newWidth = Math.max(550, (int) (height * 5f / 4f));
        }

        Dim2i basicFrameDim = new Dim2i((width - newWidth) / 2, 0, newWidth, height);
        Dim2i tabFrameDim = new Dim2i(basicFrameDim.x() + basicFrameDim.width() / 20 / 2, basicFrameDim.y() + basicFrameDim.height() / 4 / 2, basicFrameDim.width() - (basicFrameDim.width() / 20), basicFrameDim.height() / 4 * 3);
        if(SCLPClientMod.isMyBirthday())
        {
            var data = LocalDate.now();
            var year = data.getYear();
            var birthText = Text.literal("🎂:" + (year - 2004));
            int birthTextWidth = 20;
            var birthBtnDim = new Dim2i(tabFrameDim.getLimitX() - 240 - birthTextWidth, tabFrameDim.getLimitY() + 5, birthTextWidth + 10, 20);
            birthBtn_ = new FlatButtonWidget(birthBtnDim, birthText, SCLPClientMod::birthCaiDan);
        }
    }

    @Inject(method = "parentBasicFrameBuilder", at = @At("RETURN"),cancellable = true)
    void injectParentBasicFrameBuilder(Dim2i parentBasicFrameDim, Dim2i tabFrameDim, CallbackInfoReturnable<BasicFrame.Builder> cir)
    {
        var builder = cir.getReturnValue();
        if(SCLPClientMod.isMyBirthday())
        {
            builder.addChild(dim -> birthBtn_);
        }
        cir.setReturnValue(builder);
    }

    @Override
    public void close()
    {
        setUIEleVis(false);
    }

    @Override
    public void open()
    {
        setUIEleVis(true);
    }


    public void setUIEleVis(boolean vis)
    {
        /*if(birthBtn_ != null)
        {
            birthBtn_.setVisible(vis);
        }
        if(supportBtn_ != null && SCLPClientMod.options().getShouldShowSupportPageVal())
        {
            supportBtn_.setVisible(vis);
        }
        if(closeSupportBtn_ != null && SCLPClientMod.options().getShouldShowSupportPageVal())
        {
            closeSupportBtn_.setVisible(vis);
        }*/
    }
}
