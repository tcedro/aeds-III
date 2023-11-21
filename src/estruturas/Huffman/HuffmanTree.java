package src.estruturas.Huffman;
public abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequencia;
    public HuffmanTree(int freq) {
        frequencia = freq;
    }
    public int compareTo(HuffmanTree tree) {
        return frequencia - tree.frequencia;
    }
}
