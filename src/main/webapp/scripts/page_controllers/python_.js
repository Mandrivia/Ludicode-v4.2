
var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};


var levelId = getUrlParameter('level');
var idList = getUrlParameter('list');
var date = new Date();
var timer = 0;
var editor = null;


$.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : "v1/savePython/"+levelId+ "/" +Cookies["id"]+"/" + idList,
        dataType:'text',
        success : function(data, textStatus, jqXHR) {
                $('#code').val(data);
                setUp();
        },
        error : function(data, jqXHR, textStatus, errorThrown) {
            console.log(data);
            alert('get error: ' + textStatus);
        }
    });


function setUp() {
    $('#mycanvas').show()

    var output = $('#edoutput');
    var outf = function (text) {
        output.html(output.text() + text);
    };
     
     var oute = function (text) {
        output.html(output.text() + "<span style=\"color: red\">"+text+"</span>");
    };

    var clear_console = function () {
        $('#edoutput').text('');
    };
    
    var keymap = {
        "Ctrl-Enter" : function (editor) {
            Sk.configure({output: outf, uncaughtException: oute, read: builtinRead});
            Sk.canvas = "mycanvas";
           $('#mycanvas').show()

            if (editor.getValue().indexOf('turtle') > -1 ) {
                $('#mycanvas').show()
            }
            Sk.pre = "edoutput";
            var canvas_el = (Sk.TurtleGraphics || (Sk.TurtleGraphics = {}))
            canvas_el.target = 'mycanvas';
            canvas_el.width = "1000";
            
            try {
                Sk.misceval.asyncToPromise(function() {
                    return Sk.importMainWithBody("<stdin>",false,editor.getValue(),true);
                }).then(function(mod) {},
		     function(err) {
		       oute(err.toString());
		   });
            } catch(e) {
                outf(e.toString() + "\n")
            }
        },
        "Shift-Enter": function (editor) {
            Sk.configure({output: outf, read: builtinRead});
            Sk.canvas = "mycanvas";
            Sk.pre = "edoutput";
             $('#mycanvas').show();
            if (editor.getValue().indexOf('turtle') > -1 ) {
                $('#mycanvas').show()
            }
            try {
                Sk.misceval.asyncToPromise(function() {
                    return Sk.importMainWithBody("<stdin>",false,editor.getValue(),true);
                }).then(function(mod) {},
		     function(err) {
		       oute(err.toString());
		   });
            } catch(e) {
                outf(e.toString() + "\n")
            }
        }
    }


     editor = CodeMirror.fromTextArea(document.getElementById('code'), {
        parserfile: ["parsepython.js"],
        autofocus: true,
        theme: "solarized dark",
        //path: "static/env/codemirror/js/",
        lineNumbers: true,
        textWrapping: false,
        indentUnit: 4,
        height: "160px",
        fontSize: "9pt",
        autoMatchParens: true,
        extraKeys: keymap,
        parserConfig: {'pythonVersion': 2, 'strictErrors': true}
    });

    $("#skulpt_run2").click(function (e) { 
    	clear_console();
    	keymap["Ctrl-Enter"](editor)
    } );

    $("#toggledocs").click(function (e) {
        $("#quickdocs").toggle();
    });


    $('#clearoutput').click(function (e) {
        $('#edoutput').text('');
        // $('#mycanvas').hide();
    });


    function builtinRead(x) {
        if (Sk.builtinFiles === undefined || Sk.builtinFiles["files"][x] === undefined)
            throw "File not found: '" + x + "'";
        return Sk.builtinFiles["files"][x];
    }

    editor.focus();

}

function submit_python() {
	// if(date.getTime() < timer+5000)
	// 	return;
	// else
	// 	timer = date.getTime();

	// var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
	// 	// lineNumbers: true,
	// 	// mode: "text/x-csharp",
	// 	matchBrackets: true
	// });
	// console.log(editor.getValue());



	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v1/savePython/"+Cookies["id"]+"/"+levelId+ "/" + idList + "/"+encodeURIComponent(editor.getValue()),
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

$(document).ready(function() {

	/******************************
	 **** REQUETES AJAX LEVELS ****
	 ******************************/
	
	function createLevelPagination() {
		var nav = $("#levels_pagination");
		nav.html("");
		for(var i = 0 ; i < levelList.levelsAssociation.length ; i++) {
			var activeClass = (i == levelId ? "active" : "");
			console.log(i);
			console.log(levelId);
			nav.append('<li class="' + activeClass + '">'
						+'<a href="python.html?list=' + idList + '&level=' + i +'">' + (i+1) + '</a>'
						+'</li>');
		}
	}
	
	function createLevelTitle(data) {
		var listTitle = data.levelList == undefined ? "" : data.levelList.name + " : ";
		$("#level_title").html(listTitle + data.name);
	}

	function handleLevel(data) {
		window.levelData = data;
		console.log(data);
		levelList = data.levelList;
		
		$("#max_instruction").html(data.maxInstructions);
		createLevelTitle(data);
		if(levelList != null)
			createLevelPagination();
		
		$("#python-instruction").html(data.instructions);
	}
	
	
	// charge le niveau d'id "levelId"
	function loadLevel(levelId) {
		$.getJSON("v1/levels/" + levelId, function(data) {
			handleLevel(data);
		});
	}
	
	
	// charge le niveau numéro "position" dans la liste d'id "idList"
	function loadLevelInList(position, idList) {
		$.getJSON("v1/levels/py/list/" + idList + "/level/" + position, function(data) {
			handleLevel(data);
		});
	}
	
	
	if( levelId != null) {
		if(idList == null) {
			loadLevel(currentLevel);
		} else {
			loadLevelInList(levelId, idList);
		}
	} else {
		location.replace("levels.html")
	}

	/*$("#skip_level").click(function() {
		if(nextLevelId > 0){
			goToNextLevel();
		} else {
			// Afficher une fenêtre de félicitation ?
		}
	});*/
	
	$("#nextLevelButton").click(function() {
		goToNextLevel();
	});
	
	$("#levelPageButton").click(function() {
		location.href = "levels.html";
	});


});
