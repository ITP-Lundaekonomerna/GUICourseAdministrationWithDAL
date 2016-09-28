package app.dal;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import app.model.Student;

/**
 * @author baltzarmattsson
 * 2016-09-28
 * 
 * Data Access Layer for the Student table
 */


public class DALStudent {

	private final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String URL = "jdbc:sqlserver://localhost:1433;database=DATABASENAME;";
	private String USER = "USERNAME";
	private String PASS = "PASSWORD";
	
	public DALStudent() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Student> findAllStudents() {
		
		String query = "select * from Student";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Student> studentsFromDatabase = null;
		
		try {
			/** this.getConnection() is a method in this class. You can do whatever this method does,
			 *  but then you'll have to write more code
			 **/
			con = this.getConnection();
			ps = con.prepareStatement(query);
			
			/** When we issue an executeQuery, it will return a resultset
			 *	which is the result of the query, in this case this is all students
			 *  and all columns of the table if there's any data and we've done everything correctly
			 **/
			rs = ps.executeQuery();
			
			/**
			 *  Getting the values of each row, putting them in a list
			 *  to later be returned to whoever called this method
			 */
			studentsFromDatabase = new ArrayList<Student>();
			while (rs.next()) {
				/**
				 * Now we're creating a student for each row retrieved. We put the column name from the database
				 * in our rs.getString("columnName") to get the value of the row and column. If your column names
				 * differ from the ones below, change them.
				 * 
				 * The reason we're making a student for each result is because Java is object-oriented, and it's
				 * easier to maintain a Student-object compared to four separate strings, since we can use corresponding get-methods to retrieve the value
				 */
				Student s = new Student(rs.getString("pnbr"), rs.getString("name"), rs.getString("address"), rs.getString("phoneNbr"));
				
				/**
				 * Adding the newly created Student s object to our ArrayList
				 */
				studentsFromDatabase.add(s);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
				if (rs != null) rs.close();
			} catch (SQLException e) {
			}
		}
		
		/**
		 * When we've come this far, the list of students are either empty, null or full of students,
		 * which means you must use a nullcheck when using it in other methods
		 */
		return studentsFromDatabase;
		
	}
	
	/** Can also be void, int = number of rows affected in the database when the query is executed **/
	public int addStudent(Student s) {
		
		String query = "insert into Student values (?, ?, ?, ?)";
		
		Connection con = null;
		PreparedStatement ps = null;
		int numberOfRowsAffected = 0;
		
		try {
			/** this.getConnection() is a method in this class (see bottom of class). 
			 *  You can do what this method does here but then you'll have to write more code
			 **/
			con = this.getConnection();
			ps = con.prepareStatement(query);
			
			/** On each questionmark, we put the desired values, resulting in 's around the values
			 *	making it harder to SQL inject and such things.
			 *
			 *	The student, or "s" in this case, is the Student we're given when someone uses the method, i.e. input parameter
			 **/
			ps.setString(1, s.getPnbr());
			ps.setString(2, s.getName());
			ps.setString(3, s.getAddress());
			ps.setString(4, s.getPhoneNbr());
			
			/** When we issue an executeUpdate, it will return an int
			 *	which is the number of rows affected in the database,
			 *	which can be used for displaying success to user for example.
			 *	We use executeUpdate because we don't want a result, only an insert/update/delete 
			 **/
			numberOfRowsAffected = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {
			}
		}
		
		return numberOfRowsAffected;
		
	}
	
	
	public Student findStudentByStudentID(String studentID) {
		
		String query = "select * from Student where pnbr = ?";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Student studentFromDatabase = null;

		try {
			con = this.getConnection();
			ps = con.prepareStatement(query);
			ps.setString(1, studentID);
			rs = ps.executeQuery();
		
			// Since the query should only return one row (pnbr is Primary Key), we only have one student to create
			while (rs.next()) {
				studentFromDatabase = new Student(rs.getString("pnbr"), rs.getString("name"), rs.getString("address"), rs.getString("phoneNbr"));
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) ps.close();
				if (con != null) con.close();
				if (rs != null) rs.close();
			} catch (SQLException e) {
			}
		}
		
		return studentFromDatabase;
		
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}

}