/* 
 *Funciones en js útiles para el proceso de inicio de sesión
 *De esta forma no necesitamos escribirlas en cada página que las necesite.
 *Simplemente se importan con <h:outputScript>
 *@author Pedro Avila
 */
function onSignIn(googleUser) {
        // Useful data for your client-side scripts:
        var profile = googleUser.getBasicProfile();
        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log("Image URL: " + profile.getImageUrl());
        console.log("Email: " + profile.getEmail());
        
        //codigo Para guardar en el bean el email
        sendParams(profile.getEmail(), profile.getImageUrl());
        
        // The ID token you need to pass to your backend:
        var id_token = googleUser.getAuthResponse().id_token;
        console.log("ID Token: " + id_token);
        
 }
 
 
function sendParams(value1, value2) {
 passToJSFMB([ {name : 'emailUser', value : value1 }, {name : 'imageUrl', value: value2 } ]);
}



