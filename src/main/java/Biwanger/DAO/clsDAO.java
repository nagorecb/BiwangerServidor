package Biwanger.DAO;

import java.util.ArrayList;
import java.util.Iterator;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;
/**
 * @brief Clase para la gestión de la base de datos remota
 */
public class clsDAO
{
	private static clsDAO instancia= new clsDAO();

	/**
	 * Método para conseguir una única instancia de la clase DAO
	 * @return La instancia única clsDao
	 */
	public static clsDAO getInstance()
	{
		synchronized (clsDAO.class)
		{
			if(instancia == null)
			{
				instancia = new clsDAO();
			}
		}
		return instancia;
	}

	/**
	 * Método para guardar objetos en la base de datos
	 * @param objeto El objeto a guardar
	 * @return jugador guardado en la base de datos
	 */
	public clsJugador guardarObjeto(Object objeto)
	{
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		clsJugador jugador = null;
		
		Transaction tx = null;
		try
		{
			tx = pm.currentTransaction();
			tx.begin();

			if(objeto.getClass().equals(clsJugador.class))
			{
				jugador = (clsJugador) pm.makePersistent(objeto);
			} else
			{
				pm.makePersistent(objeto);
			}

			tx.commit();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		System.out.println("Empiezo a guardar en base de datos");
		return jugador;
	}

	/**
	 * Método para buscar jugador en la base de datos
	 * @param idJugador el ID del jugador a buscar
	 * @return jugador buscado en la base de datos
	 */
	public clsJugador buscarJugador(int idJugador)
	{
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		clsJugador jugadorBuscado = null;

		Transaction tx = null;
		try{
			tx = pm.currentTransaction();
			tx.begin();

			jugadorBuscado = pm.getObjectById(clsJugador.class, idJugador);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return jugadorBuscado;
	}

	/**
	 * Método para buscar usuario en la base de datos
	 * @param email el email único identificativo del usuario a buscar
	 * @return usuario buscado en la base de datos
	 */
	public clsUsuario buscarUsuario(String email)
	{
		clsUsuario retorno = null;

		ArrayList<clsUsuario> lUsuarios = leerUsuarios();

		for(clsUsuario aux: lUsuarios)
		{
			if(email.equals(aux.getEmail()))
			{
				retorno = aux;
			}
		}

		return retorno;
	}

	/**
	 * Método para la obtención de los jugadores de la base de datos
	 * @return Lista de jugadores almacenados en la base de datos
	 */
	public ArrayList<clsJugador> leerJugadores() 
	{
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		ArrayList <clsJugador> listaJugadores = new ArrayList <clsJugador>();
		Transaction tx = null;
		try{
			tx = pm.currentTransaction();
			tx.begin();
			//Para hacer la select, usamos extent
			Extent<clsJugador> extent = pm.getExtent(clsJugador.class, true);
			
			for(clsJugador jugador: extent)
			{
				listaJugadores.add(jugador);
			}
			tx.commit();
			
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		
		return listaJugadores;
	}
	/**
	 * Método para la obtención de los usuarios de la base de datos
	 * @return Lista de usuarios almacenados en la base de datos
	 */
	public ArrayList<clsUsuario> leerUsuarios() 
	{
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		ArrayList <clsUsuario> listaUsuarios = new ArrayList <clsUsuario>();
		Transaction tx = null;
		try{
			tx = pm.currentTransaction();
			tx.begin();
			//Para hacer la select, usamos extent
			Extent<clsUsuario> extent = pm.getExtent(clsUsuario.class, true);
			
			for(clsUsuario usuario: extent)
			{
				listaUsuarios.add(usuario);
			}
			tx.commit();
			
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		
		return listaUsuarios;
	}

	/**
	 * Método para la obtención de las pujas de la base de datos
	 * @return Lista de pujas almacenadas en la base de datos
	 */
	public ArrayList<clsPuja> leerPujas() 
	{
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		ArrayList <clsPuja> listaPujas = new ArrayList <clsPuja>();
		Transaction tx = null;
		try{
			tx = pm.currentTransaction();
			tx.begin();
			//Para hacer la select, usamos extent
			Extent<clsPuja> extent = pm.getExtent(clsPuja.class, true);
			
			for(clsPuja puja: extent)
			{
				listaPujas.add(puja);
			}
			tx.commit();
			
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		
		return listaPujas;
	}

	/**
	 * Método para la eliminación de un objeto dado de la base de datos
	 * @param objeto Objeto que se desea eliminar
	 */
	public void eliminarObjeto(Object objeto)
	{
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try{
			tx = pm.currentTransaction();
			tx.begin();

			pm.deletePersistent(objeto);

			tx.commit();

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}

	/**
	 * Método para la eliminación de un jugador de la plantilla
	 * @param jugador Jugador que se desea eliminar de la plantilla
	 */
	public void eliminarJugadorDePlantilla (clsJugador jugador)
	{
		jugador.setUsuarioDueno(null);
		modificarJugador(jugador);
	}

	/**
	 * Método para la modificación de un jugador dado de la base de datos
	 * @param jugador Jugador que se desea modificar
	 */
	public void modificarJugador(clsJugador jugador)
	{
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try
		{
			tx.begin();

			Extent e = pm.getExtent(clsJugador.class, true);
			Iterator iter = e.iterator();
			while(iter.hasNext())
			{
				clsJugador auxjugador = (clsJugador) iter.next();
				if(auxjugador.getId() == jugador.getId())
				{
					auxjugador.setNombre(jugador.getNombre());
					auxjugador.setPuntos(jugador.getPuntos());
					auxjugador.setPosicion(jugador.getPosicion());
					auxjugador.setPrecio(jugador.getPrecio());
					auxjugador.setEquipo(jugador.getEquipo());
					auxjugador.setAlineado(jugador.isAlineado());
					auxjugador.setNumGoles(jugador.getNumGoles());
					auxjugador.setNumAsistencias(jugador.getNumAsistencias());
					auxjugador.setNumTarjetasAmarillas(jugador.getNumTarjetasAmarillas());
					auxjugador.setNumTarjetasRojas(jugador.getNumTarjetasRojas());
					auxjugador.setNumPartidosJugados(jugador.getNumPartidosJugados());
					auxjugador.setEstado(jugador.getEstado());
					auxjugador.setEnVenta(jugador.isEnVenta());
					auxjugador.setUsuarioDueno(jugador.getUsuarioDueno());
					auxjugador.setFechaVenta(jugador.getFechaVenta());
				}
			}

			tx.commit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}

	/**
	 * Método para la modificación de un usuario dado de la base de datos
	 * @param usuario Usuario que se desea modificar
	 */
	public void modificarUsuario(clsUsuario usuario)
	{
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();

		Transaction tx = pm.currentTransaction();

		try
		{
			tx.begin();

			Extent e = pm.getExtent(clsUsuario.class, true);
			Iterator iter = e.iterator();
			while(iter.hasNext())
			{
				clsUsuario auxUsuario = (clsUsuario) iter.next();
				if(auxUsuario.getEmail().equals(usuario.getEmail()))
				{
					auxUsuario.setPassword(usuario.getPassword());
					auxUsuario.setPuntuacion(usuario.getPuntuacion());
					auxUsuario.setFondos(usuario.getFondos());
					auxUsuario.setFormacion(usuario.getFormacion());
				}
			}

			tx.commit();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}
}
