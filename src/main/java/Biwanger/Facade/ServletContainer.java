package Biwanger.Facade;

import Biwanger.AppService.clsAppServiceAdmin;
import Biwanger.AppService.clsAppServiceUser;
import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class ServletContainer
{
	clsAppServiceAdmin adminService;
	clsAppServiceUser userService;
	 
    public ServletContainer()
    {
        clsDAO dao = new clsDAO();
        adminService = new clsAppServiceAdmin(dao);
        userService = new clsAppServiceUser(dao);
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
    	List <clsUsuario> listaUsuarios = adminService.PremiarTresMejores();
        return Response.ok(listaUsuarios).build();
    }
    
    @POST
    @Path("/modificarAlineacion")
    public void modificarAlineacion(clsUsuario usuario)
    {
        userService.modificarAlineacion(usuario);
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
        return Response.ok(lJugadores).build();
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
    public Response crearJugadorRequest(clsJugador jugador)
    {
        adminService.guardarJugador(jugador);
        return Response.ok().build();
    }

    @GET
    @Path("/obtenerTodosUsuarios")
    public Response obtenerTodosUsuarios()
    {
        ArrayList<clsUsuario> lUsuarios = userService.obtenerTodosUsuarios();
        return Response.ok(lUsuarios).build();
    }
}