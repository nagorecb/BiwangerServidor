package Biwanger.AppService;

import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsUsuario;

import java.util.ArrayList;

public class clsAppServiceUser
{
    public String InicioSesion(clsUsuario usuario)
    {
        ArrayList<clsUsuario> lUsuarios = new ArrayList <clsUsuario>();
        clsUsuario u = null;

        String resultado = "";
        //Rellenar la lista de usuarios con los usuarios de la BD

        //Primero, vemos si las credenciales son de administrador
        if(usuario.getEmail().equals("ADMIN"))
        {
            if (usuario.getPassword().equals("ADMIN"))
            {
                resultado = "ADMIN";
            }
        }//Si las credenciales no son de administrador, vemos si coinciden con un usuario normal
        else
        {
            for (clsUsuario u1 : lUsuarios) {
                if (u1.equals(usuario)) {
                    if (u1.getPassword().equals(usuario.getPassword())) {
                        resultado = "USUARIO";
                    }
                }
            }
        }
        return resultado;
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
    }
}
