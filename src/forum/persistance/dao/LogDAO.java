package forum.persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import forum.services.MaConnection;

public class LogDAO implements ILogDAO {

	private static LogDAO INSTANCE = null;

	private LogDAO() {
	}

	public synchronized static LogDAO getInstance() {
		if (INSTANCE == null)
			INSTANCE = new LogDAO();
		return INSTANCE;
	}


	public void inscription(String userName, String mail, String pass, String nom, String prenom)   {
		MaConnection connexion = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		final String requete = "INSERT INTO user VALUES (?, ?, ?, ?, ?, 'user')";
		try {
			connexion = MaConnection.getInstance();
			con = connexion.getConnection();
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, mail);
			preparedStatement.setString(3, pass);
			preparedStatement.setString(4, nom);
			preparedStatement.setString(5, prenom);

			preparedStatement.executeUpdate();
			con.commit();
		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				connexion.getConnection().rollback();
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
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean connexion(String userName, String pass)   {

		String password = null;
		MaConnection connexion = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		final String requete = "select password from user where nameAccount = ?";
		try {
			connexion = MaConnection.getInstance();
			con = connexion.getConnection();
			preparedStatement = con.prepareStatement(requete);

			preparedStatement.setString(1, userName);

			ResultSet resultat = preparedStatement.executeQuery();

			while (resultat.next()) {
				password = resultat.getString("password");
			}

		} catch (final SQLException e) {
			e.printStackTrace();
			try {
				connexion.getConnection().rollback();
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
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pass.equals(password)) {
			return true;
		} else
			return false;
	}
}
