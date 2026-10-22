package com.hellin.almacen.serv
import com.hellin.almacen.model.Articulo;
import com.hellin.almacen.repository.ArticuloRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.LinkedHashMap; //añadido Elliot

// TODO: documentar la clase y todos sus métodos con Javadoc.
@Service
public class ArticuloService {

    private final ArticuloRepository repositorio;

    public ArticuloService(ArticuloRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Articulo> listarTodos() {
        // TODO (#n de la incidencia): devolver todos los artículos. Pista: repositorio.findAll()
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

 /**
 * Busca los datos básicos de un artículo.
 * Añade el mensaje "Agotado" si el stock es cero.
 *
 * @param id identificador del artículo
 * @return datos básicos o un Optional vacío si no existe
 */
public Optional<Map<String, Object>> buscarBasico(Integer id) {
    Optional<Articulo> resultado = repositorio.findById(id);

    if (resultado.isEmpty()) {
        return Optional.empty();
    }

    Articulo articulo = resultado.get();

    Map<String, Object> datos = new LinkedHashMap<>();
    datos.put("id", articulo.getId());
    datos.put("nombre", articulo.getNombre());
    datos.put("stock", articulo.getStock());

    if (articulo.getStock() == 0) {
        datos.put("mensaje", "Agotado");
    }

    return Optional.of(datos);
}
}
