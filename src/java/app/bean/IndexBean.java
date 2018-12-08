/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import app.client.CategoriaClienteREST;
import app.client.SerieClienteREST;
import app.entity.Categoria;
import app.entity.Serie;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Grupo B1
 */
@Named(value = "indexBean")
@SessionScoped
public class IndexBean implements Serializable {

    protected List<Serie> listaSeries;
    protected List<Categoria> listaCategorias;
    protected Integer categoriaIdSeleccionada;
    protected Integer serieIdSeleccionada;
    
    public IndexBean() {
    }
    
    @PostConstruct
    public void init(){
        listaSeries = getAllSeries();
        listaCategorias = getAllCategorias();
    }
    
    public void actualizarTabla(){
        if(null != categoriaIdSeleccionada){
            listaSeries = getSeriesByIdCategoria(String.valueOf(categoriaIdSeleccionada));
        }else{
            listaSeries = getAllSeries();
        }
    }

    public List<Serie> getListaSeries() {
        return listaSeries;
    }

    public void setListaSeries(List<Serie> listaSeries) {
        this.listaSeries = listaSeries;
    }

    public List<Categoria> getListaCategoria() {
        return listaCategorias;
    }

    public void setListaCategoria(List<Categoria> listaCategoria) {
        this.listaCategorias = listaCategoria;
    }

    public Integer getCategoriaIdSeleccionada() {
        return categoriaIdSeleccionada;
    }

    public void setCategoriaIdSeleccionada(Integer categoriaIdSeleccionada) {
        this.categoriaIdSeleccionada = categoriaIdSeleccionada;
    }

    public Integer getSerieIdSeleccionada() {
        return serieIdSeleccionada;
    }

    public void setSerieIdSeleccionada(Integer serieIdSeleccionada) {
        this.serieIdSeleccionada = serieIdSeleccionada;
    }
    
    
    
    
    
    public List<Serie> getAllSeries(){
        SerieClienteREST serieCliente = new SerieClienteREST();
        Response r = serieCliente.findAll_XML(Response.class);
        if (r.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>(){};
            List<Serie> series = r.readEntity(genericType);
            return series;
        }
        
        return null;
    }

    private List<Categoria> getAllCategorias() {
        CategoriaClienteREST categoriaCliente = new CategoriaClienteREST();
        Response r = categoriaCliente.findAll_XML(Response.class);
        if(r.getStatus() == 200){
            GenericType<List<Categoria>> genericType = new GenericType<List<Categoria>>(){};
            List<Categoria> categorias = r.readEntity(genericType);
            return categorias;
        } 
        return null;
    }

    private List<Serie> getSeriesByIdCategoria(String idCategoria) {
        SerieClienteREST serieCliente = new SerieClienteREST();
        Response r = serieCliente.findSeriesByIdCategoria_XML(Response.class, idCategoria);
        if (r.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>(){};
            List<Serie> series = r.readEntity(genericType);
            return series;
        }
        return null;
    }
        
    public String doEdit (Integer idSerie){
        this.setSerieIdSeleccionada(idSerie);
        return "editarSerie?faces-redirect=true";
    }
}
