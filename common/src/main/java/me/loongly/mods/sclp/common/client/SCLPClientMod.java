package me.loongly.mods.sclp.common.client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.loongly.mods.sclp.common.client.options.SCLPOptions;
import me.loongly.mods.sclp.common.language.I18NLanguage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Util;
import java.net.URI;
import com.mojang.blaze3d.Blaze3D;


public class SCLPClientMod
{
	public static final String MOD_ID = "sclp";

	public static final String MOD_NAME = "SCLP";

	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	private static final SCLPOptions CONFIG = SCLPOptions.load();

	public static SCLPOptions options() 
	{
		return CONFIG;
	}

	public static Logger logger() 
	{
        if (LOGGER == null) 
		{
            throw new IllegalStateException("[SCLP] Logger not yet available");
        }

        return LOGGER;
    }

	public static void onInitClient() 
	{
		LOGGER.info("[SCLP]Sodium Chinese Localized Package start init");
		I18NLanguage.init();
var ls = """
[SCLP]
      ____                                    ____ 
     /   /                                   /   /   
    /   /    ____________  ______  _____    /   /    ___ ___ 
   /   /___ /  _  /  _  / /     / /  _  \\  /   /___ |  //  /
  /_______/ \\____/\\____/ /  /  /  \\__   / /_______/  \\    /
 ___________________________________/  /______________/  /
/___LoongLy Software 2026_______________________________/
[SCLP]LoongLy:Sodium Chinese Localized Package init successful!(钠汉化包初始化成功！)
        """;


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
			openWeb("https://long-zixuan.github.io/html/lain.html");
		}
	}

	public static void birthCaiDan()
	{
		LOGGER.info("[SCLP]Happly birthday to LoongLy!!!");
		openWeb("https://www.loongly.me/html/clock.html");
		openWeb("https://long-zixuan.github.io/html/badapple_h.html");
	}

	public static void birthCaiDan(Screen screen)
	{
		LOGGER.info("[SCLP]Happly birthday to LoongLy!!!");
		openWeb("https://www.loongly.me/html/clock.html");
		openWeb("https://long-zixuan.github.io/html/badapple_h.html");
	}

	public static void openSupportWeb(Screen screen)
	{
		LOGGER.info("[SCLP]Open Support website.");
		openWeb("https://ifdian.net/a/loongly");
	}

	public static void openSupportWeb()//未来预留
	{
		LOGGER.info("[SCLP]Open Support website.");
		openWeb("https://ifdian.net/a/loongly");
	}

	private static void openWeb(String url)
	{
		try
		{
			Class<?> blaze3dClazz = Class.forName("com.mojang.blaze3d.Blaze3D",false,SCLPClientMod.class.getClassLoader());
			var uri = URI.create(url);
			Method openUri = blaze3dClazz.getDeclaredMethod("openUri", URI.class);
			Blaze3D.openUri(uri);
			LOGGER.info("[SCLP]MC26.3 Open Uri:" + url);
			return;//26.3的情况
		}
		catch (ClassNotFoundException | NoSuchMethodException e1)
		{

		}
		try
		{
			Class<?> osClazz = Class.forName("net.minecraft.util.Util$OS",false,SCLPClientMod.class.getClassLoader());
			Method openUri = osClazz.getDeclaredMethod("openUri", String.class);
			openUri.setAccessible(true);
			var os = Util.getPlatform();
			openUri.invoke(os,url);
			LOGGER.info("[SCLP]MC26.1,26.2 Open Uri:" + url);
			return;//26.1,26.2的情况
		}
		catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e2)
		{
			LOGGER.error("[SCLP]Open Web Error:",e2);
		}
		LOGGER.error("[SCLP]Open Web failed,MC API May changeed!");
		
	}
}

/*
      ____                                    ____ 
     /   /                                   /   /   
    /   /    ____________  ______  _____    /   /    ___ ___ 
   /   /___ /  _  /  _  / /     / /  _  \  /   /___ |  //  /
  /_______/ \____/\____/ /  /  /  \__   / /_______/  \    /
 ___________________________________/  /______________/  /
/___LoongLy Software 2026_______________________________/
 */