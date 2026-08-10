package me.loongly.mods.sclp.common.client;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.loongly.mods.sclp.common.client.gui.ECLPGameOptionPages;
import me.loongly.mods.sclp.common.client.gui.SCLPGameOptions;
import net.minecraft.Util;
import me.loongly.mods.sclp.common.language.I18NLanguage;


public class SCLPClientMod 
{

	public static final String MOD_ID = "sclp";

	public static final Logger LOGGER = LoggerFactory.getLogger("SCLP");

	private static SCLPGameOptions CONFIG;

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

	public static void onInitClient() 
	{
		//CONFIG = SCLPGameOptions.load();
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

	static int chickCount = 0;
	public static void caiDan()
	{
		chickCount++;
		if (chickCount == 10)
		{
			chickCount = 0;
			Util.getPlatform()
					.openUri("https://long-zixuan.github.io/html/lain.html");
		}
	}

	public static void birthCaiDan()
	{
		Util.getPlatform()
				.openUri("https://www.loongly.me/html/clock.html");
		Util.getPlatform()
				.openUri("https://long-zixuan.github.io/html/badapple_h.html");
	}

	public static boolean isEmbeddium()
	{
		try
		{
			Class.forName("org.embeddedt.embeddium.impl.gui.EmbeddiumGameOptionPages");
			return true;
		}
		catch (ClassNotFoundException e)
		{
			return false;
		}
	}

	public static void openSupportPage()
	{
		SCLPClientMod.LOGGER.info("[SCLP] Open Support Page.");
		Util.getPlatform()
                .openUri("https://ifdian.net/a/loongly");
	}
}
