package forum.bean.data;

import java.util.ArrayList;
import java.util.List;

import forum.UnitOfWork.IDomainObject;
import forum.UnitOfWork.Observateur;
import forum.UnitOfWork.Visiteur;

public class Hobby implements IDomainObject{
	
	private int id;
	private String type;
	private String nom;
	List<Observateur> obs;

	
	public Hobby(String type, String nom, int id) {
		this.id= id;
		this.type = type;
		this.nom = nom;
		this.obs = new ArrayList<Observateur>();
	}
	
	public Hobby() {}

	public String toString(){
		return this.getNom();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		//notifier();
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
		//notifier();
	}

	public void accepter(Visiteur v) {
		v.visiter(this);
	}
	
	public void add(Observateur o) {
		obs.add(o);
	}

	public void notifier() {
		for (Observateur o : obs)
			o.action(this);
	}
}
