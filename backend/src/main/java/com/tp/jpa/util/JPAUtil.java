package com.tp.jpa.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Mantiene una única instancia de EntityManagerFactory para toda la
 * aplicación. Se obtiene con getEntityManagerFactory() y se cierra al
 * finalizar con close().
 */
public class JPAUtil {

    private static final String PERSISTENCE_UNIT = "foodstorePU";

    private static EntityManagerFactory emf;

    private JPAUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
        return emf;
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}


/* 1. limpiar archivos generados y duplicados
2. renombrar EstadoPedido a Estado
3. cambiar contraseña por contrasena
4. implementar ProductoRepository, UsuarioRepository y PedidoRepository
5. migrar categorías desde el segundo parcial
6. migrar productos desde el segundo parcial, adaptando la relación con Categoria
7. implementar usuarios
8. implementar pedidos
9. implementar reportes
10. probar flujo completo */
