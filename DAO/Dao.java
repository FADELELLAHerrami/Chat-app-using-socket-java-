package DAO;

import java.util.List;

public interface Dao<T> {
		// save
	
		T save(T obj);
		
		// findbyId
		T findUser(String nom,String Prenom,String fonction,String password);
		

}
