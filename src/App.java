package src;

import src.config.DBConfig;
import src.services.Intercalar;

public class App {
    public static void main(String[] args) { 
        DBConfig.StartConfig(); 
        // Home.runAplicattion();
        
        // String path[] = new String[2];
        Intercalar.Start();

    }
}
