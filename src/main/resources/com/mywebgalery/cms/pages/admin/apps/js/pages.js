$(function(){

	$('.select_category_dialog').dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		buttons: {
			Cancel: function() {
				$(this).dialog('close');
			}
		},
		open: function() {
		},
		close: function(){
		}
	});//edit_category_dialog

	$('.select_category').focus(function(){
		$('.select_category_dialog').dialog('open');
	});
	$('.select_category').keypress(function(){return false;});
	$("a.category").click(function(){
		var cat = $(this)
		$('.select_category').val(cat.text());
		$('.categoryid').val(cat.attr('href'));
		$('.select_category_dialog').dialog('close');
		return false;
	});

});