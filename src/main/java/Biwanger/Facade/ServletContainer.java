package Biwanger.Facade;

import Biwanger.AppService.clsAppServiceAdmin;
import Biwanger.AppService.clsAppServiceUser;
import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.*;
import Biwanger.comun.clsHiloPujas;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class ServletContainer
{
    clsDAO dao = clsDAO.getInstance();
	clsAppServiceAdmin adminService = clsAppServiceAdmin.getInstance();
	clsAppServiceUser userService = clsAppServiceUser.getInstance();
	clsHiloPujas hilo = clsHiloPujas.getInstance();

	 
    public ServletContainer()
    {

    }

    @POST
    @Path("/login")
    public Response LoginRequest(clsUsuario usuario)
    {
        clsUsuario retorno = userService.InicioSesion(usuario);
        return Response.ok(retorno).build();
    }

    @POST
    @Path("/registro")
    public Response RegistroRequest(clsUsuario usuario)
    {

        String retorno = userService.RegistrarUser(usuario);

        return Response.ok(retorno).build();
    }

    @GET
    @Path("/premiarTresMejores")
    public Response PremiarTresMejores()
    {
    	List<clsUsuario> listaUsuarios = adminService.PremiarTresMejores();
    	ArrayList<clsUsuario> arrayUsuarios = new ArrayList<clsUsuario>();

        System.out.println("En ServletContainer tras adminService");

    	for(clsUsuario aux: listaUsuarios)
        {
            arrayUsuarios.add(aux);
            System.out.println(aux.getEmail());
        }
        System.out.println("Acabado el for de List a ArrayList");

    	clsUsuarioLista retorno = new clsUsuarioLista(arrayUsuarios);

        return Response.ok(retorno).build();
    }
    
    @POST
    @Path("/modificarAlineacion")
    public void modificarAlineacion(clsJugadorLista plantilla)
    {
        userService.modificarAlineacion(plantilla.getlJugadores());
    }

    @POST
    @Path("/modificarFormacion")
    public void modificarFormacion(clsUsuario usuario)
    {
        userService.modificarFormacion(usuario);
    }

    @GET
    @Path("/MostrarMercado")
    public Response MostrarMercado()
    {
        ArrayList<clsJugador> lJugadores = userService.MostrarMercado();

        clsJugadorLista retorno = new clsJugadorLista(lJugadores);

        return Response.ok(retorno).build();
    }

    @POST
    @Path("/pujar")
    public Response Pujar(clsPuja puja)
    {
        userService.Pujar(puja);
        return Response.ok().build();
    }

    @POST
    @Path("/VenderJugador")
    public Response venderJugador(clsJugador jugadorVenta)
    {
        userService.venderJugador(jugadorVenta);
        return Response.ok().build();
    }
    
    @POST
    @Path("/anadirPuntos")
    public Response anadirPuntosRequest(@FormParam("idJugador") int idJugador, @FormParam("puntos") int puntosAnadir)
    {
        adminService.anadirPuntos(idJugador, puntosAnadir);
        return Response.ok().build();
    }
    
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime fecha = LocalDateTime.now();
        jugador.setFechaVenta(fecha.format(formatter));
        jugador.setEstado(tokens.nextToken());

        System.out.println("El cliente dice: " + camposJugador);
        adminService.guardarJugador(jugador);
        return Response.ok().build();
    }

    @GET
    @Path("/obtenerTodosUsuarios")
    public Response obtenerTodosUsuarios()
    {
        ArrayList<clsUsuario> lUsuarios = adminService.obtenerTodosUsuarios();

        clsUsuarioLista retorno = new clsUsuarioLista(lUsuarios);

        return Response.ok(retorno).build();
    }

    @GET
    @Path("/obtenerTodosJugadores")
    public Response obtenerTodosJugadores()
    {
        ArrayList<clsJugador> lJugadores = adminService.obtenerTodosJugadores();

        clsJugadorLista retorno = new clsJugadorLista(lJugadores);

        for(clsJugador aux: retorno.getlJugadores())
        {
            System.out.println("Jugador: " + aux.getNombre());
        }

        return Response.ok(retorno).build();
    }

    @POST
    @Path("/obtenerPlantilla")
    public Response obtenerPlantilla(String email)
    {
        clsJugadorLista retorno = new clsJugadorLista(adminService.obtenerPlantilla(email));
        return Response.ok(retorno).build();
    }

    @POST
    @Path("/clasificacionEquipo")
    public Response clasificacionEquipo(String email)
    {
        ArrayList<clsJugador> plantilla = adminService.obtenerPlantilla(email);
        clsJugadorLista retorno = new clsJugadorLista(userService.ordenarEquipo(plantilla));

        return Response.ok(retorno).build();
    }

    @GET
    @Path("/clasificacionUsuarios")
    public Response clasificacionUsuarios()
    {
        clsUsuarioLista retorno = new clsUsuarioLista(userService.ordenarUsuarios());

        return Response.ok(retorno).build();
    }

    @GET
    @Path("/hardcode")
    public void hardcode()
    {
        DatosHardcoded hardcode = new DatosHardcoded();
        hardcode.metodo(dao);
       // hardcode.metodo2(dao);
    }
}
