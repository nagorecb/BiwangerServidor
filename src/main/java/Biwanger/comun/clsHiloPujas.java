package Biwanger.comun;

import Biwanger.AppService.clsAppServiceUser;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class clsHiloPujas extends Thread
{
    private clsAppServiceUser userService = new clsAppServiceUser();
    private ArrayList<clsJugador> lJugadores = new ArrayList<clsJugador>();
    private ArrayList<clsPuja> lPujas = new ArrayList<clsPuja>();

    private int contador = 1;

    public void run()
    {
        while(true)
        {
            if (contador == 1 || LocalTime.MIDNIGHT.equals(LocalTime.now()))
            {
                lJugadores = userService.MostrarMercado();

                for (clsJugador auxJugador : lJugadores)
                {
                    if(auxJugador.getFechaVenta().getDayOfMonth() < LocalDateTime.now().getDayOfMonth())
                    {
                        lPujas = (ArrayList<clsPuja>) auxJugador.getPujasRealizadas();
                        clsPuja pujaMaxima = lPujas.get(0);

                        for (int i = 1; i < lPujas.size(); i++) {
                            if (pujaMaxima.getOferta() < lPujas.get(i).getOferta()) {
                                pujaMaxima = lPujas.get(i);
                            }
                        }

                        clsUsuario vendedor = auxJugador.getUsuarioDueno();
                        clsUsuario comprador = pujaMaxima.getUsuarioPuja();

                        vendedor.EliminarJugador(auxJugador);
                        comprador.AnadirJugador(auxJugador);

                        comprador.RestarFondos(pujaMaxima.getOferta());
                        vendedor.SumarFondos(pujaMaxima.getOferta());

                        auxJugador.setPujasRealizadas(null);

                        auxJugador.setEnVenta(false);
                        contador = 0;
                    }
                }
            }
        }
    }
}
