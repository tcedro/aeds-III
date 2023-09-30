package src;

import src.config.DBConfig;
import src.entities.Player;
import src.services.Arquivo;
import src.services.CrudArvoreBplus;
import src.services.CrudSequencial;
import src.services.OrdenacaoExterna;

public class App {
    public static void main(String[] args) throws Exception { 
        DBConfig.StartConfig(); 
        // CrudSequencial.atualizarPlayer();
        // OrdenacaoExterna.intercalacao_balanceada();
        // CrudSequencial.buscarPlayerPorId();

        OrdenacaoExterna.intercalacao_balanceada();


        
    }
}
