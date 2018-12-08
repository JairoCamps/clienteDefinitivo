/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

/**
 *
 * @author Pedro Avila
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String emailUser;
    private boolean loginInvitado;
    private String imageUrl;
    
    private List<String> images;
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    @PostConstruct
    public void init(){
 
        images = new ArrayList<>();
        for(int i=1;i<=8;i++){
            images.add("portada" + i + ".jpg");
        }
    }

    
    /**
     * login()
     * Recoge los datos de logueo y los guarda en el bean.
     */
    
    public void login() {
        this.emailUser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("emailUser");
        this.imageUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("imageUrl");
        
        if(emailUser!=null && !emailUser.equals("")){
            FacesContext fc = FacesContext.getCurrentInstance();
            NavigationHandler nh = fc.getApplication().getNavigationHandler();
            nh.handleNavigation(fc, null, "/index.xhtml?faces-redirect=true");
        }

    }
    
    /**
     * Loguea al usuario en el sistema como invitado
     * @return index
     */
    
    public String loginAsInvited(){
        this.emailUser = null;
        this.loginInvitado = true;
        return "index";
    }
    
    
    
    public void logout(){
        this.emailUser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("emailUser");
        this.imageUrl = null; 
    }
    
    //Getters y setters varios
    
    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isLoginInvitado() {
        return loginInvitado;
    }
    
}
