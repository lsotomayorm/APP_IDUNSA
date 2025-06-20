package Dominio.Usuario.Modulo Usuario;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface IEquipoRepositorio {

    /**
     * @param Equipo equipo 
     * @return
     */
    public void crear(void Equipo equipo);

    /**
     * @param int id 
     * @return
     */
    public Equipo buscarPorId(void int id);

    /**
     * @return
     */
    public List<Equipo> listarTodos();

    /**
     * @param Equipo equipo 
     * @return
     */
    public void actualizar(void Equipo equipo);

    /**
     * @param int id 
     * @return
     */
    public void eliminar(void int id);

}