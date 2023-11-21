package src.casamentoDePadrao;
import java.util.HashMap;
import java.util.Map;

public class BoyerMoore {
    
    public static  Map<Integer, Integer> busca(String texto, String padrao) {
        int numComparacoes=0;
        if (padrao.length() == 0) return null;
        Map<Character, Integer> deslocamento = preprocessamento(padrao);
        Map<Integer, Integer> result = new HashMap<>();
        int m = padrao.length();
        int n = texto.length();

        int i = m - 1;
        int j = m - 1;

        while (i < n) {
            if (padrao.charAt(j) == texto.charAt(i)) {
                numComparacoes++;
                if (j == 0) {
                    System.out.println(numComparacoes);
                    result.put(i, numComparacoes);
                    System.out.println("numero de comparacao: " + numComparacoes + "posicao do arquivo: " + i);
                    return result;
                }
                i--;
                j--;
            } else {
                i += m - Math.min(j, 1 + deslocamento.getOrDefault(texto.charAt(i), -1));
                j = m - 1;
            }
        }
        return null; // Padrão não encontrado
    }

    private static Map<Character, Integer> preprocessamento(String padrao) {
        Map<Character, Integer> deslocamento = new HashMap<>();
        for (int i = 0; i < padrao.length(); i++) {
            deslocamento.put(padrao.charAt(i), i + 1);
            //p a r a l e l o
            //0 1 2 3 4 5 6 7
            //p a r a l e l e p i p e d o
        }
        return deslocamento;
    }

    public static void start(String text, String pattern) {
        Map<Integer,Integer> posicao = busca(text, pattern);
        if (posicao != null) {
            try {
                System.out.println("Padrão encontrado na posição: " + posicao.toString());
            }catch(NullPointerException e) { System.err.println(e.getMessage()); }
        } else {
            System.out.println("Padrão não encontrado.");
        }
    }
}
