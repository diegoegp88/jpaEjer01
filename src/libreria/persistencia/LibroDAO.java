// Esta clase hereda de la clase padre DAO y vemos que
// se especifica la generalización (<T>) como Mascota
// esto permite que los métodos heredados donde se solicita
// una parámetro T, en esta clase serán de tipo Libro

package libreria.persistencia;


import java.util.List;
import libreria.entidades.Autor;
import libreria.entidades.Libro;

public class LibroDAO extends DAO<Libro> {

    @Override
    public void guardar(Libro libro) {
        super.guardar(libro);
    }

    public void eliminar(long isbn) throws Exception {
        Libro libro = buscarPorISBN(isbn);
        super.eliminar(libro);
    }

    public List<Libro> listarTodos() throws Exception {
        conectar();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l ").getResultList();
        desconectar();
        return libros;
    }

    public Libro buscarPorISBN(long isbn) throws Exception {
        conectar();
        Libro libro = (Libro) em.createQuery("SELECT l FROM Libro l WHERE l.isbn LIKE :isbn").setParameter("isbn", isbn).getSingleResult();
        desconectar();
        return libro;
    }
    
    public Libro buscarPorTitulo(String titulo) throws Exception {
        conectar();
        Libro libro = (Libro) em.createQuery("SELECT l FROM Libro l WHERE l.titulo LIKE :nombre").setParameter("titulo", titulo).getSingleResult();
        desconectar();
        return libro;
    }
    
    public List<Libro> buscarPorAutor(int autorId) throws Exception {
        conectar();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l where l.autor_id LIKE: autorId").setParameter("autorid", autorId).getResultList();
        desconectar();
        return libros;
    }
    
     public List<Libro> buscarPorEditorial(int editorialId) throws Exception {
        conectar();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l where l.editorial_id LIKE: editorialId").setParameter("editorialId", editorialId).getResultList();
        desconectar();
        return libros;
    }
    
}


