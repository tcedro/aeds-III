package src.services;

import java.util.Scanner;
import src.entities.Player;

public class Crud {
    public static Player createPlayer() {
        Player player = new Player();
        Scanner src = new Scanner(System.in);
        
        System.out.println("Criar registro de jogador");
        
        System.out.println("Insira o nome do jogador: ");
        player.setName(src.nextLine());
        System.out.println("Insira o Idade do jogador: ");
        player.setAge(src.nextInt());
        System.out.println("Insira a universidade do jogador: ");
        player.setCollegeUniv(src.nextLine());
        System.out.println("Insira a posição de origem do jogador: ");
        String pos[] = new String[2];
        pos[0] = src.nextLine();
        System.out.println("Insira a posição atual do jogador: ");
        pos[1] = src.nextLine();
        player.setPositions(pos);
        System.out.println("Insira o time do pick: ");
        player.setActTeam(src.nextLine());
        
        src.close();
       
        return player;
    }
    public static void deletePlayer(int id) {
        
    }

    public static void Start() {
        int opc = 0;
        while(opc != 0) {
            System.out.println("Selecione a opção desejada");
            System.out.println("1-Criar Jogador");
            System.out.println("2-Atualizar Jogador");
            System.out.println("3-Deletar Jogador");
            System.out.println("4-Buscar por id");
            switch (opc) {
            case 1:
                try { Arquivo.salvarNovoRegistro(createPlayer()); } 
                catch(Exception e) { System.out.println(e.getMessage()); }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
            }
        }
    }
}
