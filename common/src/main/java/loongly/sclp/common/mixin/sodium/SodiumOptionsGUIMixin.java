package loongly.sclp.common.mixin.sodium;

import net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI;
import net.caffeinemc.mods.sodium.client.gui.options.OptionPage;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import loongly.sclp.common.client.gui.SCLPGameOptionPages;
import loongly.sclp.common.client.SCLPClientMod;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import net.caffeinemc.mods.sodium.client.SodiumClientMod;
import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptions;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPrompt;

import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.Util;

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;

@Mixin(SodiumOptionsGUI.class)
public abstract class SodiumOptionsGUIMixin 
{


    void birthCaiDan()
    {
        
    }

    boolean isMyBirthday(int year, int month, int day)
    {
        return month == 4 && day == 4;
    }
    @Final @Shadow(remap = false)
    private static final List<FormattedText> DONATION_PROMPT_MESSAGE;

    static {
        DONATION_PROMPT_MESSAGE = List.of(
                FormattedText.composite(Component.translatable("sclp.hello")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.1"), Component.translatable("Sodium").withColor(0x27eb92), Component.translatable("sclp.donation.prompt.2")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.3"), Component.translatable("sclp.donation.thousand_hours").withColor(0xff6e00), Component.translatable("sclp.donation.prompt.4")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.5"), Component.translatable("sclp.donation.buycoffee").withColor(0xed49ce), Component.literal(".")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.6"))
        );
    }

    @Final @Shadow(remap = false)
    private List<OptionPage> pages;


    @Inject(method = "<init>*", at = @At("TAIL"))
    private void addLSDCOptionPage(CallbackInfo ci)
    {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();


        if (isMyBirthday(year, month, day))
        {
            this.pages.add(SCLPGameOptionPages.birth());
        }
    }

    //@Redirect(method = "openDonationPrompt", at = @At(value = "INVOKE"),remap = false)
     /**
     * @reason 替换默认的 Sodium 捐赠提示，对捐赠按钮进行翻译。
     * @author Loongly
     */
    @Overwrite(remap = false)
    private void openDonationPrompt(SodiumGameOptions options) 
    {
        var videoSettingsScrIns = ((SodiumOptionsGUI)(Object)this);
        var prompt = new ScreenPrompt(videoSettingsScrIns, DONATION_PROMPT_MESSAGE, 320, 190,
                new ScreenPrompt.Action(Component.translatable("sclp.donation.buycoffee2"), this::openDonationPage));
        prompt.setFocused(true);

        options.notifications.hasSeenDonationPrompt = true;

        try 
        {
            SodiumGameOptions.writeToDisk(options);
        } 
        catch (IOException e) 
        {
            SodiumClientMod.logger()
                    .error("Failed to update config file", e);
        }
    }

    private void openDonationPage()
    {
        Util.getPlatform().openUri("https://caffeinemc.net/donate");
    }
}
