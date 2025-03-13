package loongly.sclp.mixin.sodium;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OptionImpl.class)
public class MixinSodiumOptionImpl
{
    @Inject(method = "getName", at = @At(value = "RETURN",target = "Lnet/minecraft/client/resource/language/I18n;translate(Ljava/lang/String;)V"), cancellable = true)
    public void InjectGetName(CallbackInfoReturnable<String> cir)
    {
        String oriName = cir.getReturnValue();
        cir.setReturnValue(I18n.translate(oriName));
    }

    @Inject(method = "getTooltip", at = @At(value = "RETURN",target = "Lnet/minecraft/client/resource/language/I18n;translate(Ljava/lang/String;)V"), cancellable = true)
    public void InjectGetTooltip(CallbackInfoReturnable<Text> cir)
    {
        Text oriTip = cir.getReturnValue();
        String oriTipStr = oriTip.getString();
        String tranTipStr = I18n.translate(oriTipStr);
        Text tranTip = new LiteralText(tranTipStr);
        cir.setReturnValue(tranTip);
    }
}
