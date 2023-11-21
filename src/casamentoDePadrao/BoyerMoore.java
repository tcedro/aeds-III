package src.casamentoDePadrao;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BoyerMoore {
    static int NO_OF_CHARS = 256;

    // A utility function to get maximum of two integers
    static int max (int a, int b) { return (a > b)? a: b; }

    // The preprocessing function for Boyer Moore's bad character heuristic
    static void caracterRuim( char []str, int size,int caracterRuim[])
    {
        // Initialize all occurrences as -1
        for (int i = 0; i < NO_OF_CHARS; i++)
            caracterRuim[i] = -1;

        // Fill the actual value of last occurrence 
        // of a character (indices of table are ascii and values are index of occurrence)
        for (int i = 0; i < size; i++)
            caracterRuim[(int) str[i]] = i;
    }

    /* A pattern searching function that uses Bad
    Character Heuristic of Boyer Moore Algorithm */
    static void pesquisarPadrao(char txt[], char pat[])
    {
        int tamanhoPadrao = pat.length;
        int tamanhoTexto = txt.length;

        int caracterRuim[] = new int[NO_OF_CHARS];

        // Variable to count comparisons
        int numComp = 0;

        /* Fill the bad character array by calling 
            the preprocessing function caracterRuim() 
            for given pattern */
        caracterRuim(pat, tamanhoPadrao, caracterRuim);

        int s = 0; // s is shift of the pattern with 
                    // respect to text
        // there are tamanhoTexto-tamanhoPadrao+1 potential alignments
        while(s <= (tamanhoTexto - tamanhoPadrao))
        {
            int j = tamanhoPadrao-1;

            /* Keep reducing index j of pattern while 
                characters of pattern and text are 
                matching at this shift s */
            while(j >= 0) {
                numComp++; // Increment comparison count
                if(pat[j] != txt[s+j])
                    break;
                j--;
            }

            /* If the pattern is present at current
                shift, then index j will become -1 after
                the above loop */
            if (j < 0)
            {
                System.out.println("Padrão encontrado no indice: " + s);
                System.out.println("Numero de Comparação: " + numComp);
                System.out.println("\n");

                /* Shift the pattern so that the next 
                    character in text aligns with the last 
                    occurrence of it in pattern.
                    The condition s+tamanhoPadrao < tamanhoTexto is necessary for 
                    the case when pattern occurs at the end 
                    of text */
                //txt[s+tamanhoPadrao] is character after the pattern in text
                s += (s+tamanhoPadrao < tamanhoTexto)? tamanhoPadrao-caracterRuim[txt[s+tamanhoPadrao]] : 1;

            }

            else
                /* Shift the pattern so that the bad character
                    in text aligns with the last occurrence of
                    it in pattern. The max function is used to
                    make sure that we get a positive shift. 
                    We may get a negative shift if the last 
                    occurrence of bad character in pattern
                    is on the right side of the current 
                    character. */
                s += max(1, j - caracterRuim[txt[s+j]]);
        }
    }

    /* Driver program to test above function */
    public static void start(String texto) {
        
        Scanner src = new Scanner(System.in);
        System.out.println("Escolha um padrão para o texto (Sugestão = 'Flo' || 'Florida'): ");
        String padrao = src.nextLine();

        System.out.println("==================== Boyer Moore Search Pattern ===================");
        System.out.println("Padrão: " + padrao);
        System.out.println("");
        System.out.println("OBS: Pesquisa entre 10 registros do csv");
        System.out.println("");
        System.out.println("Texto: " + texto);
        System.out.println("");     
        pesquisarPadrao(texto.toCharArray(), padrao.toCharArray());
    }
}