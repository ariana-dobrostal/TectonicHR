package app.user;

import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import app.db.Database;

public class UserDao {

	public static User getUserByEmail(String email) {
		Database db = Database.getInstance();
		Connection connection = db.connect();
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM seizmolog WHERE email = ?;"); 
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				String ime = rs.getString("ime");
				String prezime = rs.getString("prezime");
				String lozinka = rs.getString("lozinka");
				boolean prva_prijava = rs.getBoolean("prva_prijava");
				Uloga uloga = rs.getString("uloga").equals("admin") ? Uloga.ADMIN : Uloga.ZNANSTVENIK;
				User user = new User(ime, prezime, email, lozinka, uloga, prva_prijava);
				stmt.close();
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			 	return user;
			} 
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static boolean addUser(User user) {
		String ime = user.getIme();
		String prezime = user.getPrezime();
		String email = user.getEmail();
		String lozinka = user.getLozinka();
		Uloga uloga = user.getUloga();
		if(usernameExists(email)) return false;

		Database db = Database.getInstance();
		Connection connection = db.connect();
		
		try {
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO seizmolog VALUES (?, ?, ?, ?, ?, ?);"); 
			stmt.setString(1, ime);
			stmt.setString(2, prezime);
			stmt.setString(3, email);
			stmt.setString(4, lozinka);
			String ulogaStr = (uloga == Uloga.ADMIN ? "admin" : "znanstvenik");
			stmt.setString(5, ulogaStr);
			stmt.setLong(6, 1);
			stmt.executeUpdate();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
		
	}

	public static boolean usernameExists(String email) {
		try {
			User user = getUserByEmail(email);
			if(user == null) return false;
			else return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<>();
		Database db = Database.getInstance();
		Connection connection = db.connect();
		try {
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM seizmolog WHERE uloga = ?;");
			pstmt.setString(1, "znanstvenik");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String ime = rs.getString("ime");
				String prezime = rs.getString("prezime");
				String email = rs.getString("email");
				String lozinka = rs.getString("lozinka");
				Uloga uloga = Uloga.ZNANSTVENIK;
				boolean prva_prijava = rs.getBoolean("prva_prijava");
				User user = new User(ime, prezime, email, lozinka, uloga, prva_prijava);
				users.add(user);
			}
			pstmt.close();
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return users;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static boolean deleteUser(String email) {
		Database db = Database.getInstance();
		Connection connection = db.connect();
		try {
			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM seizmolog WHERE email = ?");
			pstmt.setString(1, email);
			pstmt.executeUpdate();
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return false;
	}

	public static void updatePassword (String mail, String newPassword) {
		Database db = Database.getInstance();
		Connection connection = db.connect();
		try {
			PreparedStatement pstmt = connection.prepareStatement("UPDATE seizmolog SET lozinka = ? WHERE email = ?; ");
			pstmt.setString(1, newPassword);
			pstmt.setString(2, mail);
			pstmt.executeUpdate();
			PreparedStatement pstmt1 = connection.prepareStatement("UPDATE seizmolog SET prva_prijava = 0 WHERE email = ?; ");
			pstmt1.setString(1, mail);
			pstmt1.executeUpdate();
			try {
				connection.close();
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public static void setFirstLogin(String username) {
		Database db = Database.getInstance();
		try(Connection connection = db.connect()) {
			PreparedStatement pstmt = connection.prepareStatement("UPDATE seizmolog SET prva_prijava = 0 WHERE email = ? ;");
			pstmt.setString(1, username);
			pstmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
}
