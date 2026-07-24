package me.loongly.mods.sclp.common.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.loongly.mods.sclp.common.client.options.SCLPOptions;
import me.loongly.mods.sclp.common.language.I18NLanguage;


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
		var ls = 		"	____                                       ____ \r\n" + //
						"   /   /                                      /   /   \r\n" + //
						"  /   /    _____   _____    ______  _____    /   /    __   __ \r\n" + //
						" /   /___ /  _  \\ /  _  \\  /     / /  _  \\  /   /___ \\  \\/  /\r\n" + //
						"/_______/ \\____/  \\____/  /  /  /  \\___  / /_______/  \\    /\r\n" + //
						"                                    __/ /            __/  /\r\n" + //
						"                                   /___/            /___/";
		LOGGER.info(ls);
		LOGGER.info("[SCLP] LoongLy:Sodium Chinese Localized Package init!");
		LOGGER.info("[SCLP] LoongLy:钠汉化包初始化成功！");
		I18NLanguage.init();
	}

	static int chickCount = 0;
	public static void caiDan()
	{
		chickCount++;
		if (chickCount == 10)
		{
			chickCount = 0;
			net.minecraft.util.Util.getPlatform()
					.openUri("https://long-zixuan.github.io/html/lain.html");
		}
	}

	public static void birthCaiDan()
	{
		net.minecraft.util.Util.getPlatform()
				.openUri("https://www.loongly.me/html/clock.html");
		net.minecraft.util.Util.getPlatform()
				.openUri("https://long-zixuan.github.io/html/badapple_h.html");
	}
}

/*
    ____                                       ____ 
   /   /                                      /   /   
  /   /    _____   _____    ______  _____    /   /    __   __ 
 /   /___ /  _  \ /  _  \  /     / /  _  \  /   /___ \  \/  /
/_______/ \____/  \____/  /  /  /  \___  / /_______/  \    /
                                    __/ /            __/  /
                                   /___/            /___/
 */