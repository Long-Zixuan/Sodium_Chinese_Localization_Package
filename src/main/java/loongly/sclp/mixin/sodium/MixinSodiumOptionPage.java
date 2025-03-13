package loongly.sclp.mixin.sodium;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;

import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


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

//事实上，有了MixinAbstractWidget后，不InjectOptionPage的getName方法，也可以实现翻译，但是Inject该方法可以在渲染时提前获取字符串翻译后的长度，使其更加美观
