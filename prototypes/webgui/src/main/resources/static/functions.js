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

function registerSearch() {
	$("#analyze").submit(function(ev){
		event.preventDefault();
		// Split themeInput and clear empty fields like "", " "... etc
		var themeList = split($("#themeInput").val()).filter(function(val){return val.trim()!=""});
		$.get($(this).attr('action'), {book: $("#bookID").val(), themes: themeList}, function(data) {
			var template = $('#resultTpl').html();
			var rendered = Mustache.to_html(template, JSON.parse(data))
			$("#resultsBlock").empty().append(rendered);
		});	
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
			jQuery.getJSON("themes/", {
				like: extractLast( request.term )
			}, response );
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
