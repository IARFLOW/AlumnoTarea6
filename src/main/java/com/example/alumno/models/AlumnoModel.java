package com.example.alumno.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "alumnos")
public class AlumnoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private int edad;
    
    @Column(name = "carrera")
    private String carrera;
    
    @Column(name = "pais_origen")
    private String paisOrigen;
    
    @Column(name = "promedio")
    private Double promedio;
    
    @Column(name = "semestre")
    private Integer semestre;
    
    @Column(name = "email")
    private String email;

    // Constructor por defecto requerido por JPA
    public AlumnoModel() {
    }

    // Constructor básico (compatibilidad con versión anterior)
    public AlumnoModel(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }
    
    // Constructor completo
    public AlumnoModel(int id, String nombre, int edad, String carrera, String paisOrigen, 
                      Double promedio, Integer semestre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.carrera = carrera;
        this.paisOrigen = paisOrigen;
        this.promedio = promedio;
        this.semestre = semestre;
        this.email = email;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    
    public String getPaisOrigen() { return paisOrigen; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }
    
    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
    
    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    @Override
    public String toString() {
        return "Alumno [id=" + id + ", nombre=" + nombre + ", edad=" + edad + 
               ", carrera=" + carrera + ", país=" + paisOrigen + "]";
    }
}