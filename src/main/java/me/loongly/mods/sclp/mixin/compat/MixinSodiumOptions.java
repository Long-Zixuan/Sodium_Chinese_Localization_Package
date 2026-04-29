package me.loongly.mods.sclp.mixin.compat;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.SCLPGameOptionPages;
import me.loongly.mods.sclp.language.I18N;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.Style;

@Mixin(value = SodiumGameOptions.class, remap = false)
public class MixinSodiumOptions
{
    @Inject(method = "getConfigPath", at = @At(value = "RETURN"), cancellable = true)
    private static void mixinGetConfigPath(String name, CallbackInfoReturnable<Path> cir) 
    {
        Path path = cir.getReturnValue();
        String localAppDataFolder = System.getenv("LOCALAPPDATA");
        Path nePath = Paths.get(localAppDataFolder, "Netease", "MCLauncher", "config", "mod", "1.20-" + name);
        File neFile = nePath.toFile();
        if(!neFile.getParentFile().exists())
        {
            neFile.getParentFile().mkdirs();
        }
        cir.setReturnValue(nePath);
    }
}
