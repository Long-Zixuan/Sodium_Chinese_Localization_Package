package loongly.sclp.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NetworkSettings 
{
    public static void openNetWorkSettings()
	{
		switch (OsType.getCurrentOs()) 
		{
			case WINDOWS:
				if (System.getProperty("os.version").startsWith("10."))
				{
					openWinNetworkSettings();
				}
				else
				{
					openOldNetworkConnections();
				}
				break;
			case LINUX:
				openLinuxNetworkSettings();//理论上安卓也会打开这个，但是应该是没有用的
				break;
			case MACOS:
				openMacOSNetworkSettings();
				break;
			default:
				break;
		}
	}

	public static void openOldNetworkConnections() //老版本Windows
	{
        try 
		{
            ProcessBuilder pb = new ProcessBuilder("control", "ncpa.cpl");
            pb.start();
        } 
		catch (IOException e) 
		{
            e.printStackTrace();
        }
    }

    /**
     * 打开 Windows 10/11 现代网络设置
     */
    public static void openWinNetworkSettings() 
	{
        try 
		{
            // 使用 cmd /c start 来确保 URI 被正确解析
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "ms-settings:network");
            pb.start();
        } 
		catch (IOException e) 
		{
			openOldNetworkConnections();
            e.printStackTrace();
        }
    }
    public static void openLinuxNetworkSettings() 
	{
		List<List<String>> COMMANDS = Arrays.asList(
			// GNOME (Ubuntu, Fedora, etc.)
			Arrays.asList("gnome-control-center", "network"),
			// KDE Plasma (Kubuntu, etc.)
			Arrays.asList("kcmshell5", "kcm_networkmanagement"),
			// XFCE (Xubuntu, etc.) - 通常没有独立的网络设置GUI，需打开主设置
			Arrays.asList("xfce4-settings-manager"),
			// MATE (Ubuntu MATE)
			Arrays.asList("mate-control-center", "network"),
            // 安卓(但是只有开了ADB的情况下有用)
            Arrays.asList("adb", "shell", "am", "start", "-a", "android.settings.WIRELESS_SETTINGS")
    	);
        for (List<String> command : COMMANDS) 
		{
            try 
			{
                ProcessBuilder pb = new ProcessBuilder(command);
                // redirectErrorStream(true) 将错误流合并到标准输出，便于调试
                pb.redirectErrorStream(true);
                Process process = pb.start();
                
                // 如果进程成功启动（没有立即抛出 IOException），则认为成功
                // 注意：某些命令可能启动很慢，这里仅判断是否找到可执行文件
                System.out.println("已尝试执行命令: " + String.join(" ", command));
                return; // 成功启动一个即退出
            } 
			catch (IOException e) 
			{
                // 该命令不存在或无法执行，尝试下一个
                continue;
            }
        }
    }

	public static void openMacOSNetworkSettings() 
	{
        try 
		{
            // /System/Library/PreferencePanes/Network.prefPane 是系统内置的网络设置面板
            ProcessBuilder pb = new ProcessBuilder("open", "/System/Library/PreferencePanes/Network.prefPane");
            pb.start();
        } 
		catch (IOException e) 
		{
            System.err.println("无法打开网络设置: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
