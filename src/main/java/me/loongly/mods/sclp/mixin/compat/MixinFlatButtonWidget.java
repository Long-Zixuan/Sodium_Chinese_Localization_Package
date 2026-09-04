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
                Class<?> clazz;
            try 
            {
                clazz = Class.forName("net.minecraft.util.text.TranslationTextComponent");//这玩意继承了ITextComponent
            } 
            catch (ClassNotFoundException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
            Constructor<?> constructor;
            try 
            {
                constructor = clazz.getConstructor(String.class, Object[].class);
            } 
            catch (NoSuchMethodException | SecurityException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                    return;
            }
            Object translationTextComponent;
            try 
            {
                translationTextComponent = constructor.newInstance(label, null);
            } 
            catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) 
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                    return;
            }
                field.set((FlatButtonWidget)(Object)this, translationTextComponent);
            }
        }
        catch (NoSuchFieldException | IllegalAccessException e) 
        {
            e.printStackTrace();
        }
    }
    #endif
}