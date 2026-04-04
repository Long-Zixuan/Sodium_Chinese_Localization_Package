package loongly.sclp.common.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import loongly.sclp.common.client.gui.SCLPGameOptions;
import net.minecraft.Util;


public class SCLPClientMod {

	public static final Logger LOGGER = LoggerFactory.getLogger("SCLP");

	private static SCLPGameOptions CONFIG;


	public static SCLPGameOptions options() 
	{
		return CONFIG;
	}

	public static void onInitClient() 
	{
		CONFIG = SCLPGameOptions.load();

		LOGGER.info("Init SCLP Config");
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
}
