$(document).ready(function()
{
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateDoctorForm();
	
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var method = ($("#hidDocIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "DoctorsAPI",
		type : method,
		data : $("#formDoctor").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onDocSaveComplete(response.responseText, status);
		}
	});
});

function onDocSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divDoctorGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidDocIDSave").val("");
	$("#formDoctor")[0].reset();
}


//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidDocIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val());
	$("#dName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#dPhone").val($(this).closest("tr").find('td:eq(1)').text());
	$("#dEmail").val($(this).closest("tr").find('td:eq(2)').text());
	$("#dSpecial").val($(this).closest("tr").find('td:eq(3)').text());
	$("#dHospital").val($(this).closest("tr").find('td:eq(4)').text());
	$("#dDate").val($(this).closest("tr").find('td:eq(5)').text());
	$("#dStatus").val($(this).closest("tr").find('td:eq(6)').text());
});


//REMOVE==========================================
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
		url : "DoctorsAPI",
		type : "DELETE",
		data : "docID=" + $(this).data("docid"),
		dataType : "text",
		complete : function(response, status)
		{
			onDocDeleteComplete(response.responseText, status);
		}
	});
});

function onDocDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divDoctorGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


function validateDoctorForm()
{
	// Name
	if ($("#dName").val().trim() == "")
	{
		return "Insert Doctor's name.";
	}
	
	// Phone no
	if ($("#dPhone").val().trim() == "")
	{
		return "Insert phone no.";
	}
	
	//Email-------------------------------
	if ($("#dEmail").val().trim() == "")
	{
		return "Insert Email.";
	}
	
	//Specialization-------------------------------
	if ($("#dSpecial").val().trim() == "")
	{
		return "Insert Specialization.";
	}
	
	// Hospital------------------------
	if ($("#dHospital").val().trim() == "")
	{
		return "Insert Hospital.";
	}
	
	//Date-------------------------------
	if ($("#dDate").val().trim() == "")
	{
		return "Insert Date.";
	}
	
	//Status-------------------------------
	if ($("#dStatus").val().trim() == "")
	{
		return "Insert Status.";
	}
	
	return true;
}