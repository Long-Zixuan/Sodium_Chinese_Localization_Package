package me.loongly.mods.sclp.mixin.compat;

import java.util.HashMap;

import org.embeddedt.embeddium.client.gui.options.OptionIdentifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.loongly.mods.sclp.SCLPMod;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.SCLPGameOptionPages.ViaOpt;

@Mixin(value = OptionImpl.class,remap = false)
class MixinOptionImpl<T>
{
    HashMap<String,Runnable> sclpEvents = new HashMap<String,Runnable>(){{
        put("support_project",()->{SCLPClientMod.openSupportWeb();});
        put("birth_caidan",() -> {SCLPClientMod.birthCaiDan();});
    }};

    @Shadow @Final
    OptionIdentifier<T> id;

    @Inject(method = "setValue", at = @At("HEAD"), cancellable = true)
    public void injectSetValue(Object value, CallbackInfo ci) 
    {
        if(value instanceof ViaOpt && id.getModId().equals(SCLPMod.MOD_ID))
        {
            if(sclpEvents.containsKey(id.getPath()))
            {
                sclpEvents.get(id.getPath()).run();
                ci.cancel();
            }
        }
    }
}
