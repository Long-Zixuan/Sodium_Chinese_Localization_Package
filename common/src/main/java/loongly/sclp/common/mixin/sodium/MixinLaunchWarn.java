package loongly.sclp.common.mixin.sodium;
import loongly.sclp.common.client.SCLPClientMod;

import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPrompt;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPromptable;
import net.caffeinemc.mods.sodium.desktop.LaunchWarn;
import net.minecraft.client.resources.language.I18n;


@Mixin(LaunchWarn.class)
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

//LZX-2026-04-03-001