package libreria.persistencia;

import java.util.List;
import libreria.entidades.Prestamo;

public class PrestamoDAO extends DAO<Prestamo>{
    @Override
    public void guardar(Prestamo prestamo) {
        super.guardar(prestamo);
    }
    
    public void editar(Prestamo prestamo){
        super.editar(prestamo);
    }

    public void eliminar(Integer id){
        Prestamo prestamo = buscarPorID(id);
        super.eliminar(prestamo);
    }

    public List<Prestamo> listarTodos(){
        conectar();
        List<Prestamo> prestamos = em.createQuery("SELECT p FROM Prestamo p ").getResultList();
        desconectar();
        return prestamos;
    }

    public Prestamo buscarPorID(Integer id){
        conectar();
        Prestamo prestamo = (Prestamo) em.createQuery("SELECT p FROM Prestamo p WHERE p.id LIKE :id").setParameter("id", id).getSingleResult();
        desconectar();
        return prestamo;
    }
    
    public Prestamo buscarPorIDCliente(Integer id){
        conectar();
        Prestamo prestamo = (Prestamo) em.createQuery("SELECT p FROM Prestamo p WHERE p.cliente.id LIKE :id").setParameter("id", id).getSingleResult();
        desconectar();
        return prestamo;
    }
}
