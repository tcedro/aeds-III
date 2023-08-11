package src.controllers;

import src.entities.Player;
import src.utils.CsvManager;

public class CsvController {
    private CsvManager csvManager;
    private Player player;

    public CsvController(String path) {
        this.csvManager = new CsvManager(path);
        this.player = null;
    }

    
}
