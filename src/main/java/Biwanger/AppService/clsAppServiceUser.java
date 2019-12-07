package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class clsAppServiceUser
{
    private clsDAO dao;

    public clsAppServiceUser(){}
    public clsAppServiceUser(clsDAO dao)
    {
        this.dao = dao;
    }

    public clsUsuario InicioSesion(clsUsuario usuario)
    {
        ArrayList<clsUsuario> lUsuarios;
        clsUsuario u = null;

        //Primero, vemos si las credenciales son de administrador
        if(usuario.getEmail().equals("ADMIN"))
        {
            if (usuario.getPassword().equals("ADMIN"))
            {
                u = usuario;
            }
        }//Si las credenciales no son de administrador, vemos si coinciden con un usuario normal
        else
        {
            lUsuarios = dao.leerUsuarios();

            for (clsUsuario u1 : lUsuarios)
            {
                if (u1.equals(usuario))
                {
                    if (u1.getPassword().equals(usuario.getPassword()))
                    {
                        u = u1;
                    }
                }
            }
        }

        return u;
    }

    public String RegistrarUser(clsUsuario user)
    {
        clsUsuario aux = dao.buscarUsuario(user.getEmail());

        if(aux == null)
        {
            dao.guardarObjeto(user);
            return "OK";
        } else
        {
            return "No OK";
        }
    }

    public void modificarFormacion (clsUsuario usuario)
    {
        ArrayList <clsUsuario> listUsuarios;

        listUsuarios = dao.leerUsuarios();

        for(clsUsuario u: listUsuarios) {
            if (u.equals(usuario))
            {
                dao.modificarObjeto(u);
            }
        }
    }

    public void modificarAlineacion (clsUsuario usuario)
    {
        for (clsJugador jugador: usuario.getPlantilla())
        {
            dao.modificarObjeto(jugador);
        }
    }

    public ArrayList<clsJugador> MostrarMercado()
    {
        ArrayList<clsJugador> lTodosJugadores = new ArrayList<clsJugador>();
        ArrayList<clsJugador> lJugadoresEnVenta = new ArrayList<clsJugador>();

        lTodosJugadores = dao.leerJugadores();

        for(clsJugador aux: lTodosJugadores)
        {
            if(aux.isEnVenta())
            {
                lJugadoresEnVenta.add(aux);
            }
        }

        return lJugadoresEnVenta;
    }

    public void Pujar(clsPuja puja)
    {
        dao.guardarObjeto(puja);
    }

    public void venderJugador(clsJugador jugadorVenta)
    {
        //Quitarle el jugador de la plantilla al usuario
        jugadorVenta.setAlineado(false);
        jugadorVenta.setEnVenta(true);
        jugadorVenta.setFechaVenta(LocalDateTime.now());

        dao.modificarObjeto(jugadorVenta);
    }

}
