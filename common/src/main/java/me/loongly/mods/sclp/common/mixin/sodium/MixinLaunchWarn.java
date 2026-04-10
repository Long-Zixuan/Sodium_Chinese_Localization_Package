package me.loongly.mods.sclp.common.mixin.sodium;
import net.caffeinemc.mods.sodium.client.SodiumClientMod;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//import net.caffeinemc.mods.sodium.desktop.LaunchWarn;
import net.minecraft.client.resources.language.I18n;



@Mixin(targets = "net.caffeinemc.mods.sodium.desktop.LaunchWarn")
public class MixinLaunchWarn
{
    @Inject(method = "showDialogBox", at = @At(value = "TAIL"),remap = false)
    public void injectShowDialogBox(String message,
                                     String title,
                                     int optionType,
                                     int messageType,
                                     String[] options,
                                     Object initialValue,
                                     CallbackInfo c)
    {
        message = I18n.get(message);
        title = I18n.get(title);
        for (String string : options) 
        {
            string = I18n.get(string);
        }
    }
}

//LZX-2026-04-10-002