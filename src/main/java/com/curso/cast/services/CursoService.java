package com.curso.cast.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.curso.cast.entities.Curso;
import com.curso.cast.repositories.CursoRepository;

@Service
public class CursoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CursoService.class);

	@PersistenceContext
	EntityManager em;

	@Autowired
	CursoRepository cursoRepository;
	@PersistenceContext
	EntityManager entity;

	@Transactional
	public void cadastrar(Curso curso) {
		LOGGER.info("A pagina de Cadastro foi inicializada com sucesso!");
		validaData(curso);
		validacao(curso);
		validacao1(curso);
		cursoRepository.save(curso);
	}

	@Transactional
	public void editar(Curso IdCurso) {
		LOGGER.info("A pagina de Editar Cursos foi inicializada com sucesso!");
		validaData(IdCurso);
		cursoRepository.save(IdCurso);
	}

	@Transactional
	public List<Curso> listar() {
		LOGGER.info("Lista de Cursos retornada com sucesso!");
		return cursoRepository.findAll();
	}

	public void validacao(Curso curso) {
		if (curso.getDataAbertura().isBefore(LocalDate.now())) {
			LOGGER.info("Data de Inicio não pode ser menor que a data atual!");
			throw new RuntimeException("Data de início não pode ser menor que a data atual!");

		}
	}

	public List<Curso> consultar(String descricao, LocalDate dataAbertura, LocalDate dataFechamento) {
		System.out.println(dataAbertura);
		CriteriaBuilder criteria = em.getCriteriaBuilder();
		CriteriaQuery<Curso> criteriaQuery = criteria.createQuery(Curso.class);

		Root<Curso> curso = criteriaQuery.from(Curso.class);
		List<Predicate> predList = new ArrayList<>();

		if (descricao != "") {
			Predicate descricaoPredicate = criteria.equal(curso.get("descricao"), descricao);
			predList.add(descricaoPredicate);
		}

		if (dataAbertura != null) {
			Predicate dataAberturaPredicate = criteria.greaterThanOrEqualTo(curso.get("dataAbertura"), dataAbertura);
			predList.add(dataAberturaPredicate);
		}

		if (dataFechamento != null) {
			Predicate dataFechamentoPredicate = criteria.lessThanOrEqualTo(curso.get("dataFechamento"), dataFechamento);
			predList.add(dataFechamentoPredicate);
		}

		Predicate[] predicateArray = new Predicate[predList.size()];

		predList.toArray(predicateArray);

		criteriaQuery.where(predicateArray);

		TypedQuery<Curso> query = em.createQuery(criteriaQuery);

		return query.getResultList();
	}

	private void validaData(Curso curso) {
		if (curso.getDataAbertura().isAfter(curso.getDataFechamento())) {
			LOGGER.info("A data de abertura é maior que a data de fechamento");
			throw new RuntimeException("A data de abertura é maior que a data de fechamento");
		}

	}
	private ResponseEntity<String> validacao1(Curso curso){
		Optional<Curso> cursos = cursoRepository.exists(curso.getDataAbertura(), curso.getDataFechamento());
		if (curso != null && !cursos.isEmpty()) {
			throw new RuntimeException("Existe(m) Curso(s) Planejados(s) Dentro do Período Informado");	
		}
		
		return null;
	}
	
	public Curso recuperarCurso(Integer idCurso) {
		return cursoRepository.findById(idCurso).get();
	}

	public void validaDelete(Integer idCurso) {
		Optional<Curso> curso = cursoRepository.findById(idCurso);
		Curso c = curso.get();
		LOGGER.info("Não é permitida a exclusão de cursos já realizados!");
		if (c.getDataAbertura().isBefore(LocalDate.now())) {
			throw new RuntimeException("Não é permitido a exclusão de cursos já realizados!");
		}
		cursoRepository.deleteById(idCurso);
	}

}