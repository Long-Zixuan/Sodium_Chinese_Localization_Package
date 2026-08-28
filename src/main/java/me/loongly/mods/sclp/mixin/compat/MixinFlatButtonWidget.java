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

@Mixin(FlatButtonWidget.class)
public class MixinFlatButtonWidget//1.16.5的高版本铷，构造函数label是ITextComponent类型，所以不兼容，故屏蔽
{
    @Inject(method = "<init>", at = @At(value = "RETURN"))
    public void InjectInit(Dim2i dim, String label, Runnable action, CallbackInfo ci)
    {
        try 
        {
            Field field = FlatButtonWidget.class.getDeclaredField("label");
            field.setAccessible(true);
            field.set((FlatButtonWidget)(Object)this, I18N.trans(label));
        }
        catch (NoSuchFieldException | IllegalAccessException e) 
        {
            e.printStackTrace();
        }
    }
}
