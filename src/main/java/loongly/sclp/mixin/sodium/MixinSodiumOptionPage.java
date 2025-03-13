package loongly.sclp.mixin.sodium;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;

import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



import java.util.List;
@Mixin(OptionPage.class)
public class MixinSodiumOptionPage
{
    @Inject(method = "getName", at = @At(value = "RETURN",target = "Lnet/minecraft/client/resource/language/I18n;translate(Ljava/lang/String;)V"), cancellable = true)
    public void InjectGetName(CallbackInfoReturnable<String> cir)
    {
        String oriName = cir.getReturnValue();
        cir.setReturnValue(I18n.translate(oriName));
    }
}
