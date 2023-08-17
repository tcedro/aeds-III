package src;


import src.utils.CsvManager;

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
        
        String path = pathConfig();
        String path1 = "src\\data\\nflPickPlayers.db";
        CsvManager csvManager = new CsvManager(path);
        
        csvManager.convertCsvToBinary(path1);
            
        
    }
}
