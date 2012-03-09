$(function(){

	$("a.new").click(function(){
		$('.menuid').val('');
		$('.menuparentid').val('');
		$('.menuname').val('');
		$('.edit_menu_dialog').attr('title', $(this).text()).siblings().children('.ui-dialog-title').text($(this).text());
		$('.edit_menu_dialog').dialog('open');
		return false;
	});

	$("a.editmenu").click(function(){
		var menu = $(this)
		$('.menuid').val(menu.attr('href'));
		$('.menuparentid').val(menu.attr('parent'));
		$('.menuname').val(menu.text());
		$('select.page option[value='+menu.attr('page')+']').attr('selected', 'selected');
		$('.edit_menu_dialog').attr('title', $(this).text()).siblings().children('.ui-dialog-title').text($(this).text());
		$('.edit_menu_dialog').dialog('open');
		return false;
	});

	$("a.add_submenu").click(function(){
		var menu = $(this)
		$('.menuid').val('');
		$('.menuparentid').val(menu.attr('href'));
		$('.menuname').val('');
		$('.edit_menu_dialog').attr('title', $(this).text()).siblings().children('.ui-dialog-title').text($(this).text());
		$('.edit_menu_dialog').dialog('open');
		return false;
	});

	$('.edit_menu_dialog').dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 420,
		buttons: {
			Cancel: function() {
				$(this).dialog('close');
			},
			'Submit': function() {
				if(!$('.menuname').val() || $('.menuname').val() == ''){
					alert("Name cannot be blank.");
					return;
				}
				$('.edit_menu_dialog form').submit();
			}
		},
		open: function() {
		},
		close: function(){
		}
	});//edit_menu_dialog
});