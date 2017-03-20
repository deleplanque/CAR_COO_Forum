package forum.services;

import java.io.Serializable;

import forum.bean.data.Groupe;
import forum.bean.data.MessageNotification;
import forum.bean.data.RequestNotification;
import forum.bean.data.User;

public interface INotificationServices extends Serializable{

	void sendMessageNotification(Groupe groupe)  ;

	void sendRequestionNotification(User destinateur, User userCourant)  ;

	public MessageNotification getMessageNotificationByid(int id)  ;

	public RequestNotification getRequestNotificationByid(int id)  ;

}
