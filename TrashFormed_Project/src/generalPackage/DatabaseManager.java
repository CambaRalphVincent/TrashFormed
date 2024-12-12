package generalPackage;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DatabaseManager {
	
	private Connection connection;
	
	//Constructor: Establishes a connection to the database
	public DatabaseManager(String dbUrl, String username, String password) {
		try {
			connection = DriverManager.getConnection(dbUrl, username, password);
			System.out.println("Connected to the database successfully!");
		} catch(SQLException e) {
			System.out.println("Failed to connect to the database: " + e.getMessage());
		}
	}
	
	//Close database connection
	public void closeConnection() {
		try {
			if(connection != null) {
				connection.close();
				System.out.println("Database connection closed.");
			}
		} catch (SQLException e) {
			System.out.println("Error closing connection: " + e.getMessage());
		}
	}
	
	public void createTables() {
		String userTable = "CREATE TABLE IF NOT EXISTS Users (\n"
						   + "user_id INT AUTO_INCREMENT PRIMARY KEY,\n"
						   + "username VARCHAR(50) UNIQUE NOT NULL,\n"
						   + "password VARCHAR(50) NOT NULL,\n"
						   + "role VARCHAR(10) NOT NULL);";
		
		String wasteTable = "CREATE TABLE IF NOT EXISTS WasteItems (\n"
				   + "item_id INT AUTO_INCREMENT PRIMARY KEY,\n"
				   + "user_id INT NOT NULL,\n"
				   + "item_name VARCHAR(100) NOT NULL,\n"
				   + "quantity INT NOT NULL,\n"
				   + "waste_type VARCHAR(50) NOT NULL,\n"
				   + "FOREIGN KEY(user_id) REFERENCES Users(user_id));";
		
		String tipsTable = "CREATE TABLE IF NOT EXISTS RecyclingTips (\n"
				   + "tip_id INT AUTO_INCREMENT PRIMARY KEY,\n"
				   + "waste_type VARCHAR(50) PRIMARY KEY,\n"
				   + "tip TEXT NOT NULL);";
		
		try(Statement stmt = connection.createStatement()){
			stmt.execute(userTable);
			stmt.execute(wasteTable);
			stmt.execute(tipsTable);
			System.out.println("Tables created successfully!");
		}catch(SQLException e) {
			System.out.println("Error creating tables: " + e.getMessage());
		}
	}
	
	//User operations
	public boolean insertUser(User user) {
	    String query = "INSERT INTO users (username, user_password, user_role) VALUES (?, ?, ?)";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, user.getUsername());
	        stmt.setString(2, user.getPassword());
	        stmt.setString(3, user.getRole());
	        stmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public boolean validateLogin(String username, String password) {
		String query = "SELECT * FROM users WHERE username = ? AND user_password = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			return rs.next(); // Returns true if a matching record is found
		}catch(SQLException e) {
			System.out.println("Error validating login: " + e.getMessage());
			return false;
		}
	}
	
	public boolean isAdmin(String username) {
		String query = "SELECT user_role FROM users WHERE username = ?";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return "admin".equalsIgnoreCase(rs.getString("user_role"));
			}
		}catch (SQLException e) {
			System.out.println("Error checking admin role: " + e.getMessage());
		}
		return false;
	}
	
	public int getUserId(String username) {
		String query = "SELECT user_id FROM Users WHERE username = ?";
		try(PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return rs.getInt("user_id");
			}
		} catch(SQLException e) {
			System.out.println("Error fetching user ID: " + e.getMessage());
		}
		
		return -1; //Return -1 if user not found 
	}
	
	public User getUserByUsername(String username) {
	    String query = "SELECT * FROM users WHERE username = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, username);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            int userId = rs.getInt("user_id");
	            String password = rs.getString("user_password");
	            String role = rs.getString("user_role");
	            return new User(userId, username, password, role);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // Return null if user is not found
	}

	
	//Waste item operations
	public void insertWasteItem(int userId, String itemName, int quantity, String wasteType) {
		String query = "INSERT INTO WasteItems (user_id, item_name, quantity, waste_type) VALUES (?, ?, ?, ?)";
		try(PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, userId);
			stmt.setString(2, itemName);
			stmt.setInt(3, quantity);
			stmt.setString(4, wasteType);
			stmt.executeUpdate();
			System.out.println("Waste item added successfully!");
		}catch(SQLException e) {
			System.out.println("Error inserting waste item: " + e.getMessage());
		}
	}
	
	public void displayRecyclingSummary(int userId) {
		String query = "SELECT waste_type, SUM(quantity) AS total_quantity FROM WasteItems WHERE user_id = ? GROUP BY waste_type";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			System.out.println("\nRecycling summary:");
			while(rs.next()) {
				System.out.println(rs.getString("waste_type") + ": " + rs.getInt("total_quantity") + " items recycled.");
			}
		}catch(SQLException e) {
			System.out.println("Error displaying recycling summary: " + e.getMessage());
		}
	}
	
	//Recycling tips operations
	public void insertOrUpdateTip(String wasteType, String tip) {
		String query = "INSERT INTO RecyclingTips (waste_type, tip) VALUES (?, ?)";
		try(PreparedStatement stmt = connection.prepareStatement(query)){
			stmt.setString(1, wasteType);
			stmt.setString(2, tip);
			stmt.executeUpdate();
			System.out.println("Recycling tip updated successfully!");
		}catch(SQLException e) {
			System.out.println("Error updating recyling tip: " + e.getMessage());
		}
	}
	
	public String getTipByWasteType(String wasteType) {
		String query = "SELECT GROUP_CONCAT(tip SEPARATOR '; ') AS tips FROM RecyclingTips WHERE waste_type = ?";
		try(PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, wasteType);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				String tips = rs.getString("tips");
				if(tips != null && !tips.isEmpty()) {
					
					//Format the tips for better readability
					String[] tipsArray = tips.split("; ");
					StringBuilder formattedTips = new StringBuilder("Recycling Tips for " + wasteType + ":\n");
					for(int i = 0; i < tipsArray.length; i++) {
						formattedTips.append((i + 1) + ". " + tipsArray[i] + "\n");
					}
					return formattedTips.toString();
				}
			}
		}catch(SQLException e) {
			System.out.println("Error fetching recycling tips: " + e.getMessage());
		}
		return "No tips available for this waste type.";
	}
	
	public void getAllRecyclingTips() {
	    String query = "SELECT waste_type, GROUP_CONCAT(tip SEPARATOR '; ')AS tips FROM RecyclingTips GROUP BY waste_type";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        ResultSet rs = stmt.executeQuery();
	        
	        while(rs.next()) {
	        	String wasteType = rs.getString("waste_type");
	        	String tips = rs.getString("tips");
	        	System.out.println(wasteType + ":");
	        	for(String tip: tips.split("; ")) {
	        		System.out.println("  - " + tip);
	        	}
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching recycling tips: " + e.getMessage());
	    }
	}
	
	public List<String> getAllTipsByWasteType(String wasteType) {
	    List<String> tips = new ArrayList<>();
	    String query = "SELECT tip FROM RecyclingTips WHERE waste_type = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, wasteType);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            tips.add(rs.getString("tip"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching recycling tips: " + e.getMessage());
	    }
	    return tips;
	}
	
	public boolean deleteRecyclingTip(String wasteType, int tipNumber) {
	    String query = "DELETE FROM RecyclingTips WHERE waste_type = ? AND tip_id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        // Get the tip IDs of all the tips for the given waste type
	        List<Integer> tipIds = getTipIdsByWasteType(wasteType);
	        if (tipNumber < 1 || tipNumber > tipIds.size()) {
	            System.out.println("Invalid tip number.");
	            return false;
	        }

	        int tipIdToDelete = tipIds.get(tipNumber - 1); // Convert to 0-based index
	        stmt.setString(1, wasteType);
	        stmt.setInt(2, tipIdToDelete);
	        int rowsAffected = stmt.executeUpdate();

	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        System.out.println("Error deleting recycling tip: " + e.getMessage());
	        return false;
	    }
	}
	
	private List<Integer> getTipIdsByWasteType(String wasteType) {
	    List<Integer> tipIds = new ArrayList<>();
	    String query = "SELECT tip_id FROM RecyclingTips WHERE waste_type = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, wasteType);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            tipIds.add(rs.getInt("tip_id"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching tip IDs: " + e.getMessage());
	    }
	    return tipIds;
	}
	
}
