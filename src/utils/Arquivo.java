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

public class Arquivo extends FileManager {
    public Arquivo() {}
    public Arquivo(String path) {
        super(path);
    }

    public static Player parsePlayer(String line) {
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

        return new Player(name, date,age, positions, collegeUniv, actTeam);
    }
    public void convertCsvToBinary(String pathWriteFile) {
        DataOutputStream dos;
        FileOutputStream arq;
        
        try {
            // BufferedReader reader = new BufferedReader(new FileReader(this.getFile()));
            File file = new File(this.getFile().getPath());
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            
            arq  = new FileOutputStream(pathWriteFile);
            dos = new DataOutputStream(arq);
            
            String csvLine;
            int i = 0;
            
            while ((csvLine = raf.readLine()) != null) {
                Player player = parsePlayer(csvLine);
                RandomAccessFile raf1 = new RandomAccessFile(pathWriteFile, "r");
                player.setId(i);
                
                System.out.println(i++ +" " + player.toString());
                //escrevendo no arquivo
                //tamanho do arquivo
                // dos.writeLong(raf1.length());
                //lapide
                // dos.writeChar(' ');
                dos.writeInt(player.getId());
                dos.writeUTF(player.getName());
                dos.writeInt(player.getAge());
                for (String position : player.getPositions()) dos.writeUTF(position);
                dos.writeUTF(player.getCollegeUniv());
                dos.writeUTF(player.getActTeam());
                dos.writeInt(player.getPickDate());
                
               
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

        
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } 
        return player;
    }
}