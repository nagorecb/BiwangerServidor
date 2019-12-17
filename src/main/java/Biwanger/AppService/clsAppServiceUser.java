package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class clsAppServiceUser
{
    private static clsDAO dao = clsDAO.getInstance();
    private static clsAppServiceUser instancia = null;
    public clsAppServiceUser(){ }

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
    public static void setDao()
    {
        dao = clsDAO.getInstance();
    }

    public static clsAppServiceUser getInstance()
    {
        synchronized (clsAppServiceUser.class)
        {
            if(instancia == null)
            {
                instancia = new clsAppServiceUser();
            }
        }
        return instancia;
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
                u.setFormacion(usuario.getFormacion());
                dao.modificarUsuario(u);
            }
        }
    }

    public void modificarAlineacion (ArrayList<clsJugador> plantilla)
    {
        for (clsJugador jugador: plantilla)
        {
            dao.modificarJugador(jugador);
        }
    }

    public ArrayList<clsJugador> MostrarMercado()
    {
        ArrayList<clsJugador> lTodosJugadores;
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
        jugadorVenta.setAlineado(false);
        jugadorVenta.setEnVenta(true);
        jugadorVenta.setFechaVenta(LocalDateTime.now());

        dao.modificarJugador(jugadorVenta);
    }

    public ArrayList<clsPuja> obtenerPujas(clsJugador jugador)
    {
        ArrayList<clsPuja> lTodasPujas = dao.leerPujas();
        ArrayList<clsPuja> lPujasDelJugador = new ArrayList<clsPuja>();

        for(clsPuja aux: lTodasPujas)
        {
            if(aux.getIdJugadorPuja() == jugador.getId())
            {
                lPujasDelJugador.add(aux);
            }
        }

        return lPujasDelJugador;
    }

    public clsUsuario obtenerUsuario(String email)
    {
        clsUsuario retorno = new clsUsuario();
        ArrayList<clsUsuario> lUsuarios = dao.leerUsuarios();

        for(clsUsuario aux: lUsuarios)
        {
            if(email.equals(aux.getEmail()))
            {
                retorno = aux;
            }
        }

        return retorno;
    }
}
