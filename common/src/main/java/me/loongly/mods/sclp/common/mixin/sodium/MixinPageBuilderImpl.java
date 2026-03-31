package me.loongly.mods.sclp.common.mixin.sodium;

import net.caffeinemc.mods.sodium.client.config.builder.PageBuilderImpl;
import net.minecraft.network.chat.Component;

import java.util.Dictionary;
import java.util.HashMap;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PageBuilderImpl.class)
public class MixinPageBuilderImpl
{
    @Final
    @Shadow
    private Component name;

    private HashMap<String, String> transMap = new HashMap<String,String>(){
        {
            put("Settings","sclp.settings"); //反正就很离谱啊，Iris最新版本还是这种硬编码
            put("General","stat.generalButton"); // 0.8.0-0.8.2的钠也是这种硬编码
        }
    };

    @Inject(method = "setName", at = @At(value = "RETURN"),cancellable = true)
    public void injectSetName(Component name, CallbackInfoReturnable<PageBuilderImpl> c) 
    {
        if (transMap.containsKey(name.getString())) 
        {
            name = Component.translatable(transMap.get(name.getString()));
            this.name = name;
        }
    }
}

//LZX-2025-12-31-001
