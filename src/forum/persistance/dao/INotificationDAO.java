package forum.persistance.dao;

import forum.bean.data.Groupe;
import forum.bean.data.MessageNotification;
import forum.bean.data.RequestNotification;
import forum.bean.data.User;

public interface INotificationDAO {

	public void sendMessageNotification(Groupe groupe)  ;

	public void sendRequestionNotification(User destinateur, User userCourant)  ;

	public MessageNotification getMessageNotificationByid(int id)  ;

	public RequestNotification getRequestNotificationByid(int id)  ;

}
