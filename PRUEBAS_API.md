# Pruebas de la API del almacén

## 1. Prueba del listado de artículos

Esta prueba sirve para comprobar que el endpoint `GET /api/articulos` devuelve correctamente todos los productos de la base de datos.

### Preparación

Primero arrancamos la aplicación Spring Boot utilizando la base de datos H2:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

### Petición realizada

Desde otra terminal ejecutamos:

```bash
curl -i http://localhost:8080/api/articulos
```

### Resultado esperado

- Código HTTP 200 OK.
- Respuesta en formato JSON.
- Listado de artículos con su ID, nombre, precio, stock y categoría.

### Resultado obtenido

La petición ha devuelto `HTTP/1.1 200` y un JSON con los cinco artículos del almacén:

| ID | Producto | Precio | Stock |
|---|---|---|---|
| 1 | Bolígrafo azul | 0,45 € | 500 |
| 2 | Cuaderno A4 cuadriculado | 2,10 € | 120 |
| 3 | Grapadora metálica | 8,95 € | 0 |
| 4 | Paquete 500 folios A4 | 4,80 € | 60 |
| 5 | Rotulador fluorescente | 0,95 € | 0 |

Los productos con stock 0 también aparecen en el listado, como corresponde a este endpoint.

### Conclusión

La prueba se ha completado correctamente. El controlador recibe la petición y utiliza el servicio para obtener todos los artículos.

El endpoint devuelve el código HTTP 200 y los cinco productos esperados.