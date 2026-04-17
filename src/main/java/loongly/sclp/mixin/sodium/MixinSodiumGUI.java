package loongly.sclp.mixin.sodium;
import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import net.minecraft.client.font.TextRenderer;
//import net.minecraft.client.resource.language.I18n;
import loongly.sclp.language.I18N;
import net.minecraft.client.gui.screen.Screen;

import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Drawable;
import loongly.sclp.language.I18NLanguage;

@Mixin(value = SodiumOptionsGUI.class)
public class MixinSodiumGUI extends Screen
{
    public MixinSodiumGUI(Screen parent) 
    {
        super(new TranslatableText("SCLP Mixin Sodium Options"));
    }

    private FlatButtonWidget birthButton;
    @Shadow
    @Final
    private List<Drawable> drawable;

    @Inject(method = "rebuildGUI", at = @At(value = "RETURN"),remap = false, cancellable = true)
    private void injectRebuildGUI(CallbackInfo ci)
    {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(isMyBirthday(year, month, day))
        {
            this.birthButton = new FlatButtonWidget(new Dim2i(this.width - 73, this.height - 60, 65, 20), "ᗜᴗᗜ:" + (year - 2004), this::birthCaidan);
            this.children.add(this.birthButton);
            this.drawable.add(this.birthButton);
        }
    }

    void birthCaidan()
    {
        Util.getOperatingSystem()
                .open("https://long-zixuan.github.io/html/lain.html");
        Util.getOperatingSystem()
                .open("https://long-zixuan.github.io/html/badapple_h.html");
        Util.getOperatingSystem()
                .open("https://long-zixuan.github.io/html/clock.html");
    }

    boolean isMyBirthday(int year, int month, int day)
    {
        return month == 4 && day == 4;
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

        String perImpactStr = I18N.trans("sclp.performance_impact");
        if(perImpactStr.equals("sclp.performance_impact"))
        {
            hadTrans = false;
            perImpactStr = "Performance Impact:";
        }

        if (impact != null)
        {    
           tooltip.add(Language.getInstance().reorder(new LiteralText(Formatting.GRAY + perImpactStr + impact.toDisplayString())));
        } 

        if(!hadTrans)
        {
            tooltip.add(Language.getInstance().reorder(new LiteralText(I18NLanguage.NO_FABRIC_API_WARM_ZH_CN)));
            tooltip.add(Language.getInstance().reorder(new LiteralText(I18NLanguage.NO_FABRIC_API_WARM_ZH_TW)));
            tooltip.add(Language.getInstance().reorder(new LiteralText(I18NLanguage.NO_FABRIC_API_WARM_1_EN_US)));
            tooltip.add(Language.getInstance().reorder(new LiteralText(I18NLanguage.NO_FABRIC_API_WARM_2_EN_US)));//虽然老外不太可能安装这个模组，但是考虑到这个模组实际上是支持多国语言的，使用还是按照国际惯例价格英语
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

        if(!hadTrans)
        {
            this.fillGradient(matrixStack, boxX, boxY + boxHeight - 50, boxX + boxWidth, boxY + boxHeight, 0xFFED65FF, 0xFFED65FF);
        }

        for (int i = 0; i < tooltip.size(); i++)
        {
           this.textRenderer.draw(matrixStack, tooltip.get(i), boxX + textPadding, boxY + textPadding + (i * 12), 0xFFFFFFFF);
        }
    }
}
