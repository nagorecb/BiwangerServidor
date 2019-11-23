package Biwanger.DAO;

import java.util.ArrayList;

import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;

public interface itfDAO {

	Object guardarObjeto(Object objeto);
	void cerrarConexion();
		
	ArrayList<clsUsuario> leerUsuarios();
	ArrayList<clsPuja> leerPujas();
	ArrayList<clsJugador> leerJugadores();
	
//	void eliminarUsuario(clsUsuario usuario);
//	void eliminarPuja(clsPuja puja);
//	void eliminarJugador(clsJugador jugador);

//	void modificarUsuario(clsUsuario usuario);
//	void modificarPuja(clsPuja puja);
//	void modificarJugador(clsJugador jugador);
	
	void eliminarObjeto(Object objeto);
	void modificarObjeto(Object objeto);
	
	void eliminarJugadorDePlantilla(clsJugador jugador);
	clsJugador buscarJugador(int idJugador);

}
