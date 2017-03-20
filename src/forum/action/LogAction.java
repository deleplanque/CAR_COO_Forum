package forum.action;

import java.rmi.RemoteException;
import java.util.regex.Pattern;

import forum.bean.data.User;
import forum.services.ILogServices;
import forum.utils.MyFactory;

public class LogAction {
	

	public User currentUser;
	private String errorMessage = "";
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_PASS_REGEX = Pattern.compile("^[A-Z0-9]*$",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_NAME_REGEX = Pattern.compile("^[A-Z]*$",
			Pattern.CASE_INSENSITIVE);
	final ILogServices logServices = (ILogServices) MyFactory.getInstance(ILogServices.class);
	
	private static LogAction INSTANCE = null;

	private LogAction() {
	}

	public synchronized static LogAction getInstance() {
		if (INSTANCE == null)
			INSTANCE = new LogAction();
		return INSTANCE;
	}

	public String inscription(String userName, String mail, String pass, String repeatPass, String nom, String prenom) {

		if (userNameIsGood(userName) && mailIsGood(mail) && passIsGood(pass)
				&& repeatPassIsGood(repeatPass, pass) && nameIsGood(nom) && nameIsGood(prenom)){
			logServices.inscription(userName, mail, pass, nom, prenom);
			return "Sign in success";
		}
		else {
			return errorMessage;
		}
	}
	
	
	public boolean connexion(String userName, String pass){
		boolean connexion = false;
		connexion = logServices.connexion(userName, pass);
		if(connexion){
			try {
				currentUser = UserAction.getInstance().getUserByName(userName);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println("connexion reussie");
		}
		else System.out.println("connexion echoué");
		return connexion;
	}

	public boolean userNameIsGood(String field) {
		if (field.equals(null)) {
			errorMessage = "User name can't be null";
			return false;
		} else if (field.length() < 4) {
			errorMessage = "User name lenght to small";
			return false;
		}
		return true;
	}

	public boolean mailIsGood(String field){
		if(field.equals(null)){
			errorMessage = "mail can't be null";
			return false;			
		}
		else if(field.length()<6){
			errorMessage = "Mail lenght to small";
			return false;
		}
		else if(!VALID_EMAIL_ADDRESS_REGEX.matcher(field).matches()){
			errorMessage = "Mail incorect";
			return false;
		}
		return true;
	}
	
	public boolean passIsGood(String field) {
		if (field.equals(null)) {
			errorMessage = "Passwod can't be null";
			return false;
		} else if (field.length() < 6) {
			errorMessage = "Password lenght to small";
			return false;
		}
		else if(!VALID_PASS_REGEX.matcher(field).matches()){
			errorMessage = "Password incorect";
			return false;
		}
		return true;
	}
	
	public boolean repeatPassIsGood(String field, String repeatField) {
		if (!field.equals(repeatField)) {
			errorMessage = "Password and repeat password are not the same";
			return false;
		} else 
		return true;
	}
	
	public boolean nameIsGood(String field) {
		if (field.equals(null)) {
			errorMessage = "first name or last name can't be null";
			return false;
		} 
		else if(!VALID_NAME_REGEX.matcher(field).matches()){
			errorMessage = "Firts name or last name incorect";
			return false;
		}
		return true;
	}

}
