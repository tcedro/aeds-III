package src.utils;

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
import src.services.Sort;

public class Arquivo extends FileManager {
    public Arquivo() {}
    public Arquivo(String path) {
        super(path);
    }

    public static Registro parsePlayer(String line) {
        String[] columns = line.split(","); 
        int age = 0;
        String name = columns[3];
        String collegeUniv = "";
        int date = Integer.parseInt(columns[0]);
        //Corrigir tuplas sem colunas.
        try{
            if(columns[6] != "") age = Integer.parseInt(columns[6]);
        }  catch(ArrayIndexOutOfBoundsException e) { age = 0; }
        
        try {
           collegeUniv = columns[7];
        } catch (ArrayIndexOutOfBoundsException e) { collegeUniv = "Nill"; }    
        
        String[] positions = {columns[4], columns[5]};
        String actTeam = columns[2];
        
        Player player = new Player(name, date, age, positions, collegeUniv, actTeam);
        
        return new Registro('s', 0 , player);
    }

    public void CsvToByte(String pathWriteFile) {
        DataOutputStream dos;
        FileOutputStream arq;
        
        try {
            File file = new File(this.getFile().getPath());
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            
            arq  = new FileOutputStream(pathWriteFile);
            dos = new DataOutputStream(arq);
            
            String csvLine;
            int i = 0;
            
            while ((csvLine = raf.readLine()) != null) {
                Registro reg = parsePlayer(csvLine);
                RandomAccessFile raf1 = new RandomAccessFile(pathWriteFile, "r");
                reg.getPlayer().setId(i);
                
                System.out.println(i++ + " " + reg.getPlayer().toString());
                //escrevendo no arquivo
                //tamanho do arquivo
                // dos.writeLong(raf1.length());
                //lapide
                // dos.writeChar(' ');
                // dos.writeChar(reg.getLapide());
                // dos.writeInt(reg.getSize());
                dos.writeInt(reg.getPlayer().getId());
                dos.writeUTF(reg.getPlayer().getName());
                dos.writeInt(reg.getPlayer().getAge());
                for (String position : reg.getPlayer().getPositions()) dos.writeUTF(position);
                dos.writeUTF(reg.getPlayer().getCollegeUniv());
                dos.writeUTF(reg.getPlayer().getActTeam());
                dos.writeInt(reg.getPlayer().getPickDate());
                
               
            }
            raf.close();
            
        } catch(IOException e) { System.out.println(e.getMessage()); } 
    }

    public Player readByteFile(String path) {
        DataInputStream dis;
        FileInputStream arq;

        
        Player player = new Player();
        try {
            arq = new FileInputStream(path);
            dis = new DataInputStream(arq);

            try{
                
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


                System.out.println("leitura do arquivo: " + player.toString());
            } catch(IOException e) { e.printStackTrace(); }

        
        } catch (FileNotFoundException e) { System.out.println(e.getMessage()); } 
        
        return player;
    }

    public Registro[] readClusterRegister(String path) {
        int x = 0;
        DataInputStream dis;
        FileInputStream arq;
        Registro[] reg = new Registro[100];
        
        
        try {
            arq = new FileInputStream(path);
            dis = new DataInputStream(arq);
            while (x < 100) {
                Player player = new Player();
                    try{
                        reg[x] = new Registro();
                        // char lapide = dis.readChar();
                        // if(lapide != '*') {

                        //     reg[x].setSize(dis.readInt());
                            
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
                            reg[x++].setPlayer(player);
                            // System.out.println(x + " reg: " + reg[x].toString());
                            // reg[x++].setPlayer(player);
                        // }
                        // System.out.println(x + " " + player.toString());
                    } catch(IOException e) { e.printStackTrace(); }
            }
        } 
        
        catch(FileNotFoundException e) { e.printStackTrace(); } 
        catch(IOException e)           { System.out.println(e.getMessage()); }
        
        return reg;
    }
    
    public void intercalacao_balanceada(String path) {
        Registro[] bloco = readClusterRegister(path);
        // bloco = Sort.sort(bloco);
        
        // for (int i = 0; i < bloco.length; i++) {
        //     System.out.println("Lido: " + bloco[i].getPlayer().toString());
        // }
    }



}