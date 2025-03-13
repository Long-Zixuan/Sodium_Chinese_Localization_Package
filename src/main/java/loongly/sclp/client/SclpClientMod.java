package loongly.sclp.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Environment(EnvType.CLIENT)
public class SclpClientMod implements ClientModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("sclp");

	@Override
	public void onInitializeClient() {
		LOGGER.info("[sclp] 钠模组汉化包");
	}
}
