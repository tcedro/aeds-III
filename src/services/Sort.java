package src.services;

import src.entities.Player;

public class Sort {
    public Player[] bloco;
    
    public Sort(Player[] bloco) {
        this.bloco = bloco;
    }

    public Player[] swap(int a, int b, Player[] data) {
        Player aux = data[a];
        data[a] = data[b];
        data[b] = aux;
        return data;
    }

    public Player[] sort(Player[] bloco) {
        quicksort(0, bloco.length-1);
        return this.bloco;
    }

    public void quicksort(int esq, int dir) {
        int i = esq, j = dir;
        int pivo = this.bloco[ (dir+esq) / 2 ].getAge();
        while(i <= j) {
            while(bloco[i].getAge() < pivo) i++;
            while(bloco[j].getAge() > pivo) j--;
            if(i <= j) {
                swap(i,j,bloco);
                i++;
                j--;
            }
        }
        if(esq < j) quicksort(esq, j);
        if(i < dir) quicksort(i, dir);
    }

}
