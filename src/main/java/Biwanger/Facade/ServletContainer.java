package Biwanger.Facade;

import Biwanger.AppService.clsAppServiceAdmin;
import Biwanger.AppService.clsAppServiceUser;
import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.*;
import Biwanger.comun.DatosHardcoded;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @brief Clase que conecta al cliente con el servidor
 * @details Clase que ofrece los microservicios del servidor al cliente, funcionando como fachada que recoge las peticiones del cliente y redirige a los distintos appService
 */
@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class ServletContainer
{
    clsDAO dao = clsDAO.getInstance();
	clsAppServiceAdmin adminService = clsAppServiceAdmin.getInstance();
	clsAppServiceUser userService = clsAppServiceUser.getInstance();

    /**
     * Constructor sin parámetros
     */
    public ServletContainer() { }

    /**
     * Método que recoge las peticiones de inicio de sesión
     * @param usuario usuario que quiere iniciar sesión
     * @return devuelve un Response con el usuario devuelto por userService
     */
    @POST
    @Path("/login")
    public Response LoginRequest(clsUsuario usuario)
    {
        clsUsuario retorno = userService.InicioSesion(usuario);
        return Response.ok(retorno).build();
    }

    /**
     * Método que recoge las peticiones de registro
     * @param usuario usuario que se quiere registrar
     * @return devuelve un Response con el String que indica si se ha podido o no realizar la acción
     */
    @POST
    @Path("/registro")
    public Response RegistroRequest(clsUsuario usuario)
    {

        String retorno = userService.RegistrarUser(usuario);

        return Response.ok(retorno).build();
    }

    /**
     * Método que devuelve a la petición de premiar a los tres mejores usuarios
     * @return devuelve un Response con la lista de usuarios una vez premiados
     */
    @GET
    @Path("/premiarTresMejores")
    public Response PremiarTresMejores()
    {
    	List<clsUsuario> listaUsuarios = adminService.PremiarTresMejores();

    	clsUsuarioLista retorno = new clsUsuarioLista((ArrayList<clsUsuario>)listaUsuarios);

        return Response.ok(retorno).build();
    }

    /**
     * Método que responde a las peticiones de modificar alineación
     * @param plantilla jugadores cuyos datos se van a actualizar
     * @return Response que indica que se ha podido realizar la operación
     */
    @POST
    @Path("/modificarAlineacion")
    public Response modificarAlineacion(clsJugadorLista plantilla)
    {
        userService.modificarAlineacion(plantilla.getlJugadores());

        return Response.ok().build();
    }

    /**
     * Método que responde a las peticiones de odificar la formación de un usuario
     * @param usuario usuario cuya formación se va a actualizar
     * @return Response que indica que se ha podido realizar la operación
     */
    @POST
    @Path("/modificarFormacion")
    public Response modificarFormacion(clsUsuario usuario)
    {
        userService.modificarFormacion(usuario);
        return Response.ok().build();
    }

    /**
     * Método que responde a la petición de obtener toos los jugadores en venta
     * @return devuelve una lista con los jugadores en venta dentro del Response
     */
    @GET
    @Path("/MostrarMercado")
    public Response MostrarMercado()
    {
        ArrayList<clsJugador> lJugadores = userService.MostrarMercado();

        clsJugadorLista retorno = new clsJugadorLista(lJugadores);

        return Response.ok(retorno).build();
    }

    /**
     * Método que responde a la petición de almacenar una puja realizada
     * @param puja puja a guardar
     * @return devuelve un Response indicando si se ha podido realizar la operación
     */
    @POST
    @Path("/pujar")
    public Response Pujar(clsPuja puja)
    {
        userService.Pujar(puja);
        return Response.ok().build();
    }

    /**
     * Método que responde a las peticiones de vender jugadores
     * @param jugadorVenta jugador que se quiere vender
     * @return dvuelve un Response inidicando si se ha podido realizar la operación
     */
    @POST
    @Path("/VenderJugador")
    public Response venderJugador(clsJugador jugadorVenta)
    {
        userService.venderJugador(jugadorVenta);
        return Response.ok().build();
    }

    /**
     * Método que responde a la petición de añadir puntos a los jugadores
     * @param idJugador identificativo del jugador al que se le quieren añadir los puntos
     * @param puntosAnadir puntos que se le quieren añadir al jugador
     * @return devuelve un Response indicando que la operación se ha podido realizar
     */
    @POST
    @Path("/anadirPuntos")
    public Response anadirPuntosRequest(@FormParam("idJugador") int idJugador, @FormParam("puntos") int puntosAnadir, @FormParam("asistencias") int asistencias, @FormParam("goles") int goles, @FormParam("partidos") int partidos, @FormParam("TA") int TA, @FormParam("TR") int TR)
    {
        adminService.anadirPuntos(idJugador, puntosAnadir, asistencias, goles, partidos, TA, TR);
        return Response.ok().build();
    }

    /**
     * Método que responde a las peticiones del administrador de crear jugadores
     * @param camposJugador String con todos los campos introducidos para la creación del jugador
     * @return devuelve un Response indicando que la operación se ha realizado con éxito
     */
    @POST
    @Path("/crearJugador")
    public Response crearJugadorRequest(String camposJugador)
    {
        clsJugador jugador = new clsJugador();
        StringTokenizer tokens=new StringTokenizer(camposJugador, ",");
        jugador.setNombre(tokens.nextToken());
        jugador.setPosicion(tokens.nextToken());
        jugador.setPrecio(Double.parseDouble(tokens.nextToken()));
        jugador.setEquipo(tokens.nextToken());
        jugador.setEnVenta(Boolean.parseBoolean(tokens.nextToken()));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime fecha = LocalDateTime.now();
        jugador.setFechaVenta(fecha.format(formatter));
        jugador.setEstado(tokens.nextToken());

        adminService.guardarJugador(jugador);
        return Response.ok().build();
    }

    /**
     * Método que responde a las peticiones para obtener los usuarios
     * @return Devuelve un response con la lista de todos los usuarios
     */
    @GET
    @Path("/obtenerTodosUsuarios")
    public Response obtenerTodosUsuarios()
    {
        ArrayList<clsUsuario> lUsuarios = adminService.obtenerTodosUsuarios();

        clsUsuarioLista retorno = new clsUsuarioLista(lUsuarios);

        return Response.ok(retorno).build();
    }

    /**
     * Método que responde a las peticiones para obtener los jugadores
     * @return Devuelve un response con la lista de todos los jugadores
     */
    @GET
    @Path("/obtenerTodosJugadores")
    public Response obtenerTodosJugadores()
    {
        ArrayList<clsJugador> lJugadores = adminService.obtenerTodosJugadores();

        clsJugadorLista retorno = new clsJugadorLista(lJugadores);

        return Response.ok(retorno).build();
    }

    /**
     * Método para responder a las peticiones de obtener la plantilla de un usuario
     * @param email identificador del usuario cuya plantilla se quiere obtener
     * @return devuelve un Response con la plantilla del usuario
     */
    @POST
    @Path("/obtenerPlantilla")
    public Response obtenerPlantilla(String email)
    {
        clsJugadorLista retorno = new clsJugadorLista(adminService.obtenerPlantilla(email));
        return Response.ok(retorno).build();
    }

    /**
     * Método que responde a las peticiones de obtener la clasificación de jugadores de un usuario
     * @param email identificativo del usuario cuya plantilla se quiere ordenar
     * @return devuelve un Response con la lista de jugadores del usuario ordenada
     */
    @POST
    @Path("/clasificacionEquipo")
    public Response clasificacionEquipo(String email)
    {
        ArrayList<clsJugador> plantilla = adminService.obtenerPlantilla(email);
        clsJugadorLista retorno = new clsJugadorLista(userService.ordenarEquipo(plantilla));

        return Response.ok(retorno).build();
    }

    /**
     * Método que responde a la petición de obtener la lista de todos los usuarios ordenados por puntuación
     * @return devuelve un Response con la lista de usuarios ordenada
     */
    @GET
    @Path("/clasificacionUsuarios")
    public Response clasificacionUsuarios()
    {
        clsUsuarioLista retorno = new clsUsuarioLista(userService.ordenarUsuarios());

        return Response.ok(retorno).build();
    }
}
