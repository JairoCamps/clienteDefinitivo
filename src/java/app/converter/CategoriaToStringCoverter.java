/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.converter;

import app.client.CategoriaClienteREST;
import app.entity.Categoria;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jairo
 */

@FacesConverter("categoriaToStringConverter")
public class CategoriaToStringCoverter implements Converter{

    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Categoria c = getCategoriaById(value);
        return c;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Categoria c = (Categoria) value;
        String idCat = c.getIdCategoria().toString();
        return idCat;
    }
    
    private Categoria getCategoriaById(String id){
        CategoriaClienteREST categoriaCliente = new CategoriaClienteREST();
        Response r = categoriaCliente.find_XML(Response.class, id);
        if (r.getStatus() == 200) {
            GenericType<Categoria> genericType = new GenericType<Categoria>(){};
            Categoria c = r.readEntity(genericType);
            return c;
        }
        return null;
    }
    
}
