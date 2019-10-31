package Biwanger.AppService;

import Biwanger.ObjetosDominio.*;

import java.util.ArrayList;

import Biwanger.ObjetosDominio.clsUsuario;

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
}
