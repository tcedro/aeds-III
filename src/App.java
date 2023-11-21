package src;

import src.compressao.Huffman;
import src.config.DBConfig;
import src.services.Arquivo;

public class App {
    public static void main(String[] args) throws Exception { 
        // DBConfig.StartConfig();
        // System.out.println(Arquivo.selecionarApenasDezRegistros("src\\data\\nfl_draft.csv"));
        Huffman.start(Arquivo.selecionarApenasDezRegistros("src\\data\\nfl_draft.csv"));
        // Huffman.start("paralelepipedo");
    }
}
