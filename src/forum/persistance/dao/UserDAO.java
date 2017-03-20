package forum.persistance.dao;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import forum.action.LogAction;
import forum.bean.data.Groupe;
import forum.bean.data.Hobby;
import forum.bean.data.MessageGroup;
import forum.bean.data.MessageNotification;
import forum.bean.data.RequestNotification;
import forum.bean.data.User;
import forum.services.MaConnection;
import forum.virtualProxy.GroupeProxy;
import forum.virtualProxy.HobbyProxy;
import forum.virtualProxy.ListeFriendsOfUserProxy;
import forum.virtualProxy.ListeHobbiesOfUserProxy;
import forum.virtualProxy.UserProxy;

public class UserDAO implements IUserDAO {

	private static UserDAO INSTANCE = null;
	HashMap<String, WeakReference<User>> users;
	Connection con = null;

	private UserDAO() {
		users = new HashMap<String, WeakReference<User>>();
		con = MaConnection.getInstance().getConnection();
	}

	public synchronized static UserDAO getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UserDAO();
		return INSTANCE;
	}

	public void removeUser(User user)   {

		PreparedStatement preparedStatement = null;
		final String requete = "DELETE FROM `user` WHERE `nameAccount` = ?";
		try {
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, user.getNomCompte());

			preparedStatement.executeUpdate();

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

	public ArrayList<Groupe> getListGroupOfUser(User user)   {
		ArrayList<Groupe> listGroups = new ArrayList<Groupe>();

		PreparedStatement preparedStatement = null;
		final String requete = "select * from useringroup where nameAccount = ?";
		try {
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, user.getNomCompte());

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {

				GroupeProxy groupeProxy = new GroupeProxy(resultat.getInt(2));
				listGroups.add(groupeProxy);

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

		return listGroups;
	}

	public User findUserByNameAccount(String name)   {
		if (users.containsKey(name) && users.get(name).get() == null) {

			users.remove(name);
		}
		if (users.containsKey(name)) {

			return users.get(name).get();
		} else {

			User user = null;

			PreparedStatement preparedStatement = null;
			final String requete = "select * from user where nameAccount = ?";
			try {

				preparedStatement = con.prepareStatement(requete);

				preparedStatement.setString(1, name);

				ResultSet resultat = preparedStatement.executeQuery();

				while (resultat.next()) {
					ListeFriendsOfUserProxy listFriends = new ListeFriendsOfUserProxy(name);
					ListeHobbiesOfUserProxy listHobbies = new ListeHobbiesOfUserProxy(name);
					user = new User(resultat.getString(1), resultat.getString(2), resultat.getString(3),
							resultat.getString(4), resultat.getString(5), resultat.getString(6), listHobbies,
							listFriends);
					user.add(UnitOfWork.getInstance());
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

			users.put(name, new WeakReference<User>(user));

			return user;
		}

	}

	public ArrayList<User> getFriendsListOfUser(String name)   {
		ArrayList<User> listFriends = new ArrayList<User>();

		PreparedStatement preparedStatement = null;
		final String requete = "select user2 from friend where user1 = ?";
		try {
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, name);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {

				UserProxy userProxy = new UserProxy(resultat.getString(1));
				userProxy.add(UnitOfWork.getInstance());
				listFriends.add(userProxy);

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

		return listFriends;
	}

	public ArrayList<Hobby> getHobbiesListOfUser(String name)   {
		ArrayList<Hobby> listHobbies = new ArrayList<Hobby>();

		PreparedStatement preparedStatement = null;
		final String requete = "select * from hobbiesUser where nameAccount = ?";
		try {
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, name);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {

				HobbyProxy hobbyProxy = new HobbyProxy(resultat.getInt(2));
				listHobbies.add(hobbyProxy);

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

		return listHobbies;
	}

	public List<MessageGroup> getMessageOfGroupe(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateUser(User user)   {
		PreparedStatement preparedStatement = null;
		final String requete = "UPDATE `deleplanque`.`user` SET `mail` = ?, `lastname` = ?, `firstname` = ?, `role` = ?, `password` = ? WHERE `user`.`nameAccount` = ?";
		try {
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, user.getMail());
			preparedStatement.setString(2, user.getNom());
			preparedStatement.setString(3, user.getPrenom());
			preparedStatement.setString(4, user.getRole());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.setString(6, user.getNomCompte());

			preparedStatement.execute();

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

	public void update(User user)   {
		getInstance().updateUser(user);
		System.out.println("Données mise à jour !");
	}

	public void sendFriendRequest(User destinateur, User destinataire)   {
		PreparedStatement preparedStatement = null;
		final String requete = "INSERT INTO `deleplanque`.`friendsrequest` (`id`, `destinateur`, `destinataire`) VALUES (NULL, ?, ?)";
		try {
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, destinateur.getNomCompte());
			preparedStatement.setString(2, destinataire.getNomCompte());

			preparedStatement.executeUpdate();

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

	public void acceptFriendRequest(User destinateur, User userCourant)   {
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		final String requete = "INSERT INTO `deleplanque`.`friend` (`user1`, `user2`) VALUES (?, ?)";
		final String requete2 = "INSERT INTO `deleplanque`.`friend` (`user1`, `user2`) VALUES (?, ?)";
		final String requete3 = "DELETE FROM `friendsrequest` WHERE `destinateur` = ? and `destinataire` = ?";
		try {
			preparedStatement = con.prepareStatement(requete);
			preparedStatement2 = con.prepareStatement(requete2);
			preparedStatement3 = con.prepareStatement(requete3);

			preparedStatement.setString(1, destinateur.getNomCompte());
			preparedStatement.setString(2, userCourant.getNomCompte());

			preparedStatement2.setString(2, destinateur.getNomCompte());
			preparedStatement2.setString(1, userCourant.getNomCompte());

			preparedStatement3.setString(1, destinateur.getNomCompte());
			preparedStatement3.setString(2, userCourant.getNomCompte());

			preparedStatement.executeUpdate();
			preparedStatement2.executeUpdate();
			preparedStatement3.executeUpdate();

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
			try {
				preparedStatement2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				preparedStatement3.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void declineFriendRequest(User destinateur, User userCourant)   {
		PreparedStatement preparedStatement3 = null;
		final String requete3 = "DELETE FROM `friendsrequest` WHERE `destinateur` = ? and `destinataire` = ?";
		try {
			preparedStatement3 = con.prepareStatement(requete3);

			preparedStatement3.setString(1, destinateur.getNomCompte());
			preparedStatement3.setString(2, userCourant.getNomCompte());

			preparedStatement3.executeUpdate();

		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				preparedStatement3.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public ArrayList<User> getAllUser() {
		ArrayList<User> listUser = new ArrayList<User>();
		Statement stmt = null;
		String requete = "select * from user";
		try {

			stmt = con.createStatement();
			ResultSet resultat = stmt.executeQuery(requete);

			while (resultat.next()) {
				UserProxy userProxy = new UserProxy(resultat.getString(1));
				listUser.add(userProxy);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		}

		return listUser;
	}

	public ArrayList<User> getOtherUser() {
		ArrayList<User> listUser = new ArrayList<User>();
		Statement stmt = null;
		String requete = "select * from user where nameAccount not in (select nameAccount from user where nameAccount = '"
				+ LogAction.getInstance().currentUser.getNomCompte()
				+ "') and nameAccount not in (select user2 from friend where user1 = '"
				+ LogAction.getInstance().currentUser.getNomCompte() + "')";
		try {

			stmt = con.createStatement();
			ResultSet resultat = stmt.executeQuery(requete);

			while (resultat.next()) {
				UserProxy userProxy = new UserProxy(resultat.getString(1));
				listUser.add(userProxy);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		}

		return listUser;
	}

	public void addHobby(Hobby hobby)   {
		PreparedStatement preparedStatement = null;
		final String requete = "INSERT INTO `deleplanque`.`hobbiesUser` (`nameAccount`, `idHobby`) VALUES (?, ?);";
		try {
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, LogAction.getInstance().currentUser.getNomCompte());
			preparedStatement.setInt(2, hobby.getId());

			preparedStatement.executeUpdate();

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

	public void removeHobby(Hobby hobby)   {
		PreparedStatement preparedStatement = null;
		final String requete = "DELETE FROM `hobbiesUser` WHERE `idHobby` = ? and `nameAccount` = ?";
		try {
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setInt(1, hobby.getId());
			preparedStatement.setString(2, LogAction.getInstance().currentUser.getNomCompte());

			preparedStatement.executeUpdate();

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
	public ArrayList<RequestNotification> getRequestNotification() {

		ArrayList<RequestNotification> listRequestNotification = new ArrayList<RequestNotification>();
		Statement stmt = null;
		String requete = "select * from notification where nameReceveur = '"
				+ LogAction.getInstance().currentUser.getNomCompte() + "' and type = 'request'";
		try {

			stmt = con.createStatement();
			ResultSet resultat = stmt.executeQuery(requete);

			while (resultat.next()) {
				UserProxy envoyeur = new UserProxy(resultat.getString(2));
				UserProxy receveur = new UserProxy(resultat.getString(3));
				RequestNotification rn = new RequestNotification(resultat.getInt(1), envoyeur, receveur);
				listRequestNotification.add(rn);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		}

		return listRequestNotification;
	}

	@Override
	public ArrayList<MessageNotification> getMessageNotification() {

		ArrayList<MessageNotification> listMessageNotification = new ArrayList<MessageNotification>();
		Statement stmt = null;
		String requete = "select * from notification where nameReceveur = '"
				+ LogAction.getInstance().currentUser.getNomCompte() + "' and type = 'message'";
		try {

			stmt = con.createStatement();
			ResultSet resultat = stmt.executeQuery(requete);

			while (resultat.next()) {
				UserProxy envoyeur = new UserProxy(resultat.getString(2));
				UserProxy receveur = new UserProxy(resultat.getString(3));
				GroupeProxy groupe = new GroupeProxy(resultat.getInt(5));
				MessageNotification mn = new MessageNotification(resultat.getInt(1), envoyeur, receveur, groupe);
				listMessageNotification.add(mn);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		}

		return listMessageNotification;
	}
}
