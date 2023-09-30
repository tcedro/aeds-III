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

        // Crud Sequenciais

        // CrudSequencial.criarPlayer();
        // CrudSequencial.buscarPlayerPorId();
        // CrudSequencial.deletarPlayer();
        // CrudSequencial.atualizarPlayer();

        // Crud Arvore B plus

        // CrudArvoreBplus.atualizarPlayer();
        // CrudArvoreBplus.buscarPlayerPorId();
        // CrudArvoreBplus.deletarPlayerPorId();
        // CrudArvoreBplus.buscarPlayerPorId();

        // Ordenação Externa tamanho fixo
        // OrdenacaoExterna.intercalacao_balanceada();





        // OrdenacaoExterna.intercalacao_balanceada();


        
    }
}
