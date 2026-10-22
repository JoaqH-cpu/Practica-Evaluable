
package com.hellin.almacen.controller;

import com.hellin.almacen.service.ArticuloService;
import com.hellin.almacen.model.Articulo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Controlador REST encargado de gestionar
 * las consultas de artículos del almacén.
 */
@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {

    private final ArticuloService servicio;

    /**
     * Constructor del controlador.
     *
     * @param servicio servicio que gestiona los artículos
     */
    public ArticuloController(ArticuloService servicio) {
        this.servicio = servicio;
    }

    /**
     * Devuelve el listado completo de artículos del almacén.
     *
     * @return respuesta HTTP 200 con la lista de artículos
     */
    @GetMapping
    public ResponseEntity<List<Articulo>> listarArticulos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    // TODO: GET /api/articulos/{id}/basico
    // Pendiente de implementar por otro compañero.
}
