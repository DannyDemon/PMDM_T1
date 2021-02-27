package com.example.api;

//CLASE USUARIO
public class Manga {
    private Integer id;
    private String nombre;
    private String descripcion;
    private int path;

    public Manga() {
    }

    public Manga(String nombre, String descripcion, int path) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.path = path;
    }


    public Manga(Integer id, String nombre, String descripcion, int path) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Manga{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}

