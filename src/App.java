package src;

import src.utils.FileManagement;

public class App {
    public static void main(String[] args) {
        FileManagement file = new FileManagement();
        String path = "src\\data\\nfl_draft.csv";
        
        file.setFile(path);
        file.setContent();

    }
}
