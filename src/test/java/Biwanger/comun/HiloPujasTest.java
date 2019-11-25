package Biwanger.comun;

import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HiloPujasTest
{
    @Before
    public void setUp()
    {
        clsUsuario vendedor = new clsUsuario("vendedor", "vendedor", 0, 0, null);
        clsUsuario comprador = new clsUsuario("comprador", "comprador", 0, 0, null);

        clsJugador enVentaPasado = new clsJugador();
        clsJugador enVentaActual = new clsJugador();

        clsPuja PujaPasadaAlta ;
        clsPuja PujaPasadaBaja;
        clsPuja PujaActualAlta;
        clsPuja PujaActualBaja;

        enVentaPasado.setId(0);
        enVentaActual.setId(1);

        enVentaPasado.setEnVenta(true);
        enVentaActual.setEnVenta(true);

        //Poner fechas a jugadores
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void test()
    {

    }
}
