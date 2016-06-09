$(document).ready(function() {
	registerSearch();
	registerThemeInput();
});

function split( val ) {
	return val.split( /,\s*/);
}
function extractLast( term ) {
	return split( term ).pop();
}
function arrayToString( arr, n){
	var sliced = arr.slice(0, n);
	if(sliced.length != arr.length){
		return String(sliced)+ "...";
	}
	else{
		return String(sliced);
	}
}

function registerSearch() {
	$("#analyze").submit(function(ev){
		event.preventDefault();
		// Split themeInput and clear empty fields like "", " "... etc
		var themeList = split($("#themeInput").val()).filter(function(val){return val.trim()!=""});
		
		$.ajax({
		   type: 'POST',
		   url: $(this).attr('action'),
		   data: {book: $("#bookID").val(), themes: themeList}, 
		   success: function(res,status,XHR) { 
			   var location = XHR.getResponseHeader('Location');	
			   getAnalysis(location);
		   }
		});
	});
}

// This function gets called until status equals to "FINISHED"
function getAnalysis(location) {
	$.ajax({
		type: "GET",
		url: location,
		success: function(data) {
			var parsedData = JSON.parse(data);
			
			var template = $('#resultTpl').html();
			var rendered = Mustache.to_html(template, parsedData)
			$("#resultsBlock").empty().append(rendered);
			
			if(parsedData.status != "FINISHED"){
				setTimeout(getAnalysis, 1000, location);
			}
		}
	});
}

// Edited from: http://arrea-systems.com/add_ajax_multiple_autocomplete_to_textarea
function registerThemeInput(){ 
	$("#themeInput" )
	.bind( "keydown", function( event ) {
		if ( event.keyCode === jQuery.ui.keyCode.TAB &&
				jQuery( this ).data( "ui-autocomplete" ).menu.active ) {
			event.preventDefault();
		}
	})
	.autocomplete({
		source: function( request, response ) {
			jQuery.getJSON("users/0/themes/", {
				like: extractLast( request.term )
			}, function (data) {
	            response($.map(data, function (theme) {
	                return {
	                    label: theme.title + "(" + theme.id + "): " + arrayToString(theme.tokens,5),
	                    value: theme.title
	                };
	            })); 
			});
		},
		search: function() {
			// custom minLength
			var term = extractLast( this.value );
			if ( term.length < 1 ) {
				return false;
			}
		},
		focus: function() {
			// prevent value inserted on focus
			return false;
		},
		select: function( event, ui ) {
			var terms = split( this.value );
			// remove the current input
			terms.pop();
			// add the selected item
			terms.push( ui.item.value );
			// add placeholder to get the comma-and-space at the end - used for multi select
			terms.push( "" );
			this.value = terms.join( ", " );

			return false;
		}
	});
}
