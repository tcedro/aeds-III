package src.entities;

public class Registro {
    private char lapide;
    private int size;
    private Player player;

    public Registro() {}
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
    public void clone(Player player) {
        this.player = new Player(player.getName(),player.getPickDate() , player.getAge(), player.getPositions(), player.getCollegeUniv(), player.getActTeam());
    }
}
