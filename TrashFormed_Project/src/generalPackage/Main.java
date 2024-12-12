package generalPackage;

import java.util.Scanner;
import wasteRelated.ElectronicWaste;
import wasteRelated.GlassWaste;
import wasteRelated.MetalWaste;
import wasteRelated.OrganicWaste;
import wasteRelated.PlasticWaste;
import wasteRelated.WasteItem;

import java.util.InputMismatchException;
import java.util.List;
public class Main {
	private static DatabaseManager db;

	public static void main(String[] args) {

		//Initialize the DatabaseManager
		db = new DatabaseManager("jdbc:mysql://127.0.0.1:3306/trashformed_db", "root", "PleaseDontForget***");
		db.createTables(); //Ensure the tables are setup
		
		Scanner in = new Scanner(System.in);
		boolean running = true;
		
		System.out.println("=== TrashFormed ===");
		while(running) {
			System.out.println("\nMain menu:");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.print("Enter your choice: ");
			String choice = in.nextLine();
			//in.nextLine();
				
			switch(choice) {
			case "1":
				registerUser(in);
				break;
			case "2":
				login(in);
				break;
			case "3":
				running = false;
				break;
			default: System.out.println("Invalid choice. Please try again.");
			}
		}
		
		//Close the database connection before exiting
		db.closeConnection();
		System.out.println("Thank you for using TrashFormed!");
	}
	
	
	private static void registerUser(Scanner in) {
	    System.out.println("\n=== Register ===");
	    System.out.print("Enter username: ");
	    String username = in.nextLine();
	    System.out.print("Enter password: ");
	    String password = in.nextLine();
	    System.out.print("Enter role (user/admin): ");
	    String role = in.nextLine().toLowerCase();

	    if (!role.equals("user") && !role.equals("admin")) {
	        System.out.println("Invalid role. Registration failed.");
	        return;
	    }

	    User newUser = new User(username, password, role);
	    if (db.insertUser(newUser)) {
	        System.out.println("Registration successful!");
	    } else {
	        System.out.println("Error during registration.");
	    }
	}

	
	//Method to handle login
	private static void login(Scanner in) {
	    System.out.println("\n=== Login ===");
	    System.out.print("Enter username: ");
	    String username = in.nextLine();
	    System.out.print("Enter password: ");
	    String password = in.nextLine();

	    User user = db.getUserByUsername(username);
	    if (user != null && user.validatePassword(password)) {
	        if (user.isAdmin()) {
	            System.out.println("Login as Admin successfully!");
	            adminMenu(in, user);
	        } else {
	            System.out.println("Login as User successfully!");
	            userMenu(in, user);
	        }
	    } else {
	        System.out.println("Invalid username or password.");
	    }
	}


	//Admin menu
	private static void adminMenu(Scanner in, User admin) {
	    boolean running = true;
	    while (running) {
	        System.out.println("\n=== Admin Menu ===");
	        System.out.println("1. Add/Edit Recycling Tip");
	        System.out.println("2. Delete Recycling Tip");
	        System.out.println("3. Logout");
	        System.out.print("Enter your choice: ");
	        String choice = in.nextLine();
	        //in.nextLine(); // Consume newline

	        switch (choice) {
	            case "1":
	                System.out.print("Enter waste type: ");
	                String wasteType = in.nextLine();
	                System.out.print("Enter recycling tip: ");
	                String tip = in.nextLine();
	                db.insertOrUpdateTip(wasteType, tip);
	                break;
	            case "2":
	                deleteRecyclingTip(in);
	                break;
	            case "3":
	                running = false;
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}

	
	//User menu
	private static void userMenu(Scanner in, User user) {
	    boolean running = true;

	    while (running) {
	        System.out.println("\n=== User Menu === ");
	        System.out.println("1. Add Waste Item");
	        System.out.println("2. View Recycling Summary");
	        System.out.println("3. View All Recycling Tips");
	        System.out.println("4. Logout");
	        System.out.print("Enter your choice: ");
	        String choice = in.nextLine();
	        //in.nextLine(); // Consume newline

	        switch (choice) {
	            case "1":
	                addWasteItem(in, user.getUserId());
	                break;
	            case "2":
	                db.displayRecyclingSummary(user.getUserId());
	                break;
	            case "3":
	                displayAllRecyclingTips();
	                break;
	            case "4":
	                running = false;
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }
	}

	
	private static void addWasteItem(Scanner in, int userId) {
	    System.out.print("Enter waste item name: ");
	    String itemName = in.nextLine();
	    System.out.print("Enter quantity: ");
	    int quantity = in.nextInt();
	    in.nextLine(); // Consume newline
	    System.out.print("Enter waste type (Plastic, Organic, Metal, Glass, Electronic): ");
	    String wasteType = in.nextLine();

	    WasteItem wasteItem;

	    // Create the appropriate subclass instance based on wasteType
	    switch (wasteType.toLowerCase()) {
	        case "plastic":
	            wasteItem = new PlasticWaste(0, userId, itemName, quantity);
	            break;
	        case "organic":
	            wasteItem = new OrganicWaste(0, userId, itemName, quantity);
	            break;
	        case "metal":
	            wasteItem = new MetalWaste(0, userId, itemName, quantity);
	            break;
	        case "glass":
	            wasteItem = new GlassWaste(0, userId, itemName, quantity);
	            break;
	        case "electronic":
	            wasteItem = new ElectronicWaste(0, userId, itemName, quantity);
	            break;
	        default:
	            System.out.println("Invalid waste type. Waste item not added.");
	            return;
	    }

	    // Insert the waste item into the database
	    db.insertWasteItem(userId, itemName, quantity, wasteType);

	    // Display the recycling tip from the subclass
	    System.out.println("\n========================");
	    System.out.println("Tip from system:");
	    System.out.println(wasteItem.getRecycleTip());

	    // Fetch and display additional tips for this waste type from the database
	    System.out.println("\nAdditional tips from admin:");
	    String tipsFromDb = db.getTipByWasteType(wasteType);
	    System.out.println(tipsFromDb);
	}


	private static void displayAllRecyclingTips() {

		 System.out.println("\n=== All Recycling Tips ===");
		 db.getAllRecyclingTips();
	}
	
	private static void deleteRecyclingTip(Scanner in) {
		System.out.println("\n=== Delete Recycling Tip ===");
	    System.out.print("Enter the waste type to delete its recycling tip: ");
	    String wasteType = in.nextLine();

	    // Fetch all tips for the waste type
	    List<String> tips = db.getAllTipsByWasteType(wasteType);
	    if (tips.isEmpty()) {
	        System.out.println("No tips found for " + wasteType);
	        return;
	    }

	    System.out.println("Recycling tips for " + wasteType + ":");
	    for (int i = 0; i < tips.size(); i++) {
	        System.out.println((i + 1) + ". " + tips.get(i)); // Display tip number and content
	    }
	    
	    try {
	    	System.out.print("Enter the number of the tip to delete: ");
	 	    int tipNumber = in.nextInt();
	 	    in.nextLine(); // Consume newline
	 	    
	 	   boolean success = db.deleteRecyclingTip(wasteType, tipNumber);
		    if (success) {
		        System.out.println("Recycling tip deleted successfully.");
		    } else {
		        System.out.println("Error deleting recycling tip.");
		    }
	    }catch(InputMismatchException e) {
	    	System.out.println("Please enter only the corresponding number of the recycling tip to delete.");
	    }
	}
}
