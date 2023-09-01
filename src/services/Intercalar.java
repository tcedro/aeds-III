package src.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import src.entities.Player;
import src.entities.Registro;

public class Intercalar {
    private static void gravarNoArquivo(Registro bloco[], String path) {
        int index = 0;
        try {
            RandomAccessFile rAccessFile = new RandomAccessFile(path, "rw");

            while(index < 100){
                rAccessFile.writeBoolean(bloco[index].getLapide());
                rAccessFile.writeLong(bloco[index].getSize());
                rAccessFile.writeInt(bloco[index].getPlayer().getId());
                rAccessFile.writeUTF(bloco[index].getPlayer().getName());
                rAccessFile.writeInt(bloco[index].getPlayer().getAge());
                String pos[] = bloco[index].getPlayer().getPositions();
                rAccessFile.writeUTF(pos[0]);
                rAccessFile.writeUTF(pos[1]);
                rAccessFile.writeUTF(bloco[index].getPlayer().getCollegeUniv());
                rAccessFile.writeUTF(bloco[index].getPlayer().getActTeam());
                rAccessFile.writeInt(bloco[index++].getPlayer().getPickDate());
            }

        } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
        catch   (IOException e)           { e.printStackTrace(); }
    }

    private static void separar_arquivos(String path, String[] arqTemp) {
        Registro[] bloco = new Registro[100];
        DataInputStream dis;
        FileInputStream arq;
        int x = 0;
        
        try{
            arq = new FileInputStream(path);
            dis = new DataInputStream(arq);
            
            for(int time = 0; time < 84; time++) {
                do {
                    Player player = new Player();
                    try{
                        bloco[x] = new Registro();
                        
                        Boolean lapide = dis.readBoolean();
                        if(lapide != false) {

                            bloco[x].setLapide(lapide);
                            bloco[x].setSize(dis.readLong());
                            
                            player.setId(dis.readInt());
                            player.setName(dis.readUTF());
                            player.setAge(dis.readInt());
                            String[] positions = new String[2]; 
                            positions[0] = dis.readUTF();
                            positions[1] = dis.readUTF();
                            player.setPositions(positions);
                            player.setCollegeUniv(dis.readUTF());
                            player.setActTeam(dis.readUTF());
                            player.setPickDate(dis.readInt());
                            
                            bloco[x++].setPlayer(player);
                        }
                        // System.out.println(x + " " + player.toString());
                    } catch(IOException e) { e.printStackTrace(); }
                       
                } while(x < 100); x--;
                    
                if(time % 2 == 0)   { gravarNoArquivo( Sort.sort(bloco), arqTemp[0] );}
                else                { gravarNoArquivo( Sort.sort(bloco), arqTemp[1] ); }
                
                x=0;
            }
                

            }catch(FileNotFoundException e) { e.printStackTrace(); } 
        catch(IOException e)           { System.out.println(e.getMessage()); }
    }    

    public static void intercalacao_balanceada(String db, String[] path) {
        separar_arquivos(db, path);
        
    
    }
    public static void Start() {
        int opc = -1;
        String path[] = new String[2];
        Scanner src = new Scanner(System.in);
        
        path[0] = "tmp1.txt";
        path[1] = "tmp2.txt";

        while(opc != 0) {
            System.out.println("=================== INTERCALAÇÕES ===================");
            System.out.println("0-sair");
            System.out.println("1-intercalação balanceada");
            System.out.println("2-intercalação balanceada Otimizada");
            System.out.print("Escolha a intercalação: ");
            
            opc = src.nextInt();
                    
            switch (opc) {
                case 1:
                    System.out.println("Iniciando balanceamento");
                    intercalacao_balanceada("src\\data\\nflPickPlayers.db", path);
                break;
                default:
                    break;
            }
            
        }   
        src.close();
    }

}
