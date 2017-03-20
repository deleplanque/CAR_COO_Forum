package forum.persistance.dao;


public interface ILogDAO {

	public void inscription(String userName, String mail, String pass, String nom, String prenom)  ;
	public boolean connexion(String userName, String pass)  ;
}
