package main.java.Biwanger.ObjetosDominio;

import java.io.Serializable;
import java.util.ArrayList;
import main.java.Biwanger.ObjetosDominio.*;

//@PersistenceCapable
public class clsUsuario implements Serializable
{
    //@PrimaryKey
    private String email;
    private String password;

    //AnotaciÃ³n para la relaciÃ³n de la tabla
    private ArrayList <clsJugador> plantilla;
    private int puntuacion;

    public clsUsuario()
    {
        this.email = null;
        this.password =  null;
        this.plantilla = new ArrayList <clsJugador>();
        puntuacion = 0;
    }

    public clsUsuario(String email, String password, ArrayList<clsJugador> plantilla)
    {
        this.email = email;
        this.password = password;
        this.plantilla = plantilla;
        this.puntuacion = puntuacion;
    }

    public void addJugador(clsJugador jugador)
    {
        plantilla.add(jugador);
    }
    //Getters y setters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<clsJugador> getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(ArrayList<clsJugador> plantilla) {
        this.plantilla = plantilla;
    }

    //HashCode e equals
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        clsUsuario other = (clsUsuario) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }
}
