var fileuploadaction = '';
var imagetag;
function openUploadDialog(action, image){
	fileuploadaction=action;
	imagetag = image;
	$('.uploadfiledialog').dialog('open');
}
$(function(){
	$('.uploadfiledialog').dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		buttons: {
			Cancel: function() {
				$(this).dialog('close');
			},
			'Select': function() {

				$('#fileUploadFrame').contents().find('form').submit();
				$('#fileUploadFrame').unbind().load(function(){
					var file = $('#fileUploadFrame').contents().find('div#error').text();
					if(file && file != '')
						alert(file);
					$(this).unbind();
					imagetag.attr('src', imagetag.attr('src')+'?tmp='+Math.random());
				});

				$(this).dialog('close');
			}
		},
		open: function() {
			$(this).empty();
			$(this).append('<iframe id="fileUploadFrame" style="border: 0 none;" width="250" height="100" src="'+fileuploadaction+'"></iframe>');
		},
		close: function(){
			$('#file').val('');
		}
	});//file dialog
});