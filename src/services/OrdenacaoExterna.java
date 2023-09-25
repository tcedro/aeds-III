package src.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import src.entities.Player;
import src.entities.Registro;
import src.utils.Converter;

public class OrdenacaoExterna {
    public static final int tamSegmento = 100;
    public static final String db = "src\\data\\nflPlayers.db";

    private static void distribuicao() {
        FileInputStream fis;
        DataInputStream dos;
        Registro[] registros = new Registro[tamSegmento];
        int cout = 0;
        
        try {
            fis = new FileInputStream(db);
            dos = new DataInputStream(fis);
            
            int ultimoID = dos.readInt();
            System.out.println("ultimo id: " + ultimoID);
            
            byte[] playerBytes;
            while (cout <= ultimoID) {
                for(int i = 0; i < 100; i++) {
                    registros[i] = new Registro();
                    registros[i].setLapide(dos.readBoolean());
                    registros[i].setSize(dos.readInt());

                    if(registros[i].getLapide() != true) {
                        playerBytes = dos.readNBytes(registros[i].getSize());
                        registros[i] = Converter.toObject(playerBytes);
                    
                    } else { dos.skipNBytes(registros[i].getSize()); }
                
                }
                
                if(cout % 2 == 0) { Arquivo.gravarRegistroOrdenacao(Sort.sort(registros), "tmp1.txt"); } 
                else              { Arquivo.gravarRegistroOrdenacao(Sort.sort(registros), "tmp2.txt"); }
                
                cout++;
            }

            
            fis.close();
            dos.close();
            
        }
         
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { System.out.println(e.getMessage());}
    }    
    
    private static void intercalar() {
        //cond de parada
        boolean status = true;
        
        //leitura arquivo tmp1;
        FileInputStream fis1;
        DataInputStream dos1;
        
        //leitura arquivo tmp2;
        FileInputStream fis2;
        DataInputStream dos2;
        
        //metodo de escrita com registro intercalado
        RandomAccessFile raf;
        
        //registros lidos do arquivo        
        Registro registro1 = new Registro();
        Registro registro2 = new Registro();
        
        int cout = 0;
        try {
            fis1 = new FileInputStream("tmp1.txt");
            fis2 = new FileInputStream("tmp2.txt");

            dos1 = new DataInputStream(fis1);
            dos2 = new DataInputStream(fis2);

            raf = new RandomAccessFile(db, "rws");
            raf.seek(0);
            
            int x = 0;
            int cond = -2;
            
            byte[] playerReg1, playerReg2;
            
            while(status != false || x <= 8435) {
                //ler lapide
                registro1.setLapide(dos1.readBoolean());
                registro2.setLapide(dos2.readBoolean());
                    System.out.println("lapide1= " +registro1.getLapide());
                    System.out.println("lapide2= " +registro2.getLapide());
                //tamanho do registro
                registro1.setSize(dos1.readInt());
                registro2.setSize(dos2.readInt());
                
                System.out.println("tam1= " +registro1.getSize());
                System.out.println("tam2= " +registro2.getSize());
                        
                //ler bytes dado tamanho do registro
                playerReg1 = dos1.readNBytes(registro1.getSize());
                playerReg2 = dos2.readNBytes(registro2.getSize());
                        
                //converter bytes para objeto
                registro1 = Converter.toObject(playerReg1);
                registro2 = Converter.toObject(playerReg2);

                for(cout = 1; cout < 100; cout++) {
                    if(cond == 1){ // cond para ler registro do arquivo tmp1
                        //ler lapide do arquivo tmp1
                        registro1.setLapide(dos1.readBoolean());
                        
                        //ler tamanho do registro
                        registro1.setSize(dos1.readInt());
                        
                        //ler bytes dado tamanho do registro
                        playerReg1 = dos1.readNBytes(registro1.getSize());
                        
                        //converter para objeto
                        registro1 = Converter.toObject(playerReg1);
                    
                    } else { // cond para ler registro do arquivo tmp2
                        //ler lapide do arquivo tmp1
                        registro2.setLapide(dos2.readBoolean());
                        
                        //ler tamanho do registro
                        registro2.setSize(dos2.readInt());
                        
                        //ler bytes dado tamanho do registro
                        playerReg2 = dos2.readNBytes(registro2.getSize());
                        
                        //converter para objeto
                        registro2 = Converter.toObject(playerReg2);
                    }

                    if(registro1.getPlayer().getId() <= registro2.getPlayer().getId()) {
                        raf.write(playerReg1);
                        cond = 1;
                    } 
                    else {
                        raf.write(playerReg2);
                        cond = 2;
                    }
                
                } //for
                
                x++;
                cout = 0;
            } //while

        }//try
        catch (IOException e) { System.out.println(e.getMessage());}

    }
    public static void intercalacao_balanceada() throws Exception {
        distribuicao();
        intercalar();
    }

    // public static void Start() {
    //     int opc = -1;
    //     String path[] = new String[2];
    //     Scanner src = new Scanner(System.in);
        
    //     path[0] = "tmp1.txt";
    //     path[1] = "tmp2.txt";

    //     File file1 = new File(path[0]);
    //     File file2 = new File(path[1]);
        
    //     while(opc != 0) {
    //         System.out.println("=================== INTERCALAÇÕES ===================");
    //         System.out.println("0-sair");
    //         System.out.println("1-intercalação balanceada");
    //         System.out.println("2-intercalação balanceada Otimizada");
    //         System.out.print("Escolha a intercalação: ");
            
    //         opc = src.nextInt();
                    
    //         switch (opc) {
    //             case 1:
    //                 System.out.println("Iniciando balanceamento");
    //                 try{ intercalacao_balanceada("src\\data\\nflPlayers.db", path); } 
    //                 catch(Exception e) { System.out.println(e.getMessage()); }
    //                 file2.delete();
    //                 file1.delete();
    //                 break;
                
    //             default:
    //                 break;
    //         }
            
    //     }   
    //     src.close();
    // }

}
