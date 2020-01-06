package Biwanger.comun;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsUsuario;

import java.util.ArrayList;

/**
 * @brief Clase que contiene datos para poblar la base de datos
 */
public class DatosHardcoded
{
    /**
     * El método main desde el que se lanza el hardcodeo de los datos
     * @param args No se hace uso de este parámetro
     */
    public static void main(String args[])
    {
        metodo(clsDAO.getInstance());
    }

    /**
     * Método que contiene los objetos y datos a hardcodear.
     * @param dao es la conexión con la base de datos
     */
    public static void metodo(clsDAO dao)
    {
        System.out.println("Empezando hardcodeo");
        clsUsuario usuario = new clsUsuario("usuario@usuario.es", "123", 0, 1000000, "4-4-2");
        clsUsuario usuario2 = new clsUsuario("nagorecb@opendeusto.es", "123", 30, 1000000, "4-4-2");
        clsUsuario usuario3 = new clsUsuario("hodei.olaskoaga@opendeusto.es", "123", 20, 1000000, "4-4-2");
        clsUsuario usuario4 = new clsUsuario("sara.olaizola@opendeusto.es", "123", 25, 1000000, "4-4-2");
        clsUsuario usuario5 = new clsUsuario("maiderc@opendeusto.es", "123", 0, 1000000, "4-4-2");

        clsJugador jugador1 = new clsJugador();
        jugador1.setNombre("Lionel Messi");
        jugador1.setPuntos(20);
        jugador1.setPosicion("Delantero");
        jugador1.setPrecio(20000000);
        jugador1.setEquipo("FBC");
        jugador1.setAlineado(true);
        jugador1.setEstado("Buen estado");
        jugador1.setEnVenta(false);
        jugador1.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador2 = new clsJugador();
        jugador2.setNombre("Morata");
        jugador2.setPuntos(30);
        jugador2.setPosicion("Delantero");
        jugador2.setPrecio(20000000);
        jugador2.setEquipo("Atlético de Madrid");
        jugador2.setAlineado(true);
        jugador2.setEstado("Buen estado");
        jugador2.setEnVenta(false);
        jugador2.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador3 = new clsJugador();
        jugador3.setNombre("Busquets");
        jugador3.setPuntos(10);
        jugador3.setPosicion("Centrocampista");
        jugador3.setPrecio(20000000);
        jugador3.setEquipo("FBC");
        jugador3.setAlineado(true);
        jugador3.setEstado("Buen estado");
        jugador3.setEnVenta(false);
        jugador3.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador4 = new clsJugador();
        jugador4.setNombre("Saúl");
        jugador4.setPuntos(0);
        jugador4.setPosicion("Centrocampista");
        jugador4.setPrecio(20000000);
        jugador4.setEquipo("Atlético de Madrid");
        jugador4.setAlineado(true);
        jugador4.setEstado("Buen estado");
        jugador4.setEnVenta(false);
        jugador4.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador5 = new clsJugador();
        jugador5.setNombre("Cazorla");
        jugador5.setPuntos(0);
        jugador5.setPosicion("Centrocampista");
        jugador5.setPrecio(20000000);
        jugador5.setEquipo("Villarreal");
        jugador5.setAlineado(true);
        jugador5.setEstado("Buen estado");
        jugador5.setEnVenta(false);
        jugador5.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador6 = new clsJugador();
        jugador6.setNombre("Rodri");
        jugador6.setPuntos(0);
        jugador6.setPosicion("Centrocampista");
        jugador6.setPrecio(20000000);
        jugador6.setEquipo("Manchester City");
        jugador6.setAlineado(true);
        jugador6.setEstado("Buen estado");
        jugador6.setEnVenta(false);
        jugador6.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador7 = new clsJugador();
        jugador7.setNombre("Carvajal");
        jugador7.setPuntos(4);
        jugador7.setPosicion("Defensa");
        jugador7.setPrecio(20000000);
        jugador7.setEquipo("Real Madrid");
        jugador7.setAlineado(true);
        jugador7.setEstado("Buen estado");
        jugador7.setEnVenta(false);
        jugador7.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador8 = new clsJugador();
        jugador8.setNombre("Jesús Naval");
        jugador8.setPuntos(0);
        jugador8.setPosicion("Defensa");
        jugador8.setPrecio(20000000);
        jugador8.setEquipo("Sevilla FC");
        jugador8.setAlineado(true);
        jugador8.setEstado("Buen estado");
        jugador8.setEnVenta(false);
        jugador8.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador9 = new clsJugador();
        jugador9.setNombre("Bernat");
        jugador9.setPuntos(0);
        jugador9.setPosicion("Defensa");
        jugador9.setPrecio(20000000);
        jugador9.setEquipo("Paris Saint Germain");
        jugador9.setAlineado(true);
        jugador9.setEstado("Buen estado");
        jugador9.setEnVenta(false);
        jugador9.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador10 = new clsJugador();
        jugador10.setNombre("Albiol");
        jugador10.setPuntos(5);
        jugador10.setPosicion("Defensa");
        jugador10.setPrecio(20000000);
        jugador10.setEquipo("Villarreal");
        jugador10.setAlineado(true);
        jugador10.setEstado("Buen estado");
        jugador10.setEnVenta(false);
        jugador10.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador11 = new clsJugador();
        jugador11.setNombre("De Gea");
        jugador11.setPuntos(10);
        jugador11.setPosicion("Portero");
        jugador11.setPrecio(20000000);
        jugador11.setEquipo("Manchester United");
        jugador11.setAlineado(true);
        jugador11.setEstado("Buen estado");
        jugador11.setEnVenta(false);
        jugador11.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador12 = new clsJugador();
        jugador12.setNombre("Luis Suárez");
        jugador12.setPuntos(10);
        jugador12.setPosicion("Delantero");
        jugador12.setPrecio(20000000);
        jugador12.setEquipo("FCB");
        jugador12.setAlineado(false);
        jugador12.setEstado("Buen estado");
        jugador12.setEnVenta(false);
        jugador12.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador13 = new clsJugador();
        jugador13.setNombre("Gerard Piqué");
        jugador13.setPuntos(0);
        jugador13.setPosicion("Defensa");
        jugador13.setPrecio(20000000);
        jugador13.setEquipo("FCB");
        jugador13.setAlineado(false);
        jugador13.setEstado("Buen estado");
        jugador13.setEnVenta(false);
        jugador13.setUsuarioDueno(usuario.getEmail());

        clsJugador jugador14 = new clsJugador();
        jugador14.setNombre("Kepa Arrizabalaga");
        jugador14.setPuntos(20);
        jugador14.setPosicion("Portero");
        jugador14.setPrecio(20000000);
        jugador14.setEquipo("Chelsea FC");
        jugador14.setAlineado(false);
        jugador14.setEstado("Buen estado");
        jugador14.setEnVenta(false);
        jugador14.setUsuarioDueno(usuario.getEmail());

        ArrayList<clsJugador> plantilla = new ArrayList<clsJugador>();
        plantilla.add(jugador1);
        plantilla.add(jugador2);
        plantilla.add(jugador3);
        plantilla.add(jugador4);
        plantilla.add(jugador5);
        plantilla.add(jugador6);
        plantilla.add(jugador7);
        plantilla.add(jugador8);
        plantilla.add(jugador9);
        plantilla.add(jugador10);
        plantilla.add(jugador11);
        plantilla.add(jugador12);
        plantilla.add(jugador13);
        plantilla.add(jugador14);

        for(clsJugador aux: plantilla)
        {
            dao.guardarObjeto(aux);
        }

        dao.guardarObjeto(usuario);
        dao.guardarObjeto(usuario2);
        dao.guardarObjeto(usuario3);
        dao.guardarObjeto(usuario4);
        dao.guardarObjeto(usuario5);
        System.out.println("Hardcodeo finalizado!");
    }
}
