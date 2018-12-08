/* 
 *Funciones en js útiles para el proceso de cierre de sesión
 *De esta forma no necesitamos escribirlas en cada página que las necesite.
 *Simplemente se importan con <h:outputScript> o <script>
 *@author Pedro Avila
 */
function onLoad() {
    gapi.load('auth2', function() {
    gapi.auth2.init();
    });
}


function signOut() {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            console.log('User signed out.');
        });
        
        sendLogout();
        alert("Cerrando sesión...");
        
        
}


function sendLogout() {
 logoutMB([ {
  name : 'emailUser',
  value : null
 } ]);
}


