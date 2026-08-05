package loongly.sclp.mixin.reeses_sodium_options;

import me.flashyreese.mods.reeses_sodium_options.client.gui.SodiumVideoOptionsScreen;
import me.flashyreese.mods.reeses_sodium_options.client.gui.frame.BasicFrame;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import loongly.sclp.api.ISCLPScreen;
import loongly.sclp.client.SclpClientMod;
import loongly.sclp.client.gui.SCLPGameOptionPages;
import loongly.sclp.language.I18N;

import java.time.LocalDate;
import java.util.List;


@Mixin(SodiumVideoOptionsScreen.class)
public class MixinSodiumVideoOptionsScreen
{

    @Shadow @Final
    private List<OptionPage> pages;


    @Inject(method = "<init>(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("TAIL"))
    private void addSCLPOptionPage(CallbackInfo ci)
    {
        if(!SclpClientMod.options().notShowPage)
        {
            this.pages.add(SCLPGameOptionPages.sclpPage());
        }
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(SclpClientMod.isMyBirthday(year, month, day))
        {
            birthPage_ = SCLPGameOptionPages.birthPage();
            this.pages.add(birthPage_);
        }
    }
 
    @Unique
    private OptionPage birthPage_;

}
