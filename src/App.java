package src;

import java.util.Scanner;

import src.config.DBConfig;
import src.services.Home;
import src.services.Intercalar;

public class App {
    public static String getSO() {
        return System.getProperty("os.name").toLowerCase();
    } 
    public static String pathConfig() {
        return isWindows()? "src\\data\\nfl_draft.csv":"data/nfl_draft.csv";
    }
    public static boolean isWindows() {
        return (getSO().indexOf("win") >= 0);
    }
    public static boolean isUnix() {
        return (getSO().indexOf("nix") >= 0 || getSO().indexOf("nux") >= 0 || getSO().indexOf("aix") > 0);
    }
    public static void main(String[] args) { 
        DBConfig.StartConfig(); 
        // Home.runAplicattion();
        String path[] = new String[2];
        
        path[0] = "tmp1.txt";
        path[1] = "tmp2.txt";
        Intercalar.intercalacao_balanceada("src\\data\\nflPickPlayers.db", path);
    }
}
