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
	clsDAO dao = clsDAO.getInstance();

    final double PREMIO1 = 3000;
    final double PREMIO2 = 2000;
    final double PREMIO3 = 1000;

    private int pnt1=0;
    private int pnt2=0;
    private int pnt3=0;

    public static clsAppServiceAdmin instancia = null;
    public clsAppServiceAdmin (){}
    public clsAppServiceAdmin(clsDAO dao)
    {
        this.dao = dao;
    }

    public static clsAppServiceAdmin getInstance()
    {
        synchronized (clsAppServiceAdmin.class)
        {
            if(instancia == null)
            {
                instancia = new clsAppServiceAdmin();
            }
        }
        return instancia;
    }

    public List<clsUsuario> PremiarTresMejores()
    {
        List<clsUsuario> listaUsuarios;
        listaUsuarios = dao.leerUsuarios();
        listaUsuarios = listaUsuarios.stream().sorted(Comparator.comparingInt(clsUsuario ::getPuntuacion)).collect(Collectors.toList());
        Collections.reverse(listaUsuarios);

        if (!listaUsuarios.isEmpty())
        {
            pnt1=listaUsuarios.get(0).getPuntuacion();

            for (clsUsuario usuario: listaUsuarios)
            {
                if(usuario.getPuntuacion()==pnt1)
                {
                    usuario.setFondos(usuario.getFondos()+PREMIO1);
                }
                else
                {
                    if (pnt2==0)
                    {
                        pnt2 = usuario.getPuntuacion();
                        usuario.setFondos(usuario.getFondos()+PREMIO2);
                    }
                    else if (pnt2==usuario.getPuntuacion())
                    {
                        usuario.setFondos(usuario.getFondos()+PREMIO2);
                    }
                    else
                    {
                        if (pnt3==0)
                        {
                            pnt3 = usuario.getPuntuacion();
                            usuario.setFondos(usuario.getFondos()+PREMIO3);
                        }
                        else if (pnt2==usuario.getPuntuacion())
                        {
                            usuario.setFondos(usuario.getFondos()+PREMIO3);
                        }
                        else {
                            break;
                        }
                    }
                }
            }
        }
        return listaUsuarios;
    }
    
    public void anadirPuntos (int idJugador, int puntosAnadir) 
    {
    	clsJugador jugadorAnadirPuntos = dao.buscarJugador(idJugador);
    	
    	int puntosTot = jugadorAnadirPuntos.getPuntos() + puntosAnadir;
    	
    	jugadorAnadirPuntos.setPuntos(puntosTot);
    	
    	dao.modificarJugador(jugadorAnadirPuntos);
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