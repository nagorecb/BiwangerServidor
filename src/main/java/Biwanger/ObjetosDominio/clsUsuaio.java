package ObjetosDominio;

import java.io.Serializable;

//@PersistenceCapable
public class clsUsuario implements Serializable
{
    //@PrimaryKey
    private String email;
    private String password;

    //Anotación para la relación de la tabla
    private Arraylist <clsJugador> plantilla;
    private int puntuacion;

    public clsUsuario()
    {
        this.email = null;
        this.password =  null;
        this.plantilla = new ArrayList <clsJugador>();
        puntuacion = 0;
    }

    public clsUsuario(String email, String password, Arraylist <clsJugador> plantilla)
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
        return ListReservas;
    }

    public void setPlantilla(ArrayList<clsReserva> ListReservas) {
        this.ListReservas = ListReservas;
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
