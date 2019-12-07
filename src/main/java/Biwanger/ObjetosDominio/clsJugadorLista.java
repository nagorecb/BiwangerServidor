package Biwanger.ObjetosDominio;

import java.util.ArrayList;
import java.util.List;

public class clsJugadorLista
{
    private ArrayList<clsJugador> lJugadores;

    public clsJugadorLista() {}

    public clsJugadorLista(ArrayList param)
    {
        lJugadores = param;
    }

    public ArrayList<clsJugador> getlJugadores()
    {
        return lJugadores;
    }
}
