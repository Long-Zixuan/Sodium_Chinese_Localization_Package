package me.loongly.mods.sclp.desktop.utils.browse;

import java.io.IOException;

public class Terminal 
{
    public static void openTerminal() 
    {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder = new ProcessBuilder();
        
        try 
        {
            if (os.contains("win")) 
            {
                // Windows系统
                processBuilder.command("cmd.exe", "/c", "start");
            } 
            else if (os.contains("mac")) 
            {
                // macOS系统
                processBuilder.command("open", "-a", "Terminal");
            } 
            else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) 
            {
                // Linux/Unix系统
                processBuilder.command("x-terminal-emulator");
            } 
            else 
            {
                throw new UnsupportedOperationException("Unsupported operating system");
            }
            
            processBuilder.start();
        } 
        catch (IOException e) 
        {
            System.err.println("Failed to open terminal: " + e.getMessage());
        }
    }
    
    public static void printToTerminal(String message) 
    {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder = new ProcessBuilder();
        
        try 
        {
            if (os.contains("win")) 
            {
                // Windows系统 - 使用pause命令保持窗口打开
                processBuilder.command("cmd.exe", "/k", "echo", message, "&&", "pause");
            } 
            else if (os.contains("mac") || os.contains("nix") || os.contains("nux") || os.contains("aix")) 
            {
                // macOS/Linux/Unix系统
                processBuilder.command("echo", message);
                processBuilder.command("read", "-p", "Press Enter to continue...");
            } 
            else 
            {
                throw new UnsupportedOperationException("Unsupported operating system");
            }
            
            Process process = processBuilder.start();
            process.waitFor();
        } 
        catch (IOException | InterruptedException e) 
        {
            System.err.println("Failed to print to terminal: " + e.getMessage());
        }
    }
}

