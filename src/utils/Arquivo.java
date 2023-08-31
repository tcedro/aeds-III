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

    private void gravarNoArquivo(Registro bloco[], String path) {
        DataOutputStream dos;
        FileOutputStream arq;
        int index = 0;
        try {
            arq = new FileOutputStream(path);
            dos = new DataOutputStream(arq);
            while(index < 100){
                dos.writeInt(bloco[index].getPlayer().getId());
                dos.writeUTF(bloco[index].getPlayer().getName());
                dos.writeInt(bloco[index].getPlayer().getAge());
                String pos[] = bloco[index].getPlayer().getPositions();
                dos.writeUTF(pos[0]);
                dos.writeUTF(pos[1]);
                dos.writeUTF(bloco[index].getPlayer().getCollegeUniv());
                dos.writeUTF(bloco[index].getPlayer().getActTeam());
                dos.writeInt(bloco[index].getPlayer().getPickDate());
                System.out.println("gravando elemento: " + index);
                index++;
            }

        } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
        catch   (IOException e)           { e.printStackTrace(); } 
    }

    private void separar_arquivos(String path) {
        Registro[] bloco = new Registro[100];;
        DataInputStream dis;
        FileInputStream arq;
        int x = 0;
        String numArqTemp[] = new String[2];
        
        numArqTemp[0] = "tmp1.txt";
        numArqTemp[1] = "tmp2.txt";
        
        try{
            arq = new FileInputStream(path);
            dis = new DataInputStream(arq);
            
            for(int time = 0; time < 8334; time++) {
                while (x < 100) {
                        arq = new FileInputStream(path);
                        dis = new DataInputStream(arq);
                        Player player = new Player();
                            
                        try{
                            bloco[x] = new Registro();
                                        
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
                                
                            bloco[x].setPlayer(player);
                            System.out.println(x + " gravar: " + bloco[x].getPlayer().toString());
                            x++;
                         
                            
                        } catch(IOException e) { e.printStackTrace(); }
                    }
                    if(time % 2 == 0) gravarNoArquivo(bloco, numArqTemp[0]);
                    else              gravarNoArquivo(bloco, numArqTemp[1]);
                }
                

                x=0;
            }catch(FileNotFoundException e) { e.printStackTrace(); } 
        catch(IOException e)           { System.out.println(e.getMessage()); }
    }    

    public void intercalacao_balanceada(String path) {
        separar_arquivos(path);
        
        

    }



}