package loongly.sclp.common.client;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import loongly.sclp.common.client.gui.SCLPGameOptions;
import net.minecraft.Util;


public class SCLPClientMod {

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
			LOGGER.info("Init SCLP Config");
		}
		return CONFIG;
	}

	public static void onInitClient() 
	{
		//CONFIG = SCLPGameOptions.load();

		LOGGER.info("Init SCLP Client");
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
