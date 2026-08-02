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
import net.caffeinemc.mods.sodium.client.config.structure.ModOptions;



//import net.caffeinemc.mods.sodium.client.config.builder.ModOptionsBuilderImpl;


@Mixin(value = ModOptions.class,remap = false)
public abstract class MixinModOptionsBuilderImpl
{
    @Shadow
    public abstract String name();

    @Inject(
        method = "name()Ljava/lang/String;",
        at = @At("RETURN"),
        cancellable = true
    )
    private void modifyName(CallbackInfoReturnable<String> cir) 
    {
        String original = cir.getReturnValue();
        if (SCLPClientMod.options().shouldTransModName) 
        {
            cir.setReturnValue(I18N.trans(original));
        }
    }
}

//LZX-2026-08-03-002