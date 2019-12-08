package Biwanger.ObjetosDominio;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@PersistenceCapable
public class clsUsuario
{
	private static final long serialVersionUID = 1L;
	
	@PrimaryKey
    private String email;
    private String password;
    private int puntuacion;
    private double fondos;
    private String formacion;
    
    public clsUsuario() {
    	this.email = null;
        this.password =  null;
        this.formacion = null;
        this.fondos = 0.0;
        puntuacion = 0;
	}

	public clsUsuario(String email, String password, int puntuacion, double fondos, String formacion) {
		super();
		this.email = email;
		this.password = password;
		this.puntuacion = puntuacion;
		this.fondos = fondos;
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

    public void setFormacion(String formacion) {
        this.formacion = formacion;
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


	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		clsUsuario that = (clsUsuario) o;
		return email.equals(that.email);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(email);
	}

	@Override
	public String toString() {
		return "clsUsuario [email=" + email + ", password=" + password + ", puntuacion=" + puntuacion + ", fondos="
				+ fondos + "]";
	}
}
