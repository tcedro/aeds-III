package src.services;

import java.io.DataOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import src.entities.Player;
import src.entities.Registro;

public class Arquivo {
    public static Registro parsePlayer(String line, Long size) {
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

        return new Registro(true, size , new Player(name, date, age, positions, collegeUniv, actTeam));
    }

    public static void CsvToDB(String pathRead, String pathWrite) {
        DataOutputStream dos;
        FileOutputStream arq;
        
        try {
            
            File file = new File(pathRead);
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            
            arq  = new FileOutputStream(pathWrite);
            dos = new DataOutputStream(arq);
            
            String csvLine;
            int i = 0;

            while ((csvLine = raf.readLine()) != null) {
                Registro reg = parsePlayer(csvLine, raf.getFilePointer());
                
                reg.getPlayer().setId(i);
                dos.writeBoolean(reg.getLapide());
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

    public static void salvarNovoRegistro(Player player) throws Exception{
        Registro registro = new Registro(true, 0L, player);
        try {
            RandomAccessFile raf = new RandomAccessFile("src\\data\\nflPlayers.db", "rw");

            raf.seek(raf.length());

            raf.writeBoolean(registro.getLapide());
            raf.writeLong(registro.getSize());
            raf.writeInt(registro.getPlayer().getId());
            raf.writeUTF(registro.getPlayer().getName());
            raf.writeInt(registro.getPlayer().getAge());
            for (String position : registro.getPlayer().getPositions()) raf.writeUTF(position);
            raf.writeUTF(registro.getPlayer().getCollegeUniv());
            raf.writeUTF(registro.getPlayer().getActTeam());
            raf.writeInt(registro.getPlayer().getPickDate());

            raf.close();
        
        }catch (IOException e) {
            System.out.println(e.getMessage());
            throw new Exception("Error: operação de novo jogador falhou!");
        } 
    }

    public static void deletarRegistro(int id) throws Exception {
        Registro registro = new Registro();
        boolean status = false;
        try {
            RandomAccessFile raf = new RandomAccessFile("src\\data\\nflPlayers.db", "rw");
            
            while(raf.getFilePointer() < raf.length() || status == false) {
                String line = raf.readLine();
                if(){}
            }
            
        } catch(IOException e) { throw new Exception("ERROR: operação de deletar jogador"); }
    }
}
