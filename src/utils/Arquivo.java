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

public class Arquivo extends FileManager {
    public Arquivo() {}
    public Arquivo(String path) {
        super(path);
    }

    public static Registro parsePlayer(String line, Long size) {
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

        return new Registro('s', size , player);
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
                Registro reg = parsePlayer(csvLine, raf.getFilePointer());
                RandomAccessFile raf1 = new RandomAccessFile(pathWriteFile, "r");
                
                reg.getPlayer().setId(i);
                
                //escrevendo no arquivo
                dos.writeChar(reg.getLapide());
                dos.writeLong(reg.getSize());
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

    public Registro[] lerBlocoDeRegistro(String path) {
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
                        char lapide = dis.readChar();
                        if(lapide != '*') {

                            reg[x].setLapide(lapide);
                            reg[x].setSize(dis.readLong());
                            
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
                        }
                        // System.out.println(x + " " + player.toString());
                    } catch(IOException e) { e.printStackTrace(); }
            }
        } 
        
        catch(FileNotFoundException e) { e.printStackTrace(); } 
        catch(IOException e)           { System.out.println(e.getMessage()); }
        
        return reg;
    }

}