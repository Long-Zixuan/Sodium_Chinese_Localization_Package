package me.loongly.mods.sclp.client.gui.options.storage;

import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.client.gui.SCLPGameOptions;

public class SCLPOptionsStorage implements OptionStorage<SCLPGameOptions> {
    private final SCLPGameOptions options = SCLPClientMod.options();

    @Override
    public SCLPGameOptions getData() {
        return this.options;
    }

    @Override
    public void save() {
        this.options.writeChanges();
    }
}
