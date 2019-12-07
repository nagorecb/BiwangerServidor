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
    private clsAppServiceUser userService;
    private clsDAO dao;
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
                        lPujas = userService.obtenerPujas(auxJugador);
                        clsPuja pujaMaxima = lPujas.get(0);

                        for (int i = 1; i < lPujas.size(); i++) {
                            if (pujaMaxima.getOferta() < lPujas.get(i).getOferta()) {
                                pujaMaxima = lPujas.get(i);
                            }
                        }

                        clsUsuario vendedor = userService.obtenerUsuario(auxJugador.getUsuarioDueno());
                        clsUsuario comprador = userService.obtenerUsuario(pujaMaxima.getEmailUsuarioPuja());

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
        }
    }
}
