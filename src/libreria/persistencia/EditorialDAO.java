
package libreria.persistencia;

import java.util.List;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;


public class EditorialDAO extends DAO<Editorial> {
    
    @Override
    public void guardar(Editorial editorial) {
        super.guardar(editorial);
    }
    
    public void editar(Editorial editorial){
        super.editar(editorial);
    }

    public void eliminar(Integer id) throws Exception {
        Editorial editorial = buscarPorID(id);
        super.eliminar(editorial);
    }

    public List<Editorial> listarTodos() throws Exception {
        conectar();
        List<Editorial> editorial = em.createQuery("SELECT e FROM Editorial e ").getResultList();
        desconectar();
        return editorial;
    }

    public Editorial buscarPorID(Integer id) throws Exception {
        conectar();
        Editorial editorial = (Editorial) em.createQuery("SELECT e FROM Editorial e WHERE e.id LIKE :id").setParameter("id", id).getSingleResult();
        desconectar();
        return editorial;
    }
    
}
