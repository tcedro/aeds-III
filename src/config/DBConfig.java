package src.config;

import src.services.Arquivo;

public class DBConfig {
    public static String getSO() {
        return System.getProperty("os.name").toLowerCase();
    } 
    public static String readPathCSVConfig() {
        return isWindows()? "src\\data\\nfl_draft.csv":"src/data/nfl_draft.csv";
    }
    public static String readPathDBConfig() {
        return isWindows()? "src\\data\\nflPickPlayers.db":"src/data/nflPickPlayers.db";
    }
    public static boolean isWindows() {
        return (getSO().indexOf("win") >= 0);
    }
    public static boolean isUnix() {
        return (getSO().indexOf("nix") >= 0 || getSO().indexOf("nux") >= 0 || getSO().indexOf("aix") > 0);
    }
    
    public static void startDB(String pathCSV, String pathDB) { Arquivo.CsvToDB(pathCSV, pathDB); }
    public static void StartConfig() {
        String pathCSV = readPathCSVConfig();
        String pathDB = readPathDBConfig();
        
        System.out.println("INICIALIZANDO APLICAÇÃO");
        startDB(pathCSV, pathDB);
    }
}
