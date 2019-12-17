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

public class clsDAO
{

	private static clsDAO instancia= new clsDAO();
	static PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	static PersistenceManager pm = pmf.getPersistenceManager();

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

	public clsJugador guardarObjeto(Object objeto)
	{
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
		}
		System.out.println("Empiezo a guardar en base de datos");
		return jugador;
	}

	public clsJugador buscarJugador(int idJugador) {

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
		}

		return jugadorBuscado;
	}

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

	public ArrayList<clsJugador> leerJugadores() 
	{
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
		}
		
		return listaJugadores;
	}

	public ArrayList<clsUsuario> leerUsuarios() 
	{
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
		}
		
		return listaUsuarios;
	}

	public ArrayList<clsPuja> leerPujas() 
	{
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
		}
		
		return listaPujas;
	}

	public void eliminarObjeto(Object objeto)
	{
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
		}
	}
	

	public void eliminarJugadorDePlantilla (clsJugador jugador)
	{
		jugador.setUsuarioDueno(null);
		modificarObjeto(jugador);
	}

	public void modificarObjeto (Object objeto) 
	{
		Transaction tx = null;

		try
		{
			tx = pm.currentTransaction();
			tx.begin();
			
			pm.makePersistent(objeto);
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
		}
	}

	public void modificarJugador(clsJugador jugador)
	{
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
		}
	}

	public void modificarUsuario(clsUsuario usuario)
	{
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
		}
	}

	public void cerrarConexion() 
	{
		if (pm != null && !pm.isClosed()) 
		{
			pm.close();
		}
		
	}
	
}
