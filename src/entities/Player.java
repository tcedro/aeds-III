package src.entities;

public class Player {
    private String name;
    private int id;
    private int age;
    private String[] positions;
    private String collegeUniv;
    private String actTeam;
    
    public Player(){} 
    public Player(String name, int id, int age, String[] positions, String collegeUniv, String actTeam) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.positions = positions;
        this.collegeUniv = collegeUniv;
        this.actTeam = actTeam;
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
    
}