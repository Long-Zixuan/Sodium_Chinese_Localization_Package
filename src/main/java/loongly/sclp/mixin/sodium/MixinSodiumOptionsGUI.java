package loongly.sclp.mixin.sodium;

import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import loongly.sclp.client.gui.SCLPGameOptionPages;
import loongly.sclp.client.SclpClientMod;

import java.util.List;


@Mixin(SodiumOptionsGUI.class)
public class MixinSodiumOptionsGUI 
{

    @Shadow @Final
    private List<OptionPage> pages;


    @Inject(method = "<init>(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("TAIL"))
    private void addSCLPOptionPage(CallbackInfo ci)
    {
        this.pages.add(SCLPGameOptionPages.sclpPage());
    }
}
