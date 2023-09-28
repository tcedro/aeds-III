package src.services;

import java.util.Scanner;
import src.entities.Player;

public class CrudSequencial {
    public static final Scanner src = new Scanner(System.in);

    public static void criarPlayer() {
        Player target = createPlayer();
        Arquivo.gravarNovoRegistroCrud(target);
    }
    
    public static Player createPlayer() {
        Player player = new Player();
        String pos1, pos2;
        String pos[] = new String[2];

        System.out.println("Nome do jogador: ");
        String name = src.nextLine();
        player.setName(name);

        System.out.println("Universidade: ");
        String univ = src.nextLine();
        player.setCollegeUniv(univ);

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

    private static String readNewTeam() {
        System.out.println("Novo Time:");
        String name = src.nextLine();
        return name;
    }

    public static void atualizarPlayer() {
        String novoTime = readNewTeam();
        
        System.out.println("Entre com id para alterar time atual: ");
        int id = src.nextInt();
        Player target = Arquivo.procurarRegistroCrud(id);
        
        if(target != null) {
            System.out.println("Jogador: " + target.getName());
            System.out.println("Time atual: " + target.getActTeam());

            target.setActTeam(novoTime); 
            boolean status = Arquivo.atualizarRegistroPlayer(target); 
            
            if(status) System.out.println("Concluido");
            else System.out.println("ERROR:Ao atualizar jogador");  
        
        } else System.out.println("Id nao encontrado!");
        
    }

    public static void deletarPlayer() {
        System.out.println("Entre com id para deletar do BD: ");
        int id = src.nextInt();
        boolean status = Arquivo.deletarRegistroPlayer(id);
        if(status) System.out.println("Registro deletado com sucesso!");
        else System.out.println("Error ao deletar!");
    }

    public static void buscarPlayerPorId() {
        System.out.println("Entre com id para buscar no BD: ");
        int id = src.nextInt();
        Player target = Arquivo.procurarRegistroCrud(id);
        if(target != null) {
            System.out.println("-> " + target.toString()); 
        } else {
            System.out.println("ERROR: id nao consta no BD");
        }
    }

    public static void Start() {
        int opc = -1;
        Scanner src = new Scanner(System.in);
        while(opc != 0) {
            System.out.println("================ CRUD =================");
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
                deletarPlayer();
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
