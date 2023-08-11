package src.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class FileManager
{
    private File file;
    private String[] content;
    private int length;

    public FileManager() {
        this(null, null);
    }
    public FileManager(String file) {
        this(file, null);
    }
    public FileManager(String file, String[] content) {
        this.file = new File(file);
        if(content != null) {
            this.content = content;
            this.length = content.length;
        } else {
            length = 0;
            this.content = new String[8436];
        } 
    }
    public File getFile() {
        return file;
    }
    public void setFile(String path) {
        this.file = new File(path);
    }
    public void setContent() {
        int i = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            while(raf.getFilePointer() < raf.length()) {
                this.content[i++] = raf.readLine();
            }
            raf.close();
            length = i;

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public String[] getContent() {
        return content;
    }
    public void setContent(String[] content) {
        this.content = content;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    @Override
    public String toString() {
        return "FileManagement [content=" + Arrays.toString(content) + "]";
    }
    
}