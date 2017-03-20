package forum.services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import forum.bean.data.Groupe;
import forum.bean.data.MessageGroup;
import forum.bean.data.User;

public interface IGroupServices extends Remote{
	
	public void createGroupe(String nameOfGroup, User creator) throws RemoteException;

	public void addUserInGroup(User user, Groupe groupe)  ;

	public void addListUsersInGroup(ArrayList<User> listUsers, Groupe groupe)  ;

	public void deleteGroup(Groupe groupe)  ;

	public void removeUserOfGroup(User user, Groupe groupe)  ;

	public ArrayList<User> getListUserOfGroupById(int id)  ;

	public ArrayList<User> getListUserOfGroupByName(String name)  ;

	public ArrayList<Groupe> getListGroupe()  ;

	public ArrayList<MessageGroup> getMessageOfGroupe(int id)  ;

	public Groupe getGroupById(int id)  ;

	public void createGroupeWithUsers(String nameOfGroup, User creator, ArrayList<User> listUsers)  ;

	public void removeAllUsersOfGroup(Groupe groupe)  ;

	public void removeMessageOfGroup(int id)  ;

	public void removeAllMessageOfGroupe(Groupe groupe)  ;
	
	public void removeMessageUserOfGroup(User user, Groupe groupe)  ;

	public void sendMessageGroup(MessageGroup message)  ;

	public ArrayList<User> getFreeUserForGroup(Groupe groupe)  ;


}
