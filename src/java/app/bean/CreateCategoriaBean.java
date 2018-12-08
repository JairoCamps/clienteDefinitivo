/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import app.client.CategoriaClienteREST;
import app.entity.Categoria;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Javier
 */
@Named(value = "createCategoriaBean")
@RequestScoped
public class CreateCategoriaBean {

    @Inject
    private IndexBean sb;

    
    protected Categoria c;
    
    /**
     * Creates a new instance of CrearCategoria
     */
    public CreateCategoriaBean() {
    }
    
    @PostConstruct
    public void init(){
        c=new Categoria();
    }
    
    public String crearCategoria(String nombre){
        createCategoriaByName(c.getNombre());
        
        c.setNombre("");
        this.sb.init();
        System.out.println(c.getNombre() + "---------------------------------------");
                
        return "";
    }

    public Categoria getC() {
        return c;
    }

    public void setC(Categoria c) {
        this.c = c;
    }
    
    private Categoria createCategoriaByName(java.lang.String name) {
        CategoriaClienteREST categoriaCliente = new CategoriaClienteREST();
        Response r = categoriaCliente.createCategoriaByNombre(Response.class, name);
        if(r.getStatus() == 200){
            GenericType<Categoria> genericType = new GenericType<Categoria>(){};
            Categoria categoria = r.readEntity(genericType);
            return categoria;
        } 
        return null;
    }
    
    
}
