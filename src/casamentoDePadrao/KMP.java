package src.casamentoDePadrao;

import java.util.Scanner;

public class KMP {
    void KMPSearch(String padrao, String texto) {
        System.out.println("==================== KMP PESQUISA ===================");
        System.out.println("Padrão: " + padrao);
        System.out.println("");
        System.out.println("OBS: Pesquisa entre 10 registros do csv");
        System.out.println("");
        System.out.println("Texto: " + texto);
        System.out.println("");
        int tamanhoPadrao = padrao.length();
        int tamanhoTexto = texto.length();
 
        // criar os deslocamento de caso de erro
        int deslocamento[] = new int[tamanhoPadrao];
        int idxPadrao = 0; // index for padrao[]
 
        // calcula o deslocamento 
        calcularDeslocamento(padrao, tamanhoPadrao, deslocamento);
 
        int i = 0;
        while ((tamanhoTexto - i) >= (tamanhoPadrao - idxPadrao)) {
            if (padrao.charAt(idxPadrao) == texto.charAt(i)) {
                idxPadrao++;
                i++;
            }
            if (idxPadrao == tamanhoPadrao) {
                System.out.println("Padrão encontrado: "
                                   + "no indice " + (i - idxPadrao) + "\nNumero de Comparação: " + i + "\n");
                idxPadrao = deslocamento[idxPadrao - 1];
            }
 
            //  incompatibilidade após partidas do idxPadrao
            else if (i < tamanhoTexto
                     && padrao.charAt(idxPadrao) != texto.charAt(i)) {
                if (idxPadrao != 0)
                    idxPadrao = deslocamento[idxPadrao - 1];
                else
                    i = i + 1;
            }
        }
    }
 
    void calcularDeslocamento(String padrao, int tamanhoPadrao, int deslocamento[]) {
        int len = 0;
        int i = 1;
        deslocamento[0] = 0; // deslocamento do idx 0 é sempre 0
 
        // calcular o deslocamento necessario
        while (i < tamanhoPadrao) {
            if (padrao.charAt(i) == padrao.charAt(len)) {
                len++;
                deslocamento[i] = len;
                i++;
            }
            else // (padrao[i] != padrao[len])
            {
                if (len != 0) {
                    len = deslocamento[len - 1];
 
                    // Also, note that we do not increment
                    // i here
                }
                else // if (len == 0)
                {
                    deslocamento[i] = len;
                    i++;
                }
            }
        }
    }
    public static void start(String texto) {
        Scanner src = new Scanner(System.in);
        System.out.println("Escolha um padrão para o texto (Sugestão = 'Flo' || 'Florida'): ");
        String padrao = src.nextLine();
        new KMP().KMPSearch(padrao, texto);
    }
}
