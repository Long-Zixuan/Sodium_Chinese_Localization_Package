package me.loongly.mods.sclp.mixin.compat;

import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import me.loongly.mods.sclp.language.I18N;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.lang.reflect.Field;

@Mixin(value = FlatButtonWidget.class,remap = false)
public class MixinFlatButtonWidget
{
    /*@ModifyArg(
    method = "<init>(Lme/jellysquid/mods/sodium/client/util/Dim2i;Ljava/lang/String;Ljava/lang/Runnable;)V",
        at = @At(value = "INVOKE", 
                target = "java/lang/String", 
                ordinal = 0),
        index = 1
    )
    private String modifyLabel(String originalLabel) 
    {
        return I18N.trans(originalLabel);
    }*/
   @Redirect(
        method = "<init>(Lme/jellysquid/mods/sodium/client/util/Dim2i;Ljava/lang/String;Ljava/lang/Runnable;)V",
        at = @At(value = "INVOKE", 
                target = "java/lang/String", 
                ordinal = 0),
        require = 0 // 表示不依赖其他条件，总是尝试注入
    )
    private String redirectLabel(String originalLabel) 
    {
        return I18N.trans(originalLabel);
    }
}
