package me.loongly.mods.sclp;

import me.loongly.mods.sclp.language.I18NLanguage;
import net.minecraftforge.fml.common.Mod;

@Mod(SCLPMod.MOD_ID)
public final class SCLPMod 
{
    public static final String MOD_ID = "sclp";

    public SCLPMod()
    {
        I18NLanguage.init();
    }
}
