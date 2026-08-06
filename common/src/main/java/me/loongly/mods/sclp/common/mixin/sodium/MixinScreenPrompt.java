package me.loongly.mods.sclp.common.mixin.sodium;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.loongly.mods.sclp.common.language.I18N;
import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPrompt;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPromptable;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPrompt.Action;
import java.lang.reflect.Field;
import java.util.List;


@Mixin(ScreenPrompt.class)
public abstract class MixinScreenPrompt extends ScreenPrompt
{
    public MixinScreenPrompt(ScreenPromptable parent, List<FormattedText> text, int width, int height, Action action) 
    {
        super(parent, text, width, height, action);
        //TODO Auto-generated constructor stub
    }

    @Final
    @Shadow
    private int width, height;
   
    @Shadow
    FlatButtonWidget closeButton;

    @Shadow
    FlatButtonWidget actionButton;
    @Final
    @Shadow
    ScreenPromptable parent;
    @Final
    @Shadow
    List<FormattedText> text;
    @Final
    @Shadow
    Action action;

    @Overwrite
    public void init() 
    {
      Dim2i parentDimensions = this.parent.getDimensions();
      int boxX = parentDimensions.width() / 2 - this.width / 2;
      int boxY = parentDimensions.height() / 2 - this.height / 2;
      this.closeButton = new FlatButtonWidget(new Dim2i(boxX + this.width - 84, boxY + this.height - 24, 80, 20), Component.translatable("sclp.close"), this::close);
      this.closeButton.setStyle(createButtonStyle());

      this.actionButton = new FlatButtonWidget(new Dim2i(boxX + this.width - 198, boxY + this.height - 24, 110, 20), this.action.label(), this::runAction);
      this.actionButton.setStyle(createButtonStyle());
   }

    private void close() 
    {
        this.parent.setPrompt(null);
    }

    private void runAction() 
    {
      this.action.runnable().run();
      this.close();
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