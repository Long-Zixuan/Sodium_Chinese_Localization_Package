package loongly.sclp.common.mixin.embeddium;
import loongly.sclp.common.client.SCLPClientMod;

import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.embeddedt.embeddium.impl.gui.widgets.AbstractWidget;
import org.embeddedt.embeddium.impl.gui.widgets.FlatButtonWidget;
import org.embeddedt.embeddium.api.math.Dim2i;
import org.embeddedt.embeddium.impl.gui.screen.PromptScreen;
import net.minecraft.client.gui.screens.Screen;


@Mixin(PromptScreen.class)
public class MixinScreenPrompt extends Screen
{
    protected MixinScreenPrompt(Component title) 
    {
        super(title);
        //TODO Auto-generated constructor stub
    }

    @Final @Shadow(remap = false)
    private int promptWidth; 
    
    @Final @Shadow(remap = false)
    private int promptHeight;

    @Final @Shadow(remap = false)
    private Screen prevScreen;
   
    @Shadow(remap = false)
    private FlatButtonWidget closeButton;

    @Inject(method = "init", at = @At(value = "RETURN"),remap = false)
    public void injectInit(CallbackInfo c)
    {
        this.closeButton.setVisible(false);
        var scrIns = ((PromptScreen)(Object)this);

        int boxX = (prevScreen.width / 2) - (promptWidth / 2);
        int boxY = (prevScreen.height / 2) - (promptHeight / 2);

        this.closeButton = new FlatButtonWidget(new Dim2i((boxX + promptWidth) - 84, (boxY + promptHeight) - 24, 80, 20), Component.translatable("sclp.close"), scrIns::onClose);
        this.closeButton.setStyle(createButtonStyle());
        this.addRenderableWidget(this.closeButton);
    }

   private static FlatButtonWidget.Style createButtonStyle() 
   {
        var style = new FlatButtonWidget.Style();
        style.bgDefault = 0xff2b2b2b;
        style.bgHovered = 0xff393939;
        style.bgDisabled = style.bgDefault;

        style.textDefault = 0xFFFFFFFF;
        style.textDisabled = style.textDefault;

        return style;
    }
}

//LZX-2026-04-03-001