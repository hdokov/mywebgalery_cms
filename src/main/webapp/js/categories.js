var fileuploadaction = '';
var imagetag;
function openUploadDialog(action, image){
	fileuploadaction=action;
	imagetag = image;
	$('.uploadfiledialog').dialog('open');
}
$(function(){

	$("a.new").click(function(){
		$('.catid').val('');
		$('.catparentid').val('');
		$('.catname').val('');
		$('.edit_category_dialog').dialog('open');
		return false;
	});

	$('.edit_category_dialog').dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 420,
		buttons: {
			Cancel: function() {
				$(this).dialog('close');
			},
			'Submit': function() {
				$('.edit_category_dialog form').submit();
			}
		},
		open: function() {
		},
		close: function(){
		}
	});//edit_category_dialog
});