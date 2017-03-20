package forum.virtualProxy;

import java.util.Date;

import forum.bean.data.Groupe;
import forum.bean.data.MessageGroup;
import forum.bean.data.User;
import forum.persistance.dao.MessageGroupeDAO;

public class MessageGroupeProxy extends MessageGroup{

	private int id;
	private MessageGroup inst = null;
	
	public MessageGroupeProxy(int id) {
		this.id = id;
	}
	
	public void initialize()   {
		inst = MessageGroupeDAO.getInstance().getMessageById(id);
	}

	void ensureIsInitialized() {
		if (inst == null) {
			this.initialize();
		}
	}
	
	
	public int getIdMessage() {
		ensureIsInitialized();
		return inst.getIdMessage();
	}
	
	public Groupe getGroupe() {
		ensureIsInitialized();
		return inst.getGroupe();
	}
	
	public String getContenue(){
		ensureIsInitialized();
		return inst.getContenue();
	}
	
	public Date getDateCreation(){
		ensureIsInitialized();
		return inst.getDateCreation();
	}
	
	public boolean isWithAccuse(){
		ensureIsInitialized();
		return inst.isWithAccuse();
	}
	
	public boolean isCrypt(){
		ensureIsInitialized();
		return inst.isCrypt();
	}
	
	public boolean isPrioritary(){
		ensureIsInitialized();
		return inst.isPrioritary();
	}
	
	public int getDelais(){
		ensureIsInitialized();
		return inst.getDelais();
	}
	
	public User getDestinataire(){
		ensureIsInitialized();
		return inst.getDestinataire();
	}
	
}
