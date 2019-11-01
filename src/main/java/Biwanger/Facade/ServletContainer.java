package Biwanger.Facade;

import java.util.List;

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
    @Path("/login")
    public Response LoginRequest(String email, String password)
    {
        clsUsuario usuario = new clsUsuario();//select: Sacar de la BD el usuario con ese mail y password
        return Response.ok(usuario).build();
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

}