package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsUsuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @brief clase de lógica de negocio de administrador
 * @details Esta clase realiza todas las acciones de negocio relativas a la parte de "administrador", es decir, aquí es donde se da funcionalidad a los servicios de la parte administrador del programa. es aquí donde se realizan las acciones solicitadas por la parte cliente, siendo la capa que accede a la base de datos a través de DAO
 */
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

    /**
     * Cosntructor sin parámetros
     */
    public clsAppServiceAdmin (){}

    /**
     * Método para la implementación del patrón Singleton
     * @return devuelve la instancia de tipo clsAppServiceAdmin instanciada en esta clase para asegurar que siempre hay viva una sola instancia
     */
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

    /**
     * Método que premia con los fondos instanciados como atributos de esta clase a los usuarios con las tres puntuaciones más añtas
     * @return devuelve la lista de usuarios con los fondos actualizados
     */
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
                        else if (pnt3==usuario.getPuntuacion())
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

    /**
     * Método para añadir puntos al jugador seleccionado. el administrador elige en la parte cliente cuántos puntos añadir a qué jugador
     * @param idJugador identificativo del jugador al que le vamos a añadir los puntos
     * @param puntosAnadir puntos que se le van a añadir
     */
    public void anadirPuntos (int idJugador, int puntosAnadir, int asistencias, int goles, int partidos, int TA, int TR)
    {
    	clsJugador jugadorAnadirPuntos = dao.buscarJugador(idJugador);
    	
    	int puntosTot = jugadorAnadirPuntos.getPuntos() + puntosAnadir;
    	jugadorAnadirPuntos.setPuntos(puntosTot);

    	int asistenciasTot = jugadorAnadirPuntos.getNumAsistencias() + asistencias;
        jugadorAnadirPuntos.setNumAsistencias(asistenciasTot);

        int golesTot = jugadorAnadirPuntos.getNumGoles() + goles;
        jugadorAnadirPuntos.setNumGoles(golesTot);

        int partidosTot = jugadorAnadirPuntos.getNumPartidosJugados()+ partidos;
        jugadorAnadirPuntos.setNumPartidosJugados(partidosTot);

        int TAtot = jugadorAnadirPuntos.getNumTarjetasAmarillas() + TA;
        jugadorAnadirPuntos.setNumTarjetasAmarillas(TAtot);

        int TRtot = jugadorAnadirPuntos.getNumTarjetasRojas() + TR;
        jugadorAnadirPuntos.setNumTarjetasRojas(TRtot);

    	dao.modificarJugador(jugadorAnadirPuntos);
    }

    /**
     * Método para guardar un jugador en la BD después de que el administrador haya introducido los datos
     * @param jugador jugador a guardar en la BD
     */
    public void guardarJugador (clsJugador jugador) 
    {
    	dao.guardarObjeto(jugador);
    }

    /**
     * Método para obtener una lista de tosdos los usuarios de la BD, ordenados de manera descendente por su puntuación
     * @return devuelve la lista de usuarios ordenada
     */
    public ArrayList<clsUsuario> obtenerTodosUsuarios()
    {
        ArrayList<clsUsuario> lUsuarios = dao.leerUsuarios();

        lUsuarios = (ArrayList<clsUsuario>) lUsuarios.stream().sorted(Comparator.comparingInt(clsUsuario ::getPuntuacion)).collect(Collectors.toList());
        Collections.reverse(lUsuarios);

        return lUsuarios;
    }

    /**
     * Métdodo para devolver la lista de todos los jugadores de la BD
     * @return devuelve la lista de jugadores
     */
    public ArrayList<clsJugador> obtenerTodosJugadores()
    {
        ArrayList<clsJugador> lJugadores = dao.leerJugadores();

        return lJugadores;
    }

    /**
     * Método que devuelve la plantilla correspondiente al usuario que tiene el email pasado por parámetro
     * @param email primary key del usuario cuya plantilla queremos pbtener
     * @return devuelve la lista de jugadores correspondientes al usuario en cuestión
     */
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