package forum.persistance.dao;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import forum.action.LogAction;
import forum.bean.data.Groupe;
import forum.bean.data.MessageNotification;
import forum.bean.data.Notification;
import forum.bean.data.RequestNotification;
import forum.bean.data.User;
import forum.services.MaConnection;
import forum.virtualProxy.GroupeProxy;
import forum.virtualProxy.UserProxy;

public class NotificationDAO implements INotificationDAO{
	
	private static NotificationDAO INSTANCE = null;
	Connection con = null;
	HashMap<Integer, WeakReference<Notification>> listNotifications;
	
	private NotificationDAO() {
		con = MaConnection.getInstance().getConnection();
	}

	public synchronized static NotificationDAO getInstance() {
		if (INSTANCE == null)
			INSTANCE = new NotificationDAO();
		return INSTANCE;
	}

	@Override
	public void sendMessageNotification(Groupe groupe)   {
		PreparedStatement preparedStatement = null;
		final String requete = "select nameAccount from useringroup where idGroup=? and nameAccount not in (select nameAccount from user where nameAccount = ?)";
		try {
			preparedStatement = con.prepareStatement(requete);
			
			preparedStatement.setInt(1, groupe.getIdGroupe());
			preparedStatement.setString(2, LogAction.getInstance().currentUser.getNomCompte());

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				PreparedStatement preparedStatement2 = null;
				final String requete2 = "INSERT INTO `deleplanque`.`notification` (`id`, `nameEnvoyeur`, `nameReceveur`, `type`, `idGroupe`) VALUES (NULL, ?, ?, 'message', ?);";
				try {
					preparedStatement2 = con.prepareStatement(requete2);

					preparedStatement2.setString(1, LogAction.getInstance().currentUser.getNomCompte());
					preparedStatement2.setString(2, resultat.getString(1));
					preparedStatement2.setInt(3, groupe.getIdGroupe());

					preparedStatement2.executeUpdate();

					
				} catch (final SQLException e) {
					e.printStackTrace();
					try {
						con.rollback();
					} catch (final SQLException e1) {
						e1.printStackTrace();
					}
				} finally {
					preparedStatement2.close();
				}
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	@Override
	public void sendRequestionNotification(User destinateur, User userCourant)   {
		PreparedStatement preparedStatement2 = null;
		final String requete2 = "INSERT INTO `deleplanque`.`notification` (`id`, `nameEnvoyeur`, `nameReceveur`, `type`, `idGroupe`) VALUES (NULL, ?, ?, 'request', NULL);";
		try {
			preparedStatement2 = con.prepareStatement(requete2);

			preparedStatement2.setString(1, userCourant.getNomCompte());
			preparedStatement2.setString(2, destinateur.getNomCompte());

			preparedStatement2.executeUpdate();

			
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				preparedStatement2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public MessageNotification getMessageNotificationByid(int id)   {
		MessageNotification notification = null;

		PreparedStatement preparedStatement = null;
		final String requete = "select * from `notification` where id = ?";
		try {
			preparedStatement = con.prepareStatement(requete);
			
			preparedStatement.setInt(1, id);

			ResultSet resultat = preparedStatement.executeQuery();

			
			
			while (resultat.next()) {	
				UserProxy envoyeur = new UserProxy(resultat.getString(2));
				UserProxy receveur = new UserProxy(resultat.getString(3));
				GroupeProxy groupe = new GroupeProxy(resultat.getInt(5));
				notification = new MessageNotification(resultat.getInt(1), envoyeur, receveur, groupe);					
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return notification;
	}

	@Override
	public RequestNotification getRequestNotificationByid(int id)   {
		RequestNotification notification = null;

		PreparedStatement preparedStatement = null;
		final String requete = "select * from `notification` where id = ?";
		try {
			preparedStatement = con.prepareStatement(requete);
			
			preparedStatement.setInt(1, id);

			ResultSet resultat = preparedStatement.executeQuery();

			
			
			while (resultat.next()) {	
				UserProxy envoyeur = new UserProxy(resultat.getString(2));
				UserProxy receveur = new UserProxy(resultat.getString(3));
				notification = new RequestNotification(resultat.getInt(1), envoyeur, receveur);				
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return notification;
	}

	
}
