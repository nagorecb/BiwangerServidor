package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @brief clase de lógica de negocio de _usuario_
 * @details Esta clase realiza todas las acciones de negocio relativas a la parte de "usuario", es decir, aquí es donde se da funcionalidad a los servicios de la parte usuario del programa. es aquí donde se realizan las acciones solicitadas por la parte cliente, siendo la capa que accede a la base de datos a través de DAO
 */
public class clsAppServiceUser
{
    private static clsDAO dao = clsDAO.getInstance();
    private static clsAppServiceUser instancia = null;

    /**
     * Constructor sin parámetros
     */
    public clsAppServiceUser(){ }

    /**
     * Método para comprobar si la persona que intenta iniciar sesión es el administrador o un usuario válido o no.
     * @param usuario es el objeto de tipo usuario con las credenciales introducidas por el cliente en la interfaz
     * @return devuelve un usuario
     * @retval usuario a null: el usuario no está en la BD y no puede iniciar sesión
     * @retval usuario con email y password a ADMIN: el cliente es el administrador
     * @retval usuario con email y password enviados: el usuario es correcto, por lo que se pasa el objeto con toda la información de la BD
     */
    public clsUsuario InicioSesion(clsUsuario usuario)
    {
        ArrayList<clsUsuario> lUsuarios;
        clsUsuario u = null;

        //Primero, vemos si las credenciales son de administrador
        if(usuario.getEmail().equals("ADMIN"))
        {
            if (usuario.getPassword().equals("ADMIN"))
            {
                u = usuario;
            }
        }//Si las credenciales no son de administrador, vemos si coinciden con un usuario normal
        else
        {
            lUsuarios = dao.leerUsuarios();

            for (clsUsuario u1 : lUsuarios)
            {
                if (u1.equals(usuario))
                {
                    if (u1.getPassword().equals(usuario.getPassword()))
                    {
                        u = u1;
                    }
                }
            }
        }

        return u;
    }

    /**
     * Método para la implementación del patrón Singleton
     * @return devuelve la instancia de tipo clsAppServiceUser instanciada en esta clase para asegurar que siempre hay viva una sola instancia
     */
    public static clsAppServiceUser getInstance()
    {
        synchronized (clsAppServiceUser.class)
        {
            if(instancia == null)
            {
                instancia = new clsAppServiceUser();
            }
        }
        return instancia;
    }

    /**
     * Método para registrar usuarios
     * @param user es el objeto de tipo usuario con las credenciales del nuevo usuario que se quiere registrar
     * @return devuelve un String con el estado del registro
     * @retval OK El usuario no estaba el la BD, por lo que se ha podido registrar
     * @retval No OK El usuario estaba el la BD, por lo que no se ha podido registrar
     */
    public String RegistrarUser(clsUsuario user)
    {
        clsUsuario aux = dao.buscarUsuario(user.getEmail());

        if(aux == null)
        {
            dao.guardarObjeto(user);
            return "OK";
        } else
        {
            return "No OK";
        }
    }

    /**
     * Método que actualza la formación del usuario pasado por parámetro. Se lee toda la lista de usuarios y se modifica aquel cuyo email coincide con el del usuario pasado
     * @param usuario usuario con la formación actualizada que se quiere modificar en la BD
     */
    public void modificarFormacion (clsUsuario usuario)
    {
        ArrayList <clsUsuario> listUsuarios;

        listUsuarios = dao.leerUsuarios();

        for(clsUsuario u: listUsuarios) {
            if (u.equals(usuario))
            {
                u.setFormacion(usuario.getFormacion());
                dao.modificarUsuario(u);
            }
        }
    }

    /**
     * Método para modificar el estado de los jugadores de la plantilla de un usuario una vez modificada su alineación.
     * Para ello, recorremos la lista y vamos actualizando su estado en la BD, por si su atributo "alineado" ha cambiado
     * @param plantilla es la plantilla del usuario que ha cambiado su alineación
     */
    public void modificarAlineacion (ArrayList<clsJugador> plantilla)
    {
        for (clsJugador jugador: plantilla)
        {
            dao.modificarJugador(jugador);
        }
    }

    /**
     * Método que proporciona una lista de todos los jugadores que están en venta. lee todos los jugadores de la BD y devuelve únicamente aquellos que están en venta
     * @return lista de jugadores en venta
     */
    public ArrayList<clsJugador> MostrarMercado()
    {
        ArrayList<clsJugador> lTodosJugadores;
        ArrayList<clsJugador> lJugadoresEnVenta = new ArrayList<clsJugador>();

        lTodosJugadores = dao.leerJugadores();

        for(clsJugador aux: lTodosJugadores)
        {
            if(aux.isEnVenta())
            {
                lJugadoresEnVenta.add(aux);
            }
        }

        return lJugadoresEnVenta;
    }

    /**
     * Método que almacena las pujas realizadas en la BD, sñolo hace de intermediario entre la parte servidora y la BD
     * @param puja puja que se quiere almacenar
     */
    public void Pujar(clsPuja puja)
    {
        dao.guardarObjeto(puja);
    }

    /**
     * Método para poner en venta a un jugador. Se modifican los atributos correspondientes a la venta(alineado, enVenta y fechaVenta) y se actualiza en la BD
     * @param jugadorVenta jugador que se va a poner en venta
     */
    public void venderJugador(clsJugador jugadorVenta)
    {
        jugadorVenta.setAlineado(false);
        jugadorVenta.setEnVenta(true);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime fecha = LocalDateTime.now();
        jugadorVenta.setFechaVenta(fecha.format(formatter));

        dao.modificarJugador(jugadorVenta);
    }

    /**
     * Método que devuelve todas las pujas realizadas en torno a un mismo jugador
     * @param jugador jugador cuyas pujas se quieren obtener
     * @return devuelve la lista de pujas para el jugador pasasdo por parámetro
     */
    public ArrayList<clsPuja> obtenerPujas(clsJugador jugador)
    {
        ArrayList<clsPuja> lTodasPujas = dao.leerPujas();
        ArrayList<clsPuja> lPujasDelJugador = new ArrayList<clsPuja>();

        for(clsPuja aux: lTodasPujas)
        {
            if(aux.getIdJugadorPuja() == jugador.getId())
            {
                lPujasDelJugador.add(aux);
            }
        }

        return lPujasDelJugador;
    }

    /**
     * Método que devuelve el usuario de la base de datos cuyo email coincide con el pasado por parámetro
     * @param email email del usuario a buscar
     * @return devuelve el usuario encontrado en la base de datos
     */
    public clsUsuario obtenerUsuario(String email)
    {
        clsUsuario retorno = new clsUsuario();
        ArrayList<clsUsuario> lUsuarios = dao.leerUsuarios();

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
     * Método para ordenar un equipo de jugadores de manera descendente según su puntuación
     * @param equipo lista de jugadores que se quiere ordenar
     * @return devuelve la lista de jugadores ordenada
     */
    public ArrayList<clsJugador> ordenarEquipo(ArrayList<clsJugador> equipo)
    {
        ArrayList<clsJugador> retorno = equipo;
        retorno = (ArrayList<clsJugador>) retorno.stream().sorted(Comparator.comparingInt(clsJugador ::getPuntos)).collect(Collectors.toList());
        Collections.reverse(retorno);
        return retorno;
    }

    /**
     * Método que ordena todos los usuarios de la base de datos de manera descendente segúm su puntuación
     * @return devuelve la lista de los usuarios ordenada
     */
    public ArrayList<clsUsuario> ordenarUsuarios()
    {
        ArrayList<clsUsuario> retorno = dao.leerUsuarios();
        retorno = (ArrayList<clsUsuario>) retorno.stream().sorted(Comparator.comparingInt(clsUsuario ::getPuntuacion)).collect(Collectors.toList());
        Collections.reverse(retorno);
        return retorno;
    }
}

