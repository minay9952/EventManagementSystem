package Models;

public class Person {

    private String name;
    private Gender gender;
    private int age;
    private String id;
    private static int idCount = 0;

    public Person(String name, Gender gender, int age){
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.id = "P" + idCount++;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getId() {
        return id;
    }
}
