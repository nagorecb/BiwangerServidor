package Biwanger.comun;

import Biwanger.AppService.clsAppServiceUser;
import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class clsHiloPujas extends Thread
{
    private static final clsHiloPujas instance =new clsHiloPujas();
    private clsAppServiceUser userService = clsAppServiceUser.getInstance();
    private clsDAO dao = clsDAO.getInstance();
    private ArrayList<clsJugador> lJugadores = new ArrayList<clsJugador>();
    private ArrayList<clsPuja> lPujas = new ArrayList<clsPuja>();

    private int contador = 1;

    public clsHiloPujas()
    {

    }

    public clsHiloPujas(clsAppServiceUser param1, clsDAO param2)
    {
        userService = param1;
        dao = param2;
    }

    public static final clsHiloPujas getInstance()
    {
        instance.run();
        return instance;
    }

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
                    System.out.println("\n\n\n\n\n\n\nFor para el jugador " + auxJugador.getNombre());
                    System.out.println("\n\n\n\n\n\n\nFecha " + auxJugador.getFechaVenta());
                    if(auxJugador.getFechaVenta()!=null)
                    {


                        if(auxJugador.getFechaVenta().getDayOfMonth() < LocalDateTime.now().getDayOfMonth())
                        {
                            System.out.println("\n\n\n\n\n\n\nEntro en el if por el que se supoen que la venta ha expirado");
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
