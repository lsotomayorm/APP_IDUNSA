package Dominio.Torneo.Modulo Torneo;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface ITorneoRepositorio {

    /**
     * @param Torneo torneo 
     * @return
     */
    public void crear(void Torneo torneo);

    /**
     * @param int id 
     * @return
     */
    public Torneo buscarPorId(void int id);

    /**
     * @return
     */
    public List<Torneo> listarTodos();

    /**
     * @param Torneo torneo 
     * @return
     */
    public void actualizar(void Torneo torneo);

    /**
     * @param int id 
     * @return
     */
    public void eliminar(void int id);

}