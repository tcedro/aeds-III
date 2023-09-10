package src.entities;

public class Registro {
    private Boolean lapide;
    private Long size;
    private Player player;

    public Registro() {
        this(false, 0L, new Player());
    }
    public Registro(Boolean lapide, Long size, Player player) {
        this.lapide = lapide;
        this.size = size;
        this.player = player;
    }
    public Boolean getLapide() {
        return lapide;
    }
    public void setLapide(Boolean lapide) {
        this.lapide = lapide;
    }
    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
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
