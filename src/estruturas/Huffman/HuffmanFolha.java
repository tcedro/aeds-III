package src.estruturas.Huffman;
public class HuffmanFolha extends HuffmanTree {
    public final char value;
    public HuffmanFolha(int freq, char value) {
        super(freq);
        this.value = value;
    }
}
