package forum.virtualProxy;

import java.util.ArrayList;
import java.util.List;

import forum.bean.data.User;
import forum.persistance.dao.GroupDAO;


public class ListeUserOfGroupProxy extends ArrayList<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4546494325091900762L;
	private List<User> inst = null;
	private int id;

	public ListeUserOfGroupProxy(int id) {
		this.id = id;
	}

	public void initialize()   {
		inst = GroupDAO.getInstance().getListUserOfGroupById(id);
	}

	void ensureIsInitialized() {
		if (inst == null) {
			this.initialize();
		}
	}
	
	public User get(int i) {
		ensureIsInitialized();
		return inst.get(i);
	}	
	
	public int size(){
		ensureIsInitialized();
		return inst.size();
	}
	
	
}
