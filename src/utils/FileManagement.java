package src.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class FileManagement
{
    private File file;
    private String[] content;
    private int length;

    public FileManagement() {
        this(null, null);
    }
    public FileManagement(File file, String[] content) {
        this.file = file;
        if(content != null) {
            this.content = content;
            this.length = content.length;
        } else {
            length = 0;
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
            this.content = new String[8436];
            while(raf.getFilePointer() < raf.length()) {
                this.content[i++] = raf.readLine();
            }
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
    /**
     * 
     * @return
     */

    public String readTuple(int target) {
        String value = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.seek(target);
            value = raf.readLine();
            raf.close();
            return value;
        
        } catch (IOException e) { System.out.println(e.getMessage()); }
        return value;
    }

    @Override
    public String toString() {
        return "FileManagement [content=" + Arrays.toString(content) + "]";
    }
    
}