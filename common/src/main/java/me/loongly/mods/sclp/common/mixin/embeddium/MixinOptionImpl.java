package me.loongly.mods.sclp.common.mixin.embeddium;

import java.util.HashMap;

import org.embeddedt.embeddium.api.options.OptionIdentifier;
import org.embeddedt.embeddium.api.options.structure.OptionImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import me.loongly.mods.sclp.common.language.I18N;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;


@Mixin(value = OptionImpl.class,remap = false)
public class MixinOptionImpl<T> 
{
    @Shadow @Final
    OptionIdentifier<T> id;
    HashMap<String,Runnable> sclpEvents = new HashMap<String,Runnable>(){{
        put("sclp_support",()->{openSupportPage();});
    }};
    @Shadow @Final
    Component name;
    @Inject(method = "setValue", at = @At("HEAD"), cancellable = true)
    private void injectSetValue(Object value, CallbackInfo ci) 
    {
        if(id.getModId().equals(SCLPClientMod.MOD_ID))
        {
            if(sclpEvents.containsKey(id.getPath()))
            {
                sclpEvents.get(id.getPath()).run();
                ci.cancel();
            }
        }
    }

    void openSupportPage()
	{
		SCLPClientMod.LOGGER.info("[SCLP] Open Support Page.");
		Util.getPlatform()
                .openUri("https://ifdian.net/a/loongly");
	}
    
}
