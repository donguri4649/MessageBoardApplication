window.onload = function() {
	ClassicEditor
        .create( document.querySelector( '#editor' ) )
        .then( editor => {
            console.log( editor );
        } )
        .catch( error => {
            console.error( error );
        } );
        
        ClassicEditor.config.width=600;
		ClassicEditor.config.height=500;
 
}
