package me.loongly.mods.sclp.mixin.sodium;

import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
        name = Component.translatable(name).getString();
        this.name = name;
    }
}

//LZX-2025-12-31-001
