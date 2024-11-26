package backend;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBHandler {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/NOMADOASIS";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "12345";

	private Connection connection;

    private static DBHandler instance;

    private DBHandler() {
    }

    public static DBHandler getInstance() {
        if (instance == null) {
            synchronized (DBHandler.class) {
                if (instance == null) {
                    instance = new DBHandler();
                }
            }
        }
        return instance;
    }

	public Connection connect() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			System.out.println("Database connection successful!");
		} catch (SQLException e) {
			System.err.println("Failed to connect to the database: " + e.getMessage());
		}
		return connection;
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Database connection closed.");
			} catch (SQLException e) {
				System.err.println("Failed to close the database connection: " + e.getMessage());
			}
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public boolean InsertingTravelerTODB(String email, String username, String password, String cnic, String gender, String dob)
	{


		String INSERT_TRAVELER = "INSERT INTO user1 (email, username1, password1, CNIC, Gender, DOB, userType) VALUES (?, ?, ?, ?, ?, ?, 'traveler')";


		try (PreparedStatement Query = connection.prepareStatement(INSERT_TRAVELER)) {
			// Set the values for the prepared statement from the method parameters
			Query.setString(1, email);
			Query.setString(2, username);
			Query.setString(3,password);
			Query.setString(4, cnic);
			Query.setString(5, gender);
			Query.setString(6,dob);

			// Execute the update query to insert the new traveler into the database
			int rowsAffected = Query.executeUpdate();

			// Return true if at least one row was affected, indicating success
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Return false if there was an exception
		}

	}


	public String validateTravelerLogin(String username, String password) 
	{
		String travelerID = null;
		String LOGIN_QUERY = "SELECT userID FROM user1 WHERE username1 = ? AND password1 = ?"; //AND userType = 'traveler'";

		try (PreparedStatement Query = connection.prepareStatement(LOGIN_QUERY)) 
		{
			Query.setString(1, username);
			Query.setString(2, password);

			ResultSet resultSet = Query.executeQuery();

			if (resultSet.next()) {
				travelerID = resultSet.getString("userID"); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return travelerID; 
	}

	public Traveler fetchTravelerData(String travelerID)
	{
		String query = "SELECT username1, email, DOB, CNIC, Gender FROM user1 WHERE userID = ? AND userType = 'traveler'";
		Traveler traveler = null;

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, travelerID);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				traveler = new Traveler(
						rs.getString("email"),
						rs.getString("username1"),
						"",
						rs.getString("CNIC"),
						rs.getString("Gender"),
						rs.getString("DOB")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return traveler;
	}

	public boolean updateTravelerData(String id, String un, String emaila)
	{
		String query = "UPDATE user1 SET username1 = ?, email = ? WHERE userID = ? AND userType = 'traveler'";
		String logQuery = "INSERT INTO logs1 (travelerID, logtext, Date1) VALUES (?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(query);
				PreparedStatement stmt1 = connection.prepareStatement(logQuery)) {

			stmt.setString(1, un);
			stmt.setString(2, emaila);
			stmt.setString(3, id);

			int rowsUpdated = stmt.executeUpdate();

			String logMessage = "You Updated Your Profile";
			Date currentDate = new Date(System.currentTimeMillis());

			stmt1.setString(1, id);
			stmt1.setString(2, logMessage);
			stmt1.setDate(3, currentDate);

			stmt1.executeUpdate();

			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false; 
		}
	}

	public int getItemStock(int itemID) throws SQLException {


		String stockQuery = "SELECT stock FROM Item WHERE itemID = ?";
		PreparedStatement stockStmt = connection.prepareStatement(stockQuery);
		stockStmt.setInt(1, itemID);

		ResultSet stockResult = stockStmt.executeQuery();
		if (stockResult.next()) {
			return stockResult.getInt("stock");
		}

		return 0; 
	}


	public String AddItemtoCart(int itemid, int quantity, int travelerid) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Step 1: Check if the traveler has an existing cart
			String selectCartQuery = "SELECT cartID FROM Cart WHERE travelerID = ?";
			preparedStatement = connection.prepareStatement(selectCartQuery);
			preparedStatement.setInt(1, travelerid);
			resultSet = preparedStatement.executeQuery();

			int cartID;
			if (resultSet.next()) {
				// Cart exists for the traveler
				cartID = resultSet.getInt("cartID");
			} else {
				// Create a new cart for the traveler
				String insertCartQuery = "INSERT INTO Cart (travelerID) VALUES (?)";
				preparedStatement = connection.prepareStatement(insertCartQuery, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, travelerid);
				preparedStatement.executeUpdate();

				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					cartID = resultSet.getInt(1); // Get the newly generated cartID
				} else {
					throw new SQLException("Failed to create a new cart.");
				}
			}

			// Step 2: Check if the item already exists in the cart
			String selectCartItemQuery = "SELECT quantity FROM CartItems WHERE cartID = ? AND itemID = ?";
			preparedStatement = connection.prepareStatement(selectCartItemQuery);
			preparedStatement.setInt(1, cartID);
			preparedStatement.setInt(2, itemid);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// Item exists, REPLACE the quantity
				String updateCartItemQuery = "UPDATE CartItems SET quantity = ? WHERE cartID = ? AND itemID = ?";
				preparedStatement = connection.prepareStatement(updateCartItemQuery);
				preparedStatement.setInt(1, quantity); // Set the new quantity (no addition)
				preparedStatement.setInt(2, cartID);
				preparedStatement.setInt(3, itemid);
				preparedStatement.executeUpdate();
			} else {
				// Item does not exist, insert a new record
				String insertCartItemQuery = "INSERT INTO CartItems (cartID, itemID, quantity) VALUES (?, ?, ?)";
				preparedStatement = connection.prepareStatement(insertCartItemQuery);
				preparedStatement.setInt(1, cartID);
				preparedStatement.setInt(2, itemid);
				preparedStatement.setInt(3, quantity);
				preparedStatement.executeUpdate();
			}

			return "Item successfully added to the cart with updated quantity.";

		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding item to the cart: " + e.getMessage();
		}
	}


	public List<Item> getCartItems(int travelerID) {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<Item> cartItems = new ArrayList<>();

		try {


			// Query to fetch cart items for the traveler
			String query = "SELECT i.name1, ci.quantity, i.price " +
					"FROM Cart c " +
					"JOIN CartItems ci ON c.cartID = ci.cartID " +
					"JOIN Item i ON ci.itemID = i.itemID " +
					"WHERE c.travelerID = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, travelerID);

			resultSet = preparedStatement.executeQuery();

			// Populate the list of items
			while (resultSet.next()) {
				String name = resultSet.getString("name1");
				int price = resultSet.getInt("price");
				int quantity = resultSet.getInt("quantity");

				// Create an Item object and add it to the list
				Item item = new Item();
				item.setName(name);
				item.setPrice(price);
				item.setQuantity(quantity);

				cartItems.add(item);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} 


		return cartItems; 
	}

	public boolean isCartEmpty(int travelerId) {
		String query = "SELECT * FROM cart WHERE travelerID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, travelerId);
			try (ResultSet rs = stmt.executeQuery()) {
				return !rs.next(); // If no rows are found, the cart is empty
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Error handling
		}
	}


	public void clearCart(int travelerID) {
		// SQL queries updated to use travelerID
		String deleteCartItems = "DELETE FROM CartItems WHERE cartID = (SELECT cartID FROM Cart WHERE travelerID = ?)";
		String deleteCart = "DELETE FROM Cart WHERE travelerID = ?";

		try (PreparedStatement stmtCartItems = connection.prepareStatement(deleteCartItems);
				PreparedStatement stmtCart = connection.prepareStatement(deleteCart)) {

			// Set travelerID for deleting items in CartItems
			stmtCartItems.setInt(1, travelerID);
			stmtCartItems.executeUpdate();  // Execute the delete for cart items

			// Set travelerID for deleting the cart
			stmtCart.setInt(1, travelerID);
			stmtCart.executeUpdate();  // Execute the delete for the cart itself



		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public List<String> getAllPackageNames() {
		List<String> packageNames = new ArrayList<>();
		String query = "SELECT name1 FROM Package";
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				packageNames.add(resultSet.getString("name1"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return packageNames;
	}

	// Fetch package details by name
	public Package getPackageDetails(String packageName) {
		String query = "SELECT * FROM Package WHERE name1 = ?";
		Package pkg = null;

		try (PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, packageName);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				pkg = new Package(
						resultSet.getInt("packageID"),
						resultSet.getString("name1"),
						resultSet.getString("destination"),
						resultSet.getInt("duration"),
						resultSet.getString("description1"),
						resultSet.getInt("price")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pkg;
	}


	public void addBookingPackage(int travelerID, String packageName, int quantity) {
		PreparedStatement findPackageStmt = null;
		PreparedStatement insertBookingStmt = null;
		ResultSet resultSet = null;

		try {
			// Start transaction
			connection.setAutoCommit(false);

			// Step 2: Find the packageID based on the package name
			String findPackageSQL = "SELECT packageID FROM Package WHERE name1 = ?";
			findPackageStmt = connection.prepareStatement(findPackageSQL);
			findPackageStmt.setString(1, packageName);
			resultSet = findPackageStmt.executeQuery();

			int packageID = -1;
			if (resultSet.next()) {
				packageID = resultSet.getInt("packageID");
			}

			// Step 3: If package exists, proceed to insert into BookingPackage
			if (packageID != -1) {
				String insertBookingSQL = "INSERT INTO BookingPackage (travelerID, packageID, bookingDate, quantity) VALUES (?, ?, ?, ?)";
				insertBookingStmt = connection.prepareStatement(insertBookingSQL);
				insertBookingStmt.setInt(1, travelerID);
				insertBookingStmt.setInt(2, packageID);
				insertBookingStmt.setDate(3, Date.valueOf(LocalDate.now())); // Set booking date as today's date
				insertBookingStmt.setInt(4, quantity);
				insertBookingStmt.executeUpdate();

				// Commit the transaction
				connection.commit();

			} else {
				System.out.println("Package not found: " + packageName);
			}

		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback(); // Rollback in case of error
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (findPackageStmt != null) {
					findPackageStmt.close();
				}
				if (insertBookingStmt != null) {
					insertBookingStmt.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	///////////////////////////////
	/////SHAYYYYAAAAANNNSSSS
	////////////////////////////


	public String insertQuery(String travelerID, String queryContent) throws ClassNotFoundException {
		String responseMessage = "Query submission failed.";
		String query = "INSERT INTO query1 (travelerID, queryContent, response) VALUES (?, ?, NULL)";
		String logQuery = "INSERT INTO logs1 (travelerID, logtext, Date1) VALUES (?, ?, ?)";
		try (PreparedStatement stmt1 = connection.prepareStatement(logQuery);
				PreparedStatement stmt = connection.prepareStatement(query)) 
		{
			stmt.setString(1, travelerID);
			stmt.setString(2, queryContent);

			String logMessage = "You added a Query: "+ queryContent;
			Date currentDate = new Date(System.currentTimeMillis());	

			stmt1.setString(1, travelerID);
			stmt1.setString(2, logMessage);
			stmt1.setDate(3, currentDate);

			stmt1.executeUpdate();

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0)
			{
				responseMessage = "Query submitted successfully.";
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return responseMessage;


	}

	public String assignConsultantToQuery(String travelerID, int consultantID) throws ClassNotFoundException 
	{
		String responseMessage = "Failed to assign consultant.";
		String query = "UPDATE query1 SET consultantID = ? WHERE travelerID = ? AND consultantID IS NULL LIMIT 1";
		try (
				PreparedStatement stmt = connection.prepareStatement(query)) {

			stmt.setInt(1, consultantID);
			stmt.setString(2, travelerID);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				responseMessage = "Consultant assigned successfully.";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return responseMessage;
	}

	public int getConsultantID() throws ClassNotFoundException {
		int consultantID = -1;
		String query = "SELECT userID FROM User1 WHERE userType = 'Consultant'";
		try (
				PreparedStatement stmt = connection.prepareStatement(query); 
				ResultSet rs = stmt.executeQuery())
		{
			List<Integer> clist = new ArrayList<>();
			while (rs.next())
			{
				clist.add(rs.getInt("userID"));
			}

			Random random = new Random();
			int randconsultant = random.nextInt(clist.size()); 
			consultantID = clist.get(randconsultant);

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return consultantID;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<Hotel> getAllHotels() throws SQLException {
		List<Hotel> hotels = new ArrayList<>();
		String query = "SELECT * FROM Hotel";  


		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query); 

		// Fetch hotels first
		while (resultSet.next()) {

			int hotelID = resultSet.getInt("hotelID");
			String name = resultSet.getString("name1");
			String location = resultSet.getString("location");
			String desc = resultSet.getString("description1");
			int rating = resultSet.getInt("rating");

			// Create a hotel object
			Hotel hotel = new Hotel(hotelID, name, location, desc, rating);
			hotels.add(hotel);
		}

		// Now fetch rooms for each hotel
		for (Hotel hotel : hotels) {
			List<Room> rooms = getRoomsByHotelID(connection, hotel.getHotelID());
			for (Room room : rooms) {
				hotel.addRoom(room);
			}
		}


		return hotels;
	}

	// Modify getRoomsByHotelID to accept the existing connection
	private List<Room> getRoomsByHotelID(Connection connection, int hotelID) {
		List<Room> rooms = new ArrayList<>();
		String query = "SELECT * FROM Room WHERE hotelID = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, hotelID);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int roomID = resultSet.getInt("roomID");
				int roomNum = resultSet.getInt("roomnum");
				String type = resultSet.getString("type1");
				int price = resultSet.getInt("price");
				boolean available = resultSet.getBoolean("available");

				rooms.add(new Room(roomID, hotelID, roomNum, type, price, available));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rooms;
	}

	public boolean bookRoom(int roomID) {
		String query = "UPDATE Room SET available = false WHERE roomID = ?";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, roomID);
			int rowsAffected = statement.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getRoomNumber(int roomID) {
		String query = "SELECT roomnum FROM Room WHERE roomID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, roomID);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("roomnum");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getHotelName(int hotelID) {
		String query = "SELECT name1 FROM hotel WHERE hotelID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, hotelID);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("name1");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}	

	public boolean addBookingToDatabase(int travelerID, int roomID, int hotelID, java.sql.Date bookingDate) throws ClassNotFoundException, SQLException 
	{

		String query = "INSERT INTO BookingRoom (travelerID, roomID, hotelID, bookingDate) VALUES (?, ?, ?, ?)";
		String logQuery = "INSERT INTO logs1 (travelerID, logtext, Date1) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query);
				PreparedStatement stmt1 = connection.prepareStatement(logQuery)) {
			stmt.setInt(1, travelerID);
			stmt.setInt(2, roomID);
			stmt.setInt(3, hotelID);
			stmt.setDate(4, bookingDate);

			String hotelName = getHotelName(hotelID);
			String roomNumber = getRoomNumber(roomID);

			String logMessage = "You booked room " + roomNumber + " at hotel " + hotelName + " on " + bookingDate;
			Date currentDate = new Date(System.currentTimeMillis());

			stmt1.setInt(1, travelerID);
			stmt1.setString(2, logMessage);
			stmt1.setDate(3, currentDate);

			stmt1.executeUpdate();


			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Method to remove a booking from the database
	public boolean removeBookingFromDatabase(int bookingID) {
		String query = "DELETE FROM BookingRoom WHERE bookingID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, bookingID);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}



	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<Integer> getQueryIDs(String travelerID) 
	{
		List<Integer> queryIDs = new ArrayList<>();
		String sql = "SELECT queryID FROM query1 WHERE travelerID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql))
		{
			stmt.setString(1, travelerID); // travelerID is now a String
			try (ResultSet rs = stmt.executeQuery())
			{
				while (rs.next()) 
				{
					queryIDs.add(rs.getInt("queryID"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return queryIDs;
	}

	// Method to get Query object details for a specific queryID
	public Query getQueryDetails(int queryID) {
		Query query = null;
		String sql = "SELECT queryID, consultantID, travelerID, queryContent, response FROM query1 WHERE queryID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, queryID);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int qID = rs.getInt("queryID");
					int consultantID = rs.getInt("consultantID");
					String travelerID = rs.getString("travelerID"); // Now stored as a String
					String queryContent = rs.getString("queryContent");
					String response = rs.getString("response");

					query = new Query(qID, consultantID, travelerID, queryContent);
					query.setResponse(response); // Set the response (may be null)
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return query;
	}


	public List<String> getAssignedQueries(String consultantID) {
		List<String> queryIDs = new ArrayList<>();
		String sql = "SELECT queryID FROM query1 WHERE consultantID = ? AND response IS NULL";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, consultantID);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				queryIDs.add(rs.getString("QueryID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryIDs;
	}

	public String getQueryInfo(String queryID) {
		String queryInfo = null;
		String sql = "SELECT queryContent FROM query1 WHERE QueryID = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, queryID);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				queryInfo = rs.getString("queryContent");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryInfo;
	}

	public boolean submitResponse(String queryID, String response) {
		String sql = "UPDATE query1 SET response = ? WHERE QueryID = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, response);
			stmt.setString(2, queryID);

			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getUserType(String username) {
		String query = "SELECT userType FROM User1 WHERE username1 = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getString("userType");
			} else {
				return null; // User not found
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null; // Return null in case of an exception
		}
	}


	public ArrayList<Package> getBookedPackages(int travelerID) {
		ArrayList<Package> packages = new ArrayList<>();
		String query = """
				SELECT p.packageID, p.name1, p.destination, p.duration, p.description1, p.price
				FROM BookingPackage b
				JOIN Package p ON b.packageID = p.packageID
				WHERE b.travelerID = ?;
				""";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, travelerID);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Package pkg = new Package(
							resultSet.getInt("packageID"),
							resultSet.getString("name1"),
							resultSet.getString("destination"),
							resultSet.getInt("duration"),
							resultSet.getString("description1"),
							resultSet.getInt("price")
							);
					packages.add(pkg);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return packages;
	}


	public int getPackagePrice(int packageID) {
		String query = "SELECT price FROM Package WHERE packageID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, packageID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("price");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Error case
	}

	public int getBookingQuantity(int travelerID, int packageID) {
		String query = "SELECT quantity FROM BookingPackage WHERE travelerID = ? AND packageID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, travelerID);
			stmt.setInt(2, packageID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("quantity");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Error case
	}
	public boolean cancelBooking(int travelerID, int packageID) {
		String query = "DELETE FROM BookingPackage WHERE travelerID = ? AND packageID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, travelerID);
			stmt.setInt(2, packageID);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0; // Return true if a row was deleted
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Return false if an exception occurred
		}
	}



	///////////////////////////////////////////////////////////////





	public boolean addHotel(String name, String location, String description, double rating) 

	{
		String query = "INSERT INTO Hotel (name1, location, description1, rating) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, name);
			stmt.setString(2, location);
			stmt.setString(3, description);
			stmt.setDouble(4, rating);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addRoom(int hotelID, int roomNum, String type, int price) {
		String query = "INSERT INTO Room (hotelID, roomnum, type1, price) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, hotelID);
			stmt.setInt(2, roomNum);
			stmt.setString(3, type);
			stmt.setInt(4, price);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getHotelIDByName(String name) {
		String query = "SELECT hotelID FROM Hotel WHERE name1 = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("hotelID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; 
	}

	/////////////////////////////////////////////////////////////
	//Admin Page
	///////////////////////////////////////////////

	public List<Item> getAllItems() {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		List<Item> items = new ArrayList<>();

		try {
			// Query to fetch all items from the Item table
			String query = "SELECT itemID, name1, description1, price, stock FROM Item";
			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			// Populate the list of items
			while (resultSet.next()) {
				int itemID = resultSet.getInt("itemID");
				String name = resultSet.getString("name1");
				String description = resultSet.getString("description1");
				int price = resultSet.getInt("price");
				int stock = resultSet.getInt("stock");

				// Create an Item object and add it to the list
				Item item = new Item();
				item.setItemid(itemID);
				item.setName(name);
				item.setPrice(price);
				item.setQuantity(stock);

				items.add(item);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} 

		return items; 
	}


	public void updateItemQuantity(String itemName, int amountToAdd) {
		String query = "UPDATE Item SET stock = stock + ? WHERE name1 = ?";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {

			// Set parameters for the query
			stmt.setInt(1, amountToAdd);  // The amount to add to the current quantity
			stmt.setString(2, itemName);  // The name of the selected item

			// Execute the update
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Item quantity updated successfully.");
			} else {
				System.out.println("Item not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error updating item quantity: " + e.getMessage());
		}
	}


	public boolean addPackageToDatabase(Package pkg) {
		try {
			// Prepare the SQL query
			String query = "INSERT INTO package (name1, destination, duration, description1, price) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(query);

			// Set query parameters
			stmt.setString(1, pkg.getName());
			stmt.setString(2, pkg.getDestination());
			stmt.setInt(3, pkg.getDuration());
			stmt.setString(4, pkg.getDescription());
			stmt.setInt(5, pkg.getPrice());

			// Execute the query
			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0; // Return true if a row was inserted
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	///////////////////////////////////////////////////////////
	////////////////////////////Logs///////////////////////////
	//////////////////////////////////////////////////////////

	public List<String> getTravelerLogs(String travelerID) throws SQLException
	{
		List<String> logs = new ArrayList<>();
		String query = "SELECT Date1, logtext FROM logs1 WHERE travelerID = ? ORDER BY logID DESC";

		try (PreparedStatement stmt = connection.prepareStatement(query)) 
		{
			stmt.setString(1, travelerID);

			try (ResultSet rs = stmt.executeQuery()) 
			{
				while (rs.next())
				{
					String date = rs.getDate("Date1").toString();
					String logText = rs.getString("logtext");
					logs.add("Date: " + date + ", " + logText);
				}
			}
		}

		return logs;
	}



}