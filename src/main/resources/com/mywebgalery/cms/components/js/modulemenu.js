$(function(){

	$('.select_menu_dialog').dialog({
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

	$('.select_menu').focus(function(){
		$('.select_menu_dialog').dialog('open');
	});
	$('.select_menu').keypress(function(){return false;});
	$("a.menu").click(function(){
		var menu = $(this)
		$('.select_menu').val(menu.text());
		$('.menuid').val(menu.attr('href'));
		$('.select_menu_dialog').dialog('close');
		return false;
	});

});