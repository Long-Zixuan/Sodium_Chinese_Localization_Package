package me.loongly.mods.sclp.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.loongly.mods.sclp.client.gui.SCLPGameOptions;

@Environment(EnvType.CLIENT)
public class SCLPClientMod implements ClientModInitializer
{

	public static final Logger LOGGER = LoggerFactory.getLogger("SCLP");

	private static SCLPGameOptions CONFIG;


	public static SCLPGameOptions options() 
	{
		return CONFIG;
	}


	@Override
	public void onInitializeClient() 
	{
		CONFIG = SCLPGameOptions.load();

		LOGGER.info("Init LSDC Config");
	}

	public static void caiDan()
	{
		//TO DO
	}
}
