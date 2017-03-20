package forum.services;

import forum.persistance.dao.ILogDAO;
import forum.utils.MyFactory;

public class LogServices implements ILogServices {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4457464935783654159L;
	private static LogServices INSTANCE = null;

	private LogServices() {
	}

	public synchronized static LogServices getInstance() {
		if (INSTANCE == null)
			INSTANCE = new LogServices();
		return INSTANCE;
	}


	public boolean connexion(String userName, String pass) {

		boolean connexion = false;
		final ILogDAO logDAO = (ILogDAO) MyFactory.getInstance(ILogDAO.class);
		connexion = logDAO.connexion(userName, pass);
		return connexion;
	}


	public void inscription(String userName, String mail, String pass,
			String nom, String prenom) {
		final ILogDAO logDAO = (ILogDAO) MyFactory.getInstance(ILogDAO.class);
		logDAO.inscription(userName, mail, pass, nom, prenom);

	}

}
