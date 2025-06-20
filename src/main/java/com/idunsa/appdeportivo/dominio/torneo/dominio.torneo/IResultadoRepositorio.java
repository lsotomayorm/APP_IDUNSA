package Dominio.Torneo.Modulo Torneo;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface IResultadoRepositorio {

    /**
     * @param resultado 
     * @return
     */
    public void guardar(Resultado resultado);

    /**
     * @param int id 
     * @return
     */
    public Resultado buscarPorId(void int id);

    /**
     * @param int idPartido 
     * @return
     */
    public List<Resultado> listarPorPartido(void int idPartido);

    /**
     * @param Resultado resultado 
     * @return
     */
    public void actualizar(void Resultado resultado);

    /**
     * @param int id 
     * @return
     */
    public void eliminar(void int id);

}