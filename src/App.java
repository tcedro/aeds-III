package src;

import src.config.DBConfig;
import src.entities.Player;
import src.services.Arquivo;
import src.services.Crud;
import src.services.OrdenacaoExterna;

public class App {
    public static void main(String[] args) throws Exception { 
        DBConfig.StartConfig(); 
        //Crud
        // Home.runAplicattion();
        // Crud.Start();
        // Player plr = Crud.createPlayer();
        // Arquivo.gravarNovoRegistroCrud(plr);
        // Crud.searchPlayer(8435);
        // Crud.atualizarPlayer(8435);
        // Crud.deletarPlayer(1);
        // if(Arquivo.procurarRegistroCrud(1) == null) {
        //     System.out.println("deu certoo");
        // } 
            
        // String path[] = new String[2];

        OrdenacaoExterna.intercalacao_balanceada();


        
    }
}
