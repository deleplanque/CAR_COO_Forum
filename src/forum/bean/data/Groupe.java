package forum.bean.data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import forum.UnitOfWork.IDomainObject;
import forum.UnitOfWork.Observateur;
import forum.UnitOfWork.Visiteur;
import forum.bean.Interface.IGroupe;

public class Groupe extends UnicastRemoteObject implements IDomainObject, IGroupe{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2766431234122484811L;
	private User moderator;
	private String nomGroupe;
	private List<User> users;
	private int idGroupe;
	private List<MessageGroup> listMessage;
	List<Observateur> obs;

	public Groupe(int idGroupe, String nomGroupe, User moderator, ArrayList<User> users, List<MessageGroup> listMessage) throws RemoteException{
		this.idGroupe = idGroupe;
		this.moderator = moderator;
		this.users = users;
		this.nomGroupe = nomGroupe;
		this.listMessage = listMessage;
		this.obs = new ArrayList<Observateur>();
	}
	
	public Groupe() throws RemoteException{
		this.obs = new ArrayList<Observateur>();
	}
	
	public String toString(){
		return this.getNomGroupe();
	}

	public List<MessageGroup> getListMessage() {
		return listMessage;
	}

	public void setListMessage(List<MessageGroup> listMessage) {
		this.listMessage = listMessage;
	}

	public String getNomGroupe() {
		return nomGroupe;
	}

	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
		notifier();
	}

	public void setModerator(User moderator) {
		this.moderator = moderator;
		notifier();
	}

	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}


	public User getModerator() {
		return moderator;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
		notifier();
	}


	public int getIdGroupe() {
		return idGroupe;
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
