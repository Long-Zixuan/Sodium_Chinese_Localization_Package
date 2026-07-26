package me.loongly.mods.sclp.common.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.loongly.mods.sclp.common.client.options.SCLPOptions;


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
		var ls = "[SCLP]\r\n"+ //
						"      ____                                       ____ \r\n" + //
						"     /   /                                      /   /   \r\n" + //
						"    /   /    _____   _____    ______  _____    /   /   ___  ___ \r\n" + //
						"   /   /___ /  _  \\ /  _  \\  /     / /  _  \\  /   /___ \\  \\/  /\r\n" + //
						"  /_______/ \\____/  \\____/  /  /  /  \\__   / /_______/  \\    /\r\n" + //
						" ______________________________________/  /______________/  /\r\n" + //
						"/___LoongLy Software_______________________________________/\r\n" + //
						"[SCLP]LoongLy:Sodium Chinese Localized Package init!(钠汉化包初始化成功！)\r\n";
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
			net.minecraft.util.Util.getPlatform()
					.openUri("https://long-zixuan.github.io/html/lain.html");
		}
	}

	public static void birthCaiDan()
	{
		LOGGER.info("[SCLP]Happly birthday to LoongLy!!!");
		net.minecraft.util.Util.getPlatform()
				.openUri("https://www.loongly.me/html/clock.html");
		net.minecraft.util.Util.getPlatform()
				.openUri("https://long-zixuan.github.io/html/badapple_h.html");
	}
}

/*
      ____                                       ____ 
     /   /                                      /   /   
    /   /    _____   _____    ______  _____    /   /   ___  ___ 
   /   /___ /  _  \ /  _  \  /     / /  _  \  /   /___ \  \/  /
  /_______/ \____/  \____/  /  /  /  \__   / /_______/  \    /
 ______________________________________/  /______________/  /
/___LoongLy Software_______________________________________/
 */