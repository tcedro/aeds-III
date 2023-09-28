package src.entities;

public class RegistroIndexado {
    private Long posicaoNoArquivo;
    private int id;

    public RegistroIndexado() {
    }

    public RegistroIndexado(Long posicaoNoArquivo, int id) {
        this.posicaoNoArquivo = posicaoNoArquivo;
        this.id = id;
    }

    public Long getPosicaoNoArquivo() {
        return posicaoNoArquivo;
    }

    public void setPosicaoNoArquivo(Long posicaoNoArqivo) {
        this.posicaoNoArquivo = posicaoNoArqivo;
    }

    @Override
    public String toString() {
        return "RegistroIndexado [posicaoNoArquivo=" + posicaoNoArquivo + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
