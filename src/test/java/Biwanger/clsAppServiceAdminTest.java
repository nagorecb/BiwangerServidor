package Biwanger;

import Biwanger.AppService.clsAppServiceAdmin;
import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsUsuario;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class clsAppServiceAdminTest
{

    private List<clsUsuario> listaUsuarios;
    private List<clsJugador> listaJugadores;

    private clsJugador j1,j2;
    private clsUsuario u1,u2;

    clsDAO dao = new clsDAO();
    clsAppServiceAdmin appservice = new clsAppServiceAdmin();

    @Before
    public void init()
    {
        j1 = new clsJugador(1, "Messi", 10, "DELANTERO", 500, "FCB",
                true, 10, 8, 2, 0,
                15, "buen estado", false, u1, null , null);
        dao.guardarObjeto(j1);
        listaJugadores.add(j1);

        j2 = new clsJugador(2, "Xavi", 8, "MEDIO", 500, "FCB",
                true, 10, 8, 2, 0,
                15, "buen estado", false, u1, null , null);
        dao.guardarObjeto(j2);
        listaJugadores.add(j2);

        u1 = new clsUsuario("e1", "p1", 10, 100, listaJugadores,"4-3-3");
        dao.guardarObjeto(u1);

        u2 = new clsUsuario("e2", "p2", 8, 100, null,"4-3-3");
        dao.guardarObjeto(u2);
    }

    @Test
    public void PremiarTresMejores_test()
    {
        listaUsuarios = dao.leerUsuarios();
        //consultar situación inicial
        assertTrue(listaUsuarios.get(0).getFondos()==100);
        assertTrue(listaUsuarios.get(1).getFondos()==100);

        //premiar
        listaUsuarios=appservice.PremiarTresMejores();

        //verificar que les suma los puntos
        assertTrue( listaUsuarios.get(0).getFondos()==3100);
        assertTrue( listaUsuarios.get(0).getFondos()==2100);
    }

    @Test
    public void anadirPuntos_test()
    {
        assertTrue(j1.getPuntos()==10);
        appservice.anadirPuntos(1,2);
        assertTrue(dao.buscarJugador(1).getPuntos()==12);
    }

    @Test
    public void guardarJugador_test()
    {
        j1.setNumGoles(15);
        appservice.guardarJugador(j1);

        listaJugadores = dao.leerJugadores();
        assertTrue(dao.buscarJugador(1).getNumGoles()==15);
    }
}
