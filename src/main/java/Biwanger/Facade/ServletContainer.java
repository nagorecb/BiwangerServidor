package main.java.Biwanger.Facade;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import main.java.Biwanger.AppService.*;

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class ServletContainer
{

    public ServletContainer()
    {
        clsAppServiceAdmin adminService = new clsAppServiceAdmin();
        clsAppServiceUser userService = new clsAppServiceUser();
    }

    @POST
    @Path("/LoginRequest")
    public Response LoginRequest(String email, String password)
    {
        String retorno = userService.InicioSesion(email, password);
        return retorno;
    }

    @GET
    @Path("/getRequest")
    public Response getRequest()
    {

    }

}