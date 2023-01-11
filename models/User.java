package models;

public class User {
	private int id;
	private String nom;
	private String prenom;
	private String password;	
	private String fonction;
	public User(int id, String nom, String prenom, String password ,String fonction) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.fonction = fonction;
	}
	public User(String nom, String prenom,String password ,  String fonction) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.fonction = fonction;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	
}
