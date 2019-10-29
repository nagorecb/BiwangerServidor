package AppService;

import ObjetosDominio.*;

import java.util.ArrayList;

public class clsAppServiceUsuario
{

    public boolean RegistrarUsuario(clsUsuario nuevoUsuario)
    {

    }

    public String InicioSesion(clsUsuario usuario)
    {
        ArrayList<clsUsuario> lUsuarios;
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
        }//Si las credenciales no son de administrador, vemos si coinciden con u usuario normal
        else
        {
            for (clsUsuario u : lUsuarios) {
                if (u.equals(usuario)) {
                    if (u.getPassword.equals(usuario.getPassword())) {
                        resultado = "USUARIO";
                    }
                }
            }
        }
        return resultado;
    }
}
