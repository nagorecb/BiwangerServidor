package Biwanger.Facade;

import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.when;

public class ServletContainerTest
{
    @Mock
    private ServletContainer fachada;

    clsUsuario usuarioRegistrado;
    clsUsuario usuarioNORegistrado;

    private clsUsuario usuario1;
    private clsUsuario usuario2;
    private clsUsuario usuario3;
    private int puntuacion;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        fachada = new ServletContainer();

        usuarioRegistrado = new clsUsuario();
        usuarioNORegistrado = new clsUsuario();

        usuarioRegistrado.setEmail("Registrado");
        usuarioRegistrado.setPassword("Registrado");
        usuarioNORegistrado.setEmail("NORegistrado");
        usuarioNORegistrado.setPassword("NORegistrado");

        puntuacion = 100;
        usuario1 = new clsUsuario("test1", "test1", puntuacion+3, 0, null, null);
        usuario2 = new clsUsuario("test2", "test2", puntuacion+2, 0, null, null);
        usuario3 = new clsUsuario("test3", "test3", puntuacion+1, 0, null, null);
    }

    @After
    public void tearDown()
    {
        //Eliminar usuario1, 2 y 3 de la BBDD
    }

    @Test
    public void testRegistro()
    {
        when(fachada.userService.RegistrarUser("Registrado", "Registrado")).thenReturn(true);
        when(fachada.userService.RegistrarUser("NORegistrado", "NORegistrado")).thenReturn(false);

        boolean correcta = fachada.userService.RegistrarUser(usuarioRegistrado.getEmail(), usuarioRegistrado.getPassword());
        boolean incorrecta = fachada.userService.RegistrarUser(usuarioNORegistrado.getEmail(), usuarioNORegistrado.getPassword());

        assertTrue(correcta);
        assertFalse(incorrecta);
    }

    @Test
    public void testLogin()
    {
        clsUsuario usuarioRecibidoOK = new clsUsuario();
        clsUsuario usuarioRecibidoNO = new clsUsuario();

        when(fachada.userService.InicioSesion(usuarioRegistrado)).thenReturn(usuarioRegistrado);
        when(fachada.userService.InicioSesion(usuarioNORegistrado)).thenReturn(new clsUsuario());

        usuarioRecibidoOK = fachada.userService.InicioSesion(usuarioRegistrado);
        usuarioRecibidoNO = fachada.userService.InicioSesion(usuarioNORegistrado);

        assertEquals(usuarioRecibidoOK.getEmail(), usuarioRegistrado.getEmail());
        assertFalse(usuarioRecibidoNO.equals(usuarioNORegistrado));
    }

    @Test
    public void testPremiar()
    {
        //Crear lista con usuarios ya premiados para devolver
        List<clsUsuario> listPremiados = new ArrayList<>();

        //listPremiados.add(new clsUsuario().setFondos(usuario1.getFondos()+3000));
        //listPremiados.add(new clsUsuario().setFondos(usuario2.getFondos()+2000));
        //listPremiados.add(new clsUsuario().setFondos(usuario3.getFondos()+1000));

        when(fachada.adminService.PremiarTresMejores()).thenReturn(listPremiados);

        fachada.adminService.PremiarTresMejores();
        assertEquals(listPremiados.get(0).getFondos(), usuario1.getFondos()+3000);
        assertEquals(listPremiados.get(1).getFondos(), usuario2.getFondos()+2000);
        assertEquals(listPremiados.get(2).getFondos(), usuario3.getFondos()+1000);
    }

    @Test
    public void testPujar()
    {
        clsPuja puja = new clsPuja();

        when (fachada.userService.Pujar(puja)).thenReturn(true);

        assertTrue(fachada.userService.Pujar(puja));
    }
}
