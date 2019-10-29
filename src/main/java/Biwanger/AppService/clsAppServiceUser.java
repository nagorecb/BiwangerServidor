package main.java.Biwanger.AppService;

import main.java.Biwanger.ObjetosDominio.*;

import java.util.ArrayList;

public class clsAppServiceUser
{
    public boolean RegistrarUser(clsUsuario nuevoUsuario)
    {
        return false;

    }

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
}
