package Biwanger.Facade;

import Biwanger.AppService.clsAppServiceAdmin;
import Biwanger.AppService.clsAppServiceUser;
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
        adminService = new clsAppServiceAdmin();
        userService = new clsAppServiceUser();
    }

    @POST
    @Path("/login")
    public Response LoginRequest(String email, String password)
    {
        clsUsuario usuario = new clsUsuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        clsUsuario retorno = userService.InicioSesion(usuario);

        return Response.ok(retorno).build();
    }


    @POST
    @Path("/registro")
    public Response RegistroRequest(String email, String password)
    {
        boolean retorno = userService.RegistrarUser(email, password);
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
    public Response modificarAlineacion(clsUsuario usuario)
    {
        boolean retorno = userService.modificarAlineacion(usuario);
        return Response.ok(retorno).build();
    }

    @POST
    @Path("/modificarFormacion")
    public Response modificarFormacion(clsUsuario usuario)
    {
        boolean retorno = userService.modificarFormacion(usuario);
        return Response.ok(retorno).build();
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
        boolean retorno = userService.Pujar(puja);
        return Response.ok(retorno).build();
    }

    @POST
    @Path("/VenderJugador")
    public Response venderJugador(double precio, clsJugador jugadorVenta)
    {
        userService.venderJugador(precio, jugadorVenta);
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
    


}