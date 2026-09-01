package me.loongly.mods.sclp.client;

import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

import me.loongly.mods.sclp.client.gui.SCLPGameOptions;
import me.loongly.mods.sclp.language.I18NLanguage;
import net.minecraft.client.MinecraftClient;
import java.time.LocalDate;
import java.lang.Runtime;

import org.apache.logging.log4j.LogManager;

@Mod(SCLPClientMod.MOD_ID)
@OnlyIn(Dist.CLIENT)
public class SCLPClientMod
{

    public static final String MOD_ID = "sclp";
    private static SCLPGameOptions CONFIG;
    private static Logger LOGGER;

    public static Logger logger()
    {
        if (LOGGER == null)
        {
            LOGGER = LogManager.getLogger("SCLP");
        }

        return LOGGER;
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


	public static SCLPGameOptions options() 
	{
		if(CONFIG == null)
		{
			CONFIG = SCLPGameOptions.load();
			LOGGER.info("[SCLP] Load Sodium Chinese Localized Package Config");
		}
		return CONFIG;
	}

	static int chickCount = 0;
	public static void caiDan()
	{
		chickCount++;
		if (chickCount == 10)
		{
			chickCount = 0;
			Util.getOperatingSystem()
					.open("https://long-zixuan.github.io/html/lain.html");
		}
	}

	public static void birthCaiDan()
	{
		#if BEFORE_18_1
		try
		{
			Runtime.getRuntime().exec("cmd /c start https://www.loongly.me/html/clock.html");
			Runtime.getRuntime().exec("cmd /c start https://long-zixuan.github.io/html/badapple_h.html");//只能在Windows系统生效，凑合一下，未来再反射1.16的函数实现
		}
		catch (Exception e)
		{
			SCLPClientMod.LOGGER.error("[SCLP] Failed to open birthCaiDan",e);
		}
		#else
		Util.getOperatingSystem()
				.open("https://www.loongly.me/html/clock.html");
		Util.getOperatingSystem()
				.open("https://long-zixuan.github.io/html/badapple_h.html");
		#endif
	}

	public static void openSupportPage()
	{
		SCLPClientMod.LOGGER.info("[SCLP] Open Support Page.");
		Util.getOperatingSystem()
                .open("https://ifdian.net/a/loongly");
	}

    public SCLPClientMod()
    {
		I18NLanguage.init();
		var ls = "[SCLP]\r\n"+ //
						"      ____                                    ____ \r\n" + //
						"     /   /                                   /   /   \r\n" + //
						"    /   /    ____________  ______  _____    /   /    ___ ___ \r\n" + //
						"   /   /___ /  _  /  _  / /     / /  _  \\  /   /___ |  //  /\r\n" + //
						"  /_______/ \\____/\\____/ /  /  /  \\__   / /_______/  \\    /\r\n" + //
						" ___________________________________/  /______________/  /\r\n" + //
						"/___LoongLy Software 2026_______________________________/\r\n" + //
						"[SCLP]LoongLy:Sodium Chinese Localized Package init successful!(钠汉化包初始化成功！)\r\n";
		LOGGER.info(ls);
    }
}
