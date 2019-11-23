package Biwanger.AppService;

import Biwanger.DAO.clsDAO;
import Biwanger.ObjetosDominio.clsJugador;
import Biwanger.ObjetosDominio.clsUsuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class clsAppServiceAdmin
{
	clsDAO dao = new clsDAO();

    final double PREMIO1 = 3000;
    final double PREMIO2 = 2000;
    final double PREMIO3 = 1000;

    public List<clsUsuario> PremiarTresMejores ()
    {
        List<clsUsuario> listaUsuarios = new ArrayList<>();
        List<clsUsuario> listaUsuariosOrdenada = new ArrayList<>();
        
        //llamada select* from clsusuario para conseguir la lista        
        listaUsuariosOrdenada = listaUsuarios.stream().sorted(Comparator.comparingInt(clsUsuario ::getPuntuacion)).collect(Collectors.toList());
        
        listaUsuarios.get(0).setFondos(listaUsuariosOrdenada.get(0).getFondos()+PREMIO1);
        listaUsuarios.get(1).setFondos(listaUsuariosOrdenada.get(1).getFondos()+PREMIO2);
        listaUsuarios.get(2).setFondos(listaUsuariosOrdenada.get(2).getFondos()+PREMIO3);
        
        //llamada a la bd para actualizar los datos

        return listaUsuariosOrdenada;
    }
    
    public void anadirPuntos (int idJugador, int puntosAnadir) 
    {
    	clsJugador jugadorAnadirPuntos = this.dao.buscarJugador(idJugador);
    	
    	int puntosTot = jugadorAnadirPuntos.getPuntos() + puntosAnadir;
    	
    	jugadorAnadirPuntos.setPuntos(puntosTot);
    	
    	this.dao.modificarObjeto(jugadorAnadirPuntos);
    }
    
    
    public void guardarJugador (clsJugador jugador) 
    {
    	// inicializar al jugador recibido los valores predeterminados para los campos que faltan, con constructor yab está
//    	jugador.setPuntos(0);
//    	jugador.setAlineado(false);
    	this.dao.guardarObjeto(jugador);
    }
}