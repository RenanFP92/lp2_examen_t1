package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Getter
@Setter
@Table(name = "tbl_especialidad")

public class Especialidad {
	
	@Id
	@Column(name = "id_especialidad")
	@EqualsAndHashCode.Include
	private int idEspecialidad;
	
	@Column(name = "titulo")
	private String titulo;
	
	@OneToMany(mappedBy = "especialidad")
	private List<Dentista> dentistas;
	
	@Override
	public String toString() {
		return titulo;
	}
}
