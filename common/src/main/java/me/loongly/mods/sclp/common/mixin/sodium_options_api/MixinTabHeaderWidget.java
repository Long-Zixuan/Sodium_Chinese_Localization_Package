package me.loongly.mods.sclp.common.mixin.sodium_options_api;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.MutableComponent;
import toni.sodiumoptionsapi.util.PlatformUtil;
import me.loongly.mods.sclp.common.language.I18N;
import toni.sodiumoptionsapi.gui.TabHeaderWidget;


@Mixin(value = TabHeaderWidget.class, remap = false)
public abstract class MixinTabHeaderWidget
{

    @Inject(method = "getModName", at = @At(value = "RETURN"), cancellable = true, remap = false)
    private static void mixinGetModName(String modId, CallbackInfoReturnable<String> cir) 
    {
        String modName = cir.getReturnValue();
        switch (modName) 
        {
            case "Sodium/Embeddium Chinese Localized Package":
                modName = "SCLP";
                break;
            case "Sodium Chinese Localized Package":
                modName = "SCLP";
                break;
            case "Sodium Shadowy Path Blocks":
                modName = "SSPB";
                break;
            default:
                break;
        }
        if(SCLPClientMod.options().shouldTransModName)
        {
            modName = I18N.trans(modName);
        }
        cir.setReturnValue(modName);
    }

    /**
     * 防止SSPB无法被翻译
     * 
     * @param modId The mod identifier to create label for
     * @param underline Whether the label should be underlined
     * @return A MutableComponent with the mod label and appropriate styling
     * @author Loongly
     */
    @Overwrite
    public static MutableComponent getLabel(String modId, boolean underline) 
    {
      MutableComponent var10000;
      
      var10000 = idComponent(modId);
      
      return var10000.withStyle((s) -> s.withUnderlined(underline));
   }

    @Shadow
    static MutableComponent idComponent(String namespace)
    {
        return null; 
    }
}
