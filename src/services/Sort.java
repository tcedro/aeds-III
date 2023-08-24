package src.services;

import src.entities.Player;

public class Sort {
    public static Player[] swap(int a, int b, Player[] cluster) {
        Player aux = cluster[a];
        cluster[a] = cluster[b];
        cluster[b] = aux;
        return cluster;
    }

    public static Player[] sort(Player[] cluster) {
        return  quicksort(0, cluster.length-1, cluster);
    }

    public static Player[] quicksort(int esq, int dir, Player[] cluster) {
        int i = esq, j = dir;
        int pivo = cluster[ (dir+esq) / 2 ].getAge();
        while(i <= j) {
            while(cluster[i].getAge() < pivo) i++;
            while(cluster[j].getAge() > pivo) j--;
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
