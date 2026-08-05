package loongly.sclp.mixin.reeses_sodium_options;

import me.flashyreese.mods.reeses_sodium_options.client.gui.SodiumVideoOptionsScreen;
import me.flashyreese.mods.reeses_sodium_options.client.gui.frame.BasicFrame;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import loongly.sclp.api.ISCLPScreen;
import loongly.sclp.client.SclpClientMod;
import loongly.sclp.client.gui.SCLPGameOptionPages;
import loongly.sclp.language.I18N;

import java.time.LocalDate;
import java.util.List;


@Mixin(SodiumVideoOptionsScreen.class)
public class MixinSodiumVideoOptionsScreenNew implements ISCLPScreen
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
        Dim2i tabFrameDim = new Dim2i(basicFrameDim.getOriginX() + basicFrameDim.getWidth() / 20 / 2, basicFrameDim.getOriginY() + basicFrameDim.getHeight() / 4 / 2, basicFrameDim.getWidth() - (basicFrameDim.getWidth() / 20), basicFrameDim.getHeight() / 4 * 3);
        if(SclpClientMod.options().shouldShowSupportBtn)
        {
            Dim2i supportBtnDim = new Dim2i(tabFrameDim.getLimitX() - 100, tabFrameDim.getLimitY() - 17, 100, 20);
            supportBtn_ = new FlatButtonWidget(supportBtnDim, I18N.trans("sclp.support"), () -> SclpClientMod.openSupportPage());
            Dim2i closeSupportBtnDim = new Dim2i(tabFrameDim.getLimitX() - 122, tabFrameDim.getLimitY() - 17, 20, 20);
            closeSupportBtn_ = new FlatButtonWidget(closeSupportBtnDim, "x", () -> onClickCloseSupportBtn());
        }
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(SclpClientMod.isMyBirthday(year, month, day))
        {
            Dim2i birthBtnDim = new Dim2i(tabFrameDim.getLimitX() - 272, tabFrameDim.getLimitY() + 5, 65, 20);
            birthBtn_ = new FlatButtonWidget(birthBtnDim, I18N.trans("ᗜᴗᗜ:" + (year - 2004)), () -> SclpClientMod.birthCaidan());
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
        if(SclpClientMod.isMyBirthday(year, month, day))
        {
            builder.addChild(dim -> birthBtn_);
        }
        if(SclpClientMod.options().shouldShowSupportBtn)
        {
            builder.addChild(dim -> closeSupportBtn_);
            builder.addChild(dim -> supportBtn_);
        }
        cir.setReturnValue(builder);
    }

    void onClickCloseSupportBtn()
    {
        SclpClientMod.options().shouldShowSupportBtn = false;
        try
        {
            SclpClientMod.options().writeChanges();
        }
        catch (Exception e)
        {
            SclpClientMod.LOGGER.error("[SCLP] Failed to save options", e);
        }
        supportBtn_.setVisible(false);
        closeSupportBtn_.setVisible(false);
    }

    @Unique
    private OptionPage birthPage_;

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
        if(supportBtn_ != null && SclpClientMod.options().shouldShowSupportBtn)
        {
            supportBtn_.setVisible(vis);
        }
        if(closeSupportBtn_ != null && SclpClientMod.options().shouldShowSupportBtn)
        {
            closeSupportBtn_.setVisible(vis);
        }
    }
}
