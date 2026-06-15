package com.tp.jpa;

import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.PedidoRepository;
import com.tp.jpa.repository.ProductoRepository;
import com.tp.jpa.repository.UsuarioRepository;
import java.util.List;
import com.tp.jpa.util.JPAUtil;
import com.tp.jpa.model.Categoria;

import java.util.Scanner;

/**
 * Clase principal: menú de consola del sistema Food Store.
 * Orden de uso natural: Categorías -> Productos -> Usuarios -> Pedidos.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final CategoriaRepository categoriaRepo = new CategoriaRepository();
    private static final ProductoRepository productoRepo = new ProductoRepository();
    private static final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static final PedidoRepository pedidoRepo = new PedidoRepository();

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("===== FOOD STORE - MENÚ PRINCIPAL =====");
            System.out.println("1. Gestionar Categorías");
            System.out.println("2. Gestionar Productos");
            System.out.println("3. Gestionar Usuarios");
            System.out.println("4. Gestionar Pedidos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();
            switch (op) {
                case "1": menuCategorias(); break;
                case "2": menuProductos(); break;
                case "3": menuUsuarios(); break;
                case "4": menuPedidos(); break;
                case "5": menuReportes(); break;
                case "0": salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        }
        JPAUtil.close();
        System.out.println("Aplicación finalizada.");
    }

    // ── Submenús ─────────────────────────────────────────────────
    // ── categorias ─────────────────────────────────────────────────

    private static void menuCategorias() {
        int opcion;
        do {
            System.out.println("\n----- categorias -----");
            System.out.println("1. alta");
            System.out.println("2. modificacion");
            System.out.println("3. eliminar categoria");
            System.out.println("4. listado");
            System.out.println("0. volver");
            System.out.print("opcion: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: altaCategoria(); break;
                case 2: modificarCategoria(); break;
                case 3: bajaCategoria(); break;
                case 4: listarCategorias(); break;
                case 0: System.out.println("volviendo al menu principal"); break;
                default: System.out.println("opcion invalida");
            }
        } while (opcion != 0);
    }

    // ── productos ─────────────────────────────────────────────────
    private static void menuProductos() {
        int opcion;
        do {
            System.out.println("\n----- productos -----");
            System.out.println("1. alta");
            System.out.println("2. modificacion");
            System.out.println("3. eliminar producto");
            System.out.println("4. listado");
            System.out.println("0. volver");
            System.out.print("opcion: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: altaProducto(); break;
                case 2: modificarProducto(); break;
                case 3: bajaProducto(); break;
                case 4: listarProductos(); break;
                case 0: System.out.println("volviendo al menu principal");
                    break;
                default: System.out.println("opcion invalida");
            }

        } while (opcion != 0);
    }

    // ── usuarios ─────────────────────────────────────────────────
    private static void menuUsuarios() {
        int opcion;
        do {
            System.out.println("\n----- usuarios -----");
            System.out.println("1. alta");
            System.out.println("2. modificacion");
            System.out.println("3. eliminar usuario");
            System.out.println("4. listado");
            System.out.println("5. buscar por mail");
            System.out.println("0. volver");
            System.out.print("opcion: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: altaUsuario(); break;
                case 2: modificarUsuario(); break;
                case 3: bajaUsuario(); break;
                case 4: listarUsuarios(); break;
                case 5: buscarUsuarioPorMail(); break;
                case 0: System.out.println("volviendo al menu principal"); break;
                default: System.out.println("opcion invalida");
            }
        } while (opcion != 0);
    }

    // ── pedidos ─────────────────────────────────────────────────
    private static void menuPedidos() {
        int opcion;
        do {
            System.out.println("\n----- pedidos -----");
            System.out.println("1. alta de pedido");
            System.out.println("2. cambiar estado");
            System.out.println("3. eliminar pedido");
            System.out.println("4. listado");
            System.out.println("5. pedidos por usuario");
            System.out.println("6. pedidos por estado");
            System.out.println("0. volver");
            System.out.print("opcion: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: altaPedido(); break;
                case 2: cambiarEstadoPedido(); break;
                case 3: bajaPedido(); break;
                case 4: listarPedidos(); break;
                case 5: pedidosPorUsuario(); break;
                case 6: pedidosPorEstado(); break;
                case 0: System.out.println("volviendo al menu principal"); break;
                default: System.out.println("opcion invalida");
            }
        } while (opcion != 0);
    }

    // ── reportes ─────────────────────────────────────────────────
    private static void menuReportes() {
        int opcion;
        do {
            System.out.println("\n----- reportes -----");
            System.out.println("1. productos por categoria");
            System.out.println("2. pedidos por usuario");
            System.out.println("3. pedidos por estado");
            System.out.println("4. total facturado");
            System.out.println("0. volver");
            System.out.print("opcion: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: productosPorCategoria(); break;
                case 2: pedidosPorUsuario(); break;
                case 3: pedidosPorEstado(); break;
                case 4: totalFacturado(); break;
                case 0: System.out.println("volviendo al menu principal"); break;
                default: System.out.println("opcion invalida");
            }
        } while (opcion != 0);
    }

    // ── funciones ─────────────────────────────────────────────────
    // ── categorias ─────────────────────────────────────────────────

    private static void altaCategoria() {
        System.out.println("[alta categoria] pendiente");
    }

    private static void modificarCategoria() {
        System.out.println("[modificar categoria] pendiente");
    }

    private static void bajaCategoria() {
        System.out.println("[baja categoria] pendiente");
    }

    private static void listarCategorias() {
        System.out.println("[listar categorias] pendiente");
    }

// ── productos ─────────────────────────────────────────────────

    private static void altaProducto() {
        System.out.println("[alta producto] pendiente");
    }

    private static void modificarProducto() {
        System.out.println("[modificar producto] pendiente");
    }

    private static void bajaProducto() {
        System.out.println("[baja producto] pendiente");
    }

    private static void listarProductos() {
        System.out.println("[listar productos] pendiente");
    }

    private static void productosPorCategoria() {
        System.out.println("[productos por categoria] pendiente");
    }

// ── usuarios ─────────────────────────────────────────────────

    private static void altaUsuario() {
        System.out.println("[alta usuario] pendiente");
    }

    private static void modificarUsuario() {
        System.out.println("[modificar usuario] pendiente");
    }

    private static void bajaUsuario() {
        System.out.println("[baja usuario] pendiente");
    }

    private static void listarUsuarios() {
        System.out.println("[listar usuarios] pendiente");
    }

    private static void buscarUsuarioPorMail() {
        System.out.println("[buscar usuario por mail] pendiente");
    }

// ── pedidos ─────────────────────────────────────────────────

    private static void altaPedido() {
        System.out.println("[alta pedido] pendiente");
    }

    private static void cambiarEstadoPedido() {
        System.out.println("[cambiar estado pedido] pendiente");
    }

    private static void bajaPedido() {
        System.out.println("[baja pedido] pendiente");
    }

    private static void listarPedidos() {
        System.out.println("[listar pedidos] pendiente");
    }

    private static void pedidosPorUsuario() {
        System.out.println("[pedidos por usuario] pendiente");
    }

    private static void pedidosPorEstado() {
        System.out.println("[pedidos por estado] pendiente");
    }

// ── reportes ─────────────────────────────────────────────────

    private static void totalFacturado() {
        System.out.println("[total facturado] pendiente");
    }

// ── lecturas ─────────────────────────────────────────────────

    private static int leerEntero() {
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

}
