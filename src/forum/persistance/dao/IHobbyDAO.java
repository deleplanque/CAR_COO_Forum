package forum.persistance.dao;

import java.util.ArrayList;

import forum.bean.data.Hobby;

public interface IHobbyDAO {

	public void removeHobby(Hobby hobby)  ;

	public void createHobby(String categorie, String nom)  ;

	public ArrayList<Hobby> getAllHobbies();

	public Hobby findHobbyByID(int id)  ;

	public ArrayList<String> getAllCategorie()  ;

	public ArrayList<String> getHobbyByCategorie(String categorie)  ;

	public Hobby getHobbyByName(String name)  ;

	public ArrayList<Hobby> getOtherHobbies();

	public ArrayList<String> getOtherCategorie();

	public ArrayList<String> getOtherHobbyByCategorie(String categorie)  ;
}
