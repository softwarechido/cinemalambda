package model;

import java.util.Calendar;
import java.util.Date;


public class Pelicula {
	
	private String id = null;
	private String titulo = "";
	private Date fechaPub = Calendar.getInstance().getTime();
	private String fechaPubAsText = DateHelper.dateToString(fechaPub);
	private String descripcion = ""; 
	private String director = ""; 
	private String escritor = ""; 
	private String ano = ""; 
	private String actores = ""; 
	private String clasificacion = ""; 
	private String funciones = "";
	
	public String toString(){
		
		return this.id+"|"+this.titulo+"|"+this.fechaPubAsText+"|"+this.descripcion+"|"+this.director+"|"+
			   this.escritor+"|"+this.ano+"|"+this.getActores()+"|"+this.getClasificacion()+"|"+
			   this.getFunciones()+ "\n";
	}
	
	public void asignar(Pelicula peliculaTO){
		
		setId(peliculaTO.getId());		
		setTitulo(peliculaTO.getTitulo());
		setFechaPub(peliculaTO.getFechaPub());
		setDescripcion(peliculaTO.getDescripcion());
		setDirector(peliculaTO.getDirector());
		setEscritor(peliculaTO.getEscritor());
		setAno(peliculaTO.getAno());
		setActores(peliculaTO.getActores());
		setClasificacion(peliculaTO.getClasificacion());
		setFunciones(peliculaTO.getFunciones());
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Date getFechaPub() {
		return fechaPub;
	}
	public void setFechaPub(Date fechaPub) {
		this.fechaPub = fechaPub;		
		this.fechaPubAsText = DateHelper.dateToString(fechaPub);
	}	
	
	public String getFechaPubAsText() {
		return fechaPubAsText;
	}

	public void setFechaPubAsText(String fechaPubAsText) {
		this.fechaPubAsText = fechaPubAsText;
		
		try {
			this.fechaPub = DateHelper.StringToDate(fechaPubAsText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getEscritor() {
		return escritor;
	}
	public void setEscritor(String escritor) {
		this.escritor = escritor;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getActores() {
		return actores;
	}
	public void setActores(String actores) {
		this.actores = actores;
	}
	public String getClasificacion() {
		return clasificacion;
	}
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	public String getFunciones() {
		return funciones;
	}
	public void setFunciones(String funciones) {
		this.funciones = funciones;
	}	
}
