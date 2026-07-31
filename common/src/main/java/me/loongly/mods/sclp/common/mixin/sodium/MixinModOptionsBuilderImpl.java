package me.loongly.mods.sclp.common.mixin.sodium;

import net.minecraft.client.resources.language.I18n;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import me.loongly.mods.sclp.common.language.I18N;



//import net.caffeinemc.mods.sodium.client.config.builder.ModOptionsBuilderImpl;


@Mixin(targets = "net.caffeinemc.mods.sodium.client.config.builder.ModOptionsBuilderImpl")
public class MixinModOptionsBuilderImpl
{
    @Final
    @Shadow
    private String name;

    @Inject(method = "setName", at = @At(value = "RETURN"), cancellable = true)
    public void injectSetName(String name, CallbackInfoReturnable<Runnable> c) 
    {
        if(name.equals("Sodium/Embeddium Chinese Localized Package"))
        {
            name = "Sodium Chinese Localized Package";
            this.name = name;
        }
        if(SCLPClientMod.options().shouldTransModName)
        {
            name = I18N.trans(name);
            this.name = name;
        }
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    public void injectInit(String configId, String name, String version, CallbackInfo ci) 
    {
        if(name.equals("Sodium/Embeddium Chinese Localized Package"))
        {
            name = "Sodium Chinese Localized Package";
            this.name = name;
        }
        if(SCLPClientMod.options().shouldTransModName)
        {
            name = I18N.trans(name);
            this.name = name;
        }
    }
}

//LZX-2025-12-31-001