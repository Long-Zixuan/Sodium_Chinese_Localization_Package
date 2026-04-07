package loongly.sclp.common.client.gui.options.storage;

import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;

import loongly.sclp.common.client.SCLPClientMod;
import loongly.sclp.common.client.gui.SCLPGameOptions;

import java.io.IOException;


public class SCLPOptionsStorage implements OptionStorage<SCLPGameOptions> 
{

    private final SCLPGameOptions options = SCLPClientMod.options();


    public SCLPGameOptions getData() 
    {
        return this.options;
    }

    public void save() 
    {
        try 
        {
            this.options.writeChanges();
        }
        catch (IOException e) 
        {
            throw new RuntimeException("Couldn't save SCLP config changes",e);
            //SCLPClientMod.LOGGER.warn("Couldn't save SCLP config changes" + e.toString());
            //return;
        }

        SCLPClientMod.LOGGER.info("[SCLP] Saved changes to SCLP config");
    }
}
