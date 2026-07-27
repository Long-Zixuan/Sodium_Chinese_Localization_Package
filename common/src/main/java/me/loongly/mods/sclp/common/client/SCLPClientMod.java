package me.loongly.mods.sclp.common.client;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.loongly.mods.sclp.common.client.options.SCLPOptions;
import me.loongly.mods.sclp.common.language.I18NLanguage;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Util;


public class SCLPClientMod
{

	public static final Logger LOGGER = LoggerFactory.getLogger("SCLP");

	private static final SCLPOptions CONFIG = SCLPOptions.load();

	public static final String MOD_ID = "sclp";


	public static SCLPOptions options() 
	{
		return CONFIG;
	}

	public static void onInitClient() 
	{
		LOGGER.info("[SCLP]Sodium Chinese Localized Package start init");
		I18NLanguage.init();
		var ls = "[SCLP]\r\n"+ //
						"      ____                                    ____ \r\n" + //
						"     /   /                                   /   /   \r\n" + //
						"    /   /    ____________  ______  _____    /   /    ___ ___ \r\n" + //
						"   /   /___ /  _  /  _  / /     / /  _  \\  /   /___ |  //  /\r\n" + //
						"  /_______/ \\____/\\____/ /  /  /  \\__   / /_______/  \\    /\r\n" + //
						" ___________________________________/  /______________/  /\r\n" + //
						"/___LoongLy Software____________________________________/\r\n" + //
						"[SCLP]LoongLy:Sodium Chinese Localized Package init successful!(钠汉化包初始化成功！)\r\n";
		LOGGER.info(ls);
	}

	static int chickCount = 0;
	public static void caiDan()
	{
		chickCount++;
		if (chickCount == 10)
		{
			LOGGER.info("[SCLP]Open Lain's website.");
			chickCount = 0;
			Util.getPlatform()
					.openUri("https://long-zixuan.github.io/html/lain.html");
		}
	}

	public static void birthCaiDan()
	{
		LOGGER.info("[SCLP]Happly birthday to LoongLy!!!");
		Util.getPlatform()
				.openUri("https://www.loongly.me/html/clock.html");
		Util.getPlatform()
				.openUri("https://long-zixuan.github.io/html/badapple_h.html");
	}

	public static void openSupportWeb(Screen screen)
	{
		LOGGER.info("[SCLP]Open Support website.");
		Util.getPlatform().openUri("https://ifdian.net/a/loongly");
	}

	public static void openSupportWeb()//未来预留
	{
		LOGGER.info("[SCLP]Open Support website.");
		Util.getPlatform().openUri("https://ifdian.net/a/loongly");
	}
}

/*
      ____                                    ____ 
     /   /                                   /   /   
    /   /    ____________  ______  _____    /   /    ___ ___ 
   /   /___ /  _  /  _  / /     / /  _  \  /   /___ |  //  /
  /_______/ \____/\____/ /  /  /  \__   / /_______/  \    /
 ___________________________________/  /______________/  /
/___LoongLy Software____________________________________/
 */