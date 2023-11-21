package src.compressaoDeDados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.services.Arquivo;

public class LZW {
    public static void start(String text) {
        System.out.println("\n======================= LZW =========================");
        //compressao lzw
        List<Integer> compressed = LZW.encode(Arquivo.selecionarApenasDezRegistrosDoCSV());
        System.out.println("OBS: compactação de apenas 10 registros do csv");
        System.out.println("\nTexto Compactado:");
        System.out.println(compressed);
        System.out.println("Criando novo arquivo em: data/playerCompressaoLZW.txt");
        Arquivo.compactarEmArquivoLZW(text);
        
        
        //descompressao lzw
        System.out.println("\n\nTexto Decodificado do Arquivo gerado: data/playerCompressaoLZW.txt");
        String decompressed = LZW.decode(compressed);
        System.out.println(decompressed);
        System.out.println("\n\n");
    }
    public static List<Integer> encode(String text) {
        int tamDicionario=256;
        Map <String, Integer> dicionario = new HashMap<>();
        for (int i = 0; i < tamDicionario; i++) {
            dicionario.put(String.valueOf((char)i),i);
        }
        String foundChars = "";
        List<Integer>result = new ArrayList<>();
        for (char c : text.toCharArray()) {
            String charsToAdd = foundChars + c;
            if(dicionario.containsKey(charsToAdd)) {
                foundChars = charsToAdd;
            } else {
                result.add(dicionario.get(foundChars));
                dicionario.put(charsToAdd, tamDicionario++);
                foundChars = String.valueOf(c);
            }
        }
        if(!foundChars.isEmpty()) result.add(dicionario.get(foundChars));
        return result;
    }
    public static String decode(List<Integer> encodedText) {
        int tamDicionario=256;
        Map <Integer, String> dicionario = new HashMap<>();
        for (int i = 0; i < tamDicionario; i++) {
            dicionario.put(i, String.valueOf((char)i));
        }
        
        String chars = String.valueOf((char) encodedText.remove(0).intValue());
        StringBuilder result = new StringBuilder(chars);
        for (int code : encodedText) {
            String entry = dicionario.containsKey(code) ? dicionario.get(code) : chars + chars.charAt(0);
            result.append(entry);
            dicionario.put(tamDicionario++, chars + entry.charAt(0));
            chars = entry;
        }



        return result.toString();
    }
}
