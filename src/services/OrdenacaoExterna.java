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

    private static void distribuicao() 
    {

        RandomAccessFile raf;
        Registro[] registros = new Registro[tamBloco];
        int cout = 0;
        
        try 
        {
            raf = new RandomAccessFile(db, "r");
            
            int ultimoID = raf.readInt();
            System.out.println("Ultimo ID inserido: " + ultimoID);
            
            byte[] playerBytes;
            while (cout <= ultimoID) {
                for(int i = 0; i < tamBloco; i++) {
                    registros[i] = new Registro();
                    
                    registros[i].setLapide(raf.readBoolean());
                    registros[i].setSize(raf.readInt());
                    playerBytes = new byte[registros[i].getSize()];
                    
                    if(registros[i].getLapide() != true) {
                        raf.read(playerBytes);

                        registros[i] = Converter.toObject(playerBytes);

                    } 
                    else { 
                        raf.skipBytes(registros[i].getSize()); 
                        if(i != 0) {
                            i=i-2; 
                        } 
                        else {
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

    private static void intercalar(String readFile1, String readFile2, String writeFile1, String writeFile2, int multiplicador) {
        RandomAccessFile read1;
        RandomAccessFile read2;

        RandomAccessFile write1;
        RandomAccessFile write2;

        int cond = -1;

        byte[] readPlayer1 = new byte[100], readPlayer2 = new byte[100];
        
        Registro registro1, registro2;   
        registro1 = new Registro();
        registro2 = new Registro();
        try {                
            read1 = new RandomAccessFile(readFile1, "r");
            read2 = new RandomAccessFile(readFile2, "r");

            write1 = new RandomAccessFile(writeFile1, "rw");
            write2 = new RandomAccessFile(writeFile2, "rw");

            int coutFor = 1;
            int coutFile1 = 0;
            int coutFile2 = 0;

            // primeira intercalao
            while (read1.getFilePointer() < read1.length() && read2.getFilePointer() < read2.length()) {
                coutFile1 = 0;
                coutFile2 = 0;
                System.out.println("cout for: " + coutFor);
                for(int i = 0; i < 200 * multiplicador; i++) {
                    System.out.println("count file1: " + coutFile1);
                    System.out.println("count file2: " + coutFile2);
                    System.out.println("COUNT I= " + i);
                    if(coutFile1 <  100 * multiplicador && coutFile2 < 100 * multiplicador && read1.getFilePointer() != read1.length() && read2.getFilePointer() != read2.length())  {
                        if( cond == 1 ) { // andar com ponteiro tmp1
                            registro1.setLapide( read1.readBoolean() );
                            registro1.setSize( read1.readInt() );
                            
                            readPlayer1 = new byte[registro1.getSize()];
                            read1.read(readPlayer1);
                            
                            registro1 = Converter.toObject(readPlayer1);
                        
                        } else if( cond == 2 ) {
                            registro2.setLapide( read2.readBoolean() );
                            registro2.setSize( read2.readInt() );
                            
                            readPlayer2 = new byte[registro2.getSize()];
                            read2.read(readPlayer2);
                            
                            registro2 = Converter.toObject(readPlayer2);
                        
                        } else {
                            registro1.setLapide( read1.readBoolean() );
                            registro1.setSize( read1.readInt() );
                        
                            registro2.setLapide( read2.readBoolean() );
                            registro2.setSize( read2.readInt() );

                            readPlayer1 = new byte[registro1.getSize()];
                            readPlayer2 = new byte[registro2.getSize()];

                            read1.read(readPlayer1);
                            read2.read(readPlayer2);

                            registro1 = Converter.toObject(readPlayer1);
                            registro2 = Converter.toObject(readPlayer2);
                        }
                        

                        if(registro1.getPlayer().getId() <= registro2.getPlayer().getId()) {
                            cond = 1;
                            if(coutFor % 2 == 1){
                                coutFile1++;
                                write1.writeBoolean(false);
                                write1.writeInt(readPlayer1.length);
                                write1.write(readPlayer1);
                            } else {
                                coutFile2++;
                                write2.writeBoolean(false);
                                write2.writeInt(readPlayer1.length);
                                write2.write(readPlayer1);
                            }
                        } else {
                            cond = 2;
                            if(coutFor % 2 == 1){
                                coutFile1++;
                                write1.writeBoolean(false);
                                write1.writeInt(readPlayer2.length);
                                write1.write(readPlayer2);
                            } else {
                                coutFile2++;
                                write2.writeBoolean(false);
                                write2.writeInt(readPlayer2.length);
                                write2.write(readPlayer2);
                            }
                        }
                    }
                    ///

                    if(coutFile1 == (100 * multiplicador) || read1.getFilePointer() == read1.length() ) {
                        System.out.println("entrei: coutFile1 == (100 * multiplicador) || read1.getFilePointer() == read1.length()");
                        if(coutFor % 2 == 1) {
                                registro2.setLapide( read2.readBoolean() );
                                registro2.setSize( read2.readInt() );
                                readPlayer2 = new byte[registro2.getSize()];
                                read2.read(readPlayer2);

                                write1.writeBoolean(false);
                                write1.writeInt(readPlayer2.length);
                                write1.write(readPlayer2);

                        } else {
                            System.out.println("entrei: else");
                                registro2.setLapide( read2.readBoolean() );
                                registro2.setSize( read2.readInt() );
                                readPlayer2 = new byte[registro2.getSize()];
                                read2.read(readPlayer2);

                                write2.writeBoolean(false);
                                write2.writeInt(readPlayer2.length);
                                write2.write(readPlayer2);

                        }
                    
                    } else if(coutFile2 == (100 * multiplicador) || read2.getFilePointer() == read2.length()) {
                        if(coutFor % 2 == 1) {
                                registro1.setLapide( read1.readBoolean() );
                                registro1.setSize( read1.readInt() );
                                readPlayer1 = new byte[registro1.getSize()];
                                read1.read(readPlayer1);

                                write1.writeBoolean(false);
                                write1.writeInt(readPlayer1.length);
                                write1.write(readPlayer1);

                        } else {
                                registro1.setLapide( read1.readBoolean() );
                                registro1.setSize( read1.readInt() );
                                readPlayer1 = new byte[registro1.getSize()];
                                read1.read(readPlayer1);

                                write1.writeBoolean(false);
                                write1.writeInt(readPlayer1.length);
                                write1.write(readPlayer1);

                        }
                    }
                    System.out.println("count for = " + coutFor);
                } // for
                coutFor++; 
            }  // while

            if(coutFile2 > 0 && coutFile1 > 0) { intercalar(writeFile1, writeFile2, readFile1, readFile2, multiplicador  * 2); }  
            else { Arquivo.atualizarDataBaseFileOrdenado(writeFile1); }

        } catch(IOException e) { 
            System.out.println("deu erro");
        } // try
    }
    
    // private static void intercalar(String readFile1, String readFile2, String writeFile1, String writeFile2) {
    //     //cond de parada
    //     boolean status = true;
        
    //     //leitura arquivo tmp1;
    //     RandomAccessFile fis1;
    //     RandomAccessFile fis2;
        

        
    //     //metodo de escrita com registro intercalado
    //     RandomAccessFile raf1;
    //     RandomAccessFile raf2;
        
    //     //registros lidos do arquivo        
    //     Registro registro1 = new Registro();
    //     Registro registro2 = new Registro();
        
    //     int cout = 0;
    //     int countWriteFileTmp2 = 0;
    //     try {
    //         //conectar fluxo de dados
    //         fis1 = new RandomAccessFile(readFile2, "rw");
    //         fis2 = new RandomAccessFile(readFile2, "rw");

    //         raf1 = new RandomAccessFile(writeFile1, "rw");
    //         raf1.seek(0);
            
    //         raf2 = new RandomAccessFile(writeFile2, "rw");
    //         raf2.seek(0);

    //         int x = 1;
    //         int cond = -2;
            
    //         byte[] playerReg1, playerReg2;

    //         //85 = hardcode de vezes que serão rodados ...
    //         while(x < 85 || status != false) { 
    //             System.out.println("x= " + x);
                
    //             //ler lapide
    //             registro1.setLapide(fis1.readBoolean());
    //             registro2.setLapide(fis2.readBoolean());
                
    //             System.out.println("lapide1= " + registro1.getLapide());
    //             System.out.println("lapide2= " + registro2.getLapide());
                
    //             //tamanho do registro
    //             registro1.setSize(fis1.readInt());
    //             registro2.setSize(fis2.readInt());
                
    //             System.out.println("tam1= " + registro1.getSize());
    //             System.out.println("tam2= " + registro2.getSize());

    //             playerReg1  = new byte[registro1.getSize()];
    //             playerReg2  = new byte[registro2.getSize()];
                        
    //             //ler bytes dado tamanho do registro
    //             fis1.read(playerReg1);
    //             fis2.read(playerReg2);

    //             System.out.println(Converter.toObject(playerReg1).getPlayer().toString());
    //             System.out.println(Converter.toObject(playerReg2).getPlayer().toString());

    //             //converter bytes para objeto
    //             registro1 = Converter.toObject(playerReg1);
    //             registro2 = Converter.toObject(playerReg2);
                
    //             for(cout = 1; cout < 100; cout++) {
                    
    //                 if(x % 2 == 0) { // gravacao binaria entre 2 arquivos
    //                     raf1.seek(0);

    //                     if(cond == 1) { // cond para ler registro do arquivo tmp1
    //                         //ler lapide do arquivo tmp1
                            
    //                         registro1.setLapide(fis1.readBoolean());
    //                         //ler tamanho do registro
    //                         registro1.setSize(fis1.readInt());

    //                         playerReg1 = new byte[registro1.getSize()];

    //                         //ler bytes dado tamanho do registro
    //                         fis1.read(playerReg1);
                            
    //                         //converter para objeto
    //                         registro1 = Converter.toObject(playerReg1);
                        
    //                     } else { // cond para ler registro do arquivo tmp2
    //                         //ler lapide do arquivo tmp1
    //                         registro2.setLapide(fis2.readBoolean());
    //                         //ler tamanho do registro
    //                         registro2.setSize(fis2.readInt());

    //                         playerReg2 = new byte[registro2.getSize()];

    //                         //ler bytes dado tamanho do registros
    //                         fis2.read(playerReg2);
                            
    //                         //converter para objeto
    //                         registro1 = Converter.toObject(playerReg2);
    //                     }

    //                     if(registro1.getPlayer().getId() <= registro2.getPlayer().getId()) {
    //                         raf1.write(playerReg1);
    //                         cond = 1;
    //                     } 
    //                     else {
    //                         raf1.write(playerReg2);
    //                         cond = 2;
    //                     }
                
    //                 } else {
    //                     raf2.seek(0);
    //                     if(cond == 1){ // cond para ler registro do arquivo tmp1
                            
    //                     //ler lapide do arquivo tmp1
    //                     registro1.setLapide(fis1.readBoolean());
                            
    //                     //ler tamanho do registro
    //                     registro1.setSize(fis1.readInt());
                            
    //                     //ler bytes dado tamanho do registro
    //                     playerReg1 = new byte[registro1.getSize()];

    //                     fis1.read(playerReg1);
                            
    //                     //converter para objeto
    //                     registro1 = Converter.toObject(playerReg1);
                        
    //                     } else { // cond para ler registro do arquivo tmp2
    //                         //ler lapide do arquivo tmp1
    //                         registro2.setLapide(fis2.readBoolean());
                            
    //                         //ler tamanho do registro
    //                         registro2.setSize(fis2.readInt());
                            
    //                         //ler bytes dado tamanho do registro
    //                         playerReg2 = new byte[registro2.getSize()];

    //                         fis2.read(playerReg2);
                            
    //                         //converter para objeto
    //                         registro2 = Converter.toObject(playerReg2);
    //                     }
    //                     if(registro1.getPlayer().getId() <= registro2.getPlayer().getId()) {
    //                         raf2.write(playerReg1);
    //                         cond = 1;
    //                         countWriteFileTmp2++;
    //                     } 
    //                     else {
    //                         raf2.write(playerReg2);
    //                         cond = 2;
    //                         countWriteFileTmp2++;
    //                     }
                        
    //                 }
    //             } //for
    //             if(countWriteFileTmp2 == 0) { Arquivo.atualizarDataBaseFileOrdenado(tmpFiles[0]); status = false; }
                
    //             x++;
    //             cout = 0;
    //         } //while

    //         //chamada da recursao fazer dnv ate nao escrever nada no segundo arquivo temporario
    //         if(countWriteFileTmp2 > 0 && x % 2 == 0) {
                
    //             System.out.println("tmp 3 tmp 4 tmp 0 tmp 1");
    //             intercalar(tmpFiles[2], tmpFiles[3], tmpFiles[0], tmpFiles[1]); 
    //         }
            
    //         else if(countWriteFileTmp2 > 0 && x % 2 != 0){
                
    //             System.out.println("tmp 0 tmp 1 tmp 2 tmp 3");
    //             intercalar(tmpFiles[0], tmpFiles[1], tmpFiles[2], tmpFiles[3]);
            
    //         } else {
    //             System.out.println("atualizar DB"); 
    //             Arquivo.atualizarDataBaseFileOrdenado(tmpFiles[0]);
    //         }
    //     }//try
    //     catch (IOException e) { Arquivo.atualizarDataBaseFileOrdenado(tmpFiles[0]); }

    // }
    public static void intercalacao_balanceada() throws Exception {
        distribuicao();
        intercalar( tmpFiles[0], tmpFiles[1], tmpFiles[2], tmpFiles[3] , 1);
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
