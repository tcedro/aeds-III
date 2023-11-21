package src.services;

import java.util.Scanner;

import src.casamentoDePadrao.BoyerMoore;
import src.casamentoDePadrao.KMP;
import src.compressaoDeDados.Huffman;
import src.compressaoDeDados.LZW;

public class Home {

    public static void runAplicattion() {
        int key = -1;
        Scanner src = new Scanner(System.in);
        while(key != 0) {
            
            System.out.println("=================== NFL PLAYERS PICK ===================");
            System.out.println("0-Sair");
            System.out.println("1-Huffman (Compactar) (Descompactar)");
            System.out.println("2-LZW (Compactar) (Descompactar)");
            System.out.println("3-KMP (Casamento de padrão)");
            System.out.println("4-Boyer Moore (Casamento de padrão))");

            System.out.print("Entre com a opção: ");
            key = src.nextInt();
            
            switch (key) {
                case 1:
                    Huffman.start(Arquivo.selecionarApenasDezRegistrosDoCSV());
                    break;
                case 2:
                    LZW.start(Arquivo.selecionarApenasDezRegistrosDoCSV());
                    break;
                case 3:
                    KMP.start(Arquivo.selecionarApenasDezRegistrosDoCSV());
                    break;
                case 4:
                    BoyerMoore.start(Arquivo.selecionarApenasDezRegistrosDoCSV());
                    break;
                default:
                    src.close();
                    break;
            }
        }

        src.close();
    }
}
