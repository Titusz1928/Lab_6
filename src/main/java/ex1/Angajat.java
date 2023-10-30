package ex1;

import java.time.LocalDate;

public class Angajat implements Comparable<Angajat>{
    private String Nume,Post;
    private LocalDate Data_ang;
    private float Salariu;

    public Angajat() {}

    public Angajat(String nume, String post, LocalDate data_ang, float salariu) {
        Nume = nume;
        Post = post;
        Data_ang = data_ang;
        Salariu = salariu;
    }

    @Override
    public String toString() {
        return "Angajat = "+Nume+", "+Post+", "+Data_ang+", "+Salariu;
    }

    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public LocalDate getData_ang() {
        return Data_ang;
    }

    public void setData_ang(LocalDate data_ang) {
        Data_ang = data_ang;
    }

    public float getSalariu() {
        return Salariu;
    }

    public void setSalariu(float salariu) {
        Salariu = salariu;
    }


    @Override
    public int compareTo(Angajat o) {
        return Float.compare(this.getSalariu(), o.getSalariu());
    }

}
