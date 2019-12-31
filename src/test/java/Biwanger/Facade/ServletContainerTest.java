package Biwanger.Facade;

import Biwanger.AppService.clsAppServiceAdmin;
import Biwanger.AppService.clsAppServiceUser;
import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;
import Biwanger.ObjetosDominio.clsUsuarioLista;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServletContainerTest
{
    private static final String RESPUESTA_USUARIO_REGISTRADO = "usuarioRegistrado";
    private static final String RESPUESTA_USUARIO_LOGUEADO = "usuarioLogueado";

	@InjectMocks
    private ServletContainer servletContainer;
    
    @Mock
    private clsDAO dao;
    @Mock
	private clsAppServiceAdmin adminService;
    @Mock
	private clsAppServiceUser userService;

    @Test
    public void testRegistro()
    {
        clsUsuario usuario = crearUsuario();
        when(this.userService.RegistrarUser(usuario)).thenReturn(RESPUESTA_USUARIO_REGISTRADO);
        
		Response respuestaRegistroRequest = this.servletContainer.RegistroRequest(usuario);

        assertEquals(respuestaRegistroRequest.getEntity(), RESPUESTA_USUARIO_REGISTRADO);
        assertEquals(respuestaRegistroRequest.getStatusInfo().getReasonPhrase(), "OK");
    }

    @Test
    public void testLogin()
    {
        clsUsuario usuarioEnviado = crearUsuario();
        clsUsuario usuarioLogueado = crearUsuario();
        usuarioLogueado.setFondos(300);

        when(this.userService.InicioSesion(usuarioEnviado)).thenReturn(usuarioLogueado);
        
        Response respuestaLoginRequest = this.servletContainer.LoginRequest(usuarioEnviado);
        
        assertEquals(respuestaLoginRequest.getEntity(), usuarioLogueado);
        assertEquals(respuestaLoginRequest.getStatusInfo().getReasonPhrase(), "OK");
    }

    @Test
    public void testPremiar()
    {
        ArrayList<clsUsuario> listPremiados = crearUsuarios();
        clsUsuarioLista usuLista = new clsUsuarioLista(listPremiados);

        when(this.adminService.PremiarTresMejores()).thenReturn(listPremiados);
        

        Response respuestaTresPremiadosRequest = this.servletContainer.PremiarTresMejores();
        
        assertEquals(respuestaTresPremiadosRequest.getEntity(),usuLista);
        assertEquals(respuestaTresPremiadosRequest.getStatusInfo().getReasonPhrase(), "OK");
    }
    
    private clsUsuario crearUsuario() {
		clsUsuario usuario = new clsUsuario();
        usuario.setEmail("emailUsuario");
        usuario.setPassword("passUsuario");
		return usuario;
	}
    
    private  ArrayList<clsUsuario> crearUsuarios()
    {
        int puntuacion = 100;
        clsUsuario usuario1 = new clsUsuario("test1", "test1", puntuacion+3, 0, null);
        clsUsuario usuario2 = new clsUsuario("test2", "test2", puntuacion+2, 0, null);
        clsUsuario usuario3 = new clsUsuario("test3", "test3", puntuacion+1, 0, null);
        
        ArrayList<clsUsuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);
        listaUsuarios.add(usuario3);
        
        return listaUsuarios;

    }
}
