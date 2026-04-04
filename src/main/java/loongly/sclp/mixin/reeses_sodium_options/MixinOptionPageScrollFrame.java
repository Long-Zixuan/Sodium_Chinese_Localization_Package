package loongly.sclp.mixin.reeses_sodium_options;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.VertexConsumer;
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
import me.flashyreese.mods.reeses_sodium_options.client.gui.frame.OptionPageFrame;
import me.flashyreese.mods.reeses_sodium_options.client.gui.frame.AbstractFrame;
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

import java.util.ArrayList;
import java.util.List;

@Mixin(value = OptionPageFrame.class,remap = false)
public class MixinOptionPageScrollFrame extends AbstractFrame
{
    public MixinOptionPageScrollFrame(Dim2i dim, boolean renderOutline, OptionPage page) 
    {
        super(dim, renderOutline);
    }

    @Final
    @Shadow
    protected Dim2i originalDim;

    @Shadow
    private long lastTime;

    /**
    * @author Loongly
    * @reason 为renderOptionTooltip方法，增加I18n支持。
    */
    @Overwrite(remap = false)
    private void renderOptionTooltip(MatrixStack matrixStack, ControlElement<?> element) 
    {
        if (this.lastTime + 500 > System.currentTimeMillis()) return;

        Dim2i dim = element.getDimensions();

        int textPadding = 3;
        int boxPadding = 3;

        int boxWidth = dim.getWidth();

        //Offset based on mouse position, width and height of content and width and height of the window
        int boxY = dim.getLimitY();
        int boxX = dim.getOriginX();

        Option<?> option = element.getOption();
        List<OrderedText> tooltip = new ArrayList<>(MinecraftClient.getInstance().textRenderer.wrapLines(option.getTooltip(), boxWidth - (textPadding * 2)));

        boolean hadTrans = true;

        OptionImpact impact = option.getImpact();
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
            tooltip.add(Language.getInstance().reorder(new LiteralText(Formatting.BLACK +"请安装Fabric API，否则汉化包将无法正常工作!")));
            tooltip.add(Language.getInstance().reorder(new LiteralText(Formatting.BLACK +"請安裝Fabric API，否則漢化包將無法正常工作!")));
        }

        int boxHeight = (tooltip.size() * 12) + boxPadding;
        int boxYLimit = boxY + boxHeight;
        int boxYCutoff = this.originalDim.getLimitY();

        // If the box is going to be cutoff on the Y-axis, move it back up the difference
        if (boxYLimit > boxYCutoff) 
        {
            boxY -= boxHeight + dim.getHeight();
        }

        if (boxY < 0) 
        {
            boxY = dim.getLimitY();
        }

        this.drawRect(boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0xE0000000);
        if(!hadTrans)
        {
            this.drawRect(boxX, boxY + boxHeight - 26, boxX + boxWidth, boxY + boxHeight, 0xFFED65FF);
        }
        this.drawRectOutline(boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0xFF94E4D3);

        for (int i = 0; i < tooltip.size(); i++) 
        {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(matrixStack, tooltip.get(i), boxX + textPadding, boxY + textPadding + (i * 12), 0xFFFFFFFF);
        }
    }
}
