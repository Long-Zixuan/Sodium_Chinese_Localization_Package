package loongly.sclp.mixin.sodium;

import java.util.HashMap;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import loongly.sclp.client.SclpClientMod;

@Mixin(value = OptionImpl.class,remap = false)
public class MixinOptionImpl 
{
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
