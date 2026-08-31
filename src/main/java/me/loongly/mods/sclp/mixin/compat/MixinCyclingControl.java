package me.loongly.mods.sclp.mixin.compat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Field;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.architectury.patchedmixin.staticmixin.spongepowered.asm.mixin.Final;
import dev.architectury.patchedmixin.staticmixin.spongepowered.asm.mixin.Shadow;
import me.jellysquid.mods.sodium.client.gui.options.Option;

import me.loongly.mods.sclp.language.I18N;

import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;

@Mixin(value = CyclingControl.class, remap = false)
public class MixinCyclingControl<T>
{
    #if BEFORE_18_1

    @Shadow @Final
    String[] names;

    @Inject( 
        method = "<init>(Lme/jellysquid/mods/sodium/client/gui/options/Option;Ljava/lang/Class;[Ljava/lang/String;)V",
        at = @At("RETURN"),
        require = 0
    )
    private void injectInit(Option<T> option, Class<T> enumType, String[] names,CallbackInfo ci)
    {
        /*
        也是无心插柳柳成荫，之前的这里的逻辑没有类型判断逻辑,
        这也是为什么2.0.9加载其他Embeddium的子模组游戏会崩溃的原因（因为调用了这个函数,而新版本的模组内部已经不会调用这个函数了），
        优化的时候顺带写了类型判断逻辑，顺带修复了一个bug
         */
        if(names == null)
        {
            return;//新版本names为ITextComponent[]，所以注入的时候这里是null
        }
        for (int i = 0; i < names.length; i++)
        {
            names[i] = I18N.trans(names[i]);
        }
    }

     @Inject(
        method = "<init>(Lme/jellysquid/mods/sodium/client/gui/options/Option;Ljava/lang/Class;)V",
        at = @At("RETURN")
    )
    private void injectInit2(Option<T> option, Class<T> enumType,CallbackInfo ci)
    {
        if(names == null)
        {
            return;
        }
        for (int i = 0; i < names.length; i++)
        {
            names[i] = I18N.trans(names[i]);
        }
    }
    #endif
}    
