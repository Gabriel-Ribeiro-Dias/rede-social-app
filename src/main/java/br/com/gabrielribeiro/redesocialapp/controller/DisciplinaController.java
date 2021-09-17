package br.com.gabrielribeiro.redesocialapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrielribeiro.redesocialapp.model.Disciplina;
import br.com.gabrielribeiro.redesocialapp.repository.DisciplinaRepository;

@RestController
@RequestMapping("/v1/api/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    List<Disciplina> disciplinas = new ArrayList<Disciplina>();

    @GetMapping("/")
   public ResponseEntity<?> listarTodasDisciplinasSalvas() {
	return new ResponseEntity<>(this.disciplinaRepository
							.findAll(),
							HttpStatus.OK);
    };

    @GetMapping("/{id}")
    public ResponseEntity<?> procurarDisciplinaPeloId(
	    @PathVariable(value = "id", required = true) Long id) {
	if(!this.disciplinaRepository.existsById(id)) {
	    return new ResponseEntity<>(
			"Não foi encontrada uma disciplina com esse id", HttpStatus.OK);
	}
	return new ResponseEntity<>(this.disciplinaRepository
					.findById(id),
					HttpStatus.OK);
    };

    @PostMapping("/")
    public ResponseEntity<?> adicionarDisciplina(
	    @RequestBody Disciplina newDisciplina) {

	List<Disciplina> disciplinasSalvas = this.disciplinaRepository.findAll();
	
//	if(newDisciplina.getNome()) {
//	    return new ResponseEntity<>(
//			"Já existe uma disciplina com esse nome!",
//			HttpStatus.BAD_GATEWAY);
//	}

	for (Disciplina disciplina : disciplinasSalvas) {
	    if (disciplina.equals(newDisciplina)) {
		return new ResponseEntity<>(
			"Já existe uma disciplina com esse nome!",
			HttpStatus.CONFLICT);
	    }
	}

	return new ResponseEntity<>(this.disciplinaRepository.save(
		new Disciplina(
		newDisciplina.getNome(),
		newDisciplina.getLikes(),
		newDisciplina.getNotas())),
		HttpStatus.CREATED);
		
    };

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarDisciplina(
	    @PathVariable(
	    value = "id", 
	    required = true) Long id) {
	
	if(!this.disciplinaRepository.existsById(id)) {
	    return new ResponseEntity<>(
			"Não foi encontrada uma disciplina com esse id", HttpStatus.OK);
	}
	
	ResponseEntity<?> disciplina = new ResponseEntity<>(
					this.disciplinaRepository
					.findById(id),
					HttpStatus.OK);
	this.disciplinaRepository.deleteById(id);
	return disciplina;
    };
    
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarNotaDaDisciplina(
	    @PathVariable(value = "id", required = true) Long id,
	    @RequestBody Disciplina notaDisciplina) {
	
	if(!this.disciplinaRepository.existsById(id)) {
	    return new ResponseEntity<>(
			"Não foi encontrada uma disciplina com esse id", HttpStatus.OK);
	}
	this.disciplinaRepository.findById(id).get()
				.setNotas(notaDisciplina.getNotas());
	
	
	return new ResponseEntity<>(
		this.disciplinaRepository
		.findById(id), HttpStatus.OK);
    }


}
