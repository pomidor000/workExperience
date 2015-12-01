/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.zenit.WorkingExperience;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DLights
 */
public class SessionStore {
    
    private HttpSession session = null;
    
    private HttpSession getSession() {
        if (this.session == null) {
            // get current faces instance for this request
            FacesContext facesContext = FacesContext.getCurrentInstance();
            // get current user session (or create one)
            this.session = (HttpSession)facesContext.getExternalContext().getSession(true);
        }
        
        return this.session;
    }

    public Object retrieve(String key) {
        return this.getSession().getAttribute(key);
    }
    
    public void store(String key, Object value) {
        this.getSession().setAttribute(key, value);
    }
}
