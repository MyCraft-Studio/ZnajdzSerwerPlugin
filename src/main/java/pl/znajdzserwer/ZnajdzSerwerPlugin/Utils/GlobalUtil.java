package pl.znajdzserwer.ZnajdzSerwerPlugin.Utils;

public class GlobalUtil {

    public static void setLogs(String text) {
        System.out.println("[ZnajdzSerwerPlugin] " + text);
    }

    public static String getColor(String text) {
        String color = text.replace("&", "\u00A7");
        return color;
    }
}
