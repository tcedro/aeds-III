package src.entities;

import java.util.Arrays;

public class Player {
    private String name;
    private int id;
    private int age;
    private String[] positions;
    private String collegeUniv;
    private String actTeam;
    private String pickDate;
    
    public Player(){} 
    public Player(String name, int id, int age, String[] positions, String collegeUniv, String actTeam, String pickDate) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.positions = positions;
        this.collegeUniv = collegeUniv;
        this.actTeam = actTeam;
        this.pickDate = pickDate;
    }
    public String getPickDate() {
        return pickDate;
    }
    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String[] getPositions() {
        return positions;
    }
    public void setPositions(String[] positions) {
        this.positions = positions;
    }
    public String getCollegeUniv() {
        return collegeUniv;
    }
    public void setCollegeUniv(String collegeUniv) {
        this.collegeUniv = collegeUniv;
    }
    public String getActTeam() {
        return actTeam;
    }
    public void setActTeam(String actTeam) {
        this.actTeam = actTeam;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (id != other.id)
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Player [name=" + name + ", id=" + id + ", age=" + age + ", positions=" + Arrays.toString(positions)
                + ", collegeUniv=" + collegeUniv + ", actTeam=" + actTeam + ", pickDate=" + pickDate + "]";
    }

    

    
    
}