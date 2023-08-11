package src.utils;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CsvManager extends FileManager {
    public CsvManager() {}
    public CsvManager(String path) {
        super(path);
    }
    public String readTuple(int target) {
        String value = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(this.getFile(), "r");
            raf.seek(target);
            value = raf.readLine();
            raf.close();
            return value;
        
        } catch (IOException e) { System.out.println(e.getMessage()); }
        return value;
    }
    public void readAtrPlayer() {
        try{
            FileInputStream in = new FileInputStream(this.getFile());
            DataInputStream out = new DataInputStream(in);
            System.out.println(out.readInt());
            

        }catch(IOException e) {
            System.out.println(e.getMessage());
        } 

    }
    public void writeByteFileCsv(){

    }
}
