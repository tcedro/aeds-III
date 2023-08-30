package src.entities;

public class Registro {
    private char lapide;
    private int size;
    private Player player;

    public Registro() {
        this(' ',0, null);
    }
    public Registro(char lapide, int size, Player player) {
        this.lapide = lapide;
        this.size = size;
        this.player = player;
    }
    public char getLapide() {
        return lapide;
    }
    public void setLapide(char lapide) {
        this.lapide = lapide;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    @Override
    public String toString() {
        return "Registro [lapide=" + lapide + ", size=" + size + ", player=" + player + "]";
    }
}
