package loongly.sclp.mixin.sodium;
import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import net.minecraft.client.font.TextRenderer;
import loongly.sclp.api.ISCLPScreen;
import loongly.sclp.client.OsType;
import loongly.sclp.client.SclpClientMod;
import loongly.sclp.client.gui.SCLPGameOptionPages;
//import net.minecraft.client.resource.language.I18n;
import loongly.sclp.language.I18N;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.util.math.MatrixStack;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlElement;
import me.jellysquid.mods.sodium.client.gui.options.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;
import net.minecraft.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Drawable;
import loongly.sclp.language.I18NLanguage;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import java.util.Optional;
import com.google.common.collect.ImmutableList;

@Mixin(value = SodiumOptionsGUI.class)
public class MixinSodiumGUI extends Screen implements ISCLPScreen
{
    public MixinSodiumGUI(Screen parent) 
    {
        super(new TranslatableText("SCLP Mixin Sodium Options"));
    }

    private FlatButtonWidget birthButton_;
    private FlatButtonWidget noInternetButton_;
    @Unique
    FlatButtonWidget supportBtn_;
    @Unique
    FlatButtonWidget closeSupportBtn_;
    @Shadow
    @Final
    private List<Drawable> drawable;
    @Shadow
    OptionPage currentPage;

    @Inject(method = "rebuildGUI", at = @At(value = "RETURN"),remap = false, cancellable = true)
    private void injectRebuildGUI(CallbackInfo ci)
    {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(SclpClientMod.isMyBirthday(year, month, day))
        {
            this.birthButton_ = new FlatButtonWidget(new Dim2i(this.width - 73, this.height - 60, 65, 20), "ᗜᴗᗜ:" + (year - 2004), SclpClientMod::birthCaidan);
            this.children.add(this.birthButton_);
            this.drawable.add(this.birthButton_);
        }
        if(!SclpClientMod.isConnected && SclpClientMod.options().isEnableSclp && !SclpClientMod.options().isDisableSclpNoInternetWarn)
        {
            this.noInternetButton_ = new FlatButtonWidget(new Dim2i(5, this.height - 10, 80, 10), I18N.trans("sclp.no_internet"), this::noInternet);
            this.children.add(this.noInternetButton_);
            this.drawable.add(this.noInternetButton_);
        }
        if(SclpClientMod.options().shouldShowSupportBtn && this.currentPage == sclpPage_)
        {
            Dim2i supportBtnDim = new Dim2i(this.width - 108, this.height - 60, 100, 20);
            supportBtn_ = new FlatButtonWidget(supportBtnDim, I18N.trans("sclp.support"), () -> SclpClientMod.openSupportPage());
            Dim2i closeSupportBtnDim = new Dim2i(this.width - 130, this.height - 60, 20, 20);
            closeSupportBtn_ = new FlatButtonWidget(closeSupportBtnDim, "x", this::onClickCloseSupportBtn);
            this.children.add(supportBtn_);
            this.children.add(closeSupportBtn_);
            this.drawable.add(supportBtn_);
            this.drawable.add(closeSupportBtn_);
        }
    }

    void onClickCloseSupportBtn()
    {
        SclpClientMod.options().shouldShowSupportBtn = false;
        try
        {
            SclpClientMod.options().writeChanges();
        }
        catch (IOException e)
        {
            SclpClientMod.LOGGER.error("[SCLP] Failed to write SCLP options", e);
        }
        this.closeSupportBtn_.setVisible(false);
        this.supportBtn_.setVisible(false);
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
        if(supportBtn_ != null && SclpClientMod.options().shouldShowSupportBtn)
        {
            supportBtn_.setVisible(vis);
        }
        if(closeSupportBtn_ != null && SclpClientMod.options().shouldShowSupportBtn)
        {
            closeSupportBtn_.setVisible(vis);
        }
    }

    void noInternet()
    {
        SclpClientMod.isConnected = true;
        this.noInternetButton_.setVisible(false);
        SclpClientMod.openNetworkSettings();
    }

    /**
    * @author Loongly
    * @reason 为renderOptionTooltip方法，增加I18n支持。
    */
    @Overwrite
    private void renderOptionTooltip(MatrixStack matrixStack, ControlElement<?> element)
    {
        Dim2i dim = element.getDimensions();

        int textPadding = 3;
        int boxPadding = 3;

        int boxWidth = 200;

        int boxY = dim.getOriginY();
        int boxX = dim.getLimitX() + boxPadding;

        Option<?> option = element.getOption();
        List<OrderedText> tooltip = new ArrayList<>(this.textRenderer.wrapLines(option.getTooltip(), boxWidth - (textPadding * 2)));

        OptionImpact impact = option.getImpact();

        boolean hadTrans = true;

        String perImpactStr = I18n.translate("sclp.performance_impact");
        if(perImpactStr.equals("sclp.performance_impact"))
        {
            hadTrans = false;
            perImpactStr = "Performance Impact:";
        }

        if (impact != null)
        {    
           tooltip.add(Language.getInstance().reorder(new LiteralText(Formatting.GRAY + I18N.trans("sclp.performance_impact") + impact.toDisplayString())));
        } 

        if(!hadTrans && !SclpClientMod.options().isDisableSclpFabricApiWarn && SclpClientMod.options().isEnableSclp)
        {
            tooltip.add(Language.getInstance().reorder(new LiteralText(I18N.trans("sclp.no_fabricapi_warm"))));
        }

        int boxHeight = (tooltip.size() * 12) + boxPadding;
        int boxYLimit = boxY + boxHeight;
        int boxYCutoff = this.height - 40;

        // If the box is going to be cutoff on the Y-axis, move it back up the difference
        if (boxYLimit > boxYCutoff)
        {
            boxY -= boxYLimit - boxYCutoff;
        }

        this.fillGradient(matrixStack, boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0xE0000000, 0xE0000000);

        if(!hadTrans && !SclpClientMod.options().isDisableSclpFabricApiWarn && SclpClientMod.options().isEnableSclp)
        {
            this.fillGradient(matrixStack, boxX, boxY + boxHeight - 14, boxX + boxWidth, boxY + boxHeight, 0xFFED65FF, 0xFFED65FF);
        }

        for (int i = 0; i < tooltip.size(); i++)
        {
           this.textRenderer.draw(matrixStack, tooltip.get(i), boxX + textPadding, boxY + textPadding + (i * 12), 0xFFFFFFFF);
        }
    }

    @Shadow @Final
    private List<OptionPage> pages;


    @Inject(method = "<init>(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("TAIL"))
    private void addSCLPOptionPage(CallbackInfo ci)
    {
        Optional<ModContainer> rsoOptionalModContainer = FabricLoader.getInstance().getModContainer("reeses-sodium-options");
        if(!SclpClientMod.options().notShowPage || (rsoOptionalModContainer.isPresent() && SCLPGameOptionPages.isChangeNotShowPage))
        {
            sclpPage_ = SCLPGameOptionPages.sclpPage();
            this.pages.add(sclpPage_);
        }
         LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(SclpClientMod.isMyBirthday(year, month, day))
        {
            birthPage_ = SCLPGameOptionPages.birthPage();
            this.pages.add(birthPage_);
        }
    }

    @Unique
    private OptionPage birthPage_;
    @Unique
    private OptionPage sclpPage_;

    @Inject(method = "setPage", at = @At("HEAD"), remap = false, cancellable = true)
	private void sclp$onSetPage(OptionPage page, CallbackInfo ci) 
    {
        close();
		if (page == birthPage_) 
        {
			SclpClientMod.birthCaidan();
			ci.cancel();
		}
		if (page == sclpPage_)
        {
			open();
        }
	}
}
