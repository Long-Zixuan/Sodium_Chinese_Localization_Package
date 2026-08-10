package me.loongly.mods.sclp.common.mixin.embeddium;

import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;

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

import me.loongly.mods.sclp.common.client.gui.SCLPGameOptionPages;
import me.loongly.mods.sclp.common.language.I18N;
import me.loongly.mods.sclp.common.api.ISCLPScreen;
import me.loongly.mods.sclp.common.client.SCLPClientMod;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import net.minecraft.resources.ResourceLocation;
import org.embeddedt.embeddium.api.options.OptionIdentifier;
import org.embeddedt.embeddium.impl.gui.frame.AbstractFrame;
import org.embeddedt.embeddium.impl.gui.frame.BasicFrame;
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

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;

@Mixin(EmbeddiumVideoOptionsScreen.class)
public abstract class EmbeddiumOptionsGUIMixin extends Screen implements ISCLPScreen
{
    protected EmbeddiumOptionsGUIMixin(Component title) 
    {
        super(title);
        //TODO Auto-generated constructor stub
    }

    @Final @Shadow(remap = false)
    private static final List<FormattedText> DONATION_PROMPT_MESSAGE;

    @Shadow(remap = false)
    private static final ResourceLocation LOGO_LOCATION = ResourceLocation.fromNamespaceAndPath(SCLPClientMod.MOD_ID, "textures/embeddium/gui/logo_transparent.png");

    static { 
        DONATION_PROMPT_MESSAGE = List.of(
                FormattedText.composite(Component.translatable("sclp.hello")),
                FormattedText.composite(Component.translatable("sclp.emb.donation.prompt.1"), Component.translatable("Embeddium").withColor(0x27eb92), Component.translatable("sclp.emb.donation.prompt.2")),
                FormattedText.composite(Component.translatable("sclp.emb.donation.prompt.3"), Component.translatable("sclp.donation.thousand_hours").withColor(0xff6e00), Component.translatable("sclp.emb.donation.prompt.4")),
                FormattedText.composite(Component.translatable("sclp.emb.donation.prompt.5"), Component.translatable("sclp.emb.donation.buycoffee").withColor(0xed49ce), Component.literal(".")),
                FormattedText.composite(Component.translatable("sclp.emb.donation.prompt.6"))
        );
    }

    //@Redirect(method = "openDonationPrompt", at = @At(value = "INVOKE"),remap = false)
     /**
     * @reason 替换默认的 Sodium 捐赠提示，对捐赠按钮进行翻译。
     * @author Loongly
     */
    @Overwrite(remap = false)
     private void openDonationPrompt() 
     {
        var prompt = new PromptScreen(this, DONATION_PROMPT_MESSAGE, 320, 190,
                new PromptScreen.Action(Component.translatable("sclp.emb.donation.buycoffee2"), this::openDonationPage));
        this.minecraft.setScreen(prompt);
    }

    private void openDonationPage()
    {
        Util.getPlatform().openUri("https://caffeinemc.net/donate");
    }

    @Unique
    private FlatButtonWidget birthBtn_;

    @Unique
    private FlatButtonWidget supportBtn_;

    @Unique
    private FlatButtonWidget closeSupportBtn_;

    private static final float ASPECT_RATIO = 5f / 4f;
    private static final int MINIMUM_WIDTH = 550;

    @Inject(method = "parentFrameBuilder", at = @At("RETURN"),remap = false, cancellable = true)
    void injectParentFrameBuilder(CallbackInfoReturnable<BasicFrame.Builder> c)
    {
        int newWidth = this.width;
        if (newWidth > MINIMUM_WIDTH && (float) this.width / (float) this.height > ASPECT_RATIO) 
        {
            newWidth = Math.max(MINIMUM_WIDTH, (int) (this.height *ASPECT_RATIO));
        }

        Dim2i basicFrameDim = new Dim2i((this.width - newWidth) / 2, 0, newWidth, this.height);
        Dim2i tabFrameDim = new Dim2i(basicFrameDim.x() + basicFrameDim.width() / 20 / 2, basicFrameDim.y() + basicFrameDim.height() / 4 / 2, basicFrameDim.width() - (basicFrameDim.width() / 20), basicFrameDim.height() / 4 * 3);
        if(SCLPClientMod.isMyBirthday())
        {
            var data = LocalDate.now();
            var year = data.getYear();
            var birthText = Component.literal("🎂:" + (year -2004));
            int birthTextWidth = this.minecraft.font.width(birthText);
            var birthBtnDim = new Dim2i(tabFrameDim.getLimitX() - 240 - birthTextWidth, tabFrameDim.getLimitY() + 5, birthTextWidth + 10, 20);
            birthBtn_ = new FlatButtonWidget(birthBtnDim, birthText, SCLPClientMod::birthCaiDan);
        }
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            /*var closeSupportBtnDim = new Dim2i(tabFrameDim.getLimitX() - 134 ,tabFrameDim.getLimitY() - 17 , 20, 20);
            var supportBtnDim = new Dim2i(closeSupportBtnDim.x() + closeSupportBtnDim.width() + 2, closeSupportBtnDim.y(), 100, 20);
            supportBtn_ = new FlatButtonWidget(supportBtnDim, Component.literal(I18N.trans("sclp.options.support_project.name")), () -> {openSupportWeb();});            
            closeSupportBtn_ = new FlatButtonWidget(closeSupportBtnDim, Component.literal("×"), () -> {onClickCloseSupportBtn();});*/
        }
    }

    void onClickCloseSupportBtn()
    {
        SCLPClientMod.options().shouldShowSupportPage = false;
        try
        {
            SCLPClientMod.options().writeChanges();
        }
        catch (IOException e)
        {
            SCLPClientMod.LOGGER.error("[SCLP] Failed to save options.", e);
        }
        supportBtn_.setVisible(false);
        closeSupportBtn_.setVisible(false);
    }

    void openSupportWeb()
    {
        SCLPClientMod.LOGGER.info("[SCLP]Open Support website.");
        Util.getPlatform().openUri("https://ifdian.net/a/loongly");
    }

    @Inject(method = "parentBasicFrameBuilder", at = @At("RETURN"),remap = false, cancellable = true)
    void injectParentBasicFrameBuilder(Dim2i parentBasicFrameDim, Dim2i tabFrameDim, CallbackInfoReturnable<BasicFrame.Builder> cir)
    {
        var builder = cir.getReturnValue();
        if(SCLPClientMod.isMyBirthday())
        {
            builder.addChild(dim -> birthBtn_);
        }
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            //builder.addChild(dim -> closeSupportBtn_);
            //builder.addChild(dim -> supportBtn_);
        }
        cir.setReturnValue(builder);
    }

    @Override
    public void open()
    {
        setUIEleVis(true);
    }

    @Override
    public void close()
    {
        setUIEleVis(false);
    }

    public void setUIEleVis(boolean vis)
    {
        if(birthBtn_ != null)
        {
            birthBtn_.setVisible(vis);
        }
        if(supportBtn_ != null && SCLPClientMod.options().shouldShowSupportPage)
        {
            supportBtn_.setVisible(vis);
        }
        if(closeSupportBtn_ != null && SCLPClientMod.options().shouldShowSupportPage)
        {
            closeSupportBtn_.setVisible(vis);
        }
    }
}
