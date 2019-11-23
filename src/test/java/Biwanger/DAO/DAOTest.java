package Biwanger.DAO;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import Biwanger.ObjetosDominio.clsJugador;

public class DAOTest {

	clsDAO clsDao = new clsDAO();
	
	@Test
	public void testGuardarYModificarObjeto() {
		
		clsJugador jugador = new clsJugador();
		
		jugador.setNombre("nombre");
		jugador.setEnVenta(true);
		jugador.setAlineado(true);
		
		this.clsDao.guardarObjeto(jugador);
		
//		ArrayList<clsJugador> listaJugadores = this.clsDao.leerJugadores();
//		
//		clsJugador jugadorBuscado= listaJugadores.get(listaJugadores.size()-1);
//		assertEquals(jugadorBuscado, jugador);
		
//		jugador.setNombre("nuevoNombre");
//		
//		this.clsDao.modificarJugador(jugador);
//		clsJugador jugadorModificado =listaJugadores.get(listaJugadores.size()-1);
//		
//		assertEquals(jugador,jugadorModificado);
	}

//	@Test
//	public void testBorrarJugador() 
//	{
//		clsJugador jugador = new clsJugador();
//		
//		jugador.setNombre("nombre");
//		jugador.setEnVenta(true);
//		jugador.setAlineado(true);
//		
//		clsJugador jugadorGuardado = (clsJugador) this.clsDao.guardarObjeto(jugador);
//		ArrayList<clsJugador> listaJugadoresAntes = this.clsDao.leerJugadores();
//
//		this.clsDao.eliminarObjeto(jugadorGuardado);
//		
//		ArrayList<clsJugador> listaJugadores = this.clsDao.leerJugadores();
//		assertEquals(listaJugadoresAntes.size()-1, listaJugadores.size());
//		assertEquals(listaJugadores.contains(jugadorGuardado), false);
//	}
	
}
