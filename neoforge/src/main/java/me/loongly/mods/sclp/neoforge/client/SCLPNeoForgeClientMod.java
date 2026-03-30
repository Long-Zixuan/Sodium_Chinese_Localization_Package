package me.loongly.mods.sclp.neoforge.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import me.loongly.mods.sclp.common.client.SCLPClientMod;


@Mod("sclp")
public class SCLPNeoForgeClientMod 
{

	public SCLPNeoForgeClientMod(IEventBus eventBus) 
	{
		SCLPClientMod.onInitClient();
	}
}