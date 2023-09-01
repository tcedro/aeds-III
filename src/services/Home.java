package src.services;

import java.util.Scanner;

public class Home {
    public static void runAplicattion() {
        int key = -1;
        Scanner src = new Scanner(System.in);
        while(key != 0) {
            
            System.out.println("=================== NFL PLAYERS PICK ===================");
            System.out.println("0-Sair");
            System.out.println("1-Acessar CRUD");
            System.out.println("2-Acessar Intercalações");
            System.out.print("Entre com a opção: ");
            key = src.nextInt();
            
            switch (key) {
                case 1:
                    Crud.Start();
                    break;
                case 2:
                    Intercalar.Start();
                    break;
                default:
                    break;
            }
        }

        src.close();
    }
}
