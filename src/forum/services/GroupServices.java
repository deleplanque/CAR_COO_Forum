package forum.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import forum.bean.data.Groupe;
import forum.bean.data.MessageGroup;
import forum.bean.data.User;
import forum.persistance.dao.IGroupDAO;
import forum.utils.MyFactory;

public class GroupServices extends UnicastRemoteObject implements IGroupServices{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6776684346412506860L;
	private static GroupServices INSTANCE = null;
	final IGroupDAO groupDAO = (IGroupDAO) MyFactory.getInstance(IGroupDAO.class);
	final INotificationServices notificationServices = (INotificationServices) MyFactory.getInstance(INotificationServices.class);
	
	private GroupServices() throws RemoteException{
	}

	public synchronized static GroupServices getInstance() throws RemoteException {
		if (INSTANCE == null)
			INSTANCE = new GroupServices();
		return INSTANCE;
	}
	
	 public static String encrypt(String password){
	        String crypte="";
	        for (int i=0; i<password.length();i++)  {
	            int c=password.charAt(i)^48;  
	            crypte=crypte+(char)c; 
	        }
	        return crypte;
	    }

	 public static String decrypt(String password){
	        String aCrypter="";
	        for (int i=0; i<password.length();i++)  {
	            int c=password.charAt(i)^48;  
	            aCrypter=aCrypter+(char)c; 
	        }
	        return aCrypter;
	    }


	public void createGroupe(String nameOfGroup, User creator){
		groupDAO.createGroup(nameOfGroup, creator);
		Groupe groupe = groupDAO.getGroupeByName(nameOfGroup);
		groupDAO.addUserInGroup(creator, groupe);
	}

	public void addUserInGroup(User user, Groupe groupe)   {
		groupDAO.addUserInGroup(user, groupe);
	}

	public void addListUsersInGroup(ArrayList<User> listUsers, Groupe groupe)   {
		groupDAO.addListUsersInGroup(listUsers, groupe);		
	}

	public void deleteGroup(Groupe groupe)   {
		groupDAO.deleteGroup(groupe);
	}

	public void removeUserOfGroup(User user, Groupe groupe)   {
		groupDAO.removeUserOfGroup(user, groupe);
	}

	public void removeMessageUserOfGroup(User user, Groupe groupe)   {
		groupDAO.removeMessageUserOfGroup(user,groupe);	
	}

	public ArrayList<User> getListUserOfGroupById(int id)   {
		ArrayList<User> listUsers = new ArrayList<User>();
		listUsers = groupDAO.getListUserOfGroupById(id);
		return listUsers;
	}

	public ArrayList<User> getListUserOfGroupByName(String name)   {
		ArrayList<User> listUsers = new ArrayList<User>();
		listUsers = groupDAO.getListUserOfGroupByName(name);
		return listUsers;
	}

	public ArrayList<Groupe> getListGroupe()   {
		ArrayList<Groupe> listeGroupe = new ArrayList<Groupe>();
		listeGroupe = groupDAO.getListGroupe();
		return listeGroupe;
	}

	public ArrayList<MessageGroup> getMessageOfGroupe(int id)   {
		ArrayList<MessageGroup> listMessage = new ArrayList<MessageGroup>();
		listMessage = groupDAO.getMessageOfGroupe(id);
		return listMessage;
	}

	public Groupe getGroupById(int id)   {
		Groupe groupe = new Groupe();
		groupe = groupDAO.getGroupeById(id);
		return groupe;
	}

	public void createGroupeWithUsers(String nameOfGroup, User creator, ArrayList<User> listUsers)   {
		groupDAO.createGroup(nameOfGroup, creator);
		Groupe groupe = groupDAO.getGroupeByName(nameOfGroup);
		addUserInGroup(creator, groupe);
		addListUsersInGroup(listUsers, groupe);
	}

	public void removeAllUsersOfGroup(Groupe groupe)   {
		groupDAO.removeAllUsersOfGroup(groupe);
	}

	public void removeMessageOfGroup(int id)   {
		groupDAO.removeMessageOfGroup(id);
	}

	public void removeAllMessageOfGroupe(Groupe groupe)   {
		groupDAO.removeAllMessageOfGroupe(groupe);
	}

	public void sendMessageGroup(MessageGroup message)   {
		if (message.isCrypt()){
			message.setContenue(encrypt(message.getContenue()));
			groupDAO.sendMessageGroup(message);
			notificationServices.sendMessageNotification(message.getGroupe());
		}
		else{
			groupDAO.sendMessageGroup(message);
			notificationServices.sendMessageNotification(message.getGroupe());
		}
	}

	public ArrayList<User> getFreeUserForGroup(Groupe groupe)   {
		ArrayList<User> listUsers = new ArrayList<User>();
		listUsers = groupDAO.getFreeUserForGroup(groupe);
		return listUsers;
	}



}
