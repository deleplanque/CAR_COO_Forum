package forum.action;

import java.rmi.RemoteException;
import java.util.ArrayList;

import forum.bean.data.Groupe;
import forum.bean.data.Hobby;
import forum.bean.data.MessageNotification;
import forum.bean.data.RequestNotification;
import forum.bean.data.User;
import forum.services.IUserServices;
import forum.utils.MyFactory;

public class UserAction {
	
	final IUserServices userServices = (IUserServices) MyFactory.getInstance(IUserServices.class);
	
	private static UserAction INSTANCE = null;

	private UserAction() {
	}

	public synchronized static UserAction getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UserAction();
		return INSTANCE;
	}
	
	public void removeUser(User user)  {
		if(user != null){
			userServices.removeUser(user);
		}
	}
	
	public ArrayList<Groupe> getListGroupOfUser(User user)  {
		ArrayList<Groupe> listGroupOfUser = new ArrayList<Groupe>();
		listGroupOfUser = userServices.getListGroupOfUser(user);
		return listGroupOfUser;
	}
	
	
	public ArrayList<Hobby> getListHobbiesOfUser(User user)  {
		ArrayList<Hobby> listHobbiesOfUser = new ArrayList<Hobby>();
		listHobbiesOfUser = userServices.getListHobbiesOfUser(user);
		return listHobbiesOfUser;
	}
	
	
	public ArrayList<User> getFriendsListOfUser(String name)  {
		ArrayList<User> friendsListOfUser = new ArrayList<User>();
		friendsListOfUser = userServices.getFriendsListOfUser(name);
		return friendsListOfUser;
	}
	
	public User getUserByName(String name) throws RemoteException  {
		User user = new User();
		user = userServices.getUserByName(name);
		return user;
	}
	
	public void sendFriendRequest(User destinataire)  {
		userServices.sendFriendRequest(destinataire,LogAction.getInstance().currentUser);
	}
	
	public void acceptFriendRequest(User destinateur)  {
		userServices.acceptFriendRequest(destinateur, LogAction.getInstance().currentUser);
	}
	
	public void declineFriendRequest(User destinateur)  {
		userServices.declineFriendRequest(destinateur,LogAction.getInstance().currentUser);
	}
	
	public ArrayList<User> getAllUser()  {
		ArrayList<User> users = new ArrayList<User>();
		users = userServices.getAllUser();
		return users;
	}
	
	public ArrayList<User> getOtherUser()  {
		ArrayList<User> users = new ArrayList<User>();
		users = userServices.getOtherUser();
		return users;
	}
	
	public void addHobby(Hobby hobby)  {
		userServices.addHobby(hobby);
	}
	
	public void removeHobby(Hobby hobby)  {
		userServices.removeHobby(hobby);
	}

	public ArrayList<RequestNotification> getRequestNotification() {
		ArrayList<RequestNotification> listRequestNotification = new ArrayList<RequestNotification>();
		listRequestNotification = userServices.getRequestNotification();
		return listRequestNotification;
	}
	
	public ArrayList<MessageNotification> getMessageNotification() {
		ArrayList<MessageNotification> listMessageNotification = new ArrayList<MessageNotification>();
		listMessageNotification = userServices.getMessageNotification();
		return listMessageNotification;
	}
}
