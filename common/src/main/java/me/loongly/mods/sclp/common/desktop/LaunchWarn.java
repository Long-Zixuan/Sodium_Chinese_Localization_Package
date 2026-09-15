package me.loongly.mods.sclp.common.desktop;

import me.loongly.mods.sclp.common.desktop.utils.browse.BrowseUrlHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LaunchWarn {
    private static final String HELP_URL = "https://modrinth.com/mod/mc1.16.5-sodium-chinese-localization-pack";

    private static final String RICH_MESSAGE =
                    "<html>" +
                    "<body>" +
                    "<p style='width: 600px; padding: 0 0 8px 0;'>" +
                    "You have tried to launch Sodium Chinese Localized Package (a Minecraft mod) directly, but it is not an executable program or mod installer. Instead, " +
                    "you must install Fabric/Neoforge Loader for Minecraft, and then place this file in your mods directory." +
                    "<br>看起来你试图执行钠-汉化包模组，但是它并不是一个可执行程序或模组安装器。你需要安装Fabric/Neoforge Loader，然后将这个文件放到你的模组文件夹中。"+
                    "</p>" +
                    "<p style='width: 600px; padding: 0 0 8px 0;'>" +
                    "If this is your first time installing mods with Fabric/Neoforge Loader, then click the \"Help\" button for an installation guide." +
                    "<br>如果这是你第一次使用Fabric/Neoforge Loader安装模组，那么点击“Help”按钮以寻求帮助。"+
                    "</p>" +
                    "</body>" +
                    "</html>";

    private static final String FALLBACK_MESSAGE =
                    "<html>" +
                    "<body>" +
                    "<p style='width: 600px; padding: 0 0 8px 0;'>" +
                    "You have tried to launch Sodium Chinese Localized Package (a Minecraft mod) directly, but it is not an executable program or mod installer. Instead, " +
                    "you must install Fabric/Neoforge Loader for Minecraft, and then place this file in your mods directory." +
                    "<br>看起来你试图执行钠-汉化包模组，但是它并不是一个可执行程序或模组安装器。你需要安装Fabric/Neoforge Loader，然后将这个文件放到你的模组文件夹中。"+
                    "</p>" +
                    "<p style='width: 600px; padding: 0 0 8px 0;'>" +
                    "If this is your first time installing mods with Fabric/Neoforge Loader, then visit <i>" + HELP_URL + "</i> for an installation guide." +
                    "<br>如果这是你第一次使用Fabric/Neoforge Loader安装模组，那么点击“Help”按钮以寻求帮助。"+
                    "</p>" +
                    "</body>" +
                    "</html>";

    private static final String FAILED_TO_BROWSE_MESSAGE =
            "<html>" +
                    "<body>" +
                    "<p style='width: 400px; padding: 0 0 8px 0;'>" +
                    "Failed to open the default browser! Your system may be misconfigured. Please open the URL <i>" + HELP_URL + "</i> manually." +
                    "</p>" +
                    "</body>" +
                    "</html>";
    public static final String WINDOW_TITLE = "Sodium Chinese Localized Package";

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            showHeadlessError();
        } else {
            showGraphicalError();
        }
    }

    private static void showHeadlessError() {
        System.err.println(FALLBACK_MESSAGE);
    }

    private static void showGraphicalError() {
        trySetSystemLookAndFeel();
        trySetSystemFontPreferences();

        BrowseUrlHandler browseUrlHandler = BrowseUrlHandler.createImplementation();

        if (browseUrlHandler != null) {
            showRichGraphicalDialog(browseUrlHandler);
        } else {
            showFallbackGraphicalDialog();
        }

        System.exit(0);
    }

    private static void showRichGraphicalDialog(BrowseUrlHandler browseUrlHandler) {
        int selectedOption = showDialogBox(RICH_MESSAGE, WINDOW_TITLE, JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, new String[] { "Help", "Close" }, JOptionPane.YES_OPTION);

        if (selectedOption == JOptionPane.YES_OPTION) {
            log("Opening URL: " + HELP_URL);

            try {
                browseUrlHandler.browseTo(HELP_URL);
            } catch (IOException e) {
                log("Failed to open default web browser!", e);

                showDialogBox(FAILED_TO_BROWSE_MESSAGE, WINDOW_TITLE, JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, JOptionPane.DEFAULT_OPTION);
            }
        }
    }

    private static void showFallbackGraphicalDialog() {
        // Fallback for Linux, etc. users with no "default" browser
        showDialogBox(FALLBACK_MESSAGE, WINDOW_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null);
    }

    private static int showDialogBox(String message,
                                     String title,
                                     int optionType,
                                     int messageType,
                                     String[] options,
                                     Object initialValue) {
        JOptionPane pane = new JOptionPane(message, messageType, optionType, null, options, initialValue);

        JDialog dialog = pane.createDialog(title);
        dialog.setVisible(true);

        Object selectedValue = pane.getValue();

        if (selectedValue == null) {
            return JOptionPane.CLOSED_OPTION;
        }

        // If there is not an array of option buttons:
        if (options == null) {
            if (selectedValue instanceof Integer) {
                return (Integer) selectedValue;
            }

            return JOptionPane.CLOSED_OPTION;
        }

        // If there is an array of option buttons:
        for (int counter = 0; counter < options.length; counter++) {
            String option = options[counter];

            if (option.equals(selectedValue)) {
                return counter;
            }
        }

        return JOptionPane.CLOSED_OPTION;
    }

    private static void trySetSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ignored) {
            // Ignored
        }
    }

    private static void trySetSystemFontPreferences() {
        System.setProperty("awt.useSystemAAFontSettings", "on"); // Why is this not a default?
    }

    private static void log(String message) {
        System.err.println(message);
    }

    private static void log(String message, Throwable exception) {
        System.err.println(message);
        exception.printStackTrace(System.err);
    }
}
