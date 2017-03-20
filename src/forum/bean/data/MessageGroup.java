package forum.bean.data;

import java.rmi.RemoteException;

public class MessageGroup extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9110081623381540065L;
	private Groupe groupe;
	
	public MessageGroup()throws RemoteException {}
	
	public MessageGroup(String contenue, Groupe groupe)throws RemoteException {
		this.groupe = groupe;
		setContenue(contenue);
		setCrypt(false);
		setDelais(0);
		setPrioritary(false);
		setWithAccuse(false);
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

}
