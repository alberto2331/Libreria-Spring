package com.semillero.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;



@Entity
@Table(name="libro")
@SQLDelete(sql = "UPDATE libro SET deleted=true WHERE isbn = ?")
@Where(clause="deleted=false")
public class Libro {

	@Id	
	private Long isbn;
	private String titulo;
	private Integer anio;
	private Integer ejemplares;
	private Integer prestados;

	@ManyToOne
	@JoinColumn
	private Autor autor;
	
	@ManyToOne
	@JoinColumn
	private Editorial editorial;
	
	@OneToOne
    private Portada portada;
	
	@Column(name ="deleted")
	private boolean deleted = Boolean.FALSE;

	//Constructor vacio
	public Libro() {	
	}

	//Constructor completo
	public Libro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer prestados, Autor autor,
			Editorial editorial, Portada portada) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.anio = anio;
		this.ejemplares = ejemplares;
		this.prestados = prestados;
		this.autor = autor;
		this.editorial = editorial;
		this.portada = portada;
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(Integer ejemplares) {
		this.ejemplares = ejemplares;
	}

	public Integer getPrestados() {
		return prestados;
	}

	public void setPrestados(Integer prestados) {
		this.prestados = prestados;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}

	public Portada getPortada() {
		return portada;
	}

	public void setPortada(Portada portada) {
		this.portada = portada;
	}
	
}
