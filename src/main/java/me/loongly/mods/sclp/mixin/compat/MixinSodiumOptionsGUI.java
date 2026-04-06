package me.loongly.mods.sclp.mixin.compat;

import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.SCLPGameOptionPages;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.Style;

@Mixin(value = SodiumOptionsGUI.class, remap = false)
public class MixinSodiumOptionsGUI 
{

    @Shadow
    @Final
    private List<OptionPage> pages;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo info)
    {
        this.pages.add(SCLPGameOptionPages.SCLPPage());
    }

      @Shadow
    @Final
    public static final List<StringVisitable> DONATION_PROMPT_MESSAGE;

     static {
        DONATION_PROMPT_MESSAGE = List.of(
                StringVisitable.concat(Text.literal("您好！")),
                StringVisitable.concat(Text.literal("看起来您十分中意"), Text.literal("Embeddium模组").setStyle(Style.EMPTY.withColor(0x27eb92)), Text.literal("，这是我的世界那钠模组的一个分支。")),
                StringVisitable.concat(Text.literal("钠是十分复杂的，需要"), Text.literal("上千小时").setStyle(Style.EMPTY.withColor(0xff6e00)), Text.literal("来开发、测试，以达到玩家预期的效果。")),
                StringVisitable.concat(Text.literal("如果您喜欢这个模组，不妨"), Text.literal("Support Sodium（支持钠）").setStyle(Style.EMPTY.withColor(0xed49ce)), Text.literal("。")),
                StringVisitable.concat(Text.literal("最后再一次感谢您，希望这个模组可以帮助您（和您的电脑。）"))
        );
    }
}
