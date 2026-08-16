package me.loongly.mods.sclp.client;

import net.caffeinemc.caffeineconfig.CaffeineConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Logger;

import me.loongly.mods.sclp.client.gui.SCLPGameOptions;
import me.loongly.mods.sclp.language.I18NLanguage;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class SCLPClientMod 
{
    public static final Logger LOGGER = LogManager.getLogger("Sodium Chinese Localization Pack");
    private static SCLPGameOptions CONFIG;
    private static CaffeineConfig MIXIN_CONFIG;

    public static SCLPGameOptions options() 
    {
        if (CONFIG == null) 
        {
            CONFIG = loadConfig();
        }

        return CONFIG;
    }

    static int chickCount = 0;
	public static void caiDan() throws Exception
	{
		chickCount++;
		if (chickCount == 10)
		{
            net.minecraft.util.Util.getOperatingSystem().open("https://long-zixuan.github.io/html/lain.html");
			chickCount = 0;
        }
	}

    public static void birthCaiDan()
	{
        net.minecraft.util.Util.getOperatingSystem().open("https://long-zixuan.github.io/html/clock.html");
        net.minecraft.util.Util.getOperatingSystem().open("https://long-zixuan.github.io/html/badapple_h.html");
	}

    public static CaffeineConfig mixinConfig() 
    {
        if (MIXIN_CONFIG == null) 
        {
            MIXIN_CONFIG = CaffeineConfig.builder("Sodium Chinese Localization Pack").withSettingsKey("sclp:options")

                    .withLogger(SCLPClientMod.LOGGER)
                    .build(FMLPaths.CONFIGDIR.get().resolve("sclp.properties"));
        }
        return MIXIN_CONFIG;
    }

    private static SCLPGameOptions loadConfig() 
    {
        return SCLPGameOptions.load(FMLPaths.CONFIGDIR.get().resolve("sodium-chinese-pack.toml").toFile());
    }

    public static boolean isXenon()
    {
        var modName = ModList.get().getModContainerById("xenon").map(container -> container.getModInfo().getDisplayName()).orElse(null);
        if(modName != null)
        {
            return true;
        }
        return false;
    }

    public static boolean isNewEmbeddium()
    {
        try
        {
            Class.forName("org.embeddedt.embeddium.gui.frame.BasicFrame");
            return true;
        }
        catch (ClassNotFoundException e)
        {

        }
        return false;
    }

    public static boolean isMyBirthday(int year, int month, int day)
    {
        return month == 4 && day == 4;
    }

	public static boolean isMyBirthday()
    {
		LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        return isMyBirthday(year, month, day);
    }

    public static void openSupportWeb()
    {
        SCLPClientMod.LOGGER.info("[SCLP] Open Support website.");
        net.minecraft.util.Util.getOperatingSystem().open("https://ifdian.net/a/loongly");
    }

    public SCLPClientMod() 
    {
		
    }
}
