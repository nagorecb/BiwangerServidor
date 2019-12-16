package Biwanger.Facade;

import Biwanger.AppService.clsAppServiceAdmin;
import Biwanger.AppService.clsAppServiceUser;
import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.*;
import Biwanger.comun.clsHiloPujas;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class ServletContainer
{
    clsDAO dao = clsDAO.getInstance();
	clsAppServiceAdmin adminService = clsAppServiceAdmin.getInstance();
	clsAppServiceUser userService = clsAppServiceUser.getInstance();
	clsHiloPujas hiloPujas = clsHiloPujas.getInstance();
	 
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
    public Response crearJugadorRequest(clsJugador jugador)
    {
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

        return Response.ok(retorno).build();
    }

    @POST
    @Path("/obtenerPlantilla")
    public Response obtenerPlantilla(String email)
    {
        clsJugadorLista retorno = new clsJugadorLista(adminService.obtenerPlantilla(email));
        return Response.ok(retorno).build();
    }

    @GET
    @Path("/hardcode")
    public void hardcode()
    {
        DatosHardcoded hardcode = new DatosHardcoded();
        hardcode.metodo(dao);
        hardcode.metodo2(dao);
    }
}
