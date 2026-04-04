package loongly.sclp.neoforge.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import loongly.sclp.common.client.SCLPClientMod;


@Mod("sclp")
public class SCLPNeoForgeClientMod {

	public SCLPNeoForgeClientMod(IEventBus eventBus) {
		SCLPClientMod.onInitClient();
	}
}
