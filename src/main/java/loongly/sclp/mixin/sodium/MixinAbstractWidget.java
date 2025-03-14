package loongly.sclp.mixin.sodium;
import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;

import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

@Mixin(AbstractWidget.class)
public class MixinAbstractWidget
{
    private boolean isNum(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?");
    }

    @Final
    @Shadow
    protected TextRenderer font;
    @Redirect(method = "drawString", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I"))
    public int RediectDrawString(TextRenderer instance, MatrixStack matrices, String text, float x, float y, int color)
    {
        int oriWidth = this.font.getWidth(text);
        if(text.charAt(text.length()-1) != '%')
        {
            String[] tmp = text.split(" ");
            StringBuilder textBuilder = new StringBuilder();
            List<String> transList = new ArrayList<>();
            String transStr;
            //boolean isNum = false;
            for(String t : tmp)
            {
                if(isNum(t))
                {
                    if(textBuilder.length() > 0)
                    {
                        transStr = textBuilder.toString();
                        transList.add(transStr.substring(0,transStr.length() - 1));
                        textBuilder = new StringBuilder();
                        transList.add(" " + t + " ");
                    }
                    else
                    {
                        transList.add(t + " ");
                    }

                }
                else
                {
                    textBuilder.append(t).append(" ");
                }
            }
            if(textBuilder.length() > 0)
            {
                transStr = textBuilder.toString();
                transList.add(transStr.substring(0, transStr.length() - 1));
            }
            textBuilder = new StringBuilder();
            for(String t : transList)
            {
                t = I18n.translate(t);
                textBuilder.append(t);
            }
            text = textBuilder.toString();
            //text = I18n.translate(text);
        }
        int afterTranWidth = this.font.getWidth(text);
        int deltaWidth = afterTranWidth - oriWidth;
        this.font.draw(matrices, text, x - deltaWidth, y, color);
        return 1;
    }
}
