package Biwanger.comun;

/**
 * @brief Clase desde la que se inicia el hilo.
 * @details La clase main desde el que se inicia el profile server. Lanza el hilo del servidor.
 */
public class clsMain
{
    /**
     * El método main desde el que se inicia el artefacto.
     * @param args No se hace uso de este parámetro
     */
    public static void main(String args[])
    {
        clsHiloPujas hilo = clsHiloPujas.getInstance();
        hilo.run();
    }
}
