import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JokesDAO jokesDAO;
    HttpSession session;
 
    public void init() {
        jokesDAO = new JokesDAO(); 
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	session = request.getSession();
        String action = request.getServletPath();
        System.out.println(action);
        try {
            switch (action) {
            case "/home":
            	showHome(request, response);
                break;
            case "/login":
            	checkLogin(request, response);
                break;
            case "/register-user":
            	registerUser(request, response);
                break;
            case "/logout":
            	logout(request, response);
                break;
            case "/initdb":
            	initDB(request, response);
                break;
            default:          	
            	showHome(request, response);           	
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String firstName = request.getParameter("first_name");
    	String lastName = request.getParameter("last_name");
    	String email = request.getParameter("email");
    	int age = Integer.parseInt(request.getParameter("age"));
    	String gender = request.getParameter("gender");
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	
    	RequestDispatcher dispatcher;
    	
    	if (jokesDAO.register(firstName, lastName, email, age, gender, username, password)) {
    		session.setAttribute("loggedIn", true);
    	} else {
    		session.removeAttribute("loggedIn");
    	}
    	
        dispatcher = request.getRequestDispatcher("index.jsp");   
        dispatcher.forward(request, response);
    }
    
    private void checkLogin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	RequestDispatcher dispatcher;
    	
    	if (jokesDAO.login(username, password)) {
    		session.setAttribute("loggedIn", true);
    	} else {
    		session.removeAttribute("loggedIn");
    	}
    	
        dispatcher = request.getRequestDispatcher("index.jsp");   
        dispatcher.forward(request, response);
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
    	RequestDispatcher dispatcher;
        session.removeAttribute("loggedIn");
        dispatcher = request.getRequestDispatcher("index.jsp");   
        dispatcher.forward(request, response);
    }
    
    private void showHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    
    private void initDB(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	boolean databaseInitialized = jokesDAO.initializeDatabase();
    	request.setAttribute("databaseInitialized", Boolean.toString(databaseInitialized));
        RequestDispatcher dispatcher = request.getRequestDispatcher("initdb.jsp");
        dispatcher.forward(request, response);
    }
    
    
}