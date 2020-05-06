<%@page import="com.Doctor"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Doctor Profiles Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Doctors.js"></script>

</head>
<body>
<div class="container">
	<div class="row" >
		<div class="col-6">
			<h1>Doctor Profiles Management</h1>
			
			<form id="formDoctor" name="formDoctor" method="post" action="Doctors.jsp">

				Doctor Name:
				<input id="dName" name="dName" type="text" class="form-control form-control-sm">
				<br>
				 
				Phone No:
				<input id="dPhone" name="dPhone" type="text" class="form-control form-control-sm">
				<br>
				
				Email:
				<input id="dEmail" name="dEmail" type="text" class="form-control form-control-sm">
				<br>
				
				Specialization:
				<input id="dSpecial" name="dSpecial" type="text" class="form-control form-control-sm">
				<br>
				
				Hospital:
				<input id="dHospital" name="dHospital" type="text" class="form-control form-control-sm">
				<br>
				
				Date:
				<input id="dDate" name="dDate" type="text" class="form-control form-control-sm">
				<br>
				 
				Status:
				<input id="dStatus" name="dStatus" type="text" class="form-control form-control-sm">
				<br>
				
				<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
				<input type="hidden" id="hidDocIDSave" name="hidDocIDSave" value="">
			</form>
			
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
			<br>

			<div id="divDoctorGrid">
				<%
					Doctor docObj = new Doctor();
					out.print(docObj.readItems());
				%>
			</div>
		</div>
	</div>
</div>
</body>
</html>