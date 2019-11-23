package Biwanger.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;

public class clsDAO implements itfDAO
{
	static PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	static PersistenceManager pm = pmf.getPersistenceManager();
	
	@Override
	public Object guardarObjeto(Object objeto) 
	{
		//he hecho que devuelva el objeto guardado en bd para poder tener el id también
		Object objetoGuardado = null;
		
		Transaction tx = null;
		try
		{
			tx = pm.currentTransaction();
			tx.begin();
			
			objetoGuardado = pm.makePersistent(objeto);
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
		
		return objetoGuardado;
	}
	
	@Override
	public clsJugador buscarJugador(int idJugador) {
		
		clsJugador jugadorBuscado = new clsJugador();
		jugadorBuscado = pm.getObjectById(clsJugador.class, idJugador);
		
		return jugadorBuscado;
	}
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
	public void eliminarObjeto (Object objeto)
	{
		pm.deletePersistent(objeto);
	}
	
//	@Override
//	public void eliminarJugador (clsJugador jugador)
//	{
//		pm.deletePersistent(jugador);
//	}
//	@Override
//	public void eliminarUsuario (clsUsuario usuario)
//	{
//		pm.deletePersistent(usuario);
//	}
//	@Override
//	public void eliminarPuja (clsPuja puja)
//	{
//		pm.deletePersistent(puja);
//	}
	
	@Override
	public void eliminarJugadorDePlantilla (clsJugador jugador)
	{
		jugador.setUsuarioDueno(null);
		modificarObjeto(jugador);
	}
	
	@Override
	public void modificarObjeto (Object objeto) 
	{
		pm.makePersistent(objeto);
	}
	
//	@Override
//	public void modificarJugador (clsJugador jugador) 
//	{
//		 Query<clsJugador> query = pm.newQuery(
//				 "UPDATE " +clsJugador.class.getName()+
//				 " SET NOMBRE= "+ jugador.getNombre()+
//				 " AND ALINEADO="+jugador.isAlineado()+
//				 " AND ENVENTA="+jugador.isEnVenta()+
//				 " AND EQUIPO="+jugador.getEquipo()+
//				 " AND ESTADO="+jugador.getEstado()+
//				 " AND NUMASISTENCIAS="+jugador.getNumAsistencias()+
//				 " AND NUMGOLES="+jugador.getNumGoles()+
//				 " AND NUMPARTIDOSJUGADOS="+jugador.getNumPartidosJugados()+
//				 " AND NUMTARJETASAMARILLAS="+jugador.getNumTarjetasAmarillas()+
//				 " AND NUMTARJETASROJAS="+jugador.getNumTarjetasRojas()+
//				 " AND POSICION="+jugador.getPosicion()+
//				 " AND PRECIO="+jugador.getPrecio()+
//				 " AND PUNTOS="+jugador.getPrecio()+
//				 " AND EMAILUSUDUENO="+jugador.getUsuarioDueno()+
//				 " AND PLANTILLA_INTEGER_IDX="+jugador.isAlineado()+
//				 " WHERE ID = "+jugador.getId());
//		query.execute();
		
//            pm.makePersistent(jugador);
//	}
//	@Override
//	public void modificarUsuario (clsUsuario usuario) 
//	{
//		pm.makePersistent(usuario);
//	}
//	@Override
//	public void modificarPuja (clsPuja puja) 
//	{
//		pm.makePersistent(puja);
//
//	}

	@Override
	public void cerrarConexion() 
	{
		if (pm != null && !pm.isClosed()) 
		{
			pm.close();
		}
		
	}
	
}
