$(document).ready(function(){
	$(document).on('click', 'a', function(ev){
		ev.preventDefault();
	})
	
	$(document).on('click', '.encrypt', function(ev){
		ev.preventDefault();
		encryptFiles();
	})
})