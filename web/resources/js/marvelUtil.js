/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @author Pedro Ávila
 */
var marvel = {
    render: function() {
        
        var url = "http://gateway.marvel.com/v1/public/comics?orderBy=-modified&ts=1&apikey=b25a37a62f6e92fa1467788ac668064e&hash=5f69eb74d0221852f735494c961b5d3e";
        var marvelContainer = document.getElementById("marvel");
        var message = document.getElementById("message");
        
        $.ajax({
            url: url,
            type: "GET",
            
            beforeSend: function(){
                message.innerHTML = "Cargando...";
            },
            
            complete: function(){
                message.innerHTML = "Cargado correctamente";
            },
            error: function(){
                message.innerHTML = "Problema cargando la api";
            },
            success: function(data){

               var string = "";
               string += data.attributionHTML;
               string += "<div class='row'>";
               
                for(var i=0; i<data.data.results.length; i++){
                   var element = data.data.results[i];
                   console.log(element.title);
                   
                    string += "<div class='col-md-3'>";
                    string += "<a href='" + element.urls[0].url + "' target='_blank'>"
                    string += "<img src='" + element.thumbnail.path + "/portrait_fantastic." +
                        element.thumbnail.extension  + "' />";
                    string += "</a>"
                    string += "<h3>" + element.title + "</h3>";
                    string += "</div>";
                    
                    if((i+1)%4 == 0){
                        string += "</div>";
                        string += "<div class='row'>";
                    }
               }
               marvelContainer.innerHTML = string;
            }
          });
    }
};

marvel.render();

function sendParams(value1, value2) {
 saveInfoFromApi([ {name : 'nombreSerie', value : value1 }, {name : 'imageUrl', value: value2 } ]);
}
