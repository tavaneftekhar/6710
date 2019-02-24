import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/jokesDAO")
public class JokesDAO extends HttpServlet {     
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

    
	public JokesDAO() {

    }
	       
    /**
     * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/sampledb?"
  			          + "user=john&password=pass1234");
            System.out.println(connect);
        }
    }
    
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }

     
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
    
    public boolean initializeDatabase() {

    	boolean databaseInitialized = false;
    	
        try {
            connect_func();
            
            statement = connect.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS Jokes_Tags");
            statement.executeUpdate("DROP TABLE IF EXISTS Tags");
            statement.executeUpdate("DROP TABLE IF EXISTS Reviews");
            statement.executeUpdate("DROP TABLE IF EXISTS Favorite_Users");
            statement.executeUpdate("DROP TABLE IF EXISTS Favorite_Jokes");
            statement.executeUpdate("DROP TABLE IF EXISTS Jokes");
            statement.executeUpdate("DROP TABLE IF EXISTS BlackListed");
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            
            String UsersSsqlStmt = "CREATE TABLE IF NOT EXISTS Users(" +
                    "User_Id int NOT NULL AUTO_INCREMENT," +
                    "Username nvarchar(50) NOT NULL UNIQUE," +
                    "Password nvarchar(50) NOT NULL," +
                    "First_Name nvarchar(60) NOT NULL," +
                    "Last_Name nvarchar(60) NOT NULL," +
                    "Email nvarchar(200) NOT NULL UNIQUE," +
                    "Gender nvarchar(30)," +
                    "Age int," +
                    "Type int DEFAULT 2," +
                    "PRIMARY KEY (User_Id))";           
            
            String JokeSqlStmt = "CREATE TABLE IF NOT EXISTS Jokes(" +
            		"Joke_Id INT NOT NULL AUTO_INCREMENT," +
            		"User_Id INT NOT NULL,"+
            		"Title VARCHAR(255) NOT NULL,"+
            		"Description VARCHAR(255) NOT NULL,"+
            	   	"Post_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"+
            	   	"PRIMARY KEY (Joke_Id),"+
            		"CONSTRAINT FK_User0 FOREIGN KEY (User_Id) REFERENCES Users(User_Id),"+
            		"CHECK ( 0 = (SELECT COUNT(*) FROM Jokes "+
            			"WHERE DATE(`Post_Date`) = CURDATE() "+
            			"GROUP BY User_Id "+
            			"HAVING COUNT(*) > 5)))";

            String ReviewsSqlStmt = "CREATE TABLE IF NOT EXISTS Reviews(" + //New Review_ID, not sure if we need this as a field?
                    "Joke_Id int NOT NULL," +
                    "User_Id int NOT NULL," +
                    "Score nvarchar(50) NULL DEFAULT 'good'," +
                    "Remark nvarchar(50) NULL," +
                    "Post_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "CHECK (Score IN ('excellent', 'good', 'fair', 'poor'))," +
                    "PRIMARY KEY (Joke_Id, User_Id)," +
                    "CONSTRAINT FK_Joke FOREIGN KEY (Joke_Id) REFERENCES Jokes(Joke_Id)," +
                    "CONSTRAINT FK_User FOREIGN KEY (User_Id) REFERENCES Users(User_Id))";
            
            String BlackListedSqlStmt = "CREATE TABLE IF NOT EXISTS BlackListed(" +
                    "User_Id int NOT NULL," +
                    "Blacklist_Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (User_Id)," +
                    "CONSTRAINT FK_BlackList FOREIGN KEY (User_Id) REFERENCES Users(User_Id))";
            
            String Favorite_UsersSqlStmt = "CREATE TABLE IF NOT EXISTS Favorite_Users(" +
                    "User_Id int NOT NULL," +
                    "Friend_Id int NOT NULL," +
                    "PRIMARY KEY (User_Id, Friend_Id)," +
                    "CONSTRAINT FK_Friend FOREIGN KEY (Friend_Id) REFERENCES Users(User_Id)," +
                    "CONSTRAINT FK_User2 FOREIGN KEY (User_Id) REFERENCES Users(User_Id))";

            String Favorite_JokesSqlStmt = "CREATE TABLE IF NOT EXISTS Favorite_Jokes(" +
                    "User_Id int NOT NULL," +
                    "Joke_Id int NOT NULL," +
                    "PRIMARY KEY (User_Id, Joke_Id)," +
                    "CONSTRAINT FK_Joke2 FOREIGN KEY (Joke_Id) REFERENCES Jokes(Joke_Id)," +
                    "CONSTRAINT FK_User3 FOREIGN KEY (User_Id) REFERENCES Users(User_Id))";
            
            String TagsSqlStmt = "CREATE TABLE IF NOT EXISTS Tags(" +
                    "Tag_Id INT NOT NULL AUTO_INCREMENT," +
                    "Title nvarchar(50) NOT NULL," +
                    "PRIMARY KEY (Tag_Id))";
            
            String JokesTagsSqlStmt = "CREATE TABLE IF NOT EXISTS Jokes_Tags(" +
                    "Joke_Id int NOT NULL," +
                    "Tag_Id int NOT NULL," +
                    "PRIMARY KEY (Joke_Id, Tag_Id)," +
                    "CONSTRAINT FK_Joke3 FOREIGN KEY (Joke_Id) REFERENCES Jokes(Joke_Id)," +
                    "CONSTRAINT FK_Tag FOREIGN KEY (Tag_Id) REFERENCES Tags(Tag_Id))";

            statement.executeUpdate(UsersSsqlStmt);
            statement.executeUpdate(JokeSqlStmt);
            statement.executeUpdate(ReviewsSqlStmt);
            statement.executeUpdate(BlackListedSqlStmt);
            statement.executeUpdate(Favorite_UsersSqlStmt);
            statement.executeUpdate(Favorite_JokesSqlStmt);
            statement.executeUpdate(TagsSqlStmt);
            statement.executeUpdate(JokesTagsSqlStmt);
            
            String sql = "INSERT INTO Users (Username, Password, First_Name, Last_Name, Email, Gender, Age, Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
    		preparedStatement.setString(1, "root");
    		preparedStatement.setString(2, "pass1234");
    		preparedStatement.setString(3, "Tavan");
    		preparedStatement.setString(4, "Eftekhar");
    		preparedStatement.setString(5, "root@gmail.com");
    		preparedStatement.setString(6, "M");
    		preparedStatement.setInt(7, 31);
    		preparedStatement.setInt(8, 1);
    		preparedStatement.executeUpdate();
    		preparedStatement.close();
    		
    		String gender;
    		for (int i=2; i<101; i++) {
    			if (i%2 == 0) {
        			gender = "M";
        		} else {
        			gender = "F";
        		}
    			preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        		preparedStatement.setString(1, "user"+i);
        		preparedStatement.setString(2, "pass"+i);
        		preparedStatement.setString(3, "First"+i);
        		preparedStatement.setString(4, "Last"+i);
        		preparedStatement.setString(5, "email"+i+"@gmail.com");
        		preparedStatement.setString(6, gender);
        		preparedStatement.setInt(7, i);
        		preparedStatement.setInt(8, 2);
        		preparedStatement.executeUpdate();
        		preparedStatement.close();
    		}
    		
            
            String JokesSQL = "INSERT INTO Jokes (User_Id, Title, Description) VALUES (?, ?, ?)";
            for (int i=1; i<101; i++) {
    			preparedStatement = (PreparedStatement) connect.prepareStatement(JokesSQL);
        		preparedStatement.setInt(1, i);
        		preparedStatement.setString(2, "Title "+i);
        		preparedStatement.setString(3, "Description "+i);
        		preparedStatement.executeUpdate();
        		preparedStatement.close();
    		}
            
            String ReviewsSQL = "INSERT INTO Reviews (Joke_Id, User_Id, Remark) VALUES (?, ?, ?)";
            for (int i=1; i<101; i++) {
    			preparedStatement = (PreparedStatement) connect.prepareStatement(ReviewsSQL);
        		preparedStatement.setInt(1, i);
        		preparedStatement.setInt(2, i);
        		preparedStatement.setString(3, "Remark "+i);
        		preparedStatement.executeUpdate();
        		preparedStatement.close();
    		}
            
            String BlacklistedSQL = "INSERT INTO Blacklisted (User_Id) VALUES (?)";
            for (int i=91; i<101; i++) {
    			preparedStatement = (PreparedStatement) connect.prepareStatement(BlacklistedSQL);
        		preparedStatement.setInt(1, i);
        		preparedStatement.executeUpdate();
        		preparedStatement.close();
    		}
            
            String FavoriteUsersSQL = "INSERT INTO Favorite_Users (User_Id, Friend_Id) VALUES (?, ?)";
            for (int i=1; i<101; i++) {
    			preparedStatement = (PreparedStatement) connect.prepareStatement(FavoriteUsersSQL);
        		preparedStatement.setInt(1, i);
        		preparedStatement.setInt(2, 101-i);
        		preparedStatement.executeUpdate();
        		preparedStatement.close();
    		}
            
            String FavoriteJokesSQL = "INSERT INTO Favorite_Jokes (User_Id, Joke_Id) VALUES (?, ?)";
            for (int i=1; i<101; i++) {
    			preparedStatement = (PreparedStatement) connect.prepareStatement(FavoriteJokesSQL);
        		preparedStatement.setInt(1, i);
        		preparedStatement.setInt(2, 101-i);
        		preparedStatement.executeUpdate();
        		preparedStatement.close();
    		}
            
            String TagsSQL = "INSERT INTO Tags (Title) VALUES (?)";
            for (int i=1; i<101; i++) {
    			preparedStatement = (PreparedStatement) connect.prepareStatement(TagsSQL);
        		preparedStatement.setString(1, "tag"+i);
        		preparedStatement.executeUpdate();
        		preparedStatement.close();
    		}
            
            String JokesTagsSQL = "INSERT INTO Jokes_Tags (Joke_Id, Tag_Id) VALUES (?, ?)";
            for (int i=1; i<101; i++) {
    			preparedStatement = (PreparedStatement) connect.prepareStatement(JokesTagsSQL);
        		preparedStatement.setInt(1, i);
        		preparedStatement.setInt(2, 101-i);
        		preparedStatement.executeUpdate();
        		preparedStatement.close();
    		}

            databaseInitialized = true;
        } catch (Exception e) {
            System.out.println(e);
            databaseInitialized = false;
        } finally {

        }
        return databaseInitialized;
    }
    
    public boolean login(String username, String password) throws SQLException {
    	boolean loggedIn = false;
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ? AND User_Id NOT IN (SELECT User_Id FROM Blacklisted)";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
            loggedIn = true;
        }
         
        resultSet.close();
        preparedStatement.close();
         
        return loggedIn;
    }
    
    public boolean register(String firstName, String lastName, String email, int age, String gender, String username, String password) throws SQLException {
    	boolean rowInserted = false;
    	try {
    	
    	String sql = "INSERT INTO Users (Username, Password, First_Name, Last_Name, Email, Gender, Age) VALUES (?, ?, ?, ?, ?, ?, ?)";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, firstName);
        preparedStatement.setString(4, lastName);
        preparedStatement.setString(5, email);
        preparedStatement.setString(6, gender);
        preparedStatement.setInt(7, age);
         
        rowInserted = preparedStatement.executeUpdate() > 0;

        preparedStatement.close();
    	} catch (Exception e) {
            System.out.println(e);
            rowInserted = false;
        } finally {

        }
         
        return rowInserted;
    }
    
}
