package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.controller.Controller;
import app.dal.DALCourse;
import app.model.Row;
import app.model.Student;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author baltzarmattsson
 * 2016-09-28
 *
 **/

public class CustomerApplication {

	private JFrame mainFrame;
	private Controller controller;
	private JTextField studentPnbrTextField;
	
	private JLabel studentPnbrLabel;
	private JLabel studentNameLabel;
	private JLabel studentAddressLabel;	
	private JLabel studentPhoneNbrLabel;
	private JTable table;
	
	private DefaultTableModel studentDefaultTableModel;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerApplication window = new CustomerApplication();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CustomerApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 700, 550);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 245, 688, 277);
		mainFrame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		studentPnbrTextField = new JTextField();
		studentPnbrTextField.setBounds(6, 20, 130, 26);
		mainFrame.getContentPane().add(studentPnbrTextField);
		studentPnbrTextField.setColumns(10);
		
		JLabel lblPnbr = new JLabel("PNBR");
		lblPnbr.setBounds(6, 81, 61, 16);
		mainFrame.getContentPane().add(lblPnbr);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(6, 101, 61, 16);
		mainFrame.getContentPane().add(lblName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(6, 121, 61, 16);
		mainFrame.getContentPane().add(lblAddress);
		
		JLabel lblPhonenbr = new JLabel("PhoneNbr");
		lblPhonenbr.setBounds(6, 142, 61, 16);
		mainFrame.getContentPane().add(lblPhonenbr);
		
		studentPnbrLabel = new JLabel("");
		studentPnbrLabel.setBounds(93, 81, 119, 16);
		mainFrame.getContentPane().add(studentPnbrLabel);
		
		studentNameLabel = new JLabel("");
		studentNameLabel.setBounds(93, 101, 119, 16);
		mainFrame.getContentPane().add(studentNameLabel);
		
		studentAddressLabel = new JLabel("");
		studentAddressLabel.setBounds(93, 121, 119, 16);
		mainFrame.getContentPane().add(studentAddressLabel);
		
		studentPhoneNbrLabel = new JLabel("");
		studentPhoneNbrLabel.setBounds(93, 142, 119, 16);
		mainFrame.getContentPane().add(studentPhoneNbrLabel);
		
		
		
		/** 	@IMPORTANT!
		 * 
		 *  Adding a DefaultTableModel to our JTable.
		 * 	The DefaultTableModel is where you further add rows and columns.
		 *  **/
		
		/** Setting the titles/columns of the DefaultTableModel	**/
		Object[] columnTitles = { "Column 1", "Column 2", "Column 3", "Column 4" };
		studentDefaultTableModel = new DefaultTableModel(columnTitles, 0);
		
		/** Adding the DefaultTableModel to our table **/
		table.setModel(studentDefaultTableModel);
		
		
		/** 	Button handling for searching of student		**/
		
		JButton btnSk = new JButton("Search Student (pnbr)");
		btnSk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/**
				 * Since the textfield can be empty, we must be assured that it's not 
				 * before moving on
				 **/
				if (studentPnbrTextField.getText().isEmpty() == false) {
					
					String studentID = studentPnbrTextField.getText();
					
					Student studentFromDatabase = null;
					studentFromDatabase = controller.findStudentByStudentID(studentID);
					
					/**
					 * Since the result may not have found any data/students, we must null check it
					 */
					if (studentFromDatabase != null) {
						
						/**
						 * Setting the labels accordingly. This where having Student as an object
						 * instead of separate strings is great:
						 */
						studentPnbrLabel.setText(studentFromDatabase.getPnbr());
						studentNameLabel.setText(studentFromDatabase.getName());
						studentAddressLabel.setText(studentFromDatabase.getAddress());
						studentPhoneNbrLabel.setText(studentFromDatabase.getPhoneNbr());
						
						/**
						 * Resetting the rows in the table
						 */
						studentDefaultTableModel.setRowCount(0);
						
						/**
						 * 
						 * When we've come this far, we have made sure that a student exists (studentFromDatabase != null)
						 * and now we'd like to see the courses currently studying, or previously studied.
						 */
						
						ArrayList<Row> courseRelations = null;
						courseRelations = controller.findAllCoursesByStudentID(studentID);
						
						/**
						 * This can also be empty, or null, so check for that as well
						 */
						if (courseRelations != null && courseRelations.size() > 0) {
							
							/**
							 * Note, if you only have 2 columns, but 4 attributes in the row, only the first 2 attributes will be shown,
							 * So be sure to set the correct amount of columns for each query.
							 * The correct number in this case is the number of columns in Course, plus one which we call "Status" in the query,
							 * that shows either "Currently reading" or "Grade N".
							 * Result: 4 columns: Coursecode, course name, points, and status.
							 * 
							 * If you only have one table per window (i.e. one table for Student, one for Course, and so on),
							 * it's not necessary to reset the columns, they can stay the same throughout the program and you can set these in the
							 * beginning of the program. 
							 * But if you'd like to, this is how:
							 */
							
							// Removes the columns currently in the table
							studentDefaultTableModel.setColumnCount(0);
							
							// Adding columns:
							studentDefaultTableModel.addColumn("Course code");
							studentDefaultTableModel.addColumn("Course name");
							studentDefaultTableModel.addColumn("Course points");
							studentDefaultTableModel.addColumn("Status");
							
							
							/**
							 * Right now, we have courses in the courseRelations list since we've passed the last if()-statement, meaning that the Student is either studying something,
							 * or has studied before, and we'll show this in our table, by adding a row to the table for each Row in the fetched courseRelations list:
							 */
							for (Row row : courseRelations) {
//								System.out.println(row.getAttribute1() + " " + row.getAttribute2() + " " + row.getAttribute3() + " " + row.getAttribute4());
								String[] rowData = { row.getAttribute1(), row.getAttribute2(), row.getAttribute3(), row.getAttribute4()};
								studentDefaultTableModel.addRow(rowData);
							}
							
							/**
							 * Data has been added to the studentDefaultTableModel, nothing more needs to be done.
							 */
							
						} else {
							/**
							 * Optional handling, no course relations found
							 */
						}	
					} else {
						
						/*
						 * The student wasn't found in the database, clearing information in the application
						 */
						
						// Resetting the labels and table model
						studentPnbrLabel.setText("");
						studentNameLabel.setText("");
						studentAddressLabel.setText("");
						studentPhoneNbrLabel.setText("");

						studentDefaultTableModel.setRowCount(0);
					}
					
				} else {
					
					// Resetting the labels and tabl emodel
					studentPnbrLabel.setText("");
					studentNameLabel.setText("");
					studentAddressLabel.setText("");
					studentPhoneNbrLabel.setText("");
					
					studentDefaultTableModel.setRowCount(0);
					
					/**
					 * Optional handling, the textfield is empty, maybe do something to inform the user?
					 */
				}
				
			}
		});
		btnSk.setBounds(148, 20, 185, 29);
		mainFrame.getContentPane().add(btnSk);
		
		/** Creating a controller-instance with our frame as input **/
		controller = new Controller();

	}
}
