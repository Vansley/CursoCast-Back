package com.curso.cast.controller;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.curso.cast.entities.Curso;
import com.curso.cast.services.CursoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CursoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CursoController.class);

	@Autowired
	private CursoService service;

	// Método de cadastrar
	@ApiOperation("Serviço para cadastrar curso")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cadastrar(@RequestBody Curso curso) {

		try {
			LOGGER.info("Curso Cadastrado!");

			service.cadastrar(curso);
			return ResponseEntity.status(HttpStatus.CREATED).body("Curso cadastrado com sucesso");
		} catch (Exception e) {
			LOGGER.error("Curso não cadastrado!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
		}
	}

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
	// Método de Buscar cursos por ID
	@ApiOperation("Serviço para buscar cursos por Id")
	@GetMapping(value = "/{IdCurso}")
	public ResponseEntity<Curso> getCurso(@PathVariable("IdCurso") Integer IdCurso) {
		try {
			LOGGER.info("Cursos buscados com sucesso!");
			return ResponseEntity.ok(service.recuperarCurso(IdCurso));

		} catch (Exception e) {
			LOGGER.error("Cursos não buscados.");
			return null;
		}
	}

	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
	// Método de editar todos os cursos
	@ApiOperation("Serviço para atualizar cursos")
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editar(@RequestBody Curso curso) {
		try {
			LOGGER.info("Curso atualizado com sucesso!");
			service.editar(curso);
			return ResponseEntity.status(HttpStatus.OK).body("Curso editado com sucesso!");
		} catch (Exception e) {
			LOGGER.info("Curso não atualizado.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
	// Método de deletar cursos por Id
	@ApiOperation("Serviço para deletar cursos por Id")
	@DeleteMapping(value = "/{IdCurso}")
	public ResponseEntity<String> deleteById(@PathVariable("IdCurso") Integer idCurso) {
		try {
			LOGGER.info("Curso deletado com sucesso!");
			service.validaDelete(idCurso);
			return ResponseEntity.status(HttpStatus.OK).body("Curso deletado com sucesso");

		} catch (Exception e) {
			LOGGER.error("Curso não deletado.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
	// Método de Filtrar por descricao e data
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Curso>> listarTudo(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAbertura,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFechamento) {
		List<Curso> curso = service.consultar(descricao, dataAbertura, dataFechamento);
		LOGGER.error("Método de filtrar por data e/ou descrição feito com sucesso!");
		return ResponseEntity.ok().body(curso);
	}

}