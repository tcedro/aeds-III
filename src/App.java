package src;

import src.config.DBConfig;
import src.entities.Player;
import src.services.Arquivo;
import src.services.Crud;

public class App {
    public static void main(String[] args) { 
        DBConfig.StartConfig(); 
        // Home.runAplicattion();
        Crud.Start();
        // Player plr = Crud.createPlayer();
        // Arquivo.gravarNovoRegistroCrud(plr);
        // Crud.searchPlayer(8435);
        // Crud.atualizarPlayer(8435);
        // Crud.deletarPlayer(1);
        // if(Arquivo.procurarRegistroCrud(1) == null) {
        //     System.out.println("deu certoo");
        // } 

        // String path[] = new String[2];
        
    }
}
