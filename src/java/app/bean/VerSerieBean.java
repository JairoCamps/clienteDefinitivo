/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import app.client.SerieClienteREST;
import app.entity.Serie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ofviak
 */
@Named(value = "verSerieBean")
@RequestScoped
public class VerSerieBean implements Serializable {
    
    private Serie serie;

    /**
     * Creates a new instance of VerSerieBean
     */
    public VerSerieBean() {
    }
    
    /**
     * Recoge el parametro idSerie de la url. Encuentra la serie asociada
     * y la guarda en this.serie
     */
    
    @PostConstruct
    public void init(){
        
        Map<String,String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        String idSerie =  params.get("idSerie");
        serie = findSerieById(idSerie);
    }
    
    
    //SERVICIOS USADOS
    private Serie findSerieById(String serieId){
        SerieClienteREST serv = new SerieClienteREST();
        Response r = serv.find_JSON(Response.class, serieId);
        
        if(r.getStatus()==200){
            GenericType<Serie> genericType = new GenericType<Serie>(){};
            Serie s = r.readEntity(genericType);
            return s;
        }
        
        return null;
    }
    
    //GETTERS Y SETTERS VARIOS

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }


}
