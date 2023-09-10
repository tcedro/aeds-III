package src;

import src.config.DBConfig;
import src.services.Home;

public class App {
    public static void main(String[] args) { 
        DBConfig.StartConfig(); 
        Home.runAplicattion();
        
        // String path[] = new String[2];
        // Intercalar.Start();

    }
}
