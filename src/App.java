package src;

import java.util.Scanner;

import src.config.DBConfig;
import src.services.Home;
import src.services.Intercalar;

public class App {
    public static void main(String[] args) { 
        DBConfig.StartConfig(); 
        // Home.runAplicattion();
        String path[] = new String[2];
        
        path[0] = "tmp1.txt";
        path[1] = "tmp2.txt";
        Intercalar.intercalacao_balanceada("src\\data\\nflPlayers.db", path);
    }
}
