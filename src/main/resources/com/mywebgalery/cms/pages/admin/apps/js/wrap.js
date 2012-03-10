$(function(){
	$('.select_template_dialog').dialog({
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

	$('.select_theme_dialog').dialog({
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

	$('.select_template').focus(function(){
		$('.select_template_dialog').dialog('open');
	});
	$('.select_template').keypress(function(){return false;});
	$('.template').click(function(){
		$('.select_template').val($(this).attr('href'));
		$('.select_template_dialog').dialog('close');
		return false;
	});

	$('.select_theme').focus(function(){
		$('.select_theme_dialog').dialog('open');
	});
	$('.select_theme').keypress(function(){return false;});
	$('.theme').click(function(){
		$('.select_theme').val($(this).attr('href'));
		$('.select_theme_dialog').dialog('close');
		return false;
	});

	$('.load_template').click(function(){
		var path = '/templates/'+$('.select_template').val()+'/template.html'
		$('.wrap_text').val('Loadind data ...');
		$.post(path,{},function(data){
			$('.wrap_text').val(data);
		});
	});
});