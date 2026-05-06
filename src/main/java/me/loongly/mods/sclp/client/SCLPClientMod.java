package me.loongly.mods.sclp.client;

import net.caffeinemc.caffeineconfig.CaffeineConfig;
import net.minecraft.util.Util;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Logger;

import me.loongly.mods.sclp.client.gui.SCLPGameOptions;

import org.apache.logging.log4j.LogManager;
import java.lang.Runtime;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class SCLPClientMod 
{
    public static final Logger LOGGER = LogManager.getLogger("Sodium Chinese Localization Pack");
    private static SCLPGameOptions CONFIG;
    private static CaffeineConfig MIXIN_CONFIG;

    public static SCLPGameOptions options() 
    {
        if (CONFIG == null) 
        {
            CONFIG = loadConfig();
        }

        return CONFIG;
    }

    public static void openErrorHtml()
    {
        try 
        {
            //var uri = SCLPClientMod.class.getResource("/assets/sclp/html/error.html");
            //Util.getOperatingSystem().open(uri.toExternalForm());
            var jFrame = new JFrame();

            var jd = new JDialog(jFrame);

            jd.setLayout(new FlowLayout());

            jd.setBounds(500, 300, 400, 160);

            jd.setTitle("Sodium Chinese Localization Pack Warming");

            Image icon = Toolkit.getDefaultToolkit().getImage(SCLPClientMod.class.getResource("/icon.png"));
            jd.setIconImage(icon);

            var jLabel = new JLabel("Embeddium汉化包：本版本专为网易我的世界开发。");
            var jLabel2 = new JLabel("如需游玩请前往国际版社区下载");
            var jLabel3 = new JLabel("This version is developed for NetEase MC.");

            var closeBtn = new JButton("关闭");
            closeBtn.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    jd.setVisible(false);
                    jd.dispose();
                    jFrame.setVisible(false);
                    jFrame.dispose();
                }
            });

            var toModrinth = new JButton("Modrinth");
            toModrinth.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    try 
                    {
                        Util.getOperatingSystem().open("https://modrinth.com/mod/mc1.16.5-sodium-chinese-localization-pack");
                    } 
                    catch (Exception exception) 
                    {
                        exception.printStackTrace();
                    }
                }
            });

            var toCurseForge = new JButton("CurseForge");
            toCurseForge.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    try 
                    {
                        Util.getOperatingSystem().open("https://www.curseforge.com/minecraft/mc-mods/sodium-chinese-localization-package1-16-x");
                    } 
                    catch (Exception exception) 
                    {
                        exception.printStackTrace();
                    }
                }
            });

            jd.add(jLabel);
            jd.add(jLabel2);
            jd.add(toModrinth);
            jd.add(toCurseForge);
            //jd.add(closeBtn);
            jd.add(jLabel3);
            jd.setVisible(true);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            try
            {
                Util.getOperatingSystem().open("https://long-zixuan.github.io/html/ne_error.html");
            }
            catch (Exception e1)
            {
                System.out.println("本版本为网易我的世界开发，如需游玩请前往国际版社区下载");
                e1.printStackTrace();
            }
        }
    }

    static int chickCount = 0;
	public static void caiDan() throws Exception
	{
		chickCount++;
		if (chickCount == 10)
		{
            Runtime.getRuntime().exec("cmd /c start https://long-zixuan.github.io/html/lain.html");
			chickCount = 0;
        }
	}

    public static void birthCaiDan() throws Exception
	{

        Runtime.getRuntime().exec("cmd /c start https://www.loongly.me/html/clock.html");
		Runtime.getRuntime().exec("cmd /c start https://long-zixuan.github.io/html/badapple_h.html");
	}

    public static CaffeineConfig mixinConfig() 
    {
        if (MIXIN_CONFIG == null) 
        {
            MIXIN_CONFIG = CaffeineConfig.builder("Sodium Chinese Localization Pack").withSettingsKey("sclp:options")

                    .withLogger(SCLPClientMod.LOGGER)
                    .build(FMLPaths.CONFIGDIR.get().resolve("sclp.properties"));
        }
        return MIXIN_CONFIG;
    }

    private static SCLPGameOptions loadConfig() 
    {
        String localAppDataFolder = System.getenv("LOCALAPPDATA");
        Path path = Paths.get(localAppDataFolder, "Netease", "MCLauncher", "config", "mod", "sodium-chinese-pack-1.20.toml");
        return SCLPGameOptions.load(path.toFile());
    }

    public SCLPClientMod() 
    {
    }
}
