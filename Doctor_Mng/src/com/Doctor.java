package com;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;


public class Doctor {

	private Connection connect(){
		
	Connection con = null;
			try
				{
				Class.forName("com.mysql.jdbc.Driver");
				//Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/doctor_mng","root","Ashi@2020");
				}
			catch (Exception e){
				e.printStackTrace();
			}
	return con;
	
	}
	
	//insert-----------------------------------------------------------------------------------------------------------------------------
	
	public String insertDoctor(String name, String phone, String email, String special, String hospital, String date, String status)
    {
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{    
					return "Error while connecting to the database for inserting.";
				}
				// create a prepared statement
				String query = "insert into doctors"
						+"(`docID`,`dName`,`dPhone`,`dEmail`,`dSpecial`,`dHospital`,`dDate`,`dStatus`)"
						+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
				
				SimpleDateFormat test1 = new SimpleDateFormat("yyyy-MM-dd");
				 java.util.Date jdate = test1.parse(date);
				 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, phone);
				preparedStmt.setString(4, email);
				preparedStmt.setString(5, special);
				preparedStmt.setString(6, hospital);
				preparedStmt.setObject(7, jdate.toInstant().atZone(ZoneId.of("Africa/Tunis")).toLocalDate());
				//preparedStmt.setString(7, date);
				preparedStmt.setString(8, status); 
				
	
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String newItems = readItems();
				output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
			}
				catch (Exception e)
			{
					output = "{\"status\":\"error\", \"data\": \"Error while inserting.\"}";
					System.err.println(e.getMessage());
			}
			return output;
    		}
	
	
	//view------------------------------------------------------------------------------------------------------------------------
	public String readItems(){
		
		String output = "";
			try{
			Connection con = connect();
			
			if (con == null){
				return "Error while connecting to the database for reading."; 
			}
			// Prepare the html table to be displayed
			output = "<table border='1'>"
					+ "<tr><th>Doctor Name</th>"
					+ "<th>Phone No</th>"
					+ "<th>Email</th>"
					+ "<th>Specialization</th>"
					+ "<th>Hospital</th>"
					+ "<th>Date</th>"
					+ "<th>Status</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></tr>";
			
			String query = "select * from doctors";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()){
				
				String docID = Integer.toString(rs.getInt("docID"));
				String dName = rs.getString("dName");
				String dPhone = rs.getString("dPhone");
				String dEmail = rs.getString("dEmail");
				String dSpecial = rs.getString("dSpecial");
				String dHospital = rs.getString("dHospital");
				java.util.Date dDate = rs.getDate("dDate");
				//String dDate = rs.getString("dDate");
				String dStatus = rs.getString("dStatus");
				

				// Add into the html table
				output += "<tr><td><input id='hidItemIDUpdate'name='hidItemIDUpdate' type='hidden' value='" + docID+ "'>" + dName + "</td>";
				output += "<td>" + dPhone + "</td>";
				output += "<td>" + dEmail + "</td>";
				output += "<td>" + dSpecial + "</td>";
				output += "<td>" + dHospital + "</td>";
				output += "<td>" + dDate + "</td>";
				output += "<td>" + dStatus + "</td>";
				
				
				
				// buttons
				
				output += "<td><input name='btnUpdate'type='button' "
						+ "value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' "
						+ "value='Remove'class='btnRemove btn btn-danger'data-docid='"+ docID + "'>" + "</td></tr>";
			}
			
			con.close();
			// Complete the html table
			output += "</table>";
			}
			catch (Exception e){
				output = "Error while reading the doctor details.";
				System.err.println(e.getMessage());
			}
			
	return output;
	
	}
	
	
	//update----------------------------------------------------------------------------------------------------------------
	public String updateDoctor(String ID, String name, String phone, String email, String special, String hospital, String date, String status) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE doctors SET dName=?,dPhone=?,dEmail=?,dSpecial=?,dHospital=?,dDate=?,dStatus=? WHERE docID=?";
			
			SimpleDateFormat test1 = new SimpleDateFormat("yyyy-MM-dd");
			 java.util.Date jdate = test1.parse(date);
			 
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, phone);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, special);
			preparedStmt.setString(5, hospital);
			preparedStmt.setObject(6, jdate.toInstant().atZone(ZoneId.of("Africa/Tunis")).toLocalDate());
			//preparedStmt.setString(6, date);
			preparedStmt.setString(7, status);
			preparedStmt.setInt(8, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";;
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	//delete-------------------------------------------------------------------------------------------------------------------
	public String deleteDoctor(String docID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {

				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from doctors where docID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(docID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
}

