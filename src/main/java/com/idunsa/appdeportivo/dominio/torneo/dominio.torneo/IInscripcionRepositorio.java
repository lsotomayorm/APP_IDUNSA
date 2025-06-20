package Dominio.Torneo.Modulo Torneo;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface IInscripcionRepositorio {

    /**
     * @param Inscripcion inscripcion 
     * @return
     */
    public void crear(void Inscripcion inscripcion);

    /**
     * @param int id 
     * @return
     */
    public Inscripcion buscarPorId(void int id);

    /**
     * @param int idTorneo 
     * @return
     */
    public List<Inscripcion> listarPorTorneo(void int idTorneo);

    /**
     * @param Inscripcion inscripcion 
     * @return
     */
    public void actualizar(void Inscripcion inscripcion);

    /**
     * @param int id 
     * @return
     */
    public void eliminar(void int id);

}