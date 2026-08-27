package me.loongly.mods.sclp.mixin.sodium;

import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.SCLPGameOptionPages;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;


@Mixin(SodiumOptionsGUI.class)
public abstract class SodiumOptionsGUIMixin 
{

    @Final @Shadow(remap = false)
    private List<OptionPage> pages;

    @Unique
    OptionPage birthPage_;


    @Inject(method = "<init>(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("TAIL"))
    private void addSCLPOptionPage(CallbackInfo ci)
    {
        this.pages.add(SCLPGameOptionPages.sclpPage());
        if(SCLPClientMod.isMyBirthday())
        {
            birthPage_ = SCLPGameOptionPages.birthPage();
            this.pages.add(birthPage_);
        }
    }

      @Inject(method = "setPage", at = @At("HEAD"), remap = false, cancellable = true)
	private void sclp$onSetPage(OptionPage page, CallbackInfo ci) 
    {
		if (page ==  birthPage_) 
        {
			SCLPClientMod.birthCaiDan();
			ci.cancel();
		}
	}
}
