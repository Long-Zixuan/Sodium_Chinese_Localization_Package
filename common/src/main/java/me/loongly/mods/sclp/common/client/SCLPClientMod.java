package me.loongly.mods.sclp.common.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.loongly.mods.sclp.common.client.options.SCLPOptions;


public class SCLPClientMod
{

	public static final Logger LOGGER = LoggerFactory.getLogger("SCLP");

	private static final SCLPOptions CONFIG = SCLPOptions.load();


	public static SCLPOptions options() 
	{
		return CONFIG;
	}

	public static void onInitClient() 
	{
		LOGGER.info("[SCLP] LoongLy:Sodium Chinese Localized Package init!");
	}
}
