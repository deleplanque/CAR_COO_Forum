package forum.persistance.dao;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import forum.bean.data.MessageGroup;
import forum.services.MaConnection;
import forum.virtualProxy.GroupeProxy;
import forum.virtualProxy.UserProxy;

public class MessageGroupeDAO implements IMessageGroupDAO{
	
	HashMap<Integer, WeakReference<MessageGroup>> listMessageGroup;
	private static MessageGroupeDAO INSTANCE = null;
	Connection con = null;
	

	private MessageGroupeDAO() {
		listMessageGroup = new HashMap<Integer, WeakReference<MessageGroup>>();
		con = MaConnection.getInstance().getConnection();
	}

	public synchronized static MessageGroupeDAO getInstance() {
		if (INSTANCE == null)
			INSTANCE = new MessageGroupeDAO();
		return INSTANCE;
	}

	public MessageGroup getMessageById(int id)   {
		if (listMessageGroup.containsKey(id) && listMessageGroup.get(id).get() == null) {

			listMessageGroup.remove(id);
		}
		if (listMessageGroup.containsKey(id)) {

			return listMessageGroup.get(id).get();
		} else {

			MessageGroup messageGroupe = new MessageGroup();

			PreparedStatement preparedStatement = null;
			final String requete = "select * from message where id = ?";
			try {

				preparedStatement = con.prepareStatement(requete);

				preparedStatement.setInt(1, id);

				ResultSet resultat = preparedStatement.executeQuery();

				while (resultat.next()) {
					GroupeProxy groupeProxy = new GroupeProxy(resultat.getInt(2));
					UserProxy userProxy = new UserProxy(resultat.getString(3));
					messageGroupe.setIdMessage(id);
					messageGroupe.setGroupe(groupeProxy);
					messageGroupe.setDestinataire(userProxy);
					messageGroupe.setContenue(resultat.getString(4));
					messageGroupe.setDateCreation(resultat.getDate(5));
					messageGroupe.setDelais(resultat.getInt(6));
					messageGroupe.setWithAccuse(resultat.getBoolean(7));
					messageGroupe.setPrioritary(resultat.getBoolean(8));
					messageGroupe.setCrypt(resultat.getBoolean(9));
					
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

			listMessageGroup.put(id, new WeakReference<MessageGroup>(messageGroupe));

			return messageGroupe;
		}
	}


}
