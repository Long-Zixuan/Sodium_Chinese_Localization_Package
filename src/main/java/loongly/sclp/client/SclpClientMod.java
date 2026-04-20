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
import loongly.sclp.language.I18NLanguage;


@Environment(EnvType.CLIENT)
public class SclpClientMod implements ClientModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("sclp");

	@Override
	public void onInitializeClient() {
		LOGGER.info("[sclp] 钠模组汉化包");
		I18NLanguage.init();
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
