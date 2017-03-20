package forum.action;

import java.rmi.RemoteException;
import java.util.ArrayList;

import forum.bean.data.Groupe;
import forum.bean.data.MessageGroup;
import forum.bean.data.User;
import forum.services.IGroupServices;
import forum.utils.MyFactory;

public class GroupAction {

	final IGroupServices groupServices = (IGroupServices) MyFactory.getInstance(IGroupServices.class);
	
	private static GroupAction INSTANCE = null;

	private GroupAction() {
	}

	public synchronized static GroupAction getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GroupAction();
		return INSTANCE;
	}

	public ArrayList<Groupe> getListGroupe()  {
		ArrayList<Groupe> listeGroupe = new ArrayList<Groupe>();
		listeGroupe = groupServices.getListGroupe();
		return listeGroupe;
	}
	
	public Groupe getGroupeById(int id) throws RemoteException  {
		Groupe groupe =new Groupe();
		groupe = groupServices.getGroupById(id);
		return groupe;
	}
	
	
	public void createGroupe(String nameOfGroup, User creator)   {
		if (nameOfGroupIsGood(nameOfGroup) && creator != null) {
			try {
				groupServices.createGroupe(nameOfGroup, creator);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createGroupeWithUsers(String nameOfGroup, User creator, ArrayList<User> listUsers)   {
		if (nameOfGroupIsGood(nameOfGroup) && creator != null) {
			groupServices.createGroupeWithUsers(nameOfGroup, creator, listUsers);
		}
	}
	

	public void addUserInGroup(User user, Groupe groupe)   {
		if (user != null) {
			groupServices.addUserInGroup(user, groupe);
		}
	}

	public void addListUsersInGroup(ArrayList<User> listUsers, Groupe groupe)   {
		if (listUsers != null && listUsers.size() > 0) {
			groupServices.addListUsersInGroup(listUsers, groupe);
		}
	}

	public void removeUserOfGroup(User user, Groupe groupe)   {
		if (user != null) {
			groupServices.removeUserOfGroup(user, groupe);
		}
	}
	
	public void removeAllUsersOfGroup(Groupe groupe)  {
		if(groupe!=null){
			groupServices.removeAllUsersOfGroup(groupe);
		}
	}

	public void removeMessageOfGroup(int id)  {
		if(id!=0){
			groupServices.removeMessageOfGroup(id);
		}
	}
	
	public void removeAllMessageOfGroupe(Groupe groupe)  {
		if(groupe != null){			
			groupServices.removeAllMessageOfGroupe(groupe);
		}
	}
	
	public void deleteGroup(Groupe groupe)   {
		if (groupe != null) {
			groupServices.deleteGroup(groupe);
		}
	}

	public ArrayList<User> getListUserOfGroupById(int id)   {
		ArrayList<User> listUsers = new ArrayList<User>();
		listUsers = groupServices.getListUserOfGroupById(id);
		return listUsers;
	}
	
	public ArrayList<User> getListUserOfGroupByName(String name)   {
		ArrayList<User> listUsers = new ArrayList<User>();
		listUsers = groupServices.getListUserOfGroupByName(name);
		return listUsers;
	}
	
	public ArrayList<MessageGroup> getMessageOfGroupe(int id)  {
		ArrayList<MessageGroup> listMessage = new ArrayList<MessageGroup>();
		listMessage = groupServices.getMessageOfGroupe(id);
		return listMessage;
	}
	
	public void sendMessageGroup(MessageGroup message)  {
		groupServices.sendMessageGroup(message);
	}
	
	
	public ArrayList<User> getFreeUserForGroup(Groupe groupe)  {
		ArrayList<User> listUsers = new ArrayList<User>();
		listUsers = groupServices.getFreeUserForGroup(groupe);
		return listUsers;
	}

	public boolean nameOfGroupIsGood(String name) {

		if (name == null) {
			return false;
		}
		return true;
	}

}
