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

	$("a.editcategory").click(function(){
		var cat = $(this)
		$('.catid').val(cat.attr('href'));
		$('.catparentid').val(cat.attr('parent'));
		$('.catname').val(cat.text());
		$('.edit_category_dialog').dialog('open');
		return false;
	});

	$("a.add_subcategory").click(function(){
		var cat = $(this)
		$('.catid').val('');
		$('.catparentid').val(cat.attr('href'));
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
				if(!$('.catname').val() || $('.catname').val() == ''){
					alert("Name cannot be blank.");
					return;
				}
				$('.edit_category_dialog form').submit();
			}
		},
		open: function() {
		},
		close: function(){
		}
	});//edit_category_dialog
});