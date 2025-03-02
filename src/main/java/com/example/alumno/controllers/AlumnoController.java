package com.example.alumno.controllers;

import com.example.alumno.models.AlumnoModel;
import com.example.alumno.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public AlumnoController(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;

        // Solo agregar datos iniciales si la tabla está vacía
        if (alumnoRepository.count() == 0) {
            // Ingeniería Informática
            alumnoRepository.save(new AlumnoModel(0, "Juan García", 19, "Ingeniería Informática", "España",
                    7.8, 2, "juan.garcia@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "María López", 20, "Ingeniería Informática", "España",
                    8.5, 3, "maria.lopez@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Emma Johnson", 22, "Ingeniería Informática", "Reino Unido",
                    7.9, 4, "emma.johnson@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Hiroshi Tanaka", 24, "Ingeniería Informática", "Japón",
                    9.1, 5, "hiroshi.tanaka@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Wei Chen", 23, "Ingeniería Informática", "China",
                    8.7, 5, "wei.chen@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Roberto Blanco", 32, "Ingeniería Informática", "España",
                    7.4, 1, "roberto.blanco@universidad.es"));

            // Medicina
            alumnoRepository.save(new AlumnoModel(0, "Carlos Sánchez", 23, "Medicina", "España",
                    8.9, 5, "carlos.sanchez@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Ana Rodríguez", 21, "Medicina", "España",
                    9.2, 4, "ana.rodriguez@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "William Smith", 23, "Medicina", "Estados Unidos",
                    8.2, 5, "william.smith@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Priya Patel", 20, "Medicina", "India",
                    9.5, 3, "priya.patel@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Teresa Navarro", 29, "Medicina", "España",
                    8.8, 2, "teresa.navarro@universidad.es"));

            // Derecho
            alumnoRepository.save(new AlumnoModel(0, "Pablo Martínez", 22, "Derecho", "España",
                    7.6, 4, "pablo.martinez@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Lucía Fernández", 20, "Derecho", "España",
                    8.4, 3, "lucia.fernandez@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Mohammed Al-Farsi", 22, "Derecho", "Emiratos Árabes",
                    7.8, 4, "mohammed.alfarsi@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Svetlana Ivanova", 23, "Derecho", "Rusia",
                    8.6, 5, "svetlana.ivanova@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Alberto Ruiz", 31, "Derecho", "España",
                    7.5, 1, "alberto.ruiz@universidad.es"));

            // Economía
            alumnoRepository.save(new AlumnoModel(0, "David González", 22, "Economía", "España",
                    8.1, 4, "david.gonzalez@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Elena Moreno", 19, "Economía", "España",
                    7.7, 2, "elena.moreno@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Olivia Brown", 21, "Economía", "Reino Unido",
                    8.3, 3, "olivia.brown@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "François Dubois", 25, "Economía", "Francia",
                    7.9, 6, "francois.dubois@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Beatriz Ortega", 28, "Economía", "España",
                    8.0, 1, "beatriz.ortega@universidad.es"));

            // Psicología
            alumnoRepository.save(new AlumnoModel(0, "Javier Jiménez", 24, "Psicología", "España",
                    8.5, 5, "javier.jimenez@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Carmen Pérez", 21, "Psicología", "España",
                    9.0, 4, "carmen.perez@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Noah Wilson", 20, "Psicología", "Estados Unidos",
                    8.2, 3, "noah.wilson@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Yuna Kim", 19, "Psicología", "Corea del Sur",
                    9.3, 2, "yuna.kim@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Jorge Molina", 30, "Psicología", "España",
                    7.6, 1, "jorge.molina@universidad.es"));

            // Arquitectura
            alumnoRepository.save(new AlumnoModel(0, "Alejandro Mendoza", 23, "Arquitectura", "México",
                    8.4, 5, "alejandro.mendoza@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Valentina Silva", 20, "Arquitectura", "Chile",
                    8.7, 3, "valentina.silva@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Klaus Müller", 26, "Arquitectura", "Alemania",
                    8.9, 7, "klaus.muller@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Marco Rossi", 27, "Arquitectura", "Italia",
                    8.3, 6, "marco.rossi@universidad.es"));

            // Biología
            alumnoRepository.save(new AlumnoModel(0, "Diego Ramírez", 21, "Biología", "Colombia",
                    7.9, 4, "diego.ramirez@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Sofía Castro", 19, "Biología", "Argentina",
                    8.6, 2, "sofia.castro@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Fatima Hassan", 21, "Biología", "Egipto",
                    9.1, 4, "fatima.hassan@universidad.es"));
            alumnoRepository.save(new AlumnoModel(0, "Gabriela Costa", 24, "Biología", "Brasil",
                    8.5, 5, "gabriela.costa@universidad.es"));

            // Ingeniería Civil
            alumnoRepository.save(new AlumnoModel(0, "Mateo Vargas", 22, "Ingeniería Civil", "Perú",
                    7.7, 4, "mateo.vargas@universidad.es"));

            System.out.println("Base de datos inicializada con 35 alumnos con información completa");
        }
    }

    // GET - Obtener todos los alumnos
    @GetMapping
    public List<AlumnoModel> getAlumnos() {
        return alumnoRepository.findAll();
    }

    // GET - Obtener un alumno por ID
    @GetMapping("/{id}")
    public ResponseEntity<AlumnoModel> getAlumno(@PathVariable int id) {
        Optional<AlumnoModel> alumno = alumnoRepository.findById(id);
        return alumno.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST - Crear un nuevo alumno
    @PostMapping
    public ResponseEntity<AlumnoModel> addAlumno(@RequestBody AlumnoModel alumno) {
        // Asegurar que estamos creando un nuevo alumno
        alumno.setId(0);
        AlumnoModel savedAlumno = alumnoRepository.save(alumno);
        return new ResponseEntity<>(savedAlumno, HttpStatus.CREATED);
    }

    // PUT - Actualizar un alumno existente
    @PutMapping("/{id}")
    public ResponseEntity<AlumnoModel> updateAlumno(@PathVariable int id, @RequestBody AlumnoModel alumno) {
        if (!alumnoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        alumno.setId(id);
        AlumnoModel updatedAlumno = alumnoRepository.save(alumno);
        return ResponseEntity.ok(updatedAlumno);
    }

    // DELETE - Eliminar un alumno
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlumno(@PathVariable int id) {
        if (!alumnoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        alumnoRepository.deleteById(id);
        return ResponseEntity.ok("Alumno eliminado con éxito");
    }
}
