package src;

import src.casamentoDePadrao.BoyerMoore;
import src.casamentoDePadrao.KMP;
import src.services.Arquivo;
import src.services.Home;

public class App {
    public static void main(String[] args) throws Exception { 
        Home.runAplicattion();

        String text = Arquivo.selecionarApenasDezRegistrosDoCSV();
        String pattern = "Flo";

        // BoyerMoore.start(text, pattern);
        // KMP.start(pattern, text);
    }
}
