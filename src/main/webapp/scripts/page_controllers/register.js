$(document).ready(function() {

	$("#error").hide();


	// Enregistrer un utilisateur
	$("#button_register").click(function() {
		var name = $("#name_register").val();
		var passwd = $("#password_register").val();
		var email = $("#email_register").val();
		var classe = $("input[name='classe']:checked").val();
		
		registerUser(name, passwd, email, classe);

		$("#name_register").val("");
		$("#password_register").val("");
		$("#email_register").val("");
	});

});

function registerUser(name, password, email, classe) {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v1/users/register",
		dataType : "json",
		data : JSON.stringify({
			"id" : 0,
			"name" : name,
			"password" : password, 
			"email" : email,
			"classe" : classe
		}),
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			if(data.success) {
				loginUser(name, password, "/");	
			} else {
				$("#error").empty();
				$("#error").append(data.message);
				$("#error").show();
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('Erreur lors de l\'enregistrement de votre compte : ' + textStatus);
		}
	});
}

