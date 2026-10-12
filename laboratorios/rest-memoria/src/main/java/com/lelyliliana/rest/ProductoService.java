package com.lelyliliana.rest;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
class ProductoService {
    private final Map<Long, ProductoResponse> productos = new LinkedHashMap<>();
    private long siguienteId = 1;

    // El monitor protege todas las operaciones del mapa en esta única JVM.
    // No sustituye una transacción ni una restricción de una base compartida.
    synchronized ProductoResponse crear(ProductoRequest entrada) {
        comprobarSku(entrada.sku(), null);
        var producto = new ProductoResponse(siguienteId++, entrada.sku(),
            entrada.nombre().strip(), entrada.precio());
        productos.put(producto.id(), producto);
        return producto;
    }

    synchronized ProductoResponse buscar(Long id) {
        var producto = productos.get(id);
        if (producto == null) throw new NoEncontrado();
        return producto;
    }

    synchronized Pagina<ProductoResponse> listar(String nombre, int pagina, int tamano) {
        if (pagina < 0 || tamano < 1 || tamano > 100 || nombre.length() > 100)
            throw new IllegalArgumentException();
        var filtrados = productos.values().stream()
            .filter(p -> p.nombre().toLowerCase(Locale.ROOT).contains(nombre.toLowerCase(Locale.ROOT)))
            .sorted(Comparator.comparing(ProductoResponse::id)).toList();
        long inicio = (long) pagina * tamano;
        var contenido = filtrados.stream().skip(inicio).limit(tamano).toList();
        return new Pagina<>(contenido, pagina, tamano, filtrados.size(),
            (filtrados.size() + tamano - 1) / tamano);
    }

    synchronized ProductoResponse actualizar(Long id, ProductoRequest entrada) {
        buscar(id);
        comprobarSku(entrada.sku(), id);
        var producto = new ProductoResponse(id, entrada.sku(), entrada.nombre().strip(), entrada.precio());
        productos.put(id, producto);
        return producto;
    }

    synchronized void eliminar(Long id) {
        buscar(id);
        productos.remove(id);
    }

    private void comprobarSku(String sku, Long propioId) {
        boolean repetido = productos.values().stream()
            .anyMatch(p -> p.sku().equals(sku) && !p.id().equals(propioId));
        if (repetido) throw new Conflicto();
    }
}

class NoEncontrado extends RuntimeException {}
class Conflicto extends RuntimeException {}
