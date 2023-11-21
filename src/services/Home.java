package src.services;

import java.util.Scanner;

import src.compressao.Huffman;
import src.compressao.LZW;

public class Home {
    public static void runAplicattion() {
        int key = -1;
        Scanner src = new Scanner(System.in);
        while(key != 0) {
            
            System.out.println("=================== NFL PLAYERS PICK ===================");
            System.out.println("0-Sair");
            System.out.println("1-Huffman");
            System.out.println("2-LZW");
            System.out.print("Entre com a opção: ");
            key = src.nextInt();
            
            switch (key) {
                case 1:
                    Huffman.start(Arquivo.selecionarApenasDezRegistrosDoCSV("src\\data\\nfl_draft.csv"));
                    break;
                case 2:
                    LZW.start(Arquivo.selecionarApenasDezRegistrosDoCSV("src\\data\\nfl_draft.csv"));
                    break;
                default:
                    break;
            }
        }

        src.close();
    }
}
