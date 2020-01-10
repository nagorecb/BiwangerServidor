package Biwanger.comun;

import Biwanger.AppService.clsAppServiceUser;
import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @brief Hilo que gestiona las pujas del sistema.
 * @details Cuando se inicia y a las doce de la noche cada día
 * el hilo recorre los jugadores en venta para identificar los del día anterior y, si tienen alguna puja,
 * gestiona la venta de este.
 */
public class clsHiloPujas extends Thread
{
    private static clsHiloPujas instance =null;
    private clsAppServiceUser userService = clsAppServiceUser.getInstance();
    private clsDAO dao = clsDAO.getInstance();
    private ArrayList<clsJugador> lJugadores = new ArrayList<clsJugador>();
    private ArrayList<clsPuja> lPujas = new ArrayList<clsPuja>();

    private int contador = 1;

    /**
     * Constructor vacío del hilo
     */
    public clsHiloPujas()
    {

    }

    /**
     * Constructor del hilo
     * @param param1 Recibe el AppServiceUsuario para añadir funcionalidad
     * @param param2 Recibe DAO para operar con la BBDD
     */
    public clsHiloPujas(clsAppServiceUser param1, clsDAO param2)
    {
        userService = param1;
        dao = param2;
    }

    /**
     * Devuelve la única instancia del hilo
     * @return Devuelve la única instancia del hilo
     */
    public static final clsHiloPujas getInstance()
    {
        synchronized (clsHiloPujas.class)
        {
            if(instance == null)
            {
               instance = new clsHiloPujas();
            }
        }

        return instance;
    }

    /**
     * Método run del hilo que inicia su funcionamiento
     */
    public void run()
    {
        System.out.println("\n\n\n\n\n\nENTRO EN EL HILO");
        while(true)
        {
            System.out.println("\n\n\n\n\n\n\nEntro en el while");
            if (contador == 1 || LocalTime.MIDNIGHT.equals(LocalTime.now()))
            {
                System.out.println("\n\n\n\n\n\n\nEntro en el if");
                lJugadores = userService.MostrarMercado();

                for (clsJugador auxJugador : lJugadores)
                {
                    if(auxJugador.getFechaVenta()!=null)
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                        LocalDateTime fecha = LocalDateTime.parse(auxJugador.getFechaVenta(), formatter);
                        if(fecha.getDayOfMonth() < LocalDateTime.now().getDayOfMonth())
                        {
                            System.out.println("\n\n\n\n\n\n\nEntro en el if por el que se supone que la venta ha expirado");
                            lPujas = userService.obtenerPujas(auxJugador);
                            System.out.println("\n\n\n\n\n\n\nObtengo todas las pujas");
                            clsPuja pujaMaxima = null;
                            if(!lPujas.isEmpty())
                            {
                                pujaMaxima = lPujas.get(0);
                            }
                            if(pujaMaxima == null)
                            {
                                //No se ha pujado por este jugador, se lo queda su dueño de antes
                                auxJugador.setEnVenta(false);
                                contador = 0;
                                dao.modificarJugador(auxJugador);
                            }
                            else
                            {
                                for (int i = 1; i < lPujas.size(); i++)
                                {
                                    if (pujaMaxima.getOferta() < lPujas.get(i).getOferta()) {
                                        pujaMaxima = lPujas.get(i);
                                    }
                                }
                                System.out.println("\n\n\n\n\n\n\npuja máxima "+ pujaMaxima.getOferta());

                                clsUsuario vendedor = userService.obtenerUsuario(auxJugador.getUsuarioDueno());
                                clsUsuario comprador = userService.obtenerUsuario(pujaMaxima.getEmailUsuarioPuja());
                                System.out.println("\n\n\n\n\n\n\nEl vendedor es  "+ vendedor.getEmail());
                                System.out.println("\n\n\n\n\n\n\nEl comprador es  "+ comprador.getEmail());
                                auxJugador.setUsuarioDueno(comprador.getEmail());

                                if(vendedor != null)
                                {
                                    vendedor.SumarFondos(pujaMaxima.getOferta());
                                }
                                comprador.RestarFondos(pujaMaxima.getOferta());

                                auxJugador.setEnVenta(false);
                                contador = 0;

                                dao.modificarJugador(auxJugador);
                                dao.modificarUsuario(vendedor);
                                dao.modificarUsuario(comprador);

                                for(clsPuja aux: lPujas)
                                {
                                    dao.eliminarObjeto(aux);
                                }
                            }

                        }
                     }
                    System.out.println("\n\n\n\n\n\n\nAcabo la vuelta del if ");
                }
            }
        }
    }

}
