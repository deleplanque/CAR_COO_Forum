package forum.virtualProxy;

import forum.bean.data.User;
import forum.persistance.dao.UserDAO;

public class UserProxy extends User {


	private User inst = null;
	private String name;

	public UserProxy(String name) {
		this.name = name;
	}

	public void initialize()   {
		inst = UserDAO.getInstance().findUserByNameAccount(name);
	}

	void ensureIsInitialized() {
		if (inst == null) {
			this.initialize();
		}
	}

	public String getPrenom() {
		ensureIsInitialized();
		return inst.getPrenom();
	}
	
	public String getNom(){
		ensureIsInitialized();
		return inst.getNom();
	}
	
	public String getNomCompte() {
		ensureIsInitialized();
		return inst.getNomCompte();
	}
	
	public String getMail() {
		ensureIsInitialized();
		return inst.getMail();
	}
	
	public String getRole() {
		ensureIsInitialized();
		return inst.getRole();
	}
}
