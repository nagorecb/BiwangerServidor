package Biwanger.ObjetosDominio;

import java.util.ArrayList;
import java.util.List;

public class clsJugadorLista
{
    private List<clsJugador> lJugadores;

    public clsJugadorLista() {}

    public clsJugadorLista(ArrayList param)
    {
        lJugadores = param;
    }

    public List<clsJugador> getlJugadores()
    {
        return lJugadores;
    }

    public void setlJugadores(List<clsJugador> lJugadores)
    {
        this.lJugadores = lJugadores;
    }
}
