package loongly.sclp.mixin.sodium;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpact;

import net.minecraft.client.resource.language.I18n;

import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(OptionImpact.class)
public class MixinSodiumOptionImpact
{
    @Shadow @Final
    private String text;
    @Shadow @Final
    private Formatting color;
    @Inject(method = "toDisplayString", at = @At(value = "RETURN",target = "Lnet/minecraft/client/resource/language/I18n;translate(Ljava/lang/String;)V"), cancellable = true)
    public void toDisplayString(CallbackInfoReturnable<String> cir)
    {
        cir.setReturnValue(this.color + I18n.translate(this.text));
    }
}
