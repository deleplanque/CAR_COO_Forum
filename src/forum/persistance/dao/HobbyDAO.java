package forum.persistance.dao;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import forum.action.LogAction;
import forum.bean.data.Hobby;
import forum.services.MaConnection;
import forum.virtualProxy.HobbyProxy;

public class HobbyDAO implements IHobbyDAO {

	private static HobbyDAO INSTANCE = null;

	HashMap<Integer, WeakReference<Hobby>> hobbies;
	Connection con = null;

	private HobbyDAO() {
		hobbies = new HashMap<Integer, WeakReference<Hobby>>();
		con = MaConnection.getInstance().getConnection();
	}

	public synchronized static HobbyDAO getInstance() {
		if (INSTANCE == null)
			INSTANCE = new HobbyDAO();
		return INSTANCE;
	}

	public Hobby findHobbyByID(int id)   {
		if (hobbies.containsKey(id) && hobbies.get(id).get() == null) {

			hobbies.remove(id);
		}
		if (hobbies.containsKey(id)) {

			return hobbies.get(id).get();
		} else {

			Hobby hobby = null;

			PreparedStatement preparedStatement = null;
			final String requete = "select * from hobby where id = ?";
			try {

				preparedStatement = con.prepareStatement(requete);

				preparedStatement.setInt(1, id);

				ResultSet resultat = preparedStatement.executeQuery();

				while (resultat.next()) {
					hobby = new Hobby();

					hobby.setId(id);
					hobby.setType(resultat.getString(2));
					hobby.setNom(resultat.getString(3));
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

			hobbies.put(id, new WeakReference<Hobby>(hobby));

			return hobby;
		}
	}

	public void removeHobby(Hobby hobby)   {
		PreparedStatement preparedStatement = null;
		final String requete = "DELETE FROM `hobby` WHERE `id` = ?";
		try {
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setInt(1, hobby.getId());

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

	public void createHobby(String categorie, String nom)   {
		
		PreparedStatement preparedStatement = null;
		final String requete = "INSERT INTO `deleplanque`.`hobby` (`id`, `categorie`, `nom`) VALUES (NULL, ?, ?);";
		try {
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, categorie);
			preparedStatement.setString(2, nom);

			preparedStatement.executeUpdate();;

			
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

	public ArrayList<Hobby> getAllHobbies() {
		ArrayList<Hobby> listhobby = new ArrayList<Hobby>();
		Statement stmt = null; 
		String requete = "select * from hobby";
		try {

			stmt = con.createStatement();
			ResultSet resultat = stmt.executeQuery(requete);

			while (resultat.next()) {
				HobbyProxy hobbyProxy = new HobbyProxy(resultat.getInt(1));
				listhobby.add(hobbyProxy);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		}

		return listhobby;
	}

	public ArrayList<String> getAllCategorie()   {

		ArrayList<String> listCategorie = new ArrayList<String>();

		final String requete = "select distinct(categorie) from hobby";
		Statement stmt = null;
		try {

			stmt = con.createStatement();
			ResultSet resultat = stmt.executeQuery(requete);

			while (resultat.next()) {

				listCategorie.add(resultat.getString(1));

			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		}

		return listCategorie;
	}

	public ArrayList<String> getHobbyByCategorie(String categorie)   {
		ArrayList<String> listHobby = new ArrayList<String>();

		PreparedStatement preparedStatement = null;
		final String requete = "select nom from hobby where categorie = ?";
		try {

			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, categorie);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				listHobby.add(resultat.getString(1));
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

		return listHobby;
	}

	public Hobby getHobbyByName(String name)   {
		
		Hobby hobby = null;
		PreparedStatement preparedStatement = null;
		final String requete = "select * from hobby where nom = ?";
		try {

			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, name);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				 hobby = new HobbyProxy(resultat.getInt(1));
								
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

		return hobby;
	}

	public ArrayList<Hobby> getOtherHobbies() {
		ArrayList<Hobby> listhobby = new ArrayList<Hobby>();
		Statement stmt = null; 
		String requete = "select * from hobby where id not in (select idHobby from hobbiesUser where nameAccount ='"+LogAction.getInstance().currentUser.getNomCompte()+"')";
		try {

			stmt = con.createStatement();
			ResultSet resultat = stmt.executeQuery(requete);

			while (resultat.next()) {
				HobbyProxy hobbyProxy = new HobbyProxy(resultat.getInt(1));
				listhobby.add(hobbyProxy);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		}

		return listhobby;
	}

	public ArrayList<String> getOtherCategorie() {
		ArrayList<String> listCategorie = new ArrayList<String>();

		final String requete = "select distinct(categorie) from hobby where id not in (select idHobby from hobbiesUser where nameAccount ='"+LogAction.getInstance().currentUser.getNomCompte()+"')";
		Statement stmt = null;
		try {

			stmt = con.createStatement();
			ResultSet resultat = stmt.executeQuery(requete);

			while (resultat.next()) {

				listCategorie.add(resultat.getString(1));

			}
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (final SQLException e1) {
				e1.printStackTrace();
			}
		}

		return listCategorie;
	}

	public ArrayList<String> getOtherHobbyByCategorie(String categorie)   {
		ArrayList<String> listHobby = new ArrayList<String>();

		PreparedStatement preparedStatement = null;
		final String requete = "select nom from hobby where categorie = ? and id not in (select idHobby from hobbiesUser where nameAccount ='"+LogAction.getInstance().currentUser.getNomCompte()+"')";
		try {

			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, categorie);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				listHobby.add(resultat.getString(1));
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

		return listHobby;
	}

}
