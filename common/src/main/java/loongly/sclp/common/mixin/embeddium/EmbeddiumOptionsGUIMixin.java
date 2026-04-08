package loongly.sclp.common.mixin.embeddium;

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

import net.minecraft.resources.ResourceLocation;
import org.embeddedt.embeddium.api.options.OptionIdentifier;
import org.embeddedt.embeddium.impl.gui.frame.AbstractFrame;
import org.embeddedt.embeddium.impl.gui.frame.BasicFrame;
import org.embeddedt.embeddium.impl.gui.frame.components.SearchTextFieldComponent;
import org.embeddedt.embeddium.impl.gui.frame.components.SearchTextFieldModel;
import org.embeddedt.embeddium.impl.gui.frame.tab.Tab;
import org.embeddedt.embeddium.impl.gui.frame.tab.TabFrame;
import org.embeddedt.embeddium.impl.gui.screen.PromptScreen;
import org.embeddedt.embeddium.impl.gui.theme.DefaultColors;
import org.embeddedt.embeddium.impl.render.ShaderModBridge;
import org.embeddedt.embeddium.impl.util.PlatformUtil;

import org.embeddedt.embeddium.impl.Embeddium;
import org.embeddedt.embeddium.api.OptionGUIConstructionEvent;
import org.embeddedt.embeddium.impl.data.fingerprint.HashedFingerprint;
import org.embeddedt.embeddium.impl.gui.console.Console;
import org.embeddedt.embeddium.impl.gui.console.message.MessageLevel;
import org.embeddedt.embeddium.api.options.structure.Option;
import org.embeddedt.embeddium.api.options.structure.OptionFlag;
import org.embeddedt.embeddium.api.options.structure.OptionGroup;
import org.embeddedt.embeddium.api.options.structure.OptionStorage;
import org.embeddedt.embeddium.impl.gui.widgets.FlatButtonWidget;
import org.embeddedt.embeddium.impl.gui.EmbeddiumVideoOptionsScreen;

import net.minecraft.network.chat.FormattedText;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;

@Mixin(EmbeddiumVideoOptionsScreen.class)
public abstract class EmbeddiumOptionsGUIMixin extends Screen
{
    protected EmbeddiumOptionsGUIMixin(Component title) 
    {
        super(title);
        //TODO Auto-generated constructor stub
    }

    @Final @Shadow(remap = false)
    private static final List<FormattedText> DONATION_PROMPT_MESSAGE;

    @Shadow(remap = false)
    private static final ResourceLocation LOGO_LOCATION = ResourceLocation.fromNamespaceAndPath("sclp", "textures/embeddium/gui/logo_transparent.png");

    static { //这里文本以后要改
        DONATION_PROMPT_MESSAGE = List.of(
                FormattedText.composite(Component.translatable("sclp.hello")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.1"), Component.translatable("Sodium").withColor(0x27eb92), Component.translatable("sclp.donation.prompt.2")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.3"), Component.translatable("sclp.donation.thousand_hours").withColor(0xff6e00), Component.translatable("sclp.donation.prompt.4")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.5"), Component.translatable("sclp.donation.buycoffee").withColor(0xed49ce), Component.literal(".")),
                FormattedText.composite(Component.translatable("sclp.donation.prompt.6"))
        );
    }

    //@Redirect(method = "openDonationPrompt", at = @At(value = "INVOKE"),remap = false)
     /**
     * @reason 替换默认的 Sodium 捐赠提示，对捐赠按钮进行翻译。
     * @author Loongly
     */
    @Overwrite(remap = false)
     private void openDonationPrompt() //这里文本以后要改
     {
        var prompt = new PromptScreen(this, DONATION_PROMPT_MESSAGE, 320, 190,
                new PromptScreen.Action(Component.translatable("sclp.donation.buycoffee2"), this::openDonationPage));
        this.minecraft.setScreen(prompt);
    }

    private void openDonationPage()
    {
        Util.getPlatform().openUri("https://caffeinemc.net/donate");
    }
}
