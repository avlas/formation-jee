package fr.biblioteque.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="livre")
@NamedQuery(name="livre.findByCategorie", query="select l from Livre l where l.categorie=:categorie")
public class Livre {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="titre")
	private String titre;
	
	@Column(name="date_publication")
	private Date datePublication;
	
	@Column(name="description")
	private String description;
	
	@Column(name="categorie")
	private String categorie;
	
	@ManyToOne
//	@JoinColumn(name="id_auteur")
	private Auteur auteur;
	
	@Column(name="exemplaires")
	private int exemplaires;
	
	@Column(name="exemplairesDispo")
	private int exemplairesDispo;

	public Livre() {
	}

	
	/**
	 * @param titre
	 * @param datePublication
	 * @param description
	 * @param categorie
	 * @param auteur
	 * @param exemplaires
	 * @param exemplairesDispo
	 */
	public Livre(String titre, Date datePublication, String description, String categorie, Auteur auteur,
			int exemplaires, int exemplairesDispo) {
		this.titre = titre;
		this.datePublication = datePublication;
		this.description = description;
		this.categorie = categorie;
		this.auteur = auteur;
		this.exemplaires = exemplaires;
		this.exemplairesDispo = exemplairesDispo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Date getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public Auteur getAuteur() {
		return auteur;
	}

	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	public int getExemplaires() {
		return exemplaires;
	}

	public void setExemplaires(int exemplaires) {
		this.exemplaires = exemplaires;
	}

	public int getExemplairesDispo() {
		return exemplairesDispo;
	}

	public void setExemplairesDispo(int exemplairesDispo) {
		this.exemplairesDispo = exemplairesDispo;
	}

	@Override
	public String toString() {
		return "Livre [id=" + id + ", titre=" + titre + ", datePublication=" + datePublication + ", description="
				+ description + ", categorie=" + categorie + ", exemplaires=" + exemplaires + ", exemplairesDispo="
				+ exemplairesDispo + "]";
	}
}
