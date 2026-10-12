package com.lelyliliana.productos;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.*;
import java.util.Optional;
interface ProductoRepository extends JpaRepository<Producto,Long>{
 boolean existsBySku(String sku);
 boolean existsBySkuAndIdNot(String sku,Long id);
 @EntityGraph(attributePaths="categoria") Page<Producto> findByNombreContainingIgnoreCase(String nombre,Pageable pageable);
 @Override @EntityGraph(attributePaths="categoria") Optional<Producto> findById(Long id);
}
