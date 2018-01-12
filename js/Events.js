$(document).ready(function(){
	$(document).on('click', 'a', function(ev){
		ev.preventDefault();
	})

	$(document).on('click', '.encrypt', function(ev){
		ev.preventDefault();
		encryptFiles();
	})

	$(document).on('click', '.decrypt', function(ev){
		ev.preventDefault();
		decryptFiles();
	})

	$(document).on('click', '.take_snapshot', function(ev){
		ev.preventDefault();

	})

	$(document).on('click', '.settings', function(ev){
		ev.preventDefault();
		fillSettingsForm();
		$("#settingsModal").modal('open');
	})


	function fillSettingsForm() {
		
		$("#name").val(config.name);
	}

})