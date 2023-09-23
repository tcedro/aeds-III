package src.services;

import java.util.Scanner;
import src.entities.Player;

public class Crud {
    public static final Scanner src = new Scanner(System.in);
    public static Player createPlayer() {
        Player player = new Player();
        String pos1, pos2;
        String pos[] = new String[2];

        System.out.println("Nome do jogador: ");
        player.setName(src.nextLine());

        System.out.println("Universidade: ");
        player.setCollegeUniv(src.nextLine());

        System.out.println("Time: ");
        player.setActTeam(src.nextLine());

        System.out.println("Posicao 1: ");
        pos1 = src.nextLine();

        System.out.println("Posicao 2: ");
        pos2 = src.nextLine();
        
        System.out.println("Dia do pick: ");
        player.setPickDate(src.nextInt());
        
        System.out.println("Idade: ");
        player.setAge(src.nextInt());
        

        
        pos[0] = pos1;
        pos[1] = pos2;

        player.setPositions(pos);
        return player;
    }

    public static void searchPlayer(int id) {
        Player target = Arquivo.procurarRegistroCrud(id);
        if(target != null) System.out.println("JOGADOR: " + target.toString());
        else { System.out.println("REGISTRO NAO ENCONTRADO!"); }
    }


    public static void atualizarPlayer(int id) {
        Player target = Arquivo.procurarRegistroCrud(id);

        System.out.println("NOVO NOME: ");
        String name = src.nextLine();
        target.setName(name); 
        
        boolean stat = Arquivo.atualizarRegistroPlayer(target);   
                    
        if(stat) {
            System.out.println("sucesso!");
            System.out.println(Arquivo.procurarRegistroCrud(8435).toString()); 
        }
        else System.out.println("erro.");
    }

    public static void deletarPlayer(int id){
        boolean status = Arquivo.deletarRegistroPlayer(id);
        

        if(status) {
            System.out.println("Registro deletado com sucesso!");
        } else {
            System.out.println("Error ao deletar!");
        }

    }

    // public static void Start() {
    //     int opc = -1;
    //     Scanner src = new Scanner(System.in);
    //     while(opc != 0) {
    //         System.out.println("================ CRUD =================");
    //         System.out.println("Selecione a opção desejada");
    //         System.out.println("0-Sair");
    //         System.out.println("1-Criar Jogador");
    //         System.out.println("2-Atualizar Jogador");
    //         System.out.println("3-Deletar Jogador");
    //         System.out.println("4-Buscar por id");
    //         System.out.print("Entre com a opção: ");
    //         opc = src.nextInt();
    //         switch (opc) {
    //         case 1:
    //             try { Arquivo.salvarNovoRegistro(createPlayer()); } 
    //             catch(Exception e) { System.out.println(e.getMessage()); }
    //             break;
    //         case 2:
    //             break;
    //         case 3:
    //             try { deletePlayer(opc);}
    //             catch (Exception e) { System.out.println(e.getMessage()); }
    //             break;
    //         case 4:
    //             break;
    //         default:
    //             break;
    //         }
    //     }
    // }
}
