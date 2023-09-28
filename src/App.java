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
        CrudSequencial.atualizarPlayer();
        // CrudSequencial.deletarPlayer();
        // CrudArvoreBplus.deletarPlayerPorId();
        // CrudArvoreBplus.buscarPlayerPorId();
        // CrudArvoreBplus.atualizarPlayer();
        // CrudArvoreBplus.criarPlayer();
        // CrudArvoreBplus.buscarPlayerPorId();
        // Crud.atualizarPlayer();
        // Crud.Start();
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
        CrudSequencial.buscarPlayerPorId();



        
    }
}
