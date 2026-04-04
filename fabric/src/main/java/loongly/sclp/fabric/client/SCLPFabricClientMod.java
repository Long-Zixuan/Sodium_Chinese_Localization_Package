package loongly.sclp.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import loongly.sclp.common.client.SCLPClientMod;


@Environment(EnvType.CLIENT)
public class SCLPFabricClientMod implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		SCLPClientMod.onInitClient();
	}
}
