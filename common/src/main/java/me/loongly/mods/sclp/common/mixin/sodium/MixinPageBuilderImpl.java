package me.loongly.mods.sclp.common.mixin.sodium;

import net.caffeinemc.mods.sodium.client.config.builder.PageBuilderImpl;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PageBuilderImpl.class)
public class MixinPageBuilderImpl
{
    @Final
    @Shadow
    private Component name;

    @Inject(method = "setName", at = @At(value = "RETURN"),cancellable = true)
    public void injectSetName(Component name, CallbackInfoReturnable<PageBuilderImpl> c) 
    {
        if (name.getString().equals("General")) //这么屎山的玩意估计钠下一个版本就不会这样写了
        {
            name = Component.translatable("stat.generalButton");
            this.name = name;
        }
    }
   /*@Redirect(method = "setName", at = @At(value = "INVOKE"))
   public PageBuilderImpl redirectSetName(PageBuilderImpl instance,Component name)
   {
        if (name.getString().equals(Component.literal("General").getString())) //这么屎山的玩意估计钠下一个版本就不会这样写了
        {
            name = Component.translatable("General");
        }
        this.name = name;
        return null;
   }*/
}

//LZX-2025-12-31-001
