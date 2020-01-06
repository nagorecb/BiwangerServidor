package Biwanger.DAO;

import static org.junit.Assert.*;

import java.util.ArrayList;


import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;

import com.github.javatlacati.contiperf.PerfTest;
import com.github.javatlacati.contiperf.Required;
import com.github.javatlacati.contiperf.junit.ContiPerfRule;

import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;

@Required(max = 200000, average = 200000)
public class DAOTest {

	clsDAO clsDao = new clsDAO();

	@Rule
   	public ContiPerfRule rule = new ContiPerfRule();

	@Test
	public void testGuardarYLeerJugador()
	{
		clsJugador jugadorGuardar = crearJugador();
		
		this.clsDao.guardarObjeto(jugadorGuardar);
		ArrayList<clsJugador> listaJugadores = this.clsDao.leerJugadores();
		clsJugador jugadorBuscado= listaJugadores.get(listaJugadores.size()-1);
		
		assertEquals(jugadorBuscado, jugadorGuardar);
		
		borrarJugador(jugadorGuardar);
	}
	@Test
	public void testGuardarYLeerUsuario()
	{
		clsUsuario usuarioGuardar = crearUsuario();
		
		this.clsDao.guardarObjeto(usuarioGuardar);
		ArrayList<clsUsuario> listaUsuarios = this.clsDao.leerUsuarios();
		
		// Como leerUsuarios devuelve la lista ordenada por emailUsuario, no funciona 
		// cogiendo el último de la lista, como en los otros test, por tanto compruebo que la
		// lista buscada contenga el usuario mandado a guardar.
		assertTrue(listaUsuarios.contains(usuarioGuardar));
		
		borrarUsuario(usuarioGuardar);
	}
	
	@Test
	public void testGuardarYLeerPuja()
	{
		clsPuja pujaGuardar = crearPuja();
		
		this.clsDao.guardarObjeto(pujaGuardar);
		ArrayList<clsPuja> listaPujas = this.clsDao.leerPujas();
		clsPuja pujaBuscada= listaPujas.get(listaPujas.size()-1);
		
		assertEquals(pujaBuscada, pujaGuardar);
		
		borrarPuja(pujaGuardar);
	}
	
	@Test
	public void testModificarJugador() 
	{
		
		clsJugador jugador = crearJugador();
		
		this.clsDao.guardarObjeto(jugador);
		jugador.setNombre("nuevoNombre");
		this.clsDao.modificarJugador(jugador);
		ArrayList<clsJugador> listaJugadores = this.clsDao.leerJugadores();
		clsJugador jugadorModificado =listaJugadores.get(listaJugadores.size()-1);
		
		assertEquals(jugadorModificado.getNombre(),"nuevoNombre");
		assertEquals(jugador, jugadorModificado);
		
		borrarJugador(jugador);
	}

	@Test
	public void testModificarUsuario() 
	{
		
		clsUsuario usuario = crearUsuario();
		
		this.clsDao.guardarObjeto(usuario);
		usuario.setEmail("nombre" + Math.random()+ "@gmail.com");
		this.clsDao.modificarUsuario(usuario);
		ArrayList<clsUsuario> listaUsuarios = this.clsDao.leerUsuarios();
		
		assertTrue(listaUsuarios.contains(usuario));
		
		borrarUsuario(usuario);
	}
	
	//si lo hace con jugador bien, lo hace bien con todos
	@Test
	public void testBorrarJugador() 
	{
		clsJugador jugador = crearJugador();
		
		clsJugador jugadorGuardado = (clsJugador) this.clsDao.guardarObjeto(jugador);
		ArrayList<clsJugador> listaJugadoresAntes = this.clsDao.leerJugadores();
		this.clsDao.eliminarObjeto(jugadorGuardado);
		ArrayList<clsJugador> listaJugadores = this.clsDao.leerJugadores();

		assertEquals(listaJugadores.contains(jugadorGuardado), false);

		borrarJugador(jugador);
	}
	
	@Test
	public void testBuscarJugador()
	{
		clsJugador jugador = crearJugador();
		
		clsJugador jugadorGuardado = (clsJugador) this.clsDao.guardarObjeto(jugador);
		int idJugador = jugadorGuardado.getId();
		
		clsJugador jugadorBuscado = this.clsDao.buscarJugador(idJugador);
		
		assertEquals(jugadorBuscado.getId(), idJugador);
		
		borrarJugador(jugador);	
	}
	
	@Test
	public void testBuscarUsuario()
	{
		clsUsuario usuario = crearUsuario();
		clsUsuario usuarioGuardado = crearUsuario();

		this.clsDao.guardarObjeto(usuario);
		String emailUsu = usuario.getEmail();
				
		usuarioGuardado = this.clsDao.buscarUsuario(emailUsu);
		
		assertEquals(usuarioGuardado, usuario);
		
		borrarUsuario(usuario);
		borrarUsuario(usuarioGuardado);
	}
	
	@Test
	public void testEliminarJugadorDePlantilla()
	{
		clsJugador jugador = crearJugador();
		clsUsuario usuarioDueno = crearUsuario();
		
		clsJugador jugadorGuardado = (clsJugador) this.clsDao.guardarObjeto(jugador);
		this.clsDao.guardarObjeto(usuarioDueno);

		jugadorGuardado.setUsuarioDueno(usuarioDueno.getEmail());

		clsJugador jugadorGuardadoConDueno = (clsJugador) this.clsDao.guardarObjeto(jugadorGuardado);
		assertEquals(jugadorGuardadoConDueno.getUsuarioDueno(), usuarioDueno.getEmail());

		clsJugador juagdorconduenobuscado = this.clsDao.buscarJugador(jugadorGuardadoConDueno.getId());
		assertEquals(juagdorconduenobuscado.getUsuarioDueno(), usuarioDueno.getEmail());

		this.clsDao.eliminarJugadorDePlantilla(jugadorGuardadoConDueno);
		
		clsJugador juagdorsinduenobuscado = this.clsDao.buscarJugador(jugadorGuardadoConDueno.getId());
		assertEquals(juagdorsinduenobuscado.getUsuarioDueno(), null);
		
		assertEquals(jugadorGuardadoConDueno.getUsuarioDueno(), null);
		
		borrarJugador(jugador);
		borrarUsuario(usuarioDueno);
	}
	
	//----------------Creación y borrado de objetos----------------
	
	private clsJugador crearJugador() 
	{
		clsJugador jugador = new clsJugador();
		
		jugador.setNombre("nombre");
		jugador.setEnVenta(true);
		jugador.setAlineado(true);
		return jugador;
	}
	
	private clsUsuario crearUsuario() 
	{
		clsUsuario usuario = new clsUsuario();
		
		usuario.setEmail("nombre" + Math.random()+ "@gmail.com");
		usuario.setFondos(23000);
		
		return usuario;
	}
	
	private clsPuja crearPuja() 
	{
		clsPuja puja = new clsPuja();
		clsUsuario usuarioPuja = crearUsuario();
		
		puja.setOferta(23000);
		puja.setEmailUsuarioPuja(usuarioPuja.getEmail());
		
		return puja;
	}
	
	private void borrarJugador(clsJugador jugador)
	{
		this.clsDao.eliminarObjeto(jugador);
	}
	private void borrarUsuario(clsUsuario usuario)
	{
		this.clsDao.eliminarObjeto(usuario);
	}
	private void borrarPuja(clsPuja puja)
	{
		this.clsDao.eliminarObjeto(puja);
	}
}
