package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsUsuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class clsAppServiceAdmin
{
	clsDAO dao;

    final double PREMIO1 = 3000;
    final double PREMIO2 = 2000;
    final double PREMIO3 = 1000;

    public clsAppServiceAdmin(clsDAO dao)
    {
        this.dao = dao;
    }

    public List<clsUsuario> PremiarTresMejores()
    {
        List<clsUsuario> listaUsuarios;

        System.out.println(7777777);
        listaUsuarios = dao.leerUsuarios();
        listaUsuarios = listaUsuarios.stream().sorted(Comparator.comparingInt(clsUsuario ::getPuntuacion)).collect(Collectors.toList());
        Collections.reverse(listaUsuarios);
        System.out.println(88888888);
        listaUsuarios.get(0).setFondos(listaUsuarios.get(0).getFondos()+PREMIO1);
        listaUsuarios.get(1).setFondos(listaUsuarios.get(1).getFondos()+PREMIO2);
        listaUsuarios.get(2).setFondos(listaUsuarios.get(2).getFondos()+PREMIO3);
        System.out.println(999999);
        dao.modificarObjeto(listaUsuarios.get(0));
        dao.modificarObjeto(listaUsuarios.get(1));
        dao.modificarObjeto(listaUsuarios.get(2));
        System.out.println(10000000);
        return listaUsuarios;
    }
    
    public void anadirPuntos (int idJugador, int puntosAnadir) 
    {
    	clsJugador jugadorAnadirPuntos = dao.buscarJugador(idJugador);
    	
    	int puntosTot = jugadorAnadirPuntos.getPuntos() + puntosAnadir;
    	
    	jugadorAnadirPuntos.setPuntos(puntosTot);
    	
    	dao.modificarObjeto(jugadorAnadirPuntos);
    }
    
    
    public void guardarJugador (clsJugador jugador) 
    {
    	dao.guardarObjeto(jugador);
    }

    public ArrayList<clsUsuario> obtenerTodosUsuarios()
    {
        ArrayList<clsUsuario> lUsuarios = dao.leerUsuarios();

        lUsuarios = (ArrayList<clsUsuario>) lUsuarios.stream().sorted(Comparator.comparingInt(clsUsuario ::getPuntuacion)).collect(Collectors.toList());
        Collections.reverse(lUsuarios);

        return lUsuarios;
    }

    public ArrayList<clsJugador> obtenerTodosJugadores()
    {
        ArrayList<clsJugador> lJugadores = dao.leerJugadores();

        return lJugadores;
    }

    public ArrayList<clsJugador> obtenerPlantilla(String email)
    {
        ArrayList<clsJugador> lTodosJugadores = obtenerTodosJugadores();
        ArrayList<clsJugador> Plantilla = new ArrayList<clsJugador>();

        for(clsJugador aux: lTodosJugadores)
        {
            if(aux.getUsuarioDueno().equals(email))
            {
                Plantilla.add(aux);
            }
        }

        return Plantilla;
    }

}