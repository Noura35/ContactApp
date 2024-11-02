package model;


public class Contact {

    //attributs:
    private int id;
    private String nom;
    private String pseudo;
    private String tel;

    //Default Constructor:
    public Contact() {
    }

    //Constructor without id:
    public Contact( String nom , String pseudo  , String tel) {
        this.nom = nom;
        this.tel = tel;
        this.pseudo = pseudo;
    }

    //Constructor with all attr:
    public Contact(int id, String nom , String pseudo  , String tel) {
        this.id=id;
        this.nom = nom;
        this.tel = tel;
        this.pseudo = pseudo;
    }



    //setter & getter:
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }



}