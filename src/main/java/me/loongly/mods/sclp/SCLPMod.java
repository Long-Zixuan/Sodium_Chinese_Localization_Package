package me.loongly.mods.sclp;

import me.loongly.mods.sclp.client.SCLPClientMod;
import me.loongly.mods.sclp.language.I18NLanguage;
import net.minecraftforge.fml.common.Mod;

@Mod(SCLPMod.MOD_ID)
public final class SCLPMod 
{
    public static final String MOD_ID = "sclp";

    public SCLPMod()
    {
        I18NLanguage.init();
        
		var ls = "[SCLP]\r\n"+ //
						"      ____                                    ____ \r\n" + //
						"     /   /                                   /   /   \r\n" + //
						"    /   /    ____________  ______  _____    /   /    ___ ___ \r\n" + //
						"   /   /___ /  _  /  _  / /     / /  _  \\  /   /___ |  //  /\r\n" + //
						"  /_______/ \\____/\\____/ /  /  /  \\__   / /_______/  \\    /\r\n" + //
						" ___________________________________/  /______________/  /\r\n" + //
						"/___LoongLy Software 2026_______________________________/\r\n" + //
						"[SCLP]LoongLy:Sodium Chinese Localized Package init successful!(钠汉化包初始化成功！)\r\n";
		SCLPClientMod.LOGGER.info(ls);
    }
}
