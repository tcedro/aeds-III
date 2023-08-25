package src.services;

import src.entities.Registro;

public class Sort {
    public static Registro[] swap(int a, int b, Registro[] cluster) {
        Registro aux = cluster[a];
        cluster[a] = cluster[b];
        cluster[b] = aux;
        return cluster;
    }

    public static Registro[] sort(Registro[] cluster) {
        return  quicksort(0, cluster.length-1, cluster);
    }

    public static Registro[] quicksort(int esq, int dir, Registro[] cluster) {
        int i = esq, j = dir;
        int pivo = cluster[ (dir+esq) / 2 ].getPlayer().getAge();
        while(i <= j) {
            while(cluster[i].getPlayer().getAge() < pivo) i++;
            while(cluster[j].getPlayer().getAge() > pivo) j--;
            if(i <= j) {
                swap(i,j,cluster);
                i++;
                j--;
            }
        }
        if(esq < j) quicksort(esq, j, cluster);
        if(i < dir) quicksort(i, dir, cluster);
        return cluster;
    }
}
