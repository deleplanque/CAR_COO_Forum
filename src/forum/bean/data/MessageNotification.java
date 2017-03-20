package forum.bean.data;

public class MessageNotification extends Notification{
	
	private String type;
	private Groupe groupe;
	
	public MessageNotification(int id, User envoyeur, User receveur, Groupe groupe) {
		super(id, envoyeur, receveur);
		this.type = "message";
		this.groupe = groupe;
	}


	public Groupe getGroupe() {
		return groupe;
	}

	public void setIdGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
