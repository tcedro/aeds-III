package src.compressao;

import java.util.PriorityQueue;

import src.estruturas.Huffman.HuffmanFolha;
import src.estruturas.Huffman.HuffmanNode;
import src.estruturas.Huffman.HuffmanTree;

public class Huffman {
    public static void start(String text) {
        int[] frequenciaCaracteres = new int[413]; // equivalente a 10 linhas do csv
        for (char c : text.toCharArray()) {
            frequenciaCaracteres[c]++;
        }
        //criar arvore
        HuffmanTree tree = startTree(frequenciaCaracteres);

        System.out.println("TABELA DE CODIGOS");
        System.out.println("SIMBOLO\tQuantidade\thuffmanCodigo");
        printCodes(tree, new StringBuffer());
        
        String encode = encode(tree, text);
        System.out.println("\nTexto Compactado:");
        System.out.println(encode);

        //decodificar
        System.out.println("\n\nTexto Decodificado");
        System.out.println(decode(tree, encode));

    }

    public static HuffmanTree startTree(int[] charFreq) {
        //fila de prioridade
        //ordem de frequencia da letra
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        //criar folhas da arvore para cada letra
        for (int i = 0; i < charFreq.length; i++) {
            if(charFreq[i] > 0) {
                trees.offer(new HuffmanFolha(charFreq[i], (char)i));
            }
        }
        //criar arvores de baixo pra cima
        while (trees.size() > 1) {
            HuffmanTree a = trees.poll(); // retorna o proxima da fila ou null se vazio
            HuffmanTree b = trees.poll();

            //cria os nos da arvore binaria
            trees.offer(new HuffmanNode(a, b));
        }

        //retorna o nó da arvore
        return trees.poll();
    }

    public static String encode(HuffmanTree tree, String text) {
        assert tree != null;
        String encodeText = "";
        for (char c : text.toCharArray()) {
            encodeText += ( getCodes( tree, new StringBuffer(), c ) );
        }
        return encodeText;
    }

    public static String decode(HuffmanTree tree, String encode) {
        assert tree != null;
        String decodeText="";
        HuffmanNode node = (HuffmanNode)tree;
        for (char code : encode.toCharArray()) {
            if(code == '0'){
                if(node.left instanceof HuffmanFolha) {
                    decodeText += ((HuffmanFolha)node.left).value;
                    node = (HuffmanNode)tree;
                } else {
                    node = (HuffmanNode)node.left;
                }
            } else if(code == '1'){
                if(node.right instanceof HuffmanFolha) {
                    decodeText += ((HuffmanFolha)node.right).value;
                    node = (HuffmanNode)tree;
                } else {
                    node = (HuffmanNode) node.right;
                }
            }
        }
    
        return decodeText;
    }

    public static void printCodes(HuffmanTree tree, StringBuffer prefix){
        assert tree != null;
        if(tree instanceof HuffmanFolha) {
            HuffmanFolha leaf = (HuffmanFolha) tree;

            System.out.println(leaf.value + "\t" + leaf.frequencia+ "\t\t" + prefix);

        }else if(tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode) tree;

            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);

            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
    public static String getCodes(HuffmanTree tree, StringBuffer prefix, char c) {
        assert tree != null;

        if(tree instanceof HuffmanFolha) {
            HuffmanFolha folha = (HuffmanFolha)tree;
            if(folha.value == c) return prefix.toString();
            
        } else if ( tree instanceof HuffmanNode ) {
            HuffmanNode node = (HuffmanNode) tree;

            prefix.append('0');
            String left = getCodes(node.left, prefix, c);
            prefix.deleteCharAt(prefix.length()-1);

            prefix.append('1');
            String right = getCodes(node.right, prefix, c);
            prefix.deleteCharAt(prefix.length() - 1);

            if(left==null) return right; else return left;
        }
        return null;
    }
}
