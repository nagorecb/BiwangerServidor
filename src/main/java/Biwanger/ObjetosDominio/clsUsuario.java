package Biwanger.ObjetosDominio;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@PersistenceCapable
public class clsUsuario implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@PrimaryKey
    private String email;
    private String password;
    private int puntuacion;
    private double fondos;
    private String formacion;
    
    @Persistent(mappedBy="usuarioDueno")
    private List <clsJugador> plantilla;
    
    @Persistent(mappedBy="usuarioPuja")
    private List <clsPuja> pujas;
    
    public clsUsuario() {
    	this.email = null;
        this.password =  null;
        this.formacion = null;
        this.fondos = 0.0;
        this.plantilla = new ArrayList <clsJugador>();
        puntuacion = 0;
	}

	public clsUsuario(String email, String password, int puntuacion, double fondos, List<clsJugador> plantilla, String formacion) {
		super();
		this.email = email;
		this.password = password;
		this.puntuacion = puntuacion;
		this.fondos = fondos;
		this.plantilla = plantilla;
		this.formacion = formacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public double getFondos() {
		return fondos;
	}

	public void setFondos(double fondos) {
		this.fondos = fondos;
	}

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String email) {
        this.formacion = formacion;
    }

	public List<clsJugador> getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(List<clsJugador> plantilla) {
		this.plantilla = plantilla;
	}

	//Plantilla
	public void AnadirJugador (clsJugador jug)
	{
		this.plantilla.add(jug);
	}

	public void EliminarJugador (clsJugador jug)
	{
		this.plantilla.remove(jug);
	}

	//Fondos
	public void SumarFondos (double precioVenta)
	{
		this.fondos +=precioVenta;
	}

	public void RestarFondos (double precioCompra)
	{
		this.fondos -=precioCompra;
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
