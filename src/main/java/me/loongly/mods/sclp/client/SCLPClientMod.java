package me.loongly.mods.sclp.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.minecraft.util.Util;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.loongly.mods.sclp.client.gui.SCLPGameOptions;
import me.loongly.mods.sclp.language.I18NLanguage;

@Environment(EnvType.CLIENT)
public class SCLPClientMod implements ClientModInitializer
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
		Util.getOperatingSystem()
				.open("https://www.loongly.me/html/clock.html");
		Util.getOperatingSystem()
				.open("https://long-zixuan.github.io/html/badapple_h.html");
	}

	public static void openSupportPage()
	{
		SCLPClientMod.LOGGER.info("[SCLP] Open Support Page.");
		Util.getOperatingSystem()
                .open("https://ifdian.net/a/loongly");
	}

	@Override
	public void onInitializeClient() 
	{
		// TODO Auto-generated method stub
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

	public static boolean isSOA()
    {
        return FabricLoader.getInstance().getModContainer("sodiumoptionsapi").isPresent();
    }

	public static boolean isEmb()
    {
        return FabricLoader.getInstance().getModContainer("embeddium").isPresent();
    }

	public static boolean isNewEmb()
	{
		if(!isEmb())
		{
			return false;
		}

		var emb = FabricLoader.getInstance().getModContainer("embeddium").get();
		try
		{
			return emb.getMetadata().getVersion().compareTo(Version.parse("0.3.16")) >= 0;
		}
		catch (Exception e){}
		return false;
	}
}
