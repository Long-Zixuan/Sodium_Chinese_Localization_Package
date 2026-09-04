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
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

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
			#if BEFORE_18_1
			try
			{
				Class<?> utilClass = Class.forName("net.minecraft.util.Util");
				Method method = utilClass.getMethod("func_110647_a");//getOperatingSystem
				Object os = method.invoke(null);
				Class<?> osClass = os.getClass();//Class.forName("net.minecraft.util.Util$OperatingSystem");
				method = osClass.getMethod("func_195640_a", String.class);//open
				method.invoke(os, "https://long-zixuan.github.io/html/lain.html");
			}
			catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
			#else
			Util.getOperatingSystem()
					.open("https://long-zixuan.github.io/html/lain.html");
			#endif
		}
	}

	public static void birthCaiDan()
	{
		#if BEFORE_18_1
		try
		{
			Class<?> utilClass = Class.forName("net.minecraft.util.Util");
			Method method = utilClass.getMethod("func_110647_a");//getOperatingSystem
			Object os = method.invoke(null);
			Class<?> osClass = os.getClass();//Class.forName("net.minecraft.util.Util$OperatingSystem");
			method = osClass.getMethod("func_195640_a", String.class);//open
			method.invoke(os, "https://www.loongly.me/html/clock.html");
			method.invoke(os, "https://long-zixuan.github.io/html/badapple_h.html");
		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e)
		{
			e.printStackTrace();
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
		#if BEFORE_18_1
		try
		{
			Class<?> utilClass = Class.forName("net.minecraft.util.Util");
			Method method = utilClass.getMethod("func_110647_a");//getOperatingSystem
			Object os = method.invoke(null);
			Class<?> osClass = os.getClass();//Class.forName("net.minecraft.util.Util$OperatingSystem");
			method = osClass.getMethod("func_195640_a", String.class);//open
			method.invoke(os, "https://ifdian.net/a/loongly");
		}
		catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		#else
		Util.getOperatingSystem()
                .open("https://ifdian.net/a/loongly");
		#endif
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
