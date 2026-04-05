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


@Mixin(ScreenPrompt.class)
public class MixinScreenPrompt
{
    @Final @Shadow(remap = false)
    private int width; 
    
    @Final @Shadow(remap = false)
    private int height;

    @Final @Shadow(remap = false)
    private ScreenPromptable parent;
   
    @Shadow(remap = false)
    private FlatButtonWidget closeButton;

    @Inject(method = "init", at = @At(value = "RETURN"),remap = false)
    public void injectInit(CallbackInfo c)
    {
        //var scrIns = ((ScreenPrompt)(Object)this);
        var parentDimensions = this.parent.getDimensions();

        int boxX = (parentDimensions.width() / 2) - (width / 2);
        int boxY = (parentDimensions.height() / 2) - (height / 2);

        this.closeButton = new FlatButtonWidget(new Dim2i((boxX + width) - 84, (boxY + height) - 24, 80, 20), Component.translatable("sclp.close"), this::close);
        this.closeButton.setStyle(createButtonStyle());
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