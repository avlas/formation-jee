package fr.jee.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plat")
public class Plat {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private int tarif;
	
	
	/**
	 * 
	 */
	public Plat() {
	}

	/**
	 * @param name
	 * @param tarif
	 */
	public Plat(String name, int tarif) {
		this.name = name;
		this.tarif = tarif;
	}

	/**
	 * @param name
	 * @param tarif
	 */
	public Plat(int id, String name, int tarif) {
		this.id = id;
		this.name = name;
		this.tarif = tarif;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTarif() {
		return tarif;
	}
	public void setTarif(int tarif) {
		this.tarif = tarif;
	}

	@Override
	public String toString() {
		return "Plat [name=" + name + ", tarif=" + tarif + "]";
	}	
	
}
