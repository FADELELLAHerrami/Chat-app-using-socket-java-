package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;
import connection.MyConnection;

public class DaoImpl implements Dao<User> {

	@Override
	public User save(User user) {
		Connection c = MyConnection.getConnexion();
		if (c != null) {
			try {
				PreparedStatement st = c.prepareStatement(
						"Insert into utilisateur " + "(nom,prenom,password,fonction) " + "values(?,?,?,?);",
						PreparedStatement.RETURN_GENERATED_KEYS);
				st.setString(1, user.getNom());
				st.setString(2, user.getPrenom());
				st.setString(3, user.getPassword());
				st.setString(4, user.getFonction());
				st.executeUpdate();
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					user.setId(rs.getInt(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	@Override
	public User findUser(String nom, String Prenom, String password, String fonction) {
		Connection c = MyConnection.getConnexion();
		User user = null;
		if (c != null) {
			try {
				PreparedStatement st = c.prepareStatement(
						"Select * from utilisateur where nom = ? And prenom = ? And password = ? and fonction = ? ;");
				st.setString(1, nom);
				st.setString(2, Prenom);
				st.setString(3, password);
				st.setString(4, fonction);
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					user = new User(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
							rs.getString("password"), rs.getString("fonction"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

}
