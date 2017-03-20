package forum.virtualProxy;

import java.util.List;

import forum.bean.data.Groupe;
import forum.bean.data.User;
import forum.persistance.dao.GroupDAO;

public class GroupeProxy extends Groupe{

	private Groupe inst = null;
	private int id;

	public GroupeProxy(int id) {
		this.id = id;
	}

	public void initialize()   {
		inst = GroupDAO.getInstance().getGroupeById(id);
	}

	void ensureIsInitialized() {
		if (inst == null) {
			this.initialize();
		}
	}

	public User getModerator() {
		ensureIsInitialized();
		return inst.getModerator();
	}
	

	public int getIdGroupe() {
		ensureIsInitialized();
		return inst.getIdGroupe();
	}
	
	public List<User> getUsers() {
		ensureIsInitialized();
		return inst.getUsers();
	}
	
	public String getNomGroupe() {
		ensureIsInitialized();
		return inst.getNomGroupe();
	}
}
