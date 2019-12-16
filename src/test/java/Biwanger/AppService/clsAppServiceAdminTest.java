package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsUsuario;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class clsAppServiceAdminTest
{
    private List<clsUsuario> listaUsuarios;
     private List<clsJugador> listaJugadores;

     private clsJugador j1,j2;
     private clsUsuario u1,u2;

     clsDAO dao = clsDAO.getInstance();
     clsAppServiceAdmin appservice = clsAppServiceAdmin.getInstance();


     private void init()
    {
        System.out.println("empiezo");

        j1 = new clsJugador(1, "Messi", 10, "DELANTERO", 500, "FCB",
                true, 10, 8, 2, 0,
                15, "buen estado", false, u1.getEmail(), null);
        dao.guardarObjeto(j1);
        listaJugadores.add(j1);

        j2 = new clsJugador(2, "Xavi", 8, "MEDIO", 500, "FCB",
                true, 10, 8, 2, 0,
                15, "buen estado", false, u1.getEmail(), null);
        dao.guardarObjeto(j2);
        listaJugadores.add(j2);

        u1 = new clsUsuario("e1", "p1", 10, 100,"4-3-3");
        dao.guardarObjeto(u1);

        u2 = new clsUsuario("e2", "p2", 8, 100, "4-3-3");
        dao.guardarObjeto(u2);
    }
    /**
    @Test
    public void PremiarTresMejores_test()
    {
        listaUsuarios = dao.leerUsuarios();
        //consultar situación inicial
        assertTrue(listaUsuarios.get(0).getFondos()==100.00);
        assertTrue(listaUsuarios.get(1).getFondos()==100.000);

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
    }**/
}

