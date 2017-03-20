
import java.rmi.RemoteException;
import java.util.ArrayList;

import forum.action.GroupAction;
import forum.action.HobbyAction;
import forum.action.UserAction;
import forum.bean.data.Groupe;
import forum.bean.data.Hobby;
import forum.bean.data.Message;
import forum.bean.data.MessageAccuse;
import forum.bean.data.MessageDelai;
import forum.bean.data.MessageGroup;
import forum.bean.data.User;
import forum.presentation.ConnectionFrame;

public class Main {


	public static void listGroup(){
		ArrayList<Groupe> listGroupe = null;
		listGroupe = GroupAction.getInstance().getListGroupe();
		for (int i=0; i< listGroupe.size(); i++){
			System.out.println(listGroupe.get(i).getNomGroupe());
			System.out.println("Moderateur: " + listGroupe.get(i).getModerator().getNom()+ " "+ listGroupe.get(i).getModerator().getPrenom());
			for (int j=0; j<listGroupe.get(i).getUsers().size(); j++){
				System.out.println(listGroupe.get(i).getUsers().get(j).getNom()+ " " + listGroupe.get(i).getUsers().get(j).getPrenom()); 
			}
		}
	}
	
	public static void listMessage(){
		ArrayList<MessageGroup> listMessage = new ArrayList<MessageGroup>();
		listMessage = GroupAction.getInstance().getMessageOfGroupe(2);
		
		for(int i=0; i<listMessage.size(); i++){
			System.out.println(listMessage.get(i).getContenue());
			
		}
	}
	
	public static void listUser() throws RemoteException{
		ArrayList<User> listUser = new ArrayList<User>();
		listUser.add(UserAction.getInstance().getUserByName("Elisyo"));
		
		GroupAction.getInstance().createGroupeWithUsers("withUser", UserAction.getInstance().getUserByName("dylan"), listUser);
		System.out.println("creation reussie");
	}
	
	public static void typeOfMessage() throws RemoteException{
		ArrayList<Groupe> listGroupe = null;
		listGroupe = GroupAction.getInstance().getListGroupe();
		Message m = new MessageGroup("Je suis un message dans un groupe", listGroupe.get(1));
		m = new MessageAccuse(m);
	//	m = new MessagePrioritaire(m);
	//	m = new MessageChiffre(m);
		m = new MessageDelai(m, 10);
		
		
		System.out.println(m.getContenue());
		if(!m.isWithAccuse()){
			System.out.println("sans accusé de reception");
		}
		else{
			System.out.println("avec accusé de reception");
		}
		
		if(!m.isPrioritary()){
			System.out.println("non prioritaire");
		}
		else{
			System.out.println("et prioritaire");
		}
		if(m.getDelais() >0){
			System.out.println("qui expirera dans:" + m.getDelais() + "minutes");
		}
		else{
			System.out.println("sans delais");
		}
		if(!m.isCrypt()){
			System.out.println("non crypte");
		}
		else{
			System.out.println("crypte");
		}
	}
	
	public static void listHobby(){
		ArrayList<Hobby> listHobby = HobbyAction.getInstance().getAllHobbies();
		
		for (int i=0; i<listHobby.size();i++){
			System.out.println(listHobby.get(i).getNom()+" "+listHobby.get(i).getType());
		}
	}
	
	public static void listCategorie()  {
		ArrayList<String> listcategorie = HobbyAction.getInstance().getAllCategorie();
		
		for (int i=0;i<listcategorie.size();i++){
			System.out.println(listcategorie.get(i));
			ArrayList<String> listhobby = HobbyAction.getInstance().getHobbyByCategorie(listcategorie.get(i));
			for(int j=0; j<listhobby.size();j++){
				System.out.println(listhobby.get(j));
			}
		}
	}
	
	public static void main(String[] args)   {
		new ConnectionFrame();
		
		/* Usage Unit of Work
		 * 
		User user = UserAction.getInstance().getUserByName("dylan");
		user.setNom("test");
		UnitOfWork.getInstance().commit();
		*/
		
		//logAction.inscription("test1", "test@test1.fr", "vermelles1", "vermelles1", "dele", "dyle");
		//LogAction.getInstance().connexion("test1", "vermelles1");
		//listCategorie();
		//listGroup();
		//listHobby();
		//listMessage();
		//listUser();
		
		//GroupAction.getInstance().removeAllUsersOfGroup(GroupAction.getInstance().getGroupeById(1));
		
		
		/*Hobby hobby = HobbyAction.getInstance().getHobbyByName("K1");
		
		System.out.println(hobby.getType()+" "+hobby.getNom());*/
		
		
		//HobbyAction.getInstance().createHobby("Sport individuel", "Tennis");

		/*
		LogAction.getInstance().connexion("dylan", "user");
		
		 ArrayList<User> list = UserAction.getInstance().getOtherUser();
		
		for (int i =0; i<list.size(); i++){
			System.out.println(list.get(i).getNomCompte());
		}*/
	}

}
