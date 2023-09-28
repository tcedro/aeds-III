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
    public static final int tamBloco = 100;
    public static final String db = "src\\data\\nflPlayers.db";
    public static final String[] tmpFiles = { "tmp1.txt", "tmp2.txt", "tmp3.txt", "tmp4.txt" };

    private static void distribuicao() {

        RandomAccessFile raf;
        Registro[] registros = new Registro[tamBloco];
        int cout = 0;
        
        try {
            raf = new RandomAccessFile(db, "r");
            
            int ultimoID = raf.readInt();
            System.out.println("ultimo id: " + ultimoID);
            
            byte[] playerBytes;
            while (cout <= ultimoID) {
                for(int i = 0; i < 100; i++) {
                    registros[i] = new Registro();
                    
                    registros[i].setLapide(raf.readBoolean());
                    registros[i].setSize(raf.readInt());
                    playerBytes = new byte[registros[i].getSize()];
                    System.out.println("lapide= " + registros[i].getLapide());
                    System.out.println("size= " + registros[i].getSize());
                    if(registros[i].getLapide() != true) {
                        raf.read(playerBytes);
                        registros[i] = Converter.toObject(playerBytes);


                    } else { 
                        raf.skipBytes(registros[i].getSize()); 
                        if(i != 0) {
                            i=i-2; 
                        } else {
                            i=i-1;
                        }
                    }
                
                }
                
                if(cout % 2 == 0) { Arquivo.gravarRegistroOrdenacao( Sort.sort(registros), tmpFiles[0] ); } 
                else              { Arquivo.gravarRegistroOrdenacao( Sort.sort(registros), tmpFiles[1] ); }
                
                cout++;
            }

            
            raf.close();
            
        }
         
        catch (FileNotFoundException e) { e.printStackTrace(); }
        catch (IOException e) { System.out.println(e.getMessage());}
        catch(NullPointerException e) {
            e.printStackTrace();
        }
    }    
    
    private static void intercalar(String readFile1, String readFile2, String writeFile1, String writeFile2) {
        //cond de parada
        boolean status = true;
        
        //leitura arquivo tmp1;
        FileInputStream fis1;
        DataInputStream dos1;
        
        //leitura arquivo tmp2;
        FileInputStream fis2;
        DataInputStream dos2;
        
        //metodo de escrita com registro intercalado
        RandomAccessFile raf1;
        RandomAccessFile raf2;
        
        //registros lidos do arquivo        
        Registro registro1 = new Registro();
        Registro registro2 = new Registro();
        
        int cout = 0;
        int countWriteFileTmp2 = 0;
        try {
            //conectar fluxo de dados
            fis1 = new FileInputStream(readFile1);
            fis2 = new FileInputStream(readFile2);

            dos1 = new DataInputStream(fis1);
            dos2 = new DataInputStream(fis2);

            raf1 = new RandomAccessFile(writeFile1, "rws");
            raf1.seek(0);
            
            raf2 = new RandomAccessFile(writeFile2, "rws");
            raf2.seek(0);

            int x = 1;
            int cond = -2;
            
            byte[] playerReg1, playerReg2;

            //85 = hardcode de vezes que serão rodados ...
            while(x < 85 || status != false) { 
                System.out.println("x= " + x);
                
                //ler lapide
                registro1.setLapide(dos1.readBoolean());
                registro2.setLapide(dos2.readBoolean());
                
                System.out.println("lapide1= " + registro1.getLapide());
                System.out.println("lapide2= " + registro2.getLapide());
                
                //tamanho do registro
                registro1.setSize(dos1.readInt());
                registro2.setSize(dos2.readInt());
                
                System.out.println("tam1= " + registro1.getSize());
                System.out.println("tam2= " + registro2.getSize());
                        
                //ler bytes dado tamanho do registro
                playerReg1 = dos1.readNBytes(registro1.getSize());
                playerReg2 = dos2.readNBytes(registro2.getSize());

                System.out.println(Converter.toObject(playerReg1).getPlayer().toString());
                System.out.println(Converter.toObject(playerReg2).getPlayer().toString());
                //converter bytes para objeto
                registro1 = Converter.toObject(playerReg1);
                registro2 = Converter.toObject(playerReg2);
                
                for(cout = 1; cout < 100; cout++) {
                    
                    if(x % 2 == 0) { // gravacao binaria entre 2 arquivos
                        raf1.seek(0);

                        if(cond == 1) { // cond para ler registro do arquivo tmp1
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
                            raf1.write(playerReg1);
                            cond = 1;
                        } 
                        else {
                            raf1.write(playerReg2);
                            cond = 2;
                        }
                
                    } else {
                        raf2.seek(0);
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
                            raf2.write(playerReg1);
                            cond = 1;
                            countWriteFileTmp2++;
                        } 
                        else {
                            raf2.write(playerReg2);
                            cond = 2;
                            countWriteFileTmp2++;
                        }
                        
                    }
                } //for
                if(countWriteFileTmp2 == 0) { Arquivo.atualizarDataBaseFileOrdenado(tmpFiles[0]); status = false; }
                
                x++;
                cout = 0;
            } //while

            //chamada da recursao fazer dnv ate nao escrever nada no segundo arquivo temporario
            if(countWriteFileTmp2 > 0 && x % 2 == 0) {
                
                System.out.println("tmp 3 tmp 4 tmp 0 tmp 1");
                intercalar(tmpFiles[2], tmpFiles[3], tmpFiles[0], tmpFiles[1]); 
            }
            
            else if(countWriteFileTmp2 > 0 && x % 2 != 0){
                
                System.out.println("tmp 0 tmp 1 tmp 2 tmp 3");
                intercalar(tmpFiles[0], tmpFiles[1], tmpFiles[2], tmpFiles[3]);
            
            } else {
                System.out.println("atualizar DB"); 
                Arquivo.atualizarDataBaseFileOrdenado(tmpFiles[0]);
            }
        }//try
        catch (IOException e) { Arquivo.atualizarDataBaseFileOrdenado(tmpFiles[0]); }

    }
    public static void intercalacao_balanceada() throws Exception {
        distribuicao();
        intercalar( tmpFiles[0], tmpFiles[1], tmpFiles[2], tmpFiles[3] );
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
