package forum.services;

import java.io.Serializable;

public interface ILogServices extends Serializable{
	
	public void inscription(String userName, String mail, String pass, String nom, String prenom);
	public boolean connexion(String userName, String pass);

}
