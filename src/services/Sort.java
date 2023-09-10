package src.services;

import src.entities.Registro;

public class Sort {
    public static Registro[] swap(int a, int b, Registro[] bloco) {
        Registro aux = bloco[a];
        bloco[a] = bloco[b];
        bloco[b] = aux;
        return bloco;
    }

    public static Registro[] sort(Registro[] bloco) {
        return  quicksort(0, bloco.length-1, bloco);
    }

    public static Registro[] quicksort(int esq, int dir, Registro[] bloco) {
        int i = esq, j = dir;
        int pivo = bloco[ (dir+esq) / 2 ].getPlayer().getId();
        while(i <= j) {
            while(bloco[i].getPlayer().getId() < pivo) i++;
            while(bloco[j].getPlayer().getId() > pivo) j--;
            if(i <= j) {
                swap(i, j, bloco);
                i++;
                j--;
            }
        }
        if(esq < j) quicksort(esq, j, bloco);
        if(i < dir) quicksort(i, dir, bloco);
        return bloco;
    }
}
