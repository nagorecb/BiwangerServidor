package Biwanger.Facade;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Biwanger.ObjetosDominio.clsUsuario;
import Biwanger.AppService.clsAppServiceAdmin;
import Biwanger.AppService.clsAppServiceUser;

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
    @Path("/LoginRequest")
    public Response LoginRequest(String email, String password)
    {
        clsUsuario u = new clsUsuario();//Sacar de la BD el usuario con ese mail y password

       // String retorno = userService.InicioSesion(u);
        Response retorno = new Response();
        return retorno;
    }

    @POST
    @Path("/RegistroRequest")
    public Response RegistroRequest(String email, String password)
    {
        //boolean retorno = userService.RegistrarUser(email, password);
        Response retorno = new Response();
        return retorno;
    }

    @GET
    @Path("/PremiarTresMejores")
    public Response PremiarTresMejores()
    {
      //  ArrayList <clsUsuario> listaUsuarios = userService.PremiarTresMejores();
        Response retorno = new Response();
        return listaUsuarios;
    }
    
    @GET
    @Path("/getRequest")
    public Response getRequest()
    {

    }

}