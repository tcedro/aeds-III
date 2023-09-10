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
        RandomAccessFile raf;
        int index = 0;
        try {
            raf = new RandomAccessFile(path, "rws");
            raf.seek(raf.length());
            while(index < 100){
                raf.writeBoolean(bloco[index].getLapide());
                raf.writeLong(bloco[index].getSize());
                raf.writeInt(bloco[index].getPlayer().getId());
                raf.writeUTF(bloco[index].getPlayer().getName());
                raf.writeInt(bloco[index].getPlayer().getAge());
                String pos[] = bloco[index].getPlayer().getPositions();
                raf.writeUTF(pos[0]);
                raf.writeUTF(pos[1]);
                raf.writeUTF(bloco[index].getPlayer().getCollegeUniv());
                raf.writeUTF(bloco[index].getPlayer().getActTeam());
                raf.writeInt(bloco[index++].getPlayer().getPickDate());
            }
        
            raf.close();
        } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
        catch   (IOException e)           { e.printStackTrace(); }
    }

    private static void distribuicao(String path, String[] arqTemp) {
        Registro[] bloco = new Registro[100];
        DataInputStream dis;
        FileInputStream arq;
        int x = 0;
        int value = 0;
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
                    } catch(IOException e) { e.printStackTrace(); }
                       
                } while(x < 100); x--;
                    
                if(time % 2 == 0)   { gravarNoArquivo( Sort.sort(bloco), arqTemp[0] );}
                else                { gravarNoArquivo( Sort.sort(bloco), arqTemp[1] ); }
                
                x=0;
            }
                
            }catch(FileNotFoundException e) { e.printStackTrace(); } 
        catch(Exception e)           { System.out.println(e.getMessage()); }
    }    
    
    private static void intercalar(String pathRead[], String pathWrite) {
        RandomAccessFile raf1, raf2;
        Registro bloco1 = new Registro(), bloco2 = new Registro();

        try{
            raf1 = new RandomAccessFile(pathRead[0], "r");
            raf2 = new RandomAccessFile(pathRead[1], "r");
            raf1.seek(0); raf2.seek(0);
            while(raf1.getFilePointer() < raf1.length() || raf2.getFilePointer() < raf2.length())  { 

                //ler registro do primeiro arquivo
                    boolean lapide0 = raf1.readBoolean();
                    if(lapide0 != false) {
                        try{
                            bloco1.setLapide(lapide0);
                            bloco1.setSize(raf1.readLong());
                            bloco1.getPlayer().setId(raf1.readInt());
                            bloco1.getPlayer().setName(raf1.readUTF());
                            bloco1.getPlayer().setAge(raf1.readInt());
                            String[] positions = new String[2]; 
                            positions[0] = raf1.readUTF();
                            positions[1] = raf1.readUTF();
                            bloco1.getPlayer().setPositions(positions);
                            bloco1.getPlayer().setCollegeUniv(raf1.readUTF());
                            bloco1.getPlayer().setActTeam(raf1.readUTF());
                            bloco1.getPlayer().setPickDate(raf1.readInt());
                        
                            System.out.println("bloco 1" + bloco1.getPlayer().toString());
                        } catch(NullPointerException e) { bloco1.getPlayer().setId(-1); }
                    }
                    
                    //ler registro do segundo arquivo
                    boolean lapide1 = raf2.readBoolean();
                    if(lapide1 != false) {
                        try {
                            bloco2.setLapide(lapide1);
                            bloco2.setSize(raf2.readLong());
                            bloco2.getPlayer().setId(raf2.readInt());
                            bloco2.getPlayer().setName(raf2.readUTF());
                            bloco2.getPlayer().setAge(raf2.readInt());
                            String[] positions = new String[2]; 
                            positions[0] = raf2.readUTF();
                            positions[1] = raf2.readUTF();
                            bloco2.getPlayer().setPositions(positions);
                            bloco2.getPlayer().setCollegeUniv(raf2.readUTF());
                            bloco2.getPlayer().setActTeam(raf2.readUTF());
                            bloco2.getPlayer().setPickDate(raf2.readInt());
                            System.out.println("bloco 2" + bloco2.getPlayer().toString());
                        
                        } catch(NullPointerException e) { bloco2.getPlayer().setId(-1); }
                    }

                //intercala no 3 arquivo
                if(bloco1.getPlayer().getId() <= bloco2.getPlayer().getId()) { Arquivo.gravarNoArquivoIntercalacao(pathWrite, bloco1); }
                else { Arquivo.gravarNoArquivoIntercalacao(pathWrite, bloco2); }

            }
            
            raf1.close();
            raf2.close();
        } 
        catch(FileNotFoundException e)      { e.printStackTrace();}
        catch(IllegalArgumentException e)   { e.printStackTrace(); }
        catch(IOException e)                { System.out.println(e.getMessage()); }

    }
    public static void intercalacao_balanceada(String db, String[] path) throws Exception {
        try{
            distribuicao(db, path);
            intercalar(path, "src\\data\\nflPlayers.db");
        } catch(Exception e) { throw new Exception("ERROR:Intercalação falhou."); }
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
                    try{ intercalacao_balanceada("src\\data\\nflPlayers.db", path); } 
                    catch(Exception e) { System.out.println(e.getMessage()); }
                    break;
                
                default:
                    break;
            }
            
        }   
        src.close();
    }

}
