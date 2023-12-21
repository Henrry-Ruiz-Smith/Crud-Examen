package org.cibertec.edu.pe.Repository;

import org.cibertec.edu.pe.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoria extends JpaRepository<Categoria, Integer> {

}
