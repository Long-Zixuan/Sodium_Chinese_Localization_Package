package me.loongly.mods.sclp.common.mixin.sodium;

import net.caffeinemc.mods.sodium.client.config.structure.ModOptions;
import net.caffeinemc.mods.sodium.client.gui.ColorTheme;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPrompt;
import net.caffeinemc.mods.sodium.client.gui.widgets.CenteredFlatWidget;
import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.gui.widgets.PageListWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import me.loongly.mods.sclp.common.language.I18N;

@Mixin(value = ModOptions.class,remap = false)
public abstract class MixinModOptionsBuilderImpl
{
    /*@Redirect(
        method = "name()Ljava/lang/String;",
        at = @At(value = "INVOKE", 
                target = "Lnet/caffeinemc/mods/sodium/client/config/structure/ModOptions;name()Ljava/lang/String;"),
        require = 1
    )
    private String redirectName(ModOptions instance) 
    {
        String originalName = instance.name();
        if (SCLPClientMod.options().getShouldTransModName()) 
        {
            return I18N.trans(originalName);
        }
        return originalName;
    }*/
    /*@ModifyConstant(
        method = "name()Ljava/lang/String;",
        constant = @Constant
    )
    private String modifyName(String constant) 
    {
        if (SCLPClientMod.options().getShouldTransModName()) 
        {
            return I18N.trans(constant);
        }
        return constant;
    } */
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
        if (SCLPClientMod.options().getShouldTransModName()) 
        {
            cir.setReturnValue(I18N.trans(original));
        }
    }
}

//LZX-2026-08-03-001