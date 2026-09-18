package me.loongly.mods.sclp.desktop;

import me.loongly.mods.sclp.common.client.SCLPClientMod;
import me.loongly.mods.sclp.desktop.utils.browse.BrowseUrlHandler;
import me.loongly.mods.sclp.desktop.utils.browse.Terminal;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LaunchWarn 
{
    private static final String HELP_URL = "https://github.com/Long-Zixuan/Sodium_Chinese_Localization_Package/wiki/Installation-%E5%AE%89%E8%A3%85";

    private static final String RICH_MESSAGE;
    
    private static final String FALLBACK_MESSAGE = """
                    You have tried to launch Sodium Chinese Localized Package (a Minecraft mod) directly, but it is not an executable program or mod installer. Instead, 
                    you must install Fabric Loader or Neoforge Loader for Minecraft, and then place this file in your mods directory.
                    
                    
                    看起来你试图执行钠-汉化包模组，但是它并不是一个可执行程序或模组安装器。你需要安装Fabric加载器或者Neoforge加载器，然后将这个文件放到你的模组文件夹中。
                    如果这是你第一次使用Fabric加载器或者Neoforge加载器安装模组，那么点击“Help”按钮以寻求帮助。
                    
                    
                    あなたがナトリウム-中国語化パッケージモジュールを実行しようとしているように見えますが、それは実行可能プログラムやモジュールインストーラではありません。FabricローダーまたはNeoforgeローダーをインストールした後、このファイルをモジュールフォルダに入れてください。
                    これがFabricローダーやNeoforgeローダーで初めてMODをインストールする場合、ヘルプボタンをクリックしてサポートを受けてください。
                    
                    
                    Il semble que vous essayiez d'exécuter un module de sodium, mais ce n'est pas un programme exécutable ou un installateur de module. Vous devez installer Fabric loader ou neoforge Loader, puis déposer ce fichier dans votre dossier modules.
                    Si c’est la première fois que vous installez un module avec Fabric loader ou neoforge Loader, appuyez sur le bouton « aide » pour obtenir de l’aide.""";

    private static final String FAILED_TO_BROWSE_MESSAGE;
    
    public static final String WINDOW_TITLE = "钠-汉化包(SCLP)";
    private static final String OPENED_HELP_PAGE_MESSAGE = """
        The help page has been opened. If the page cannot load, please check your network connection or try using VPN
        已经打开帮助页面,如果页面无法加载请检查网络连接或使用VPN后尝试
        ヘルプページが開きました。ページがロードできない場合はネットワーク接続を確認するか、VPNを使用してみてください
        La page d'aide est déjà ouverte, si la page ne se charge pas, vérifiez votre connexion réseau ou essayez après avoir utilisé un VPN
        """;

    static 
    {//使用String Buffer性能更好（虽然也不差这点性能）
        StringBuffer tmpRichMsg = new StringBuffer();
        tmpRichMsg.append("<html>")
            .append("<body>")
            .append("<p style='width: 600px; padding: 0 0 8px 0;'>")
            .append("You have tried to launch Sodium Chinese Localized Package (a Minecraft mod) directly, but it is not an executable program or mod installer. Instead, ")
            .append("you must install Fabric Loader or Neoforge Loader for Minecraft, and then place this file in your mods directory.")
            .append("</p>")
            .append("<p style='width: 600px; padding: 0 0 8px 0;'>")
            .append("If this is your first time installing mods with Fabric Loader or Neoforge Loader, then click the \"Help\" button for an installation guide.")
            .append("<p style='width: 600px; padding: 0 0 8px 0;'>")
            .append("<br>看起来你试图执行钠-汉化包模组，但是它并不是一个可执行程序或模组安装器。你需要安装Fabric加载器或者Neoforge加载器，然后将这个文件放到你的模组文件夹中。")
            .append("</p>")
            .append("<p style='width: 600px; padding: 0 0 8px 0;'>")
            .append("如果这是你第一次使用Fabric加载器或者Neoforge加载器安装模组，那么点击\"Help\"按钮以寻求帮助。")
            .append("</p>")
            .append("<p style='width: 600px; padding: 0 0 8px 0;'>")
            .append("<br>あなたがナトリウム-中国語化パッケージモジュールを実行しようとしているように見えますが、それは実行可能プログラムやモジュールインストーラではありません。FabricローダーまたはNeoforgeローダーをインストールした後、このファイルをモジュールフォルダに入れてください。")
            .append("</p>")
            .append("<p style='width: 600px; padding: 0 0 8px 0;'>")
            .append("これがFabricローダーやNeoforgeローダーで初めてMODをインストールする場合、ヘルプボタンをクリックしてサポートを受けてください。")
            .append("</p>")
            .append("<p style='width: 600px; padding: 0 0 8px 0;'>")
            .append("<br>Il semble que vous essayiez d'exécuter un module de sodium, mais ce n'est pas un programme exécutable ou un installateur de module. Vous devez installer Fabric loader ou neoforge Loader, puis déposer ce fichier dans votre dossier modules.")
            .append("</p>")
            .append("<p style='width: 600px; padding: 0 0 8px 0;'>")
            .append("Si c'est la première fois que vous installez un module avec Fabric loader ou neoforge Loader, appuyez sur le bouton \"aide\" pour obtenir de l'aide.")
            .append("</p>")
            .append("</body>")
            .append("</html>");
        RICH_MESSAGE = tmpRichMsg.toString();
        StringBuffer tmpFailToBrowseMsg = new StringBuffer();
        tmpFailToBrowseMsg.append("<html>")
            .append("<body>")
            .append("<p style='width: 400px; padding: 0 0 8px 0;'>")
            .append("Failed to open the default browser! Your system may be misconfigured. Please open the URL <i>")
            .append(HELP_URL)
            .append("</i> manually.")
            .append("<br><br>无法在您的计算机中打开浏览器！您的系统可能被错误配置。请手动打开 <i>")
            .append(HELP_URL)
            .append("</i>")
            .append("</p>")
            .append("</body>")
            .append("</html>");
        FAILED_TO_BROWSE_MESSAGE = tmpFailToBrowseMsg.toString();
    }

    public static void main(String[] args) 
    {
        if (GraphicsEnvironment.isHeadless()) 
        {
            showHeadlessError();
        } 
        else 
        {
            showGraphicalError();
        }
    }

    private static void showHeadlessError() 
    {
        Terminal.openTerminal();
        Terminal.printToTerminal(FALLBACK_MESSAGE);
    }

    private static void showGraphicalError() 
    {
        trySetSystemLookAndFeel();
        trySetSystemFontPreferences();

        BrowseUrlHandler browseUrlHandler = BrowseUrlHandler.createImplementation();

        if (browseUrlHandler != null) 
        {
            showRichGraphicalDialog(browseUrlHandler);
        } 
        else 
        {
            showFallbackGraphicalDialog();
        }

        System.exit(0);
    }

    private static void showRichGraphicalDialog(BrowseUrlHandler browseUrlHandler) 
    {
        Image iconImg = Toolkit.getDefaultToolkit().getImage(LaunchWarn.class.getResource("/icon.png"));
        Icon iconImage = new ImageIcon(iconImg);
        int selectedOption = showDialogBox(RICH_MESSAGE, WINDOW_TITLE, JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, new String[] { "Help", "Close" }, JOptionPane.YES_OPTION, iconImage);

        if (selectedOption == JOptionPane.YES_OPTION) 
        {
            log("Opening URL: " + HELP_URL);

            try 
            {
                browseUrlHandler.browseTo(HELP_URL);
                showDialogBox(OPENED_HELP_PAGE_MESSAGE, WINDOW_TITLE, JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, JOptionPane.DEFAULT_OPTION,null);
            } 
            catch (IOException e) 
            {
                log("Failed to open default web browser!", e);

                showDialogBox(FAILED_TO_BROWSE_MESSAGE, WINDOW_TITLE, JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, JOptionPane.DEFAULT_OPTION,null);
            }
        }
    }

    private static void showFallbackGraphicalDialog() 
    {
        // Fallback for Linux, etc. users with no "default" browser
        showDialogBox(FALLBACK_MESSAGE, WINDOW_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,null);
    }

    private static int showDialogBox(String message,
                                     String title,
                                     int optionType,
                                     int messageType,
                                     String[] options,
                                     Object initialValue,
                                     Icon icon) 
    {
        JOptionPane pane = new JOptionPane(message, messageType, optionType, icon, options, initialValue);

        JDialog dialog = pane.createDialog(title);
        Image iconImg = Toolkit.getDefaultToolkit().getImage(LaunchWarn.class.getResource("/icon.png"));
        dialog.setIconImage(iconImg);
        dialog.setVisible(true);

        Object selectedValue = pane.getValue();

        if (selectedValue == null) 
        {
            return JOptionPane.CLOSED_OPTION;
        }

        // If there is not an array of option buttons:
        if (options == null) 
        {
            if (selectedValue instanceof Integer) 
            {
                return (Integer) selectedValue;
            }

            return JOptionPane.CLOSED_OPTION;
        }

        // If there is an array of option buttons:
        for (int counter = 0; counter < options.length; counter++) 
        {
            String option = options[counter];

            if (option.equals(selectedValue)) 
            {
                return counter;
            }
        }

        return JOptionPane.CLOSED_OPTION;
    }

    private static void trySetSystemLookAndFeel() 
    {
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (ReflectiveOperationException | UnsupportedLookAndFeelException ignored) 
        {
            // Ignored
        }
    }

    private static void trySetSystemFontPreferences() 
    {
        System.setProperty("awt.useSystemAAFontSettings", "on"); // Why is this not a default?
    }

    private static void log(String message) 
    {
        System.err.println(message);
    }

    private static void log(String message, Throwable exception) 
    {
        System.err.println(message);
        exception.printStackTrace(System.err);
    }
}
