package Biwanger.ObjetosDominio;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;

public class clsUsuarioTest
{
    clsUsuario usuario;
    double fondos;
    double otrosFondos;

    @Before
    public void setUp(){
        usuario = new clsUsuario("email",  "password",  0,  0, "4-4-2");
        fondos = 300;
        otrosFondos = 200;
    }
    @Test
    public void testSumarFondos ()
    {
        assertTrue(usuario.getFondos()==0);
        usuario.SumarFondos(fondos);
        assertTrue(usuario.getFondos()==fondos);
    }

    @Test
    public void testRestarFondos ()
    {
        assertTrue(usuario.getFondos()==0);
        usuario.RestarFondos(otrosFondos);
        assertTrue(usuario.getFondos()==- otrosFondos);
    }
}
