package com.semillero.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Prestamo {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid2")
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date devolucion;
	
	private Double multa;
	
	@OneToOne
	private Libro libro;
	
	@ManyToOne
	@JoinColumn
	private Cliente cliente;
	
	public Prestamo() {
    }

    public Prestamo(Libro libro, Date fecha, Date devolucion, Cliente cliente, Double multa) {        
        this.libro = libro;
        this.fecha = fecha;
        this.devolucion = devolucion;
        this.cliente = cliente;
        this.multa= multa;
    }
	
	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getDevolucion() {
		return devolucion;
	}
	public void setDevolucion(Date devolucion) {
		this.devolucion = devolucion;
	}
	public Double getMulta() {
		return multa;
	}
	public void setMulta(Double multa) {
		this.multa = multa;
	}	
}
