package src.entities;

public class Registro {
    private boolean lapide;
    private int size;
    private Player player;

    public Registro() {
        this(false, 0, new Player());
    }
    public Registro(boolean lapide, int size, Player player) {
        this.lapide = lapide;
        this.size = size;
        this.player = player;
    }
    public boolean getLapide() {
        return lapide;
    }
    public void setLapide(boolean lapide) {
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
