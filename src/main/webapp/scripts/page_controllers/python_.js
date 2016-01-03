var levelId = 0; // TMP


function submit_python() {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v1/savePython/"+Cookies["id"]+"/"+levelId+"/"+encodeURIComponent(document.getElementById('code').value),
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			if (data.success) {
				console.log("Python sauvegarde !");
			} else {
				console.log(data.message);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('postUser error: ' + textStatus);
		}
	});
}