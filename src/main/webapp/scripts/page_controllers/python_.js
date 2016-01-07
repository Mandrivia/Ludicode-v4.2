var levelId = 0; // TMP

var date = new Date();
var timer = 0;
var editor = null;

$(document).ready(function () {
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


});

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
		url : "v1/savePython/"+Cookies["id"]+"/"+levelId+"/"+encodeURIComponent(editor.getValue()),
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