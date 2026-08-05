package loongly.sclp.mixin.reeses_sodium_options;

import me.flashyreese.mods.reeses_sodium_options.client.gui.SodiumVideoOptionsScreen;
import me.flashyreese.mods.reeses_sodium_options.client.gui.frame.BasicFrame;
import me.flashyreese.mods.reeses_sodium_options.client.gui.frame.OptionPageFrame;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

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


@Mixin(OptionPageFrame.class)
public class OptionPageFrameMixin
{
    @Shadow @Final
    OptionPage page;
    @Inject(method = "render", at = @At("TAIL"))
    void injectRender(MatrixStack drawContext, int mouseX, int mouseY, float delta, CallbackInfo c) 
    {
        if(page.getName().equals(I18N.trans("sclp.page_name")))//emmm 有点不太保险
        {
            SodiumVideoOptionsScreen embScreen = getSodiumScreen();
            if(embScreen != null)
            {
                ISCLPScreen sclpScreen = (ISCLPScreen) embScreen;
                sclpScreen.open();
            }
            return;
        }
        SodiumVideoOptionsScreen embScreen = getSodiumScreen();
        if(embScreen != null)
        {
            ISCLPScreen sclpScreen = (ISCLPScreen) embScreen;
            sclpScreen.close();
        }
    }

    private static SodiumVideoOptionsScreen getSodiumScreen()
    {
        Screen sodiumScr = MinecraftClient.getInstance().currentScreen;
        try
        {
            if(sodiumScr instanceof SodiumVideoOptionsScreen)
            {
                return ((SodiumVideoOptionsScreen) sodiumScr);
            }
        }
        catch (Exception e)
        {
            SclpClientMod.LOGGER.error("[SCLP] Error when rebuild Embeddium Video Options Screen UI", e);
        }
        return null;
    }
}
