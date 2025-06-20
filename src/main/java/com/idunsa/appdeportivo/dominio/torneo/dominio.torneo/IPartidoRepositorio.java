package Dominio.Torneo.Modulo Torneo;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface IPartidoRepositorio {

    /**
     * @param Partido partido 
     * @return
     */
    public void crear(void Partido partido);

    /**
     * @param int id 
     * @return
     */
    public Partido buscarPorId(void int id);

    /**
     * @param int idTorneo 
     * @return
     */
    public List<Partido> listarPorTorneo(void int idTorneo);

    /**
     * @param Partido partido 
     * @return
     */
    public void actualizar(void Partido partido);

    /**
     * @param int id 
     * @return
     */
    public void eliminar(void int id);

}