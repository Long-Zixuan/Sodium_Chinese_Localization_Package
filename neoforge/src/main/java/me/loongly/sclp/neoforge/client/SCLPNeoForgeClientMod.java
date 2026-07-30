package me.loongly.sclp.neoforge.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import me.loongly.sclp.common.client.SCLPClientMod;
import me.loongly.sclp.common.client.gui.ECLPGameOptionPages;
import org.embeddedt.embeddium.api.OptionGUIConstructionEvent;


@Mod(SCLPClientMod.MOD_ID)
public class SCLPNeoForgeClientMod {

	public SCLPNeoForgeClientMod(IEventBus eventBus) 
	{
		SCLPClientMod.onInitClient();
		if(SCLPClientMod.isEmbeddium())
		{
			OptionGUIConstructionEvent.BUS.addListener(event -> {
            	event.addPage(ECLPGameOptionPages.buildPage());
        	});
		}
	}
}
