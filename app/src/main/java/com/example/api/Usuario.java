package com.example.api;

//CLASE USUARIO
public class Usuario {
    private Integer id;
    private String nombre;
    private String contra;
    private int num;
    private int ges;
    private int root;
    private String path;

    public Usuario() {
    }


    public Usuario(Integer id, String nombre, String contra, int num, int ges, int root) {
        this.id = id;
        this.nombre = nombre;
        this.contra = contra;
        this.num = num;
        this.ges = ges;
        this.root = root;

    }

    public Usuario(String nombre, String contra, int num, int ges, int root) {
        this.nombre = nombre;
        this.contra = contra;
        this.num = num;
        this.ges = ges;
        this.root = root;

    }

    public Usuario(String nombre, String contra, int num, int ges, int root, String path) {
        this.nombre = nombre;
        this.contra = contra;
        this.num = num;
        this.ges = ges;
        this.root = root;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int isGes() {
        return ges;
    }

    public void setGes(int ges) {
        this.ges = ges;
    }

    public int isRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", contra=" + contra + ", num=" + num + ", ges=" + ges + ", root=" + root + '}';
    }


}

