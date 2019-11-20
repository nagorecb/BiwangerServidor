package Biwanger.comun;

import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;

import java.time.LocalTime;
import java.util.ArrayList;

public class clsHiloPujas extends Thread
{
    private ArrayList<clsJugador> lJugadores = new ArrayList<clsJugador>();
    private ArrayList<clsPuja> lPujas = new ArrayList<clsPuja>();

    private int contador = 1;

    public void run()
    {
        while(true)
        {
            if (contador == 1 || LocalTime.MIDNIGHT.equals(LocalTime.now()))
            {
                //Leer todas los jugadores EN VENTA de la BBDD

                for (clsJugador auxJugador : lJugadores)
                {
                    //Comprobar fecha de venta del jugador

                    lPujas = (ArrayList<clsPuja>) auxJugador.getPujasRealizadas();
                    clsPuja pujaMaxima = lPujas.get(0);

                    for (int i = 1; i < lPujas.size(); i++)
                    {
                        if (pujaMaxima.getOferta() < lPujas.get(i).getOferta())
                        {
                            pujaMaxima = lPujas.get(i);
                        }
                    }

                    //Quitar el jugador de la plantilla del dueño
                    //Añadir el jugador a la plantilla del comprador
                    //Restar el dinero al comprador
                    //Añadir el dinero al vendedor

                    auxJugador.setEnVenta(false);
                    contador = 0;
                }
            }
        }
    }
}
