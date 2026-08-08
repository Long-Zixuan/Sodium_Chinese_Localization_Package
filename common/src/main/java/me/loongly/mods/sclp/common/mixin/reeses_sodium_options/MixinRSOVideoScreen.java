package me.loongly.mods.sclp.common.mixin.reeses_sodium_options;
import java.time.LocalDate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.flashyreese.mods.reeses_sodium_options.client.gui.SodiumVideoOptionsScreen;
import me.flashyreese.mods.reeses_sodium_options.client.gui.frame.BasicFrame;
import me.loongly.mods.sclp.common.api.ISCLPScreen;
import me.loongly.mods.sclp.common.client.SCLPClientMod;
import me.loongly.mods.sclp.common.language.I18N;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;
import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;

@Mixin(SodiumVideoOptionsScreen.class)
public class MixinRSOVideoScreen implements ISCLPScreen
{
    @Unique
    FlatButtonWidget supportBtn_;
    @Unique
    FlatButtonWidget closeSupportBtn_;
    @Unique
    FlatButtonWidget birthBtn_;

    @Inject(method = "parentFrameBuilder", at = @At("RETURN"),cancellable = true, remap = false)
    void injectParentFrameBuilder(CallbackInfoReturnable<BasicFrame.Builder> c)
    {
        // Calculates if resolution exceeds 16:9 ratio, force 16:9
        int width = ((SodiumVideoOptionsScreen)(Object)this).width;
        int height = ((SodiumVideoOptionsScreen)(Object)this).height;
        int newWidth = width;
        if ((float) width / (float) height > 1.77777777778) 
        {
            newWidth = (int) (height * 1.77777777778);
        }

        Dim2i basicFrameDim = new Dim2i((width - newWidth) / 2, 0, newWidth, height);
        Dim2i tabFrameDim = new Dim2i(basicFrameDim.x() + basicFrameDim.width() / 20 / 2, basicFrameDim.y() + basicFrameDim.height() / 4 / 2, basicFrameDim.width() - (basicFrameDim.width() / 20), basicFrameDim.height() / 4 * 3);
       
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            Dim2i supportBtnDim = new Dim2i(tabFrameDim.getLimitX() - 100, tabFrameDim.getLimitY() - 17, 100, 20);
            supportBtn_ = new FlatButtonWidget(supportBtnDim, Component.literal(I18N.trans("sclp.options.support_project.name")),() -> {openSupportPage();});
            Dim2i closeSupportBtnDim = new Dim2i(tabFrameDim.getLimitX() - 122, tabFrameDim.getLimitY() - 17, 20, 20);
            closeSupportBtn_ = new FlatButtonWidget(closeSupportBtnDim, Component.literal("×"), () -> {onClickCloseSupportBtn();});
        }
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(SCLPClientMod.isMyBirthday(year, month, day))
        {
            int offect = 272;
      
            Dim2i birthBtnDim = new Dim2i(tabFrameDim.getLimitX() - offect, tabFrameDim.getLimitY() + 5, 65, 20);
            birthBtn_ = new FlatButtonWidget(birthBtnDim, Component.literal("🎂:" + (year -2004)), () -> {birthCaidan();});
        }

    }

    @Inject(method = "parentBasicFrameBuilder", at = @At("RETURN"),cancellable = true,remap = false)
    void injectParentBasicFrameBuilder(Dim2i parentBasicFrameDim, Dim2i tabFrameDim, CallbackInfoReturnable<BasicFrame.Builder> cir)
    {
        BasicFrame.Builder builder = cir.getReturnValue();
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(SCLPClientMod.isMyBirthday(year, month, day))
        {
            builder.addChild(dim -> birthBtn_);
        }
        if(SCLPClientMod.options().shouldShowSupportPage)
        {
            builder.addChild(dim -> closeSupportBtn_);
            builder.addChild(dim -> supportBtn_);
        }
        cir.setReturnValue(builder);
    }

    void birthCaidan()
    {
        SCLPClientMod.LOGGER.info("[SCLP]Happy birthday to LoongLy!");

        Util.getPlatform()
                .openUri("https://long-zixuan.github.io/html/badapple_h.html");
        Util.getPlatform()
                .openUri("https://long-zixuan.github.io/html/clock.html");
    }

	void openSupportPage()
	{
		SCLPClientMod.LOGGER.info("[SCLP] Open Support Page.");
		Util.getPlatform()
                .openUri("https://ifdian.net/a/loongly");
	}

    void onClickCloseSupportBtn()
    {
        SCLPClientMod.options().shouldShowSupportPage = false;
        try
        {
            SCLPClientMod.options().writeChanges();
        }
        catch (Exception e)
        {
            SCLPClientMod.LOGGER.error("[SCLP] Failed to save options", e);
        }
        supportBtn_.setVisible(false);
        closeSupportBtn_.setVisible(false);
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
        // if(birthBtn_ != null)
        // {
        //     birthBtn_.setVisible(vis);
        // }
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
