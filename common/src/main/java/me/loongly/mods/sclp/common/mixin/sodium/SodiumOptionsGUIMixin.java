package me.loongly.mods.sclp.common.mixin.sodium;

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
import me.loongly.mods.sclp.common.client.SCLPClientMod;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import net.caffeinemc.mods.sodium.client.SodiumClientMod;
import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptions;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPrompt;

import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.Util;

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;

@Mixin(SodiumOptionsGUI.class)
public abstract class SodiumOptionsGUIMixin extends Screen
{
    protected SodiumOptionsGUIMixin(Component title) 
    {
        super(title);
        //TODO Auto-generated constructor stub
    }

    @Final @Shadow(remap = false)
    private static final List<FormattedText> DONATION_PROMPT_MESSAGE;

    static {
        DONATION_PROMPT_MESSAGE = List.of(
                FormattedText.composite(Component.translatable("sclp.hello")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.1"), Component.translatable("Sodium").withColor(0x27eb92), Component.translatable("sclp.donation.prompt.2")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.3"), Component.translatable("sclp.donation.thousand_hours").withColor(0xff6e00), Component.translatable("sclp.donation.prompt.4")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.5"), Component.translatable("sclp.donation.buycoffee").withColor(0xed49ce), Component.literal(".")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.6"))
        );
    }

    @Final @Shadow(remap = false)
    private List<OptionPage> pages;

    @Shadow(remap = false)
    OptionPage currentPage;

    @Unique
    OptionPage birthPage_;

    @Unique
    OptionPage sclpPage_;


    @Inject(method = "<init>*", at = @At("TAIL"))
    private void addLSDCOptionPage(CallbackInfo ci)
    {
        this.sclpPage_ = SCLPGameOptionPages.sclpPage();
        //this.pages.add(this.sclpPage_);
        if(SCLPClientMod.isMyBirthday())
        {
            birthPage_ = SCLPGameOptionPages.birthPage();
            this.pages.add(birthPage_);
        }
    }

    @Inject(method = "setPage", at = @At("HEAD"), remap = false, cancellable = true)
	private void sclp$onSetPage(OptionPage page, CallbackInfo ci) 
    {
		if (page ==  birthPage_) 
        {
			SCLPClientMod.birthCaiDan();
			ci.cancel();
		}
	}

    //@Redirect(method = "openDonationPrompt", at = @At(value = "INVOKE"),remap = false)
     /**
     * @reason 替换默认的 Sodium 捐赠提示，对捐赠按钮进行翻译。
     * @author Loongly
     */
    @Overwrite(remap = false)
    private void openDonationPrompt(SodiumGameOptions options) 
    {
        var videoSettingsScrIns = ((SodiumOptionsGUI)(Object)this);
        var prompt = new ScreenPrompt(videoSettingsScrIns, DONATION_PROMPT_MESSAGE, 320, 190,
                new ScreenPrompt.Action(Component.translatable("sclp.donation.buycoffee2"), this::openDonationPage));
        prompt.setFocused(true);

        options.notifications.hasSeenDonationPrompt = true;

        try 
        {
            SodiumGameOptions.writeToDisk(options);
        } 
        catch (IOException e) 
        {
            SodiumClientMod.logger()
                    .error("Failed to update config file", e);
        }
    }

    private void openDonationPage()
    {
        Util.getPlatform().openUri("https://caffeinemc.net/donate");
    }

    private FlatButtonWidget birthButton_;

    private FlatButtonWidget supportBtn_;
    private FlatButtonWidget closeSupportBtn_;

    @Inject(method = "rebuildGUI", at = @At("TAIL"),remap = false)
    private void injectRebuild(CallbackInfo ci)
    { 
        SodiumOptionsGUI videoSettingsScrIns = (SodiumOptionsGUI)((Object)this);
        int width = videoSettingsScrIns.width;
        int height = videoSettingsScrIns.height;
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(SCLPClientMod.isMyBirthday(year, month, day))
        {
            this.birthButton_ = new FlatButtonWidget(new Dim2i(width - 73, height - 55, 65, 20), Component.literal("🎂:" + (year - 2004)), SCLPClientMod::birthCaiDan);
            this.addRenderableWidget(birthButton_);
        }
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            var closeSupportBtnDim = new Dim2i(this.width - 142, this.height - 52, 20, 20);
            var supportBtnDim = new Dim2i(closeSupportBtnDim.x() + closeSupportBtnDim.width() + 2, closeSupportBtnDim.y(), 100, 20);
            supportBtn_ = new FlatButtonWidget(supportBtnDim, Component.literal(I18N.trans("sclp.options.support_project.name")), () -> {openSupportWeb();});            
            closeSupportBtn_ = new FlatButtonWidget(closeSupportBtnDim, Component.literal("×"), () -> {onClickCloseSupportBtn();});
            this.addRenderableWidget(supportBtn_);
            this.addRenderableWidget(closeSupportBtn_);
            supportBtn_.setVisible(false);
            closeSupportBtn_.setVisible(false);
        }
        if(currentPage == sclpPage_)
        {
            setSupportBtnVis(true);
        }
        else
        {
            setSupportBtnVis(false);
        }
    }

    void setSupportBtnVis(boolean vis)
    {
        if(supportBtn_ != null && closeSupportBtn_ != null)
        {
            supportBtn_.setVisible(vis);
            closeSupportBtn_.setVisible(vis);
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
        setSupportBtnVis(false);
    }

    void openSupportWeb()
    {
        SCLPClientMod.LOGGER.info("[SCLP]Open Support website.");
        Util.getPlatform().openUri("https://ifdian.net/a/loongly");
    }
}
