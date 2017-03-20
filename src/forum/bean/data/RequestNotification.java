package forum.bean.data;

public class RequestNotification extends Notification{
	
	private String type;

	public RequestNotification(int id, User envoyeur, User receveur) {
		super(id, envoyeur, receveur);
		this.type = "request";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
