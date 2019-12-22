package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsUsuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

/**
 * Unit test for simple App.
 */
public class clsAppServiceAdminTest
{
    private ArrayList<clsUsuario> listaUsuarios;
    private ArrayList<clsJugador> listaJugadores;

    private clsJugador j1,j2;
    private clsUsuario u1,u2,u3;
    private int idj1, idj2;

    clsDAO dao;
    clsAppServiceAdmin appservice;

    @Before
    public void init()
    {
        dao = clsDAO.getInstance();
        appservice = clsAppServiceAdmin.getInstance();

        System.out.println("empiezo");
        listaJugadores = new ArrayList<clsJugador>();
        listaUsuarios = new ArrayList<clsUsuario>();

        u1 = new clsUsuario();
        u1.setEmail("e1");
        u1.setFondos(300.000);
        u1.setPuntuacion(10);

        u2 = new clsUsuario();
        u2.setEmail("e2");
        u2.setFondos(300.00);
        u2.setPuntuacion(10);

        u3 = new clsUsuario();
        u3.setEmail("e3");
        u3.setFondos(300.00);
        u3.setPuntuacion(8);

        j1 = new clsJugador();
        j1.setPuntos(10);
        j1.setUsuarioDueno(u1.getEmail());

        j2 = new clsJugador();
        j2.setPuntos(8);
        j2.setUsuarioDueno(u1.getEmail());

        listaUsuarios.add(u1);
        listaUsuarios.add(u2);
        listaUsuarios.add(u3);
        listaJugadores.add(j1);
        listaJugadores.add(j2);

        dao.guardarObjeto(u1);
        dao.guardarObjeto(u2);
        dao.guardarObjeto(u3);
        j1=dao.guardarObjeto(j1);
        j2=dao.guardarObjeto(j2);

        idj1=j1.getId();
        idj2=j2.getId();
    }

    @Test
    public void PremiarTresMejores_test()
    {
        listaUsuarios = dao.leerUsuarios();
        //consultar situación inicial

        assertTrue(listaUsuarios.get(0).getFondos()==300.00);
        assertTrue(listaUsuarios.get(1).getFondos()==300.00);
        assertTrue(listaUsuarios.get(2).getFondos()==300.00);

        //premiar
        listaUsuarios= (ArrayList<clsUsuario>) appservice.PremiarTresMejores();
        System.out.println(listaUsuarios.get(0).getPuntuacion()+" "+listaUsuarios.get(1).getPuntuacion() );
        System.out.println(listaUsuarios.get(0).getFondos()+ " "+listaUsuarios.get(1).getPuntuacion());
        //verificar que les suma los puntos
        assertTrue( listaUsuarios.get(0).getFondos()==3300);
        assertTrue( listaUsuarios.get(1).getFondos()==3300);
        assertTrue( listaUsuarios.get(2).getFondos()==2300);
    }

    @Test
    public void anadirPuntos_test()
    {
        assertTrue(j1.getPuntos()==10);
        appservice.anadirPuntos(j1.getId(),2);
        assertTrue(dao.buscarJugador(j1.getId()).getPuntos()==12);
    }

    @Test
    public void guardarJugador_test()
    {
        j1.setNumGoles(15);
        appservice.guardarJugador(j1);

        listaJugadores = dao.leerJugadores();
        assertTrue(dao.buscarJugador(idj1).getNumGoles()==15);
    }

    @After
    public void TearDown()
    {
        dao.eliminarObjeto(j1);
        dao.eliminarObjeto(j2);
        dao.eliminarObjeto(u1);
        dao.eliminarObjeto(u2);
        dao.eliminarObjeto(u3);
    }
}