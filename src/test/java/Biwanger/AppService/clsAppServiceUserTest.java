package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.databene.contiperf.junit.ContiPerfRule;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

@PerfTest(invocations = 1000)
@Required(max = 1200, average = 250)
public class clsAppServiceUserTest
{
    clsDAO DAO;
    clsAppServiceUser appservice;
    //inicio y registro
    clsUsuario usuarioEnBD;
    clsUsuario usuarioNoBD;
    clsUsuario NoAdmin;
    clsUsuario Admin;
    clsUsuario Retorno;
    boolean retornoBool;

    //Modificar formacion y plantilla

    //Compra-Venta
    clsJugador jugadorEnVenta1;
    clsJugador jugadorEnVenta2;
    clsJugador jugadorNoEnVenta;
    ArrayList <clsJugador> EnVenta;
    ArrayList <clsJugador> ArrRetorno;
    clsPuja puja1;
    ArrayList <clsPuja> pujas;
    double precioVenta;
    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @Before
    public void setUp()
    {
        DAO = new clsDAO();
        appservice = new clsAppServiceUser(DAO);
        usuarioEnBD = new clsUsuario("emailBD",  "passwordBD",  0,  0, "4-4-2");
        usuarioNoBD = new clsUsuario("emailNoBD",  "passwordNoBD",  0,  0,"4-4-2");
        NoAdmin = new clsUsuario("ADMIN",  "NOADMIN",  0,  0, "4-4-2");
        Admin = new clsUsuario("ADMIN",  "ADMIN",  0,  0, "4-4-2");
        //Meter en la BD
        DAO.guardarObjeto(usuarioEnBD);

        jugadorEnVenta1 = new clsJugador(1, "juagdor 1", 0, "delantero", 1000, "equipo1", true, 0, 0, 0, 0,0, "estado",true, usuarioEnBD.getEmail(),
                null);
        jugadorEnVenta2 = new clsJugador(2, "juagdor 2", 0, "delantero", 1000, "equipo1", true, 0, 0, 0, 0,0, "estado",true, usuarioEnBD.getEmail(),
                null);
        jugadorNoEnVenta = new clsJugador(3, "juagdor 3", 0, "delantero", 1000, "equipo1", true, 0, 0, 0, 0,0, "estado",false, usuarioEnBD.getEmail(),
                null);
        EnVenta = new ArrayList<clsJugador>();
        EnVenta.add(jugadorEnVenta1);
        EnVenta.add(jugadorEnVenta2);
        //Guardamos los tres jugadores
        DAO.guardarObjeto(jugadorEnVenta1);
        DAO.guardarObjeto(jugadorEnVenta2);
        DAO.guardarObjeto(jugadorNoEnVenta);

        puja1 = new clsPuja(usuarioEnBD.getEmail(), jugadorEnVenta1.getId(), 300);
        precioVenta = 300;
    }

    //Test funcionalidad
    @Test
    public void InicioSesionTest()
    {
        //Username ADMIN pero contraseña no válida
        Retorno = appservice.InicioSesion(NoAdmin);
        assertTrue(Retorno==null);

        //Username ADMIN y contraseña ADMIN
        Retorno = appservice.InicioSesion(Admin);
        assertTrue(Retorno.getEmail().equals("ADMIN"));
        assertTrue(Retorno.getPassword().equals("ADMIN"));

        //Intento de inicio de sesión con usuario que no está en la BD
         Retorno = appservice.InicioSesion(usuarioNoBD);
         assertTrue(Retorno == null);

        //Intento de inicio de sesión con usuario que está en la BD
        Retorno = appservice.InicioSesion(usuarioEnBD);
        assertTrue(Retorno.equals(usuarioEnBD));
    }

    @Test
    public void RegistrarUserTest()
    {
        //Probar a registrarse con un usuario que ya está registrado
        //retornoBool = appservice.RegistrarUser(usuarioEnBD);
        //assertFalse(retornoBool);
        String retorno =  appservice.RegistrarUser(usuarioEnBD);
        assertTrue(retorno.equals("No OK"));

        //Probar a regstrarse con un usuario que no está en la BD
       // retornoBool = appservice.RegistrarUser(usuarioNoBD);
        //assertTrue(retornoBool);
        retorno = appservice.RegistrarUser(usuarioNoBD);
        assertTrue(retorno.equals("OK"));
    }




    @Test
    public void ModificarFormacionTest()
    {
        usuarioEnBD.setFormacion("3-6-1");
        appservice.modificarFormacion(usuarioEnBD);
        ArrayList <clsUsuario> usuarios = DAO.leerUsuarios();
        for (clsUsuario u :usuarios)
        {
            if(u.getEmail().equals(usuarioEnBD.getEmail())) {
                usuarioEnBD = u;
            }
        }
        assertEquals(usuarioEnBD.getFormacion(), "3-6-1");
        //4-4-2 era la original al crear el objeto
        assertFalse(usuarioEnBD.getFormacion() =="4-4-2");
    }

    @Test
    public void ModificarAlineacion()
    {
        jugadorEnVenta1.setUsuarioDueno(usuarioEnBD.getEmail());
        jugadorEnVenta2.setUsuarioDueno(usuarioEnBD.getEmail());
        jugadorNoEnVenta.setUsuarioDueno(usuarioEnBD.getEmail());

        EnVenta = new ArrayList<clsJugador>();

        EnVenta.add(jugadorEnVenta1);
        EnVenta.add(jugadorEnVenta2);
        EnVenta.add(jugadorNoEnVenta);

        jugadorEnVenta1.setAlineado(false);
        jugadorEnVenta2.setPosicion("Portero");
        jugadorNoEnVenta.setPosicion("Medio-centro");

        appservice.modificarAlineacion(EnVenta);

        assertFalse(jugadorEnVenta1.isAlineado());
        assertTrue(jugadorEnVenta2.isAlineado());
        assertTrue(jugadorNoEnVenta.isAlineado());

        assertEquals(jugadorEnVenta1.getPosicion(), "delantero");
        assertEquals(jugadorEnVenta2.getPosicion(), "Portero");
        assertEquals(jugadorNoEnVenta.getPosicion(), "Medio-centro");
    }

    @Test
    public void MostratMercadoTest()
    {
        ArrRetorno = appservice.MostrarMercado();

        for (clsJugador j:ArrRetorno)
        {
            assertTrue(j.isEnVenta());
        }
    }

    @Test
    public void PujarTest()
    {
        pujas = DAO.leerPujas();
        assertFalse(pujas.contains(puja1));

        appservice.Pujar(puja1);

        pujas = DAO.leerPujas();
        assertTrue(pujas.contains(puja1));
    }
/*
    @Test
    public void venderJugadorTest() {
        jugadorNoEnVenta.setPrecio(precioVenta);
        appservice.venderJugador(jugadorNoEnVenta);
        assertFalse(usuarioEnBD.getEmail().equals(jugadorNoEnVenta.getUsuarioDueno()));
        assertFalse(jugadorNoEnVenta.isAlineado());
        assertTrue(jugadorNoEnVenta.getUsuarioDueno().equals(null));
        assertTrue(jugadorNoEnVenta.isEnVenta());
        pujas = DAO.leerPujas();
        retornoBool = false;
        for (clsPuja p : pujas) {
            if (p.getIdJugadorPuja()==jugadorNoEnVenta.getId()&& p.getEmailUsuarioPuja().equals(usuarioEnBD.getEmail())) {
                retornoBool = true;
            }
        }
        assertTrue(retornoBool);
    }
    */

    @After
    public void TearDown()
    {
        DAO.eliminarObjeto(usuarioEnBD);
        DAO.eliminarObjeto(usuarioNoBD);
        DAO.eliminarObjeto(jugadorEnVenta1);
        DAO.eliminarObjeto(jugadorEnVenta2);
        DAO.eliminarObjeto(jugadorNoEnVenta);
    }
}
