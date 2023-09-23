package src.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import src.entities.Player;
import src.entities.Registro;

public class Converter {
    public static byte[] toByteArray(Player obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {

            ObjectOutputStream oos = new ObjectOutputStream(bos);
           
            oos.writeInt(obj.getId());
            oos.writeUTF(obj.getName());
            oos.writeInt(obj.getAge());
            for (String position : obj.getPositions()) oos.writeUTF(position);
            oos.writeUTF(obj.getCollegeUniv());
            oos.writeUTF(obj.getActTeam());
            oos.writeInt(obj.getPickDate());
            
            
            oos.close();
            bytes = bos.toByteArray();

            bos.close();
        } catch(IOException e) { e.printStackTrace(); }
        
        return bytes;
    }

    public static Registro toObject(byte[] bytes) {
        Registro obj = null;
        String pos[] = new String[2];
        try{
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = new Registro();

            obj.getPlayer().setId(ois.readInt());
            obj.getPlayer().setName(ois.readUTF());
            obj.getPlayer().setAge(ois.readInt());
            for (int i = 0; i < 2; i++) pos[i] = ois.readUTF();
            obj.getPlayer().setPositions(pos);
            obj.getPlayer().setCollegeUniv(ois.readUTF());
            obj.getPlayer().setActTeam(ois.readUTF());
            obj.getPlayer().setPickDate(ois.readInt());
        }
    
        catch (IOException e) { e.printStackTrace(); }
        
        return obj;
    }
}
