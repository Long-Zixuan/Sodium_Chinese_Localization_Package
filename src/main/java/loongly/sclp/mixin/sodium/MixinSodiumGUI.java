package loongly.sclp.mixin.sodium;
import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;
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

import java.util.ArrayList;
import java.util.List;

@Mixin(value = SodiumOptionsGUI.class)
public class MixinSodiumGUI extends Screen
{
    public MixinSodiumGUI(Screen parent) 
    {
        super(new TranslatableText("SCLP Mixin Sodium Options"));
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
           tooltip.add(Language.getInstance().reorder(new LiteralText(Formatting.GRAY + perImpactStr + impact.toDisplayString())));
        } 

        if(!hadTrans)
        {
            tooltip.add(Language.getInstance().reorder(new LiteralText(Formatting.RED +"请安装Fabric API，否则汉化包将无法正常工作！")));
            tooltip.add(Language.getInstance().reorder(new LiteralText(Formatting.RED +"請安裝Fabric API，否則漢化包將無法正常工作！")));
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
            this.fillGradient(matrixStack, boxX, boxY + boxHeight - 26, boxX + boxWidth, boxY + boxHeight, 0xED65FFFF, 0xED65FFFF);
        }

        for (int i = 0; i < tooltip.size(); i++)
        {
           this.textRenderer.draw(matrixStack, tooltip.get(i), boxX + textPadding, boxY + textPadding + (i * 12), 0xFFFFFFFF);
        }
    }
}
