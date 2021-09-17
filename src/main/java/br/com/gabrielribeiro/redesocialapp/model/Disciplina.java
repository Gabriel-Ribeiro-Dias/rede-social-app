package br.com.gabrielribeiro.redesocialapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name="Disciplina")
@Table(name = "disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    @Column(
	    name = "id",
	    updatable = false)
    private Long id;

    
    
    @EqualsAndHashCode.Include
    @NonNull
    @Column(
	    name="nome",
	    nullable = false,
	    columnDefinition = "TEXT")
    public String nome;
    
    

    @Column(name= "likes")
    public int likes = 0;

    
    @Setter(value = AccessLevel.NONE)
    @Column(name = "notas")
    public double[] notas;
    
    

    @Setter(value = AccessLevel.NONE)
    @Column(name = "nota")
    public double nota;

    // construtor
    public Disciplina(String nome, int likes, double[] notas) {
	this.nome = nome;
	this.likes = likes;
	this.notas = notas;
	setNota(notas);
    };
    
    public Disciplina(){
    };
    
    public void setNotas(double[] notas) {
	this.notas = notas;
	setNota(notas);
    }

    public void setNota(double[] notas) {
	double mediaDasNotas = 0.0;
	double somaDasNotas = 0;
	int qntDeNotas = notas.length;
	for (double nota : notas) {
	    somaDasNotas = somaDasNotas + nota;
	}

	mediaDasNotas = somaDasNotas / qntDeNotas;

	this.nota = mediaDasNotas;
    }

}
