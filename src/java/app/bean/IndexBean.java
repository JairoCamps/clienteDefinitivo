/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import app.client.CategoriaClienteREST;
import app.client.CategoriaSerieClienteREST;
import app.client.SerieClienteREST;
import app.entity.Categoria;
import app.entity.Categoriaserie;
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
        actualizarTabla();
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
        CategoriaSerieClienteREST categoriaSerieCliente = new CategoriaSerieClienteREST();
        Response r = categoriaSerieCliente.findSeriesByIdCategoriaIntermedio_XML(Response.class, idCategoria);
        if (r.getStatus() == 200) {
            GenericType<List<Serie>> genericType = new GenericType<List<Serie>>(){};
            List<Serie> series = r.readEntity(genericType);
            return series;
        }
        return null;
    }
    public String doCrear (){
        this.setSerieIdSeleccionada(-1);
        return "editarSerie?faces-redirect=true";
    }
    public String doEdit (Integer idSerie){
        this.setSerieIdSeleccionada(idSerie);
        return "editarSerie?faces-redirect=true";
    }
    
     public String doBorrar(Integer idSerie){   
        //borramos las Categoriaserie de la base de datos
         removeCategoriaSerie(idSerie);
               
        //borramos la serie de la base de datos
         removeSerie(idSerie);
         
         //actualizamos la tabla
         actualizarTabla();
        return "/index.xhtml?faces-redirect=true";
    }
    
    private void removeCategoriaSerie(Integer idSerie){
        List<Categoriaserie> listaCategoriaSerie = null;
        CategoriaSerieClienteREST csCliente = new CategoriaSerieClienteREST();
        Response r = csCliente.findIntermediaByIdSerie_XML(Response.class, idSerie.toString());
        
        if(r.getStatus() == 200){
            
            GenericType<List<Categoriaserie>> genericType = new GenericType<List<Categoriaserie>>(){};
            listaCategoriaSerie = r.readEntity(genericType);   
        }
        
        if(listaCategoriaSerie != null){
            for(Categoriaserie cs : listaCategoriaSerie){
                csCliente.remove(cs.getIdCategoriaSerie().toString());
            }
        }
    }
    
    private void removeSerie(Integer idSerie){
        Serie serie = null;
        SerieClienteREST sCliente = new SerieClienteREST();
        Response r = sCliente.findSerieByIdSerie_JSON(Response.class, idSerie.toString());
        
        if(r.getStatus() == 200){
            GenericType<Serie> genericType = new GenericType<Serie>(){};
            serie = r.readEntity(genericType);
        }
        
        if(serie != null){
            //borramos la serie de la base de datos
            sCliente.remove(serie.getIdSerie().toString());
        }
    }
    
}
