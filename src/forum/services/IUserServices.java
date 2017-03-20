package forum.services;

import java.rmi.Remote;
import java.util.ArrayList;

import forum.bean.data.Groupe;
import forum.bean.data.Hobby;
import forum.bean.data.MessageNotification;
import forum.bean.data.RequestNotification;
import forum.bean.data.User;

public interface IUserServices extends Remote {

	public void removeUser(User user)  ;

	public ArrayList<Groupe> getListGroupOfUser(User user)  ;

	public ArrayList<Hobby> getListHobbiesOfUser(User user)  ;

	public ArrayList<User> getFriendsListOfUser(String name)  ;

	public User getUserByName(String name)  ;

	public void sendFriendRequest(User destinateur, User destinataire)  ;

	public void acceptFriendRequest(User destinateur, User userCourant)  ;

	public void declineFriendRequest(User destinateur, User userCourant)  ;

	public ArrayList<User> getAllUser();

	public ArrayList<User> getOtherUser();

	public void addHobby(Hobby hobby)  ;

	public void removeHobby(Hobby hobby)  ;

	public ArrayList<RequestNotification> getRequestNotification();

	public ArrayList<MessageNotification> getMessageNotification();

}
