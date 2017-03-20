package forum.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MaConnection {
	private static final Logger LOGGER = Logger.getLogger(MaConnection.class.getName());

	private static MaConnection INSTANCE = null;

	private MaConnection() {}

	public synchronized static MaConnection getInstance() {
		if (INSTANCE == null)
			INSTANCE = new MaConnection();
		return INSTANCE;
	}

	public Connection getConnection() {
		Connection connexion = null;
		try {
			//String url = "jdbc:mysql://localhost:8888/deleplanque";
			String url = "jdbc:mysql://webtp.fil.univ-lille1.fr/deleplanque";
			String username = "deleplanque";
			String mdp = "vermelles0312";
			connexion = DriverManager.getConnection(url, username, mdp);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Can't join the database.");
			System.exit(0);
		}
		
		return connexion;
	}
}