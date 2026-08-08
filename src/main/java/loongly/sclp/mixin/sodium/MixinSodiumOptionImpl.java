package loongly.sclp.mixin.sodium;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import loongly.sclp.client.SclpClientMod;
import loongly.sclp.language.I18N;
//import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.HashMap;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = OptionImpl.class,remap = false)
public class MixinSodiumOptionImpl
{
    @Inject(method = "getName", at = @At(value = "RETURN",target = "Lnet/minecraft/client/resource/language/I18n;translate(Ljava/lang/String;)V"), cancellable = true)
    public void InjectGetName(CallbackInfoReturnable<String> cir)
    {
        String oriName = cir.getReturnValue();
        cir.setReturnValue(I18N.trans(oriName));
    }

    @Inject(method = "getTooltip", at = @At(value = "RETURN",target = "Lnet/minecraft/client/resource/language/I18n;translate(Ljava/lang/String;)V"), cancellable = true)
    public void InjectGetTooltip(CallbackInfoReturnable<Text> cir)
    {
        Text oriTip = cir.getReturnValue();
        String oriTipStr = oriTip.getString();
        String tranTipStr = I18N.trans(oriTipStr);
        Text tranTip = new LiteralText(tranTipStr);
        cir.setReturnValue(tranTip);
    }

    HashMap<String,Runnable> sclpEvents = new HashMap<String,Runnable>(){{
        put("sclp.support",()->{SclpClientMod.openSupportPage();});
        put("sclp.birth",()->{SclpClientMod.birthCaidan();});
    }};

    @Shadow @Final
    String name;
    @Inject(method = "setValue", at = @At("HEAD"), cancellable = true)
    private void injectSetValue(Object value, CallbackInfo ci) 
    {
        if(sclpEvents.containsKey(name))
        {
            sclpEvents.get(name).run();
            ci.cancel();
        }
    }
}
