package me.loongly.mods.sclp.mixin.compat;

import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import me.loongly.mods.sclp.language.I18N;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.SCLPUIBuilder;

@Mixin(FlatButtonWidget.class)
public class MixinFlatButtonWidget
{
    #if BEFORE_18_1
    @Inject(method = "<init>", at = @At(value = "RETURN"))
    public void InjectInit(Dim2i dim, String label, Runnable action, CallbackInfo ci)
    {
        if(!SCLPClientMod.options().sclpOn)
        {
            return;
        }
        try 
        {
            Field field = FlatButtonWidget.class.getDeclaredField("label");
            field.setAccessible(true);
            Object l = field.get((FlatButtonWidget)(Object)this);
            if(l instanceof String)
            {
                field.set((FlatButtonWidget)(Object)this, I18N.trans(label));
            }
            else
            {
                Object translationTextComponent = SCLPUIBuilder.create1_16TextComponent(I18N.trans(label));
                field.set((FlatButtonWidget)(Object)this, translationTextComponent);
            }
        }
        catch (NoSuchFieldException | IllegalAccessException e) 
        {
            SCLPClientMod.logger().error("[SCLP] :",e);//warn
        }
    }
    #endif
}