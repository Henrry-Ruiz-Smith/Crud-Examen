package org.cibertec.edu.pe.Repository;

import org.cibertec.edu.pe.Model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDocente extends JpaRepository<Docente, Integer> {

}
