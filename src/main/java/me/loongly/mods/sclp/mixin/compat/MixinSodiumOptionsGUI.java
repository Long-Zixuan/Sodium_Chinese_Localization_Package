package me.loongly.mods.sclp.mixin.compat;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.prompt.ScreenPrompt;
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
import net.minecraft.text.Style;
import net.minecraft.text.OrderedText;

import java.io.IOException;
import java.util.List;

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
                StringVisitable.concat(Text.translatable("sclp.hello")),
                StringVisitable.concat(Text.translatable("sclp.donation.1"), Text.translatable("Embeddium").setStyle(Style.EMPTY.withColor(0x27eb92)), Text.translatable("sclp.donation.2")),
                StringVisitable.concat(Text.translatable("sclp.donation.3"), Text.translatable("sclp.donation.4").setStyle(Style.EMPTY.withColor(0xff6e00)), Text.translatable("sclp.donation.5")),
                StringVisitable.concat(Text.translatable("sclp.donation.6"), Text.translatable("sclp.donation.7").setStyle(Style.EMPTY.withColor(0xed49ce)), Text.translatable("sclp.dot")),
                StringVisitable.concat(Text.translatable("sclp.donation.8"))
        );
    }

    /**
     * @reason 替换默认的 Sodium 捐赠提示，对捐赠按钮进行翻译。
     * @author Loongly
     */
    /*@Overwrite
    private void openDonationPrompt(SodiumGameOptions options) 
    {
        SodiumOptionsGUI sodiumGUI = (SodiumOptionsGUI)((Object)this);
        ScreenPrompt prompt = new ScreenPrompt(sodiumGUI, DONATION_PROMPT_MESSAGE, 320, 190,
                new ScreenPrompt.Action(Text.translatable("Support Sodium"), this::openDonationPage));
        prompt.setFocused(true);

        options.notifications.hasSeenDonationPrompt = true;

        try 
        {
            options.writeChanges();
        } 
        catch (IOException e) 
        {
            SodiumClientMod.logger()
                    .error("Failed to update config file", e);
        }
    }

    private void openDonationPage() 
    {
        net.minecraft.util.Util.getOperatingSystem().open("https://caffeinemc.net/donate");
    }*/
}
