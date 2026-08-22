package loongly.sclp.mixin.sodium_extra;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import loongly.sclp.client.SclpClientMod;
//import net.minecraft.client.resource.language.I18n;
import loongly.sclp.language.I18N;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.resource.language.I18n;
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
import me.flashyreese.mods.sodiumextra.client.gui.scrollable_page.*;
import me.jellysquid.mods.sodium.client.gui.options.control.Control;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlElement;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.Option;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpact;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;
import loongly.sclp.language.I18NLanguage;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = OptionPageScrollFrame.class,remap = false)
public class MixinOptionPageScrollFrame extends AbstractFrame
{
    public MixinOptionPageScrollFrame(Dim2i dim, boolean renderOutline, OptionPage page) 
    {
        super(dim);
    }

    /**
    * @author Loongly
    * @reason 为renderOptionTooltip方法，增加I18n支持。
    */
    @Overwrite(remap = false)
    private void renderOptionTooltip(MatrixStack matrixStack, ControlElement<?> element) 
    {
        Dim2i dim = element.getDimensions();
        int textPadding = 3;
        int boxPadding = 3;
        int boxWidth = 200;
        int boxY = Math.max(dim.getOriginY(), this.dim.getOriginY());
        int boxX = this.dim.getLimitX() + boxPadding;
        Option<?> option = element.getOption();
        List<OrderedText> tooltip = new ArrayList<>(MinecraftClient.getInstance().textRenderer.wrapLines(option.getTooltip(), boxWidth - textPadding * 2));
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

        int boxHeight = tooltip.size() * 12 + boxPadding;
        int boxYLimit = boxY + boxHeight;
        int boxYCutoff = this.dim.getLimitY() - 25;
        if (boxYLimit > boxYCutoff)
        {
            boxY -= boxYLimit - boxYCutoff;
        }

        this.drawRect((double)boxX, (double)boxY, (double)(boxX + boxWidth), (double)(boxY + boxHeight), -536870912);

        if(!hadTrans && !SclpClientMod.options().isDisableSclpFabricApiWarn && SclpClientMod.options().isEnableSclp)
        {
            this.drawRect(boxX, boxY + boxHeight - 14, boxX + boxWidth, boxY + boxHeight, 0xFFED65FF);
        }

        for(int i = 0; i < tooltip.size(); ++i) 
        {
            MinecraftClient.getInstance().textRenderer.draw(matrixStack, (OrderedText)tooltip.get(i), (float)(boxX + textPadding), (float)(boxY + textPadding + i * 12), -1);
        }
   }
}
