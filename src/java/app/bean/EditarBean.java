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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jairo
 */
@Named(value = "editarBean")
@RequestScoped
public class EditarBean {

    @Inject
    private IndexBean indexBean;
    
    protected Serie serie;
    protected List<Categoria> listaCategorias;
    protected List<Categoria> listaCategoriasSeleccionadas;
    
    public EditarBean() {
    }
    
    @PostConstruct
    public void init(){
        serie = this.getSerieById(String.valueOf(indexBean.serieIdSeleccionada));
        listaCategorias = indexBean.getListaCategoria();
        listaCategoriasSeleccionadas = this.getCategoriasByIdSerie(serie.getIdSerie().toString());       
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public IndexBean getIndexBean() {
        return indexBean;
    }

    public void setIndexBean(IndexBean indexBean) {
        this.indexBean = indexBean;
    }

    public List<Categoria> getListaCategoriasSeleccionadas() {
        return listaCategoriasSeleccionadas;
    }

    public void setListaCategoriasSeleccionadas(List<Categoria> listaCategoriasSeleccionadas) {
        this.listaCategoriasSeleccionadas = listaCategoriasSeleccionadas;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
    
    

    
    private Serie getSerieById(String id) {
        SerieClienteREST serieCliente = new SerieClienteREST();
        Response r = serieCliente.find_XML(Response.class, id);
        if (r.getStatus() == 200) {
            GenericType<Serie> genericType = new GenericType<Serie>(){};
            Serie s = r.readEntity(genericType);
            return s;
        }
        return null;
    }
    
    private List<Categoria> getCategoriasByIdSerie(String id){
        CategoriaSerieClienteREST csCliente = new CategoriaSerieClienteREST();
        Response r = csCliente.findCategoriasByIdSerieIntermedio_XML(Response.class, id);
        if (r.getStatus() == 200) {
            GenericType<List<Categoria>> genericType = new GenericType<List<Categoria>>(){};
            List<Categoria> l = r.readEntity(genericType);
            return l;
        }
        return null;
    }
    
    
    private void createCategoriaserie(Categoriaserie cs){
        CategoriaSerieClienteREST cscliente = new CategoriaSerieClienteREST();
        cscliente.create_JSON(cs);
    }
    
    private void editarSerie (String id){
        SerieClienteREST serieCliente = new SerieClienteREST();
        serieCliente.edit_XML(serie, id);
    }
    
    private void eliminarCats (String idSerie){
        CategoriaSerieClienteREST csCliente = new CategoriaSerieClienteREST();
        Response r = csCliente.findIntermediaByIdSerie_XML(Response.class, idSerie);
        List<Categoriaserie> l;
        if (r.getStatus() == 200) {
            GenericType<List<Categoriaserie>> genericType = new GenericType<List<Categoriaserie>>(){};
            l = r.readEntity(genericType);   
        }else{
            l = null;
        }
        
        if (l != null){
            for (Categoriaserie cs : l){
                csCliente.remove(cs.getIdCategoriaSerie().toString());
            }
        }
        
    }
    
    public String doEditar(){
        
        editarSerie(serie.getIdSerie().toString());
        eliminarCats(serie.getIdSerie().toString());
              
        for(Categoria c : listaCategoriasSeleccionadas){
            
            Categoriaserie nuevaCs = new Categoriaserie();
            nuevaCs.setCategoriaidCategoria(c);
            nuevaCs.setSerieidSerie(serie);
            createCategoriaserie(nuevaCs);
        }
        
        
        
        indexBean.init();
        return "index?faces-redirect=true";
    }
}
