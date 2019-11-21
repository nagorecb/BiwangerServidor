package Biwanger.AppService;

import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsPuja;
import Biwanger.ObjetosDominio.clsUsuario;

import java.util.ArrayList;

public class clsAppServiceUser
{
    public clsUsuario InicioSesion(clsUsuario usuario)
    {
        ArrayList<clsUsuario> lUsuarios = new ArrayList <clsUsuario>();
        clsUsuario u = new clsUsuario();

        String resultado = "";
        //Rellenar la lista de usuarios con los usuarios de la BD

        //Primero, vemos si las credenciales son de administrador
        if(usuario.getEmail().equals("ADMIN"))
        {
            if (usuario.getPassword().equals("ADMIN"))
            {
                u.setEmail("ADMIN");
            }
        }//Si las credenciales no son de administrador, vemos si coinciden con un usuario normal
        else
        {
            for (clsUsuario u1 : lUsuarios) {
                if (u1.equals(usuario)) {
                    if (u1.getPassword().equals(usuario.getPassword())) {
                        u = u1;
                    }
                }
            }
        }
        return u;
    }

    public boolean RegistrarUser(String email, String password)
    {
        clsUsuario user = new clsUsuario();
        user.setEmail(email);
        user.setPassword(password);

        boolean resultado = false; //Se intenta guardar el usuario en la BD y se recoge en un boolean

        return resultado;
    }

    public boolean modificarFormacion (clsUsuario usuario)
    {
        ArrayList <clsUsuario> usuarios = new ArrayList<clsUsuario>();
        //cargar de BD
        for(clsUsuario u: usuarios) {
            if (u.equals(usuario)) {
                //guardar formacion en BD
                return true;
            }
        }
        return false;
    }

    public boolean modificarAlineacion (clsUsuario usuario)
    {
        for (clsJugador jugador : usuario.getPlantilla())
        {
            //guardar jugador en BD
        }

        return false; //Hay que modificar, devolver lo que tenga que devolver
    }

    public ArrayList<clsJugador> MostrarMercado()
    {
        ArrayList<clsJugador> lTodosJugadores = new ArrayList<clsJugador>();
        ArrayList<clsJugador> lJugadoresEnVenta = new ArrayList<clsJugador>();

        //Leer toda la lista de jugadores de la BBDD

        for(clsJugador aux: lTodosJugadores)
        {
            if(aux.isEnVenta())
            {
                lJugadoresEnVenta.add(aux);
            }
        }

        return lJugadoresEnVenta;
    }

    public boolean Pujar(clsPuja puja)
    {
        boolean resultado = false;

        //Se intenta guardar la puja en la BBDD

        return resultado;
    }

    public void venderJugador(double precio, clsJugador jugadorVenta)
    {
        //Quitarle el jugador de la plantilla al usuario
        clsUsuario dueno = jugadorVenta.getUsuarioDueno();
        dueno.EliminarJugador(jugadorVenta);
        jugadorVenta.setAlineado(false);
        jugadorVenta.setUsuarioDueno(null);
        //Modificar datos de jugaodor desde DAO, eliminar el jugador de la plantilla del usuario desde DAO

        //Crear puja y guardarla en la BD

        clsPuja puja = new clsPuja(dueno,jugadorVenta, precio);
        //Añadir puja DAO
    }

}
