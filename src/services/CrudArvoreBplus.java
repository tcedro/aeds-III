package src.services;

import java.util.Scanner;

public class CrudArvoreBplus {
    public static void deletarPlayerPorId(){
        Scanner scr = new Scanner(System.in);
        System.out.println("Entre com ID para excluir do arquivo: ");
        int id = scr.nextInt();

        long ptr = Arquivo.arvore.buscar(id);
        boolean status = Arquivo.deletarPlayerComIndexArvoreBPlus(ptr);
        
        if(status) {
            System.out.println("Operacao feita com sucesso!");
        
        
        } else { System.out.println("ERROR:falhou ao deletar do db!"); }
        
        
        scr.close();
    }
    public static void criarPlayer(){}
    public static void atualizarPlayer(){}
    public static void buscarPlayerPorId(){
        Scanner scr = new Scanner(System.in);
        // System.out.println("Entre com ID para buscar no BD: ");
        // int id = scr.nextInt();

        long ptr = Arquivo.arvore.buscar(1);
        boolean status = Arquivo.buscarPlayerPorIdComIndexArvoreBPlus(ptr);
        if(status) {
            System.out.println("Operacao feita com sucesso!");
        } else { System.out.println("ERROR:registro nao encontrado!"); }
        scr.close();
    }
    
    public static void Start() {
        int opc = -1;
        Scanner src = new Scanner(System.in);
        while(opc != 0) {
            System.out.println("================ CRUD ARVORE B+ =================");
            System.out.println("Selecione a opção desejada");
            System.out.println("0-Sair");
            System.out.println("1-Criar Jogador");
            System.out.println("2-Atualizar Jogador");
            System.out.println("3-Deletar Jogador");
            System.out.println("4-Buscar por id");
            System.out.print("Entre com a opc: ");
            opc = src.nextInt();
            switch (opc) {
            case 1:
                criarPlayer();
                break;
            case 2:
                atualizarPlayer();
                break;
            case 3:
                deletarPlayerPorId();
                break;
            case 4:
                buscarPlayerPorId();
                break;
            default:
                break;
            }
        }

    }
}
