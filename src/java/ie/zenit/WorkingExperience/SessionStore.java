
package ie.zenit.WorkingExperience;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

// this method basicaly store the session for all pages, then the user can use the webpages to work in application without 
// worrying if the user is logged on, or logged off
public class SessionStore {
    
    private HttpSession session = null; // assigning the variable to null in default
    
    private HttpSession getSession() {
        if (this.session == null) {
            // get current faces instance for this request
            FacesContext facesContext = FacesContext.getCurrentInstance();
            // get current user session (or create one)
            this.session = (HttpSession)facesContext.getExternalContext().getSession(true);
        }
        
        return this.session;
    } // it is checking if the session is active (!= to null), and set the faces

    public Object retrieve(String key) {
        return this.getSession().getAttribute(key); // this method is storing the key variable(usefull in other methods) 
    }                                               // and returning its value
    
    public void store(String key, Object value) {
        this.getSession().setAttribute(key, value); // the method is storing the key and value variable to be used in other methods
    }
}
