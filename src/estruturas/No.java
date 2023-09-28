package src.estruturas;

class No {
  public int ordem;
  public Chave[] chaves;
  public No[] filhos;
  public int numChaves;
  public boolean folha;
  public No irmao;

  public No(int ordem) {
    this.ordem = ordem;
    this.chaves = new Chave[ordem - 1];
    this.filhos = new No[ordem];
    this.numChaves = 0;
    this.folha = true;
    this.irmao = null;
  }

  public void print() {
    System.out.print("[");
    for (int i = 0; i < numChaves; i++) {
      System.out.print(chaves[i] == null ? "null" : chaves[i].id);
      if (i < numChaves - 1) {
        System.out.print(", ");
      }
    }
    System.out.print("]");
  }

  public No clone() {
    No novo = new No(ordem);
    novo.numChaves = numChaves;
    novo.folha = folha;
    novo.irmao = irmao;
    for (int i = 0; i < numChaves; i++) {
      novo.chaves[i] = chaves[i];
    }
    for (int i = 0; i <= numChaves; i++) {
      novo.filhos[i] = filhos[i];
    }
    return novo;
  }
}
