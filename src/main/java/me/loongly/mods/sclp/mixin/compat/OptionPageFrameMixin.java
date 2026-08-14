package me.loongly.mods.sclp.mixin.compat;

import org.embeddedt.embeddium.gui.EmbeddiumVideoOptionsScreen;
import org.embeddedt.embeddium.gui.frame.OptionPageFrame;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import me.loongly.mods.sclp.client.gui.SCLPGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.loongly.mods.sclp.api.ISCLPScreen;
import me.loongly.mods.sclp.client.SCLPClientMod;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nonnull;


@Mixin(value = OptionPageFrame.class)
class OptionPageFrameMixin
{
    @SuppressWarnings("null")
    @Unique @Nonnull
    private static final Identifier LOGO_LOCATION = Identifier.of("sclp", "textures/sclp/gui/LZXLOGO.png");

    @SuppressWarnings("null")
    @Unique @Nonnull
    private static final Identifier LS_LOCATION = Identifier.of("sclp", "textures/sclp/gui/LSLOGO.png");

    private static final int LOGO_SIZE = 5;

    @Shadow @Final
    OptionPage page;
    @Inject(method = "render", at = @At("TAIL"))
    void injectRender(DrawContext drawContext, int mouseX, int mouseY, float delta, CallbackInfo c) 
    {
        if(page.getId().getModId().equals("sclp"))
        {
            var embScreen = getEmbScreen();
            if(embScreen != null)
            {
                ISCLPScreen sclpScreen = (ISCLPScreen) embScreen;
                sclpScreen.open();
            }
            //drawContext.drawTexture(LOGO_LOCATION, mouseX - LOGO_SIZE / 2, mouseY - LOGO_SIZE / 2, 0, 0, LOGO_SIZE, LOGO_SIZE, LOGO_SIZE, LOGO_SIZE);
            if(LS_LOCATION != null)//暂时未知为何LS_LOCATION为null,但是加了Oculus就不会为null
            {
                drawContext.drawTexture(LS_LOCATION, 0, 0, 0, 0, 40, 22, 40, 22);
            }
            return;
        }
        var embScreen = getEmbScreen();
        if(embScreen != null)
        {
            ISCLPScreen sclpScreen = (ISCLPScreen) embScreen;
            sclpScreen.close();
        }
    }

    private static EmbeddiumVideoOptionsScreen getEmbScreen()
    {
        var embScreen = MinecraftClient.getInstance().currentScreen;
        try
        {
            if(embScreen instanceof EmbeddiumVideoOptionsScreen)
            {
                return ((EmbeddiumVideoOptionsScreen) embScreen);
            }
        }
        catch (Exception e)
        {
            SCLPClientMod.LOGGER.error("[SCLP] Error when rebuild Embeddium Video Options Screen UI", e);
        }
        return null;
    }
}
