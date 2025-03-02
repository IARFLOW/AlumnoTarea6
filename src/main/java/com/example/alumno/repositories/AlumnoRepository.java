package com.example.alumno.repositories;

import com.example.alumno.models.AlumnoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<AlumnoModel, Integer> {
    // Las operaciones básicas CRUD se heredan de JpaRepository
    // Podemos añadir consultas personalizadas si fueran necesarias
}