package forum.services;

import java.io.Serializable;
import java.util.ArrayList;

import forum.bean.data.Hobby;

public interface IHobbyServices extends Serializable{

	public void removeHobby(Hobby hobby)  ;

	public void createHobby(String categorie, String nom)  ;

	public ArrayList<Hobby> getAllHobbies();

	public ArrayList<String> getAllCategorie()  ;

	public ArrayList<String> getHobbyByCategorie(String categorie)  ;

	public Hobby getHobbyByName(String name)  ;

	public ArrayList<Hobby> getOtherHobbies();

	public ArrayList<String> getOtherCategorie();

	public ArrayList<String> getOtherHobbyByCategorie(String categorie)  ;

}
