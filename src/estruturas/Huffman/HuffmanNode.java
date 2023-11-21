package src.estruturas.Huffman;
public class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right;
    public HuffmanNode(HuffmanTree left, HuffmanTree right) {
        super(left.frequencia + right.frequencia);
        this.left = left;
        this.right = right;
    }
}
