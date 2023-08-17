package src.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import src.entities.Player;

public class CsvManager extends FileManager {
    public CsvManager() {}
    public CsvManager(String path) {
        super(path);
    }

    public static Player parsePlayer(String line) {
        String[] columns = line.split(","); 
        int age = 0;
        String name = columns[3];
        int id = Integer.parseInt(columns[1]);
        if(columns[6] != "") {
            age = Integer.parseInt(columns[6]);
        }  
        String[] positions = {columns[4], columns[5]};
        String collegeUniv = columns[7];
        String actTeam = columns[2];
        String pickDate = columns[0];

        return new Player(name, id, age, positions, collegeUniv, actTeam, pickDate);
    }
    public void convertCsvToBinary(String pathToWrite) {
        DataOutputStream dos;
        FileOutputStream arq;
        try {
            // BufferedReader reader = new BufferedReader(new FileReader(this.getFile()));
            File file = new File(this.getFile().getPath());
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            
            arq  = new FileOutputStream(pathToWrite);
            dos = new DataOutputStream(arq);
            
            raf.close();
            
            String line;
            int i = 0;
            
            while ((line = raf.readLine()) != null) {
                Player player = parsePlayer(line);
                System.out.println(i++ +" " + player.toString());
                
                dos.writeUTF(player.getName());
                dos.writeInt(player.getId());
                dos.writeInt(player.getAge());
                
                for (String position : player.getPositions()) dos.writeUTF(position);
                
                dos.writeUTF(player.getCollegeUniv());
                dos.writeUTF(player.getActTeam());
                dos.writeUTF(player.getPickDate());
                dos.writeChars(" ");
            
            }
        } catch(IOException e) { System.out.println(e.getMessage()); }
    }
}