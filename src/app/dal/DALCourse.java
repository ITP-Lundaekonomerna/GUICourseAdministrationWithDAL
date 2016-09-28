package app.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.model.Row;

/**
 * @author baltzarmattsson
 * 2016-09-28
 *
 * Data Access Layer for the Course table
 */

public class DALCourse {

	private final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String URL = "jdbc:sqlserver://localhost:1433;database=DATABASENAME;";
	private String USER = "USERNAME";
	private String PASS = "PASSWORD";
	
	public DALCourse() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Finds all courses associated with the Student ID, both studied and studying
	 */
	
	public ArrayList<Row> findCoursesByStudentID(String studentID) {
		
		String query = "";
		
		/** 	Getting currently reading courses 	**/
		
		query = "select c.*, 'Currently studying' as Status from Course c "
				+ "inner join Studying sing on sing.code = c.code "
				+ "inner join Student s on s.pnbr = sing.pnbr "
				+ "where s.pnr = ?";
		
		query += " union all ";
		
		/** 	Getting previously studied courses 	**/
		query += "select c.*, concat('Grade ', sied.grade) from Course c "
				+ "inner join Studied sied on sied.code = c.code "
				+ "inner join Student s on s.pnbr = sied.pnbr"
				+ "where s.pnr = ?";
		
		ArrayList<Row> valuesFromDatabase = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			/**
			 * this.getConnection() is a method in this class (see bottom of
			 * class). You can do what this method does here but then you'll
			 * have to write it every time. Plus, if you want to change it you 
			 * change on one location instead of many.
			 **/

			con = this.getConnection();
			ps = con.prepareStatement(query);
			
			/** Adding student ID to the two question marks we have in the query**/
			ps.setString(1, studentID);
			ps.setString(2, studentID);
			
			/** Executing query **/
			
			rs = ps.executeQuery();
			
			/**
			 * Right now, our ResultSet rs holds the result of the query, which can be
			 * empty or full, depending on the data stored.
			 * 
			 * We want to store this data in an object for easier handling, thus using the Row-class (see app.model.Row)
			 */
			
			valuesFromDatabase = new ArrayList<Row>();
			
			/** Creating an array which will contain our attributes **/
			String[] attributeArray = new String[10];
			
			while (rs.next()) {
				
				/**
				 * Looping through the number of columns contained in the metadata-set.
				 * Note that the columnIndex starts at 1 since SQL is 1 based index (instead of 0-based as in java)
				 * 
				 * The ..&& columnIndex <= 10  is there to not go above 10 attributes, if say the row has 46 columns, since we have
				 * limited the attributes to only 10 for now, hence, both conditions in the for-loop must be true, and it breaks
				 * if the number of columns has reached it's end, or when 10 columns has been read, whatever happens first.
				 */
				for (int columnIndex = 1; (columnIndex <= rs.getMetaData().getColumnCount() && columnIndex <= 10); columnIndex++) {
					
					/**
					 *  We remove 1 because Java is 0-based index, but we'd still like to loop through them at the same time.
					 *  This means that the first item of columnIndex will be put as the first item in the attributeArray:
					 *  i.e. column[1] = attributeArray[0], column[2] = attributeArray[1], ..., column[n] = attributeArray[n-1]
					 */
					int attributeArrayIndex = columnIndex - 1; 
					
					/**
					 *  Since the values can be null in the database (a Student address could be NULL), 
					 *  we must null-check them and put an empty string to avoid nullpointerexceptions further on
					 */
					if (rs.getString(columnIndex) == null) {
						attributeArray[attributeArrayIndex] = "";
					/**
					 * If the value is NOT null, we can add it as usual.
					 */
					} else {
						attributeArray[attributeArrayIndex] = rs.getString(columnIndex);
					}
					
				}
				
				/**
				 *  We're now done with one row, and will now add that as a Row-object to our ArrayList
				 */
				Row row = new Row(attributeArray[0], attributeArray[1], attributeArray[2], attributeArray[3], attributeArray[4], attributeArray[5], attributeArray[6], attributeArray[7], attributeArray[8], attributeArray[9]);
				valuesFromDatabase.add(row);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				/**
				 * We're closing the resultset, preparedstatement and connection, in that order,
				 * to be sure that everything is closed. If we close the connection first, we cannot access
				 * the other two items (ps and rs) since they are depended on the connection. But if the closing 
				 * of connection fails the rs and ps could remain unclosed, which we do not want. The Catch-block is empty
				 * since we don't care about those potential exceptions
				 */
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				// empty
			}
		}
		
		/**
		 * Finally, we're returning our list which we have filled with rows from the database.
		 * Be sure to use null/empty-checks further on, since this can be empty and null
		 */
		return valuesFromDatabase;
		
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
	
}
