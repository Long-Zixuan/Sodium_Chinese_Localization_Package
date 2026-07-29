package loongly.sclp.client.gui.options.storage;

import java.io.IOException;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;

import loongly.sclp.client.SclpClientMod;
import loongly.sclp.client.gui.SCLPGameOptions;

import java.io.IOException;

public class SCLPOptionsStorage implements OptionStorage<SCLPGameOptions>
{
    private final SCLPGameOptions options = SclpClientMod.options();


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
            //throw new RuntimeException("[SCLP]Couldn't save SCLP config changes", e);
            SclpClientMod.LOGGER.error("[SCLP]Couldn't save SCLP config changes", e);
        }

        SclpClientMod.LOGGER.info("[SCLP] Saved changes to SCLP config");
    }
}
