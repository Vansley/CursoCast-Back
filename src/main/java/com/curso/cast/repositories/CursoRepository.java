package com.curso.cast.repositories;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.curso.cast.entities.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
	@Query("SELECT c from Curso c  where c.dataAbertura <= :data_inicio AND c.dataFechamento >= :data_fim") 
	Optional<Curso> exists(@Param("data_inicio") LocalDate data_inicio, @Param("data_fim") LocalDate data_fim);
}