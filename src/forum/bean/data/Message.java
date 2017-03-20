package forum.bean.data;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

import forum.UnitOfWork.IDomainObject;
import forum.UnitOfWork.Observateur;
import forum.UnitOfWork.Visiteur;
import forum.bean.Interface.IMessage;

public abstract class Message extends UnicastRemoteObject implements IDomainObject, IMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1555851551269306868L;

	protected Message() throws RemoteException {
		super();
	}

	private int idMessage;
	private User destinataire;
	public User getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(User destinataire) {
		this.destinataire = destinataire;
	}

	private Date dateCreation;
	private String contenue;
	private int delais;
	private boolean isPrioritary;
	private boolean isCrypt;
	private boolean withAccuse;
	List<Observateur> obs;
	
	public String toString(){
		return "("+this.getDateCreation().toString()+")" + this.getDestinataire() + " : " + this.getContenue();
	}
	
	public int getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
		//notifier();
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
		//notifier();
	}

	public String getContenue() {
		return contenue;
	}

	public void setContenue(String contenue) {
		this.contenue = contenue;
		//notifier();
	}
	
	
	public int getDelais() {
		return delais;
	}

	public void setDelais(int delais) {
		this.delais = delais;
	}

	public boolean isPrioritary() {
		return isPrioritary;
	}

	public void setPrioritary(boolean isPrioritary) {
		this.isPrioritary = isPrioritary;
	}

	public boolean isCrypt() {
		return isCrypt;
	}

	public void setCrypt(boolean isCrypt) {
		this.isCrypt = isCrypt;
	}

	public boolean isWithAccuse() {
		return withAccuse;
	}

	public void setWithAccuse(boolean withAccuse) {
		this.withAccuse = withAccuse;
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
