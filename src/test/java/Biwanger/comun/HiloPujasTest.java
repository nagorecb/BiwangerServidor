package Biwanger.comun;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class HiloPujasTest
{
    private clsHiloPujas hilo;

    private clsUsuario vendedor;
    private clsUsuario comprador;

    private clsJugador enVentaPasado;
    private clsJugador enVentaActual;

    private clsPuja PujaPasadaAlta ;
    private clsPuja PujaPasadaBaja;
    private clsPuja PujaActualAlta;
    private clsPuja PujaActualBaja;

    clsDAO dao = new clsDAO();

    @Before
    public void setUp()
    {
        vendedor = new clsUsuario("vendedor", "vendedor", 0, 0, null);
        comprador = new clsUsuario("comprador", "comprador", 0, 0, null);

        enVentaPasado = new clsJugador();
        enVentaActual = new clsJugador();

        enVentaPasado.setId(0);
        enVentaActual.setId(1);

        enVentaPasado.setEnVenta(true);
        enVentaActual.setEnVenta(true);

        enVentaPasado.setUsuarioDueno(vendedor.getEmail());
        enVentaActual.setUsuarioDueno(vendedor.getEmail());

        //Año, mes, hora y minutos igual que momento actual, pero día -1
        enVentaPasado.setFechaVenta(LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth()-1, LocalTime.now().getHour(), LocalTime.now().getMinute()));
        enVentaActual.setFechaVenta(LocalDateTime.now());

        PujaPasadaAlta = new clsPuja(comprador.getEmail(), enVentaPasado.getId(), 1000);
        PujaPasadaBaja = new clsPuja(comprador.getEmail(), enVentaPasado.getId(), 500);

        PujaActualAlta = new clsPuja(comprador.getEmail(), enVentaActual.getId(), 1000);
        PujaActualBaja = new clsPuja(comprador.getEmail(), enVentaActual.getId(), 500);

        ArrayList<clsPuja> listPujasPasadas = new ArrayList<clsPuja>();
        ArrayList<clsPuja> listPujasActuales = new ArrayList<clsPuja>();

        listPujasPasadas.add(PujaPasadaAlta);
        listPujasPasadas.add(PujaPasadaBaja);

        listPujasActuales.add(PujaActualAlta);
        listPujasActuales.add(PujaActualBaja);

        dao.guardarObjeto(vendedor);
        dao.guardarObjeto(comprador);
        dao.guardarObjeto(enVentaPasado);
        dao.guardarObjeto(enVentaActual);
        dao.guardarObjeto(PujaPasadaAlta);
        dao.guardarObjeto(PujaPasadaBaja);
        dao.guardarObjeto(PujaActualAlta);
        dao.guardarObjeto(PujaActualBaja);

        hilo = new clsHiloPujas();
    }

    @After
    public void tearDown()
    {
        dao.eliminarObjeto(vendedor);
        dao.eliminarObjeto(comprador);
        dao.eliminarObjeto(enVentaPasado);
        dao.eliminarObjeto(enVentaActual);
        dao.eliminarObjeto(PujaPasadaAlta);
        dao.eliminarObjeto(PujaPasadaBaja);
        dao.eliminarObjeto(PujaActualAlta);
        dao.eliminarObjeto(PujaActualBaja);

        hilo.interrupt();
    }

    @Test
    public void test()
    {
        hilo.run();

        assertEquals(enVentaPasado.getUsuarioDueno(), comprador.getEmail());
        assertEquals(enVentaActual.getUsuarioDueno(), vendedor.getEmail());

        assertEquals(1000, vendedor.getFondos());
        assertEquals(-1000, comprador.getFondos());

        //No compilan porque ya no existen. Quizás leemos dos listas de pujas asociadas a cada jugador y comprobamos con esas dos listas
        //assertNull(enVentaPasado.getPujasRealizadas());
        //assertNotNull(enVentaActual.getPujasRealizadas());

        assertFalse(enVentaPasado.isEnVenta());
        assertTrue(enVentaActual.isEnVenta());
    }
}
