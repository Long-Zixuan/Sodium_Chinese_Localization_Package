package me.loongly.mods.sclp.mixin.sodium;

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

import me.loongly.mods.sclp.client.gui.SCLPGameOptionPages;
import me.loongly.mods.sclp.language.I18N;
import me.loongly.mods.sclp.client.SCLPClientMod;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.prompt.ScreenPrompt;
import me.jellysquid.mods.sodium.client.gui.prompt.ScreenPromptable;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import net.minecraft.client.gui.screen.Screen;

@Mixin(SodiumOptionsGUI.class)
public abstract class SodiumOptionsGUIMixin// extends Screen implements ScreenPromptable
{
    /*protected SodiumOptionsGUIMixin(Text title) 
    {
        super(title);
        //TODO Auto-generated constructor stub
    }

    @Shadow(remap = false)
    private static final List<StringVisitable> DONATION_PROMPT_MESSAGE = List.of(StringVisitable.concat(new StringVisitable[]{Text.literal("Hello!")}), StringVisitable.concat(new StringVisitable[]{Text.literal("It seems that you've been enjoying "), Text.literal("Sodium").withColor(2616210), Text.literal(", the free and open-source optimization mod for Minecraft.")}), StringVisitable.concat(new StringVisitable[]{Text.literal("Mods like these are complex. They require "), Text.literal("thousands of hours").withColor(16739840), Text.literal(" of development, debugging, and tuning to create the experience that players have come to expect.")}), StringVisitable.concat(new StringVisitable[]{Text.literal("If you'd like to show your token of appreciation, and support the development of our mod in the process, then consider "), Text.literal("buying us a coffee").withColor(15550926), Text.literal(".")}), StringVisitable.concat(new StringVisitable[]{Text.literal("And thanks again for using our mod! We hope it helps you (and your computer.)")}));

    /*static {
        DONATION_PROMPT_MESSAGE = List.of(
                StringVisitable.concat(Text.translatable("sclp.hello")),
                StringVisitable.concat(Text.translatable("sclp.donation.prompt.1"), Text.translatable("Sodium").withColor(0x27eb92), Text.translatable("sclp.donation.prompt.2")),
                StringVisitable.concat(Text.translatable("sclp.donation.prompt.3"), Text.translatable("sclp.donation.thousand_hours").withColor(0xff6e00), Text.translatable("sclp.donation.prompt.4")),
                StringVisitable.concat(Text.translatable("sclp.donation.prompt.5"), Text.translatable("sclp.donation.buycoffee").withColor(0xed49ce), Text.literal(".")),
                StringVisitable.concat(Text.translatable("sclp.donation.prompt.6"))
        );
    }*/

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
        this.pages.add(this.sclpPage_);
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
    /*@Overwrite(remap = false)
    private void openDonationPrompt(SodiumGameOptions options) 
    {
        var videoSettingsScrIns = ((SodiumOptionsGUI)(Object)this);
        var prompt = new ScreenPrompt(videoSettingsScrIns, DONATION_PROMPT_MESSAGE, 320, 190,
                new ScreenPrompt.Action(Text.translatable("sclp.donation.buycoffee2"), this::openDonationPage));
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
        Util.getOperatingSystem().open("https://caffeinemc.net/donate");
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
            this.birthButton_ = new FlatButtonWidget(new Dim2i(width - 73, height - 55, 65, 20), Text.literal("🎂:" + (year - 2004)), SCLPClientMod::birthCaiDan);
            this.addDrawableChild(birthButton_);
        }
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            var closeSupportBtnDim = new Dim2i(this.width - 142, this.height - 52, 20, 20);
            var supportBtnDim = new Dim2i(closeSupportBtnDim.x() + closeSupportBtnDim.width() + 2, closeSupportBtnDim.y(), 100, 20);
            supportBtn_ = new FlatButtonWidget(supportBtnDim, Text.literal(I18N.trans("sclp.options.support_project.name")), () -> {openSupportWeb();});            
            closeSupportBtn_ = new FlatButtonWidget(closeSupportBtnDim, Text.literal("×"), () -> {onClickCloseSupportBtn();});
            this.addDrawableChild(supportBtn_);
            this.addDrawableChild(closeSupportBtn_);
            supportBtn_.setVisible(false);
            closeSupportBtn_.setVisible(false);
        }
        if(currentPage == sclpPage_)
        {
            //setSupportBtnVis(true);
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
        Util.getOperatingSystem().open("https://ifdian.net/a/loongly");
    }*/
}
