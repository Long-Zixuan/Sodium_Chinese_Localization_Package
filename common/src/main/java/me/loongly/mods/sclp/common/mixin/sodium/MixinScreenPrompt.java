package me.loongly.mods.sclp.common.mixin.sodium;

import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPrompt;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPromptable;


@Mixin(ScreenPrompt.class)
public class MixinScreenPrompt
{
    @Final
    @Shadow
    private int width, height;

    @Final
    @Shadow
    private ScreenPromptable parent;
   
    @Final
    @Shadow
    FlatButtonWidget closeButton;

    @Inject(method = "init", at = @At(value = "RETURN"))
    public void injectInit(CallbackInfo c)
    {
        //var scrIns = ((ScreenPrompt)(Object)this);

        var parentDimensions = this.parent.getDimensions();

        int boxX = parentDimensions.getCenterX() - (this.width / 2);
        int boxY = parentDimensions.getCenterY() - (this.height / 2);
       
        this.closeButton = new FlatButtonWidget(new Dim2i((boxX + this.width) - 84, (boxY + this.height) - 24, 80, 20), Component.translatable("sclp.close"), this::close);
    }

    private void close() 
    {
        this.parent.setPrompt(null);
    }

    private static FlatButtonWidget.Style createButtonStyle() 
    {
      FlatButtonWidget.Style style = new FlatButtonWidget.Style();
      style.bgDefault = -13948117;
      style.bgHovered = -13027015;
      style.bgDisabled = style.bgDefault;
      style.textDefault = -1;
      style.textDisabled = style.textDefault;
      return style;
   }
}

//LZX-2026-04-03-001