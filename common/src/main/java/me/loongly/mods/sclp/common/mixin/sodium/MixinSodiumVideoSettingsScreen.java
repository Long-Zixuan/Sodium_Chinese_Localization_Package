package me.loongly.mods.sclp.common.mixin.sodium;
import net.caffeinemc.mods.sodium.client.SodiumClientMod;

import net.caffeinemc.mods.sodium.client.config.builder.PageBuilderImpl;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen;
import net.caffeinemc.mods.sodium.client.gui.widgets.FlatButtonWidget;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.Util;
import net.caffeinemc.mods.sodium.client.gui.SodiumOptions;
import net.caffeinemc.mods.sodium.client.gui.prompt.ScreenPrompt;


@Mixin(VideoSettingsScreen.class)
public class MixinSodiumVideoSettingsScreen
{
    FlatButtonWidget birthBtn;

    @Final
    @Shadow
    boolean insetX;

    final int BIRTH_BTN_WIDTH = 40;
    final int BIRTH_BTN_HEIGHT = 20;

    @Inject(method = "rebuild", at = @At(value = "TAIL"))//CallbackInfoReturnable<PageBuilderImpl>
    public void injectRebuild(CallbackInfo c) 
    {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        if(isMyBirthday(year, month, day))
        {
            var videoSettingsScrIns = ((VideoSettingsScreen)(Object)this);
            this.birthBtn = new FlatButtonWidget(new Dim2i(videoSettingsScrIns.getLimitX() - BIRTH_BTN_WIDTH - ifNotInsetX(5), videoSettingsScrIns.getY() + BIRTH_BTN_HEIGHT, BIRTH_BTN_WIDTH, BIRTH_BTN_HEIGHT), Component.literal("🎂:" + (year - 2004)), SCLPClientMod::birthCaiDan, true, false);
            videoSettingsScrIns.addRenderableWidget(this.birthBtn);
        }
    }

    private int ifNotInsetX(int value) 
    {
        return this.insetX ? 0 : value;
    }

    boolean isMyBirthday(int year, int month, int day)
    {
        return month == 4 && day == 4;
    }

    private static final List<FormattedText> DONATION_PROMPT_I18N_MESSAGE;

    static {
        DONATION_PROMPT_I18N_MESSAGE = List.of(
                FormattedText.composite(Component.translatable("sclp.hello")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.1"), Component.translatable("Sodium").withColor(0x27eb92), Component.translatable("sclp.donation.prompt.2")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.3"), Component.translatable("sclp.donation.thousand_hours").withColor(0xff6e00), Component.translatable("sclp.donation.prompt.4")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.5"), Component.translatable("sclp.donation.buycoffee").withColor(0xed49ce), Component.literal(".")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.6"))
        );
    }

    @Redirect(method = "openDonationPrompt", at = @At(value = "INVOKE",target = "Lnet/caffeinemc/mods/sodium/client/gui/SodiumOptions;writeToDisk(Lnet/caffeinemc/mods/sodium/client/gui/SodiumOptions;)V"))
    public void mixinOpenDonationPrompt(SodiumOptions options) 
    {
        var videoSettingsScrIns = ((VideoSettingsScreen)(Object)this);
        var prompt = new ScreenPrompt(videoSettingsScrIns, DONATION_PROMPT_I18N_MESSAGE, 320, 190,
                new ScreenPrompt.Action(Component.translatable("sclp.donation.buycoffee2"), this::openDonationPage));
        prompt.setFocused(true);

        options.notifications.hasSeenDonationPrompt = true;

        try 
        {
            SodiumOptions.writeToDisk(options);
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

//LZX-2026-04-03-001