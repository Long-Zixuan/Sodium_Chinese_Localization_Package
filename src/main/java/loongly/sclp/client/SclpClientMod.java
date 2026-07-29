package loongly.sclp.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import loongly.sclp.client.gui.SCLPGameOptions;
import loongly.sclp.language.I18NLanguage;


@Environment(EnvType.CLIENT)
public class SclpClientMod implements ClientModInitializer 
{

	public static final Logger LOGGER = LogManager.getLogger("sclp");

	private static SCLPGameOptions CONFIG;


	public static SCLPGameOptions options() 
	{
		return CONFIG;
	}

	@Override
	public void onInitializeClient() 
	{
		I18NLanguage.init();
		CONFIG = SCLPGameOptions.load();
		String ls = "[SCLP]\r\n"+ //
						"      ____                                    ____ \r\n" + //
						"     /   /                                   /   /   \r\n" + //
						"    /   /    ____________  ______  _____    /   /    ___ ___ \r\n" + //
						"   /   /___ /  _  /  _  / /     / /  _  \\  /   /___ |  //  /\r\n" + //
						"  /_______/ \\____/\\____/ /  /  /  \\__   / /_______/  \\    /\r\n" + //
						" ___________________________________/  /______________/  /\r\n" + //
						"/___LoongLy Software 2026_______________________________/\r\n" + //
						"[SCLP]LoongLy:Sodium Chinese Localized Package init successful!(钠汉化包初始化成功！)\r\n";
		LOGGER.info(ls);
		CompletableFuture.runAsync
        (
            ()->
            {
				isConnected = isConnected();
			}
		);
	}

	public static boolean isConnected = true;

	public static boolean isConnected() 
	{
        try 
		{
            URL url = new URL("https://gitee.com");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            int responseCode = urlConnection.getResponseCode();
            return responseCode == 200;

        } 
		catch (IOException e) 
		{
            return false;
        }
    }

	public static void openNetworkSettings()
	{
		NetworkSettings.openNetWorkSettings();
	}
	
}
