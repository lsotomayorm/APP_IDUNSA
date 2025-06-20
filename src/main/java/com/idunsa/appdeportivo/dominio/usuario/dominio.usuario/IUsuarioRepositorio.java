package Dominio.Usuario.Modulo Usuario;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface IUsuarioRepositorio {

    /**
     * @param Usuario usuario 
     * @return
     */
    public void crear(void Usuario usuario);

    /**
     * @param int id 
     * @return
     */
    public Usuario buscarPorId(void int id);

    /**
     * @param int CUI 
     * @return
     */
    public Usuario buscarPorCUI(void int CUI);

    /**
     * @param Usuario usuario 
     * @return
     */
    public void actualizar(void Usuario usuario);

}