package forum.bean.data;

public class Notification {
	
	private int id;
	private User envoyeur;
	private User receveur;
	
	public Notification(int id, User envoyeur, User receveur) {
		this.id = id;
		this.envoyeur= envoyeur;
		this.receveur=receveur;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getEnvoyeur() {
		return envoyeur;
	}

	public void setEnvoyeur(User envoyeur) {
		this.envoyeur = envoyeur;
	}

	public User getReceveur() {
		return receveur;
	}

	public void setReceveur(User receveur) {
		this.receveur = receveur;
	}
	
	

}
