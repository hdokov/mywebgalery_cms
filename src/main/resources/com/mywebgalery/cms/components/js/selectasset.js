var selectassetaction = '';
var currentAssetPath = '/admin/apps/selectassets';
function openSelectAssetDialog(action){
	selectassetaction=action;
	$('.select_asset_dialog').dialog('open');
}
var initAssetLinks = function(){
	$('.dirlink').click(function(){
		currentAssetPath=$(this).attr('href');
		loadAssetsPath();
		return false;
	});
	$('.filelink').click(function(){
		var link = encodeURIComponent($(this).attr('href'));
		log(link);
		window.location.href = selectassetaction+"/"+link;
		return false;
	});

};
function loadAssetsPath(){
	$('.select_asset_dialog').empty().append("<h2>Loading assets. Please wait...</h2>");
	$.post(currentAssetPath,{},function(data){
		data = $(data).filter('div.select_asset_content');
		$('.select_asset_dialog').empty().append(data);
		initAssetLinks();
	});
}
$(function(){

	$("a.select_asset").click(function(){
		openSelectAssetDialog($(this).attr('href'));
		return false;
	});

	$('.select_asset_dialog').dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 640,
		height: 480,
		buttons: {
			Cancel: function() {
				$(this).dialog('close');
			}
		},
		open: function() {
			loadAssetsPath();
		},
		close: function(){
		}
	});//edit_menu_dialog
});