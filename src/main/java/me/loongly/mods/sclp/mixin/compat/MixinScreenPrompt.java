package me.loongly.mods.sclp.mixin.compat;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.prompt.ScreenPrompt;

import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget.Style;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.SCLPGameOptionPages;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;
import net.minecraft.util.Util;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.OrderedText;
import me.jellysquid.mods.sodium.client.gui.prompt.ScreenPromptable;

import java.io.IOException;
import java.util.List;

@Mixin(value = ScreenPrompt.class)
public class MixinScreenPrompt
{
    /*@Final @Shadow(remap = false)
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
        var parentDimensions = parent.getDimensions();
        int boxX = (parentDimensions.width() / 2) - (width / 2);
        int boxY = (parentDimensions.height() / 2) - (height / 2);

        this.closeButton = new FlatButtonWidget(new Dim2i((boxX + width) - 84, (boxY + height) - 24, 80, 20), Text.translatable("Close"), this::close);
        this.closeButton.setStyle(createButtonStyle());
    }

    private void close() 
    {
        this.parent.setPrompt(null);
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
    }*/
}
