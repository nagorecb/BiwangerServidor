package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsUsuario;
//import org.databene.contiperf.*;
//import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.javatlacati.contiperf.PerfTest;
import com.github.javatlacati.contiperf.Required;
import com.github.javatlacati.contiperf.junit.ContiPerfRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;

@Required(max = 800000, average = 800000)
public class clsAppServiceAdminTest
{
    private ArrayList<clsUsuario> listaUsuarios;
    private ArrayList<clsJugador> listaJugadores;

    private clsJugador j1,j2;
    private clsUsuario u1,u2,u3;
    private int idj1, idj2;

    clsDAO dao;
    clsAppServiceAdmin appservice;

   @Rule
   public ContiPerfRule rule = new ContiPerfRule();
    
    @Before
    public void init()
    {
        dao = clsDAO.getInstance();
        appservice = clsAppServiceAdmin.getInstance();

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
        //premiar
        ArrayList<clsUsuario> listaUsuariosPremiados = (ArrayList<clsUsuario>) appservice.PremiarTresMejores();
        
        //verificar que les suma los puntos
        listaUsuarios = dao.leerUsuarios();
        List<clsUsuario> listaUsuariosOrdenada = listaUsuarios.stream().sorted(Comparator.comparingInt(clsUsuario ::getPuntuacion)).collect(Collectors.toList());
        Collections.reverse(listaUsuariosOrdenada);
        assertTrue( listaUsuariosPremiados.get(0).getFondos()==listaUsuariosOrdenada.get(0).getFondos());
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

    @Test
    public void obtenerTodosUsuarios_test()
    {
        ArrayList <clsUsuario> usuarios = appservice.obtenerTodosUsuarios();
        assertTrue(usuarios.contains(u1)&&usuarios.contains(u2)&&usuarios.contains(u3));
    }

    @Test
    public void obtenertodosJugadores_test()
    {
        ArrayList <clsJugador> jugadores = appservice.obtenerTodosJugadores();
        assertTrue(jugadores.contains(j1)&&jugadores.contains(j2));
    }

    @Test
    public void obtenerPlantilla_test()
    {
        ArrayList <clsJugador> jugadoresU1 = appservice.obtenerPlantilla(u1.getEmail());
        assertTrue(jugadoresU1.contains(j1)&&jugadoresU1.contains(j2));
        ArrayList <clsJugador> jugadoresU2 = appservice.obtenerPlantilla(u2.getEmail());
        assertTrue(jugadoresU2.isEmpty());
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