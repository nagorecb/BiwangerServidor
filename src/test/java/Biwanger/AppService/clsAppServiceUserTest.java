
package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;
import com.github.javatlacati.contiperf.PerfTest;
import com.github.javatlacati.contiperf.Required;
import com.github.javatlacati.contiperf.junit.ContiPerfRule;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

@Required(max = 700000, average = 400000)
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

    int puntos1 = 30;
    int puntos2 = 20;
    int puntos3 = 10;
    int puntos4 = 0;

    //Modificar formacion y plantilla

    //Compra-Venta
    clsJugador jugadorEnVenta1;
    clsJugador jugadorEnVenta2;
    clsJugador jugadorNoEnVenta;
    clsJugador jugadorPujar;
    ArrayList <clsJugador> EnVenta;
    ArrayList <clsJugador> ArrRetorno;
    clsPuja puja1;
    ArrayList <clsPuja> pujas;
    double precioVenta;

    //Para ordenar usuarios
    clsUsuario usuario1;
    clsUsuario usuario2;
    clsUsuario usuario3;
    clsUsuario usuario4;
    @Rule
   public ContiPerfRule rule = new ContiPerfRule();
    @Before
    public void setUp()
    {
        DAO = clsDAO.getInstance();
        appservice = clsAppServiceUser.getInstance();

        usuarioEnBD = new clsUsuario("emailBD",  "passwordBD",  0,  0, "4-4-2");
        usuarioNoBD = new clsUsuario("emailNoBD",  "passwordNoBD",  0,  0,"4-4-2");
        NoAdmin = new clsUsuario("ADMIN",  "NOADMIN",  0,  0, "4-4-2");
        Admin = new clsUsuario("ADMIN",  "ADMIN",  0,  0, "4-4-2");
        //Meter en la BD
        DAO.guardarObjeto(usuarioEnBD);

        jugadorEnVenta1 = new clsJugador(1, "juagdor 1", puntos3, "delantero", 1000, "equipo1", true, 0, 0, 0, 0,0, "estado",true, usuarioEnBD.getEmail(),
                null);
        jugadorEnVenta2 = new clsJugador(2, "juagdor 2", puntos1, "delantero", 1000, "equipo1", true, 0, 0, 0, 0,0, "estado",true, usuarioEnBD.getEmail(),
                null);
        jugadorNoEnVenta = new clsJugador(3, "juagdor 3", puntos2, "delantero", 1000, "equipo1", true, 0, 0, 0, 0,0, "estado",false, usuarioEnBD.getEmail(),
                null);
        jugadorPujar = new clsJugador(4, "juagdor 3", puntos4, "delantero", 1000, "equipo1", true, 0, 0, 0, 0,0, "estado",false, usuarioEnBD.getEmail(),
                null);
        EnVenta = new ArrayList<clsJugador>();
        EnVenta.add(jugadorEnVenta1);
        EnVenta.add(jugadorEnVenta2);


        //Guardamos los tres jugadores
        jugadorEnVenta1 = DAO.guardarObjeto(jugadorEnVenta1);
        jugadorEnVenta2 = DAO.guardarObjeto(jugadorEnVenta2);
        jugadorNoEnVenta = DAO.guardarObjeto(jugadorNoEnVenta);
        jugadorPujar = DAO.guardarObjeto(jugadorPujar);
        puja1 = new clsPuja(usuarioEnBD.getEmail(), jugadorEnVenta1.getId(), 300);
        precioVenta = 300;


        usuario1 = new clsUsuario("email1",  "pass1",  puntos1,  0, "4-4-2");
        usuario2 = new clsUsuario("email2",  "pass2",  puntos2,  0, "4-4-2");
        usuario3 = new clsUsuario("email3",  "pass3",  puntos3,  0, "4-4-2");
        usuario4 = new clsUsuario("email4",  "pass4",  puntos4,  0, "4-4-2");

        DAO.guardarObjeto(usuario1);
        DAO.guardarObjeto(usuario2);
        DAO.guardarObjeto(usuario3);
        DAO.guardarObjeto(usuario4);

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

                @Test
                public void venderJugadorTest() {
                    jugadorNoEnVenta.setPrecio(precioVenta);
                    appservice.venderJugador(jugadorNoEnVenta);
                    assertFalse(jugadorNoEnVenta.isAlineado());
                    assertTrue(jugadorNoEnVenta.isEnVenta());
                }

                   @Test
                   public void obtenerPujasTest()
                   {
                       DAO.guardarObjeto(puja1);
                       ArrayList<clsPuja> pujas = appservice.obtenerPujas(jugadorEnVenta1);
                       assertTrue(pujas.contains(puja1));
                   }

                   @Test
                   public void obtenerUsuarioTest()
                   {
                       clsUsuario u =appservice.obtenerUsuario(usuarioEnBD.getEmail());
                       assertEquals(u, usuarioEnBD);
                   }

        @Test
        public void ordenarEquipoTest()
        {
            ArrayList <clsJugador> jugadores = new ArrayList<clsJugador>();
            jugadores.add(jugadorEnVenta1);
            jugadores.add(jugadorEnVenta2);
            jugadores.add(jugadorNoEnVenta);
            jugadores.add(jugadorPujar);

            //Tal y como los hemos metido, no están ordenados por puntos:
            boolean b = ( (jugadores.get(0).getPuntos() == puntos1) && (jugadores.get(1).getPuntos() == puntos2) && (jugadores.get(2).getPuntos() == puntos3) && (jugadores.get(3).getPuntos() == puntos4));
            assertFalse(b);
            //Ahora que ordenamos los jugadores, la condición sí se deberñia cumplir
            jugadores = appservice.ordenarEquipo(jugadores);
            b= ( (jugadores.get(0).getPuntos() == puntos1) && (jugadores.get(1).getPuntos() == puntos2) && (jugadores.get(2).getPuntos() == puntos3) && (jugadores.get(3).getPuntos() == puntos4));
            assertTrue(b);
        }

           @Test
           public void ordenarUsusariosTest()
           {
               //Para que en la lista de usuarios de la BD solo esten los que creemos ahora
               jugadorEnVenta1.setUsuarioDueno(usuario3.getEmail());
               DAO.modificarJugador(jugadorEnVenta1);
               jugadorEnVenta2.setUsuarioDueno(usuario2.getEmail());
               DAO.modificarJugador(jugadorEnVenta2);
               jugadorNoEnVenta.setUsuarioDueno(usuario1.getEmail());
               DAO.modificarJugador(jugadorNoEnVenta);
               jugadorPujar.setUsuarioDueno(usuario4.getEmail());
               DAO.modificarJugador(jugadorPujar);

               ArrayList<clsUsuario> usuarioslista = new ArrayList<clsUsuario>();
               usuarioslista.add(usuario3);
               usuarioslista.add(usuario2);
               usuarioslista.add(usuario1);
               usuarioslista.add(usuario4);

               boolean b = ( (usuarioslista.get(0).getPuntuacion() == puntos1) && (usuarioslista.get(1).getPuntuacion() == puntos2) && (usuarioslista.get(2).getPuntuacion() == puntos3) && (usuarioslista.get(3).getPuntuacion() == puntos4));
               assertFalse(b);

               ArrayList <clsUsuario> TodosUsuariosOrdenados = new ArrayList<clsUsuario>();
               ArrayList <clsUsuario> usuarios = new ArrayList<clsUsuario>();
               TodosUsuariosOrdenados = appservice.ordenarUsuarios();
               for(clsUsuario u: TodosUsuariosOrdenados)
               {
                   if(usuarioslista.contains(u))
                   {
                       usuarios.add(u);
                   }
               }

               b = ( (usuarios.get(0).getPuntuacion()==puntos1) && (usuarios.get(1).getPuntuacion()==puntos2) && (usuarios.get(2).getPuntuacion()==puntos3) && (usuarios.get(3).getPuntuacion()==puntos4) );
               assertTrue(b);
           }

    @After
    public void TearDown()
    {
        DAO.eliminarObjeto(jugadorEnVenta1);
        DAO.eliminarObjeto(jugadorEnVenta2);
        DAO.eliminarObjeto(jugadorNoEnVenta);
        DAO.eliminarObjeto(puja1);
        DAO.eliminarObjeto(usuarioEnBD);
        DAO.eliminarObjeto(usuarioNoBD);
        DAO.eliminarObjeto(usuario1);
        DAO.eliminarObjeto(usuario2);
        DAO.eliminarObjeto(usuario3);
        DAO.eliminarObjeto(usuario4);
    }
}
