package libreria.persistencia;

import java.util.List;
import libreria.entidades.Cliente;

public class ClienteDAO extends DAO<Cliente>  {
    @Override
    public void guardar(Cliente cliente) {
        super.guardar(cliente);
    }
    
    public void editar(Cliente cliente){
        super.editar(cliente);
    }

    public void eliminar(Integer id){
        Cliente cliente = buscarPorID(id);
        super.eliminar(cliente);
    }

    public List<Cliente> listarTodos(){
        conectar();
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c ").getResultList();
        desconectar();
        return clientes;
    }

    public Cliente buscarPorID(Integer id){
        conectar();
        Cliente cliente = (Cliente) em.createQuery("SELECT c FROM Cliente c WHERE c.id LIKE :id").setParameter("id", id).getSingleResult();
        desconectar();
        return cliente;
    }
}
