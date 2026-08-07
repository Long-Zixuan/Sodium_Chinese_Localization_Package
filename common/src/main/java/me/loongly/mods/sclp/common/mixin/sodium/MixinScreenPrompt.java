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
public abstract class MixinScreenPrompt//不继承ScreenPrompt，原因未知（具体是public @NotNull List<AbstractWidget> getWidgets()函数报错了），但是继承了之后对init的overwrite不起作用，原因未知
{
    @Shadow @Final
    FlatButtonWidget closeButton;

    @Inject(method = "init", at = @At(value = "RETURN"))
    public void injectInit(CallbackInfo c)
    {
      Class<?> clazz = closeButton.getClass();
      try 
      {
        Field field = clazz.getDeclaredField("label");
        field.setAccessible(true);
        field.set(closeButton, Component.literal(I18N.trans("sclp.close")));
      }
      catch (Exception e) 
      {
        e.printStackTrace();
      }
    }
}

//LZX-2026-04-03-001