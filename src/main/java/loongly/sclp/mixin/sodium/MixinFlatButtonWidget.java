package loongly.sclp.mixin.sodium;

import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;

//import net.minecraft.client.resource.language.I18n;
import loongly.sclp.language.I18N;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FlatButtonWidget.class)
public class MixinFlatButtonWidget
{
    @Shadow
    private String label;

    @Inject(method = "<init>", at = @At(value = "TAIL",target = "Lnet/minecraft/client/resource/language/I18n;translate(Ljava/lang/String;)V"))
    public void InjectInit(Dim2i dim, String label, Runnable action, CallbackInfo ci)
    {
        this.label = I18N.trans(label);
    }
}
