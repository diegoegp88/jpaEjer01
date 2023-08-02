// Esta clase hereda de la clase padre DAO y vemos que
// se especifica la generalización (<T>) como Mascota
// esto permite que los métodos heredados donde se solicita
// una parámetro T, en esta clase serán de tipo Autor


package libreria.persistencia;


import java.util.List;
import libreria.entidades.Autor;

public class AutorDAO extends DAO<Autor> {

    @Override
    public void guardar(Autor autor) {
        super.guardar(autor);
    }

    public void eliminar(Integer id){
        Autor autor = buscarPorID(id);
        super.eliminar(autor);
    }

    public List<Autor> listarTodos(){
        conectar();
        List<Autor> autores = em.createQuery("SELECT a FROM Autor a ").getResultList();
        desconectar();
        return autores;
    }

    public Autor buscarPorID(Integer id){
        conectar();
        Autor autor = (Autor) em.createQuery("SELECT a FROM Autor a WHERE a.id LIKE :id").setParameter("id", id).getSingleResult();
        desconectar();
        return autor;
    }
}


