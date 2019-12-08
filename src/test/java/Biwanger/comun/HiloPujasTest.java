
package Biwanger.comun;

import Biwanger.AppService.clsAppServiceUser;
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
import java.util.concurrent.TimeUnit;

public class HiloPujasTest
{
    private clsAppServiceUser userService;
    private clsDAO dao;
    private clsHiloPujas hilo;

    private clsUsuario vendedor;
    private clsUsuario comprador;

    private clsJugador enVentaPasado;
    private clsJugador enVentaActual;

    private clsPuja PujaPasadaAlta ;
    private clsPuja PujaPasadaBaja;
    private clsPuja PujaActualAlta;
    private clsPuja PujaActualBaja;

    @Before
    public void setUp()
    {
        dao = new clsDAO();
        userService = new clsAppServiceUser(dao);
        vendedor = new clsUsuario("vendedor", "vendedor", 0, 0, null);
        comprador = new clsUsuario("comprador", "comprador", 0, 0, null);

        enVentaPasado = new clsJugador();
        enVentaActual = new clsJugador();

        enVentaPasado.setNombre("EnventaPasado");
        enVentaActual.setNombre("EnVentaActual");

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
        System.out.println("\n\n\n\n\n\n\nFecha enventapasado " + enVentaPasado.getFechaVenta());
        System.out.println("\n\n\n\n\n\n\nFecha enVentaActual " + enVentaActual.getFechaVenta());
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
        enVentaPasado = dao.guardarObjeto(enVentaPasado);
        enVentaActual = dao.guardarObjeto(enVentaActual);
        dao.guardarObjeto(PujaPasadaAlta);
        dao.guardarObjeto(PujaPasadaBaja);
        dao.guardarObjeto(PujaActualAlta);
        dao.guardarObjeto(PujaActualBaja);

        hilo = new clsHiloPujas(userService, dao);
    }

    @After
    public void tearDown()
    {
        dao.eliminarObjeto(vendedor);
        dao.eliminarObjeto(comprador);
        //dao.eliminarObjeto(enVentaPasado);
        //dao.eliminarObjeto(enVentaActual);
        dao.eliminarObjeto(PujaPasadaAlta);
        dao.eliminarObjeto(PujaPasadaBaja);
        dao.eliminarObjeto(PujaActualAlta);
        dao.eliminarObjeto(PujaActualBaja);
    }

    @Test
    public void test() throws InterruptedException {
        hilo.start();
        TimeUnit.SECONDS.sleep(5);
        hilo.interrupt();
        //Vemos que al jugador que estaba en venta cuya puja ha expirado tiene ahora como dueño al comprador
        enVentaPasado = dao.buscarJugador(enVentaPasado.getId());
        System.out.println("Email del vendido " + enVentaPasado.getUsuarioDueno());
        enVentaActual = dao.buscarJugador(enVentaActual.getId());
        assertEquals(enVentaPasado.getUsuarioDueno(), comprador.getEmail());
        //Vemos que la venta que aun está vigente tiene al vendedor como
        assertEquals(enVentaActual.getUsuarioDueno(), vendedor.getEmail());

        //Vemos que se hayan incrementado y decrementado los fondos a raíz de la puja
        assertEquals(1000, vendedor.getFondos());
        assertEquals(-1000, comprador.getFondos());

        //Comprobamos que el vigente sigue en venta pero el otro no
        assertFalse(enVentaPasado.isEnVenta());
        assertTrue(enVentaActual.isEnVenta());
    }
}
