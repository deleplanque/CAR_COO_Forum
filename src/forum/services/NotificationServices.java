package forum.services;

import forum.bean.data.Groupe;
import forum.bean.data.MessageNotification;
import forum.bean.data.RequestNotification;
import forum.bean.data.User;
import forum.persistance.dao.INotificationDAO;
import forum.utils.MyFactory;

public class NotificationServices implements INotificationServices{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5057988187505968679L;
	private static NotificationServices INSTANCE = null;
	final INotificationDAO notificationDAO = (INotificationDAO) MyFactory.getInstance(INotificationDAO.class);
	
	private NotificationServices() {
	}

	public synchronized static NotificationServices getInstance() {
		if (INSTANCE == null)
			INSTANCE = new NotificationServices();
		return INSTANCE;
	}
	
	@Override
	public void sendMessageNotification(Groupe groupe)   {
		notificationDAO.sendMessageNotification(groupe);
		
	}

	@Override
	public void sendRequestionNotification(User destinateur, User userCourant)   {
		notificationDAO.sendRequestionNotification(destinateur,userCourant);
	}

	@Override
	public MessageNotification getMessageNotificationByid(int id)   {
		return notificationDAO.getMessageNotificationByid(id);
	}

	@Override
	public RequestNotification getRequestNotificationByid(int id)   {
		return notificationDAO.getRequestNotificationByid(id);
	}

}
