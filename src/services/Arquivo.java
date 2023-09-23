package src.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;


import src.entities.Player;
import src.entities.Registro;
import src.utils.Converter;

public class Arquivo {
    public static final String db = "src\\data\\nflPlayers.db";
    public static Player parsePlayer(String line) {
        String[] columns = line.split(","); 
        int age = 0;
        String name = columns[3];
        String collegeUniv = "";
        int date = Integer.parseInt(columns[0]);
        
        //Corrigir tuplas sem colunas.
        try { if(columns[6] != "") age = Integer.parseInt(columns[6]); }  
        catch(ArrayIndexOutOfBoundsException e) { age = 0; }
        try { collegeUniv = columns[7]; } 
        catch (ArrayIndexOutOfBoundsException e) { collegeUniv = "Nill"; }    
        
        String[] positions = {columns[4], columns[5]};
        String actTeam = columns[2];

        return new Player(name, date, age, positions, collegeUniv, actTeam);
    }

    public static void CsvToDB(String pathRead, String pathWrite) {
        DataOutputStream dos;
        FileOutputStream arq;
        
        try {
            
            File file = new File(pathRead);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            
            arq  = new FileOutputStream(pathWrite);
            dos = new DataOutputStream(arq);
            
            String csvLine;
            int i = 0;

            //pos do ultimo id
            dos.writeInt(0);
            //gravar todos registros convertendo pra byte array
            while ((csvLine = raf.readLine()) != null) {
                Player player = parsePlayer(csvLine);
                player.setId(++i);


                System.out.println("csvToDb: " + player.toString());
                
                byte[] byteArray = Converter.toByteArray(player);
                dos.writeBoolean(false);
                dos.writeInt(byteArray.length);
                System.out.println("size of byte: " + byteArray.length);
                dos.write(byteArray);

                
            }
        
            //gravando ultimo id            
            raf = new RandomAccessFile(pathWrite, "rws");
            raf.seek(0);
            raf.writeInt(i);
            raf.close();



        } catch(IOException e) { System.out.println(e.getMessage()); } 
    }


    public static void gravarNovoRegistroCrud(Player player) {
        RandomAccessFile raf;
        int id;

        try {
            raf = new RandomAccessFile(db, "rws");
            //definidno id, conforme ultimo inserido;
            raf.seek(0);
            id = raf.readInt();
            player.setId(++id);
            
            raf.seek(raf.length());

            System.out.println("Gravando registro: " + player.toString());
            byte[] byteArray = Converter.toByteArray(player);
            
            raf.writeBoolean(false);
            raf.writeInt(byteArray.length);
            raf.write(byteArray);

            //ultimo id id inserido
            raf.seek(0);
            raf.writeInt(player.getId());
            raf.seek(0);

            System.out.println("ultimo id: " + raf.readInt());
            raf.close();

        }catch(IOException e) { e.printStackTrace(); }
    }

    public static Player procurarRegistroCrud(int id) {
        FileInputStream fis;
        DataInputStream dos;
        Registro registro = new Registro();
        int cout = 0;
        
        try {
            fis = new FileInputStream(db);
            dos = new DataInputStream(fis);
            
            int ultimoID = dos.readInt();
            System.out.println(ultimoID);
            byte[] regBytes;

            while(cout < ultimoID) {
        
                registro.setLapide(dos.readBoolean());
                registro.setSize(dos.readInt());
                
                if(registro.getLapide() != true) {
                    
                    regBytes = dos.readNBytes(registro.getSize());
                    registro = Converter.toObject(regBytes);
        
                    if(registro.getPlayer().getId() == id) { return registro.getPlayer(); }

                } else { dos.skipBytes(registro.getSize()); }

                cout++;
            }
            dos.close();
            fis.close();

        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e) { e.printStackTrace(); }
       
        return null;
    }

    public static boolean atualizarRegistroPlayer(Player target) {
        RandomAccessFile raf;
        Registro registro = new Registro();
        int cout=0;
        int oldLen=0;
        
        try {
            raf = new RandomAccessFile(db, "rw");
            
            int ultimoID = raf.readInt();
            System.out.println(ultimoID);
            Long pos;
            
            byte[] regBytes;

            while(cout < ultimoID) {
                registro.setLapide(raf.readBoolean());
                oldLen = raf.readInt();
                regBytes=new byte[oldLen];
                registro.setSize(oldLen);
                pos = raf.getFilePointer();

                if(registro.getLapide() != true) {
                    raf.read(regBytes, 0, registro.getSize());
                    registro = Converter.toObject(regBytes);
                    
                    if(registro.getPlayer().getId() == target.getId()) {
                        registro.setPlayer(target);
                        regBytes = Converter.toByteArray(target);
                        System.out.println("size antigo - size novo " + oldLen +  " - " + regBytes.length);
                        
                        if(oldLen >= regBytes.length) {
                            System.out.println("gravei na mema posicao ");
                            raf.seek(pos);
                            raf.write(regBytes);
                        
                        } else {
                            System.out.println("gravei no final ");
                            raf.seek(raf.length());
                            raf.write(regBytes);
                            raf.seek(pos-2);
                            raf.writeBoolean(true);
                        
                        }

                        raf.close();
                        return true;
                    }
                }
             
                cout++;
            }
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e) { e.printStackTrace(); }
       
        return false;
    }

    public static boolean deletarRegistroPlayer(int id) {
        boolean status=false;
        RandomAccessFile raf;
        Registro registro = new Registro();
        int cout = 0;
        byte[] regBytes;
        try {
            raf = new RandomAccessFile(db, "rw");
        
            int ultimoID = raf.readInt();
            System.out.println(ultimoID);
            Long pos;
        
            while(cout < ultimoID) {
                boolean lapide = raf.readBoolean();
                int len = raf.readInt();
                
                registro.setSize(len);
                regBytes = new byte[len];
                pos = raf.getFilePointer();

                if(registro.getLapide() != true) {
                    raf.read(regBytes, 0, registro.getSize());
                    registro = Converter.toObject(regBytes);
                    registro.setLapide(lapide);
                    registro.setSize(len);
                    
                    if(registro.getPlayer().getId() == id) {
                        pos = pos-2;
                        raf.seek(pos);
                        raf.writeBoolean(true);
                        raf.close();
                        return true;
                    }
                }
             
                cout++;
            }
        
        
        
        } catch(IOException e) { e.printStackTrace(); }
       

        return status;
    }

}
