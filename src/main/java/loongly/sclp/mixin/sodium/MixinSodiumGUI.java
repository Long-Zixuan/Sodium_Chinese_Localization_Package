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
public class MixinSodiumGUI extends Screen
{
    public MixinSodiumGUI(Screen parent) 
    {
        super(new TranslatableText("SCLP Mixin Sodium Options"));
    }

    private FlatButtonWidget noInternetButton_;
    @Shadow
    @Final
    private List<Drawable> drawable;
    @Shadow
    OptionPage currentPage;

    @Inject(method = "rebuildGUI", at = @At(value = "RETURN"),remap = false, cancellable = true)
    private void injectRebuildGUI(CallbackInfo ci)
    {
        if(!SclpClientMod.isConnected && SclpClientMod.options().isEnableSclp && !SclpClientMod.options().isDisableSclpNoInternetWarn)
        {
            this.noInternetButton_ = new FlatButtonWidget(new Dim2i(5, this.height - 10, 80, 10), I18N.trans("sclp.no_internet"), this::noInternet);
            this.children.add(this.noInternetButton_);
            this.drawable.add(this.noInternetButton_);
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
            this.pages.add(SCLPGameOptionPages.sclpPage());
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

    @Inject(method = "setPage", at = @At("HEAD"), remap = false, cancellable = true)
	private void sclp$onSetPage(OptionPage page, CallbackInfo ci) 
    {
		if (page == birthPage_) 
        {
			SclpClientMod.birthCaidan();
			ci.cancel();
		}
	}
}
