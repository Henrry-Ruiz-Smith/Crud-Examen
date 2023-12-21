package org.cibertec.edu.pe.Controller;

import java.util.List;
import java.util.Optional;

import org.cibertec.edu.pe.Model.Categoria;
import org.cibertec.edu.pe.Model.Docente;
import org.cibertec.edu.pe.Repository.ICategoria;
import org.cibertec.edu.pe.Repository.IDocente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docente")
public class DocenteController {

    @Autowired
    private IDocente repo;

    @Autowired
    private ICategoria categoriaRepository;
    // Listar todos los docentes
    @GetMapping
    public List<Docente> listar() {
        List<Docente> docentes = repo.findAll();
        // Iterar sobre la lista y cargar la información de la categoría para cada
        // docente
        docentes.forEach(docente -> docente.getCategoria().getNombre());
        return docentes;
    }

    // Obtener un docente por ID
    @GetMapping("/{iddocente}")
    public Optional<Docente> obtenerPorId(@PathVariable int iddocente) {
        return repo.findById(iddocente);
    }

    // Agregar un docente
    @PostMapping("/agregarDocente")
    public ResponseEntity<?> agregarDocente(@RequestBody Docente docente) {
        if (docente.getCategoria() != null && docente.getCategoria().getIdcategoria() != 0) {
            Optional<Categoria> optionalCategoria = categoriaRepository
                    .findById(docente.getCategoria().getIdcategoria());

            if (optionalCategoria.isPresent()) {
                docente.setCategoria(optionalCategoria.get());
                Docente nuevoDocente = repo.save(docente);
                return new ResponseEntity<>(nuevoDocente, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("La categoría especificada no es válida", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Debe proporcionar el código de la categoría", HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar un docente
    @PutMapping("/actualizarDocente/{iddocente}")
    public ResponseEntity<Docente> actualizarDocente(@PathVariable int iddocente,
            @RequestBody Docente docenteActualizado) {
        Optional<Docente> optionalDocente = repo.findById(iddocente);

        if (optionalDocente.isPresent()) {
            Docente docenteExistente = optionalDocente.get();

            // Actualizar los campos necesarios
            docenteExistente.setNombre(docenteActualizado.getNombre());
            docenteExistente.setDni(docenteActualizado.getDni());
            docenteExistente.setFechaNacimiento(docenteActualizado.getFechaNacimiento());
            docenteExistente.setSueldo(docenteActualizado.getSueldo());
            docenteExistente.setEmail(docenteActualizado.getEmail());
            docenteExistente.setSexo(docenteActualizado.getSexo());
            docenteExistente.setCategoria(docenteActualizado.getCategoria());

            // Guardar el docente actualizado en la base de datos
            Docente docenteActualizadoGuardado = repo.save(docenteExistente);

            return new ResponseEntity<>(docenteActualizadoGuardado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un Docente
    @DeleteMapping("/eliminarDocente/{iddocente}")
    public ResponseEntity<String> eliminarDocente(@PathVariable int iddocente) {
        Optional<Docente> optionalDocente = repo.findById(iddocente);

        if (optionalDocente.isPresent()) {
            repo.deleteById(iddocente);
            return new ResponseEntity<>("Docente eliminado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Docente no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}
