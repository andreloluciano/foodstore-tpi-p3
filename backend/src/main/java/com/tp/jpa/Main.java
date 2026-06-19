package com.tp.jpa;

import java.util.ArrayList;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.enums.Estado;
import com.tp.jpa.model.enums.FormaPago;
import com.tp.jpa.repository.CategoriaRepository;
import com.tp.jpa.repository.PedidoRepository;
import com.tp.jpa.repository.ProductoRepository;
import com.tp.jpa.repository.UsuarioRepository;
import java.util.List;
import com.tp.jpa.util.JPAUtil;
import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Producto;
import com.tp.jpa.model.Usuario;
import com.tp.jpa.model.enums.Rol;
import java.util.Optional;



import java.util.Scanner;


/**
 * Clase principal: menú de consola del sistema Food Store.
 * Orden de uso natural: Categorías -> Productos -> Usuarios -> Pedidos.
 */
public class Main {

    static {
        LogManager.getLogManager().reset();
        Logger.getLogger("").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.SQL").setLevel(Level.OFF);
        Logger.getLogger("org.hibernate.orm.jdbc.bind").setLevel(Level.OFF);
    }

    private static final Scanner sc = new Scanner(System.in);

    private static final CategoriaRepository categoriaRepo = new CategoriaRepository();
    private static final ProductoRepository productoRepo = new ProductoRepository();
    private static final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private static final PedidoRepository pedidoRepo = new PedidoRepository();

    public static void main(String[] args) {

        LogManager.getLogManager().reset();
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

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
        System.out.print("nombre: ");
        String nombre = sc.nextLine();

        if (nombre.isBlank()) {
            System.out.println("el nombre no puede estar vacio");
            return;
        }

        System.out.print("descripcion: ");
        String descripcion = sc.nextLine();

        Categoria categoria = Categoria.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .build(); // eliminado y fecha vienen por default

        Categoria categoriaGuardada = categoriaRepo.guardar(categoria); // guardo en variable para mostrar id

        System.out.println("categoria " + categoriaGuardada.getNombre()
                + " creada con id: " + categoriaGuardada.getId());
    }

    private static void modificarCategoria() {

        System.out.println("categorias disponibles para modificar: ");
        listarCategorias();

        System.out.print("id de categoria a editar: ");
        Long id = leerLong();

        Categoria categoria = categoriaRepo.buscarPorId(id).orElse(null);

        if (categoria == null || categoria.isEliminado()) {
            System.out.println("no existe una categoria activa con ese id");
            return;
        }

        System.out.println("nombre actual: " + categoria.getNombre());
        System.out.print("nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.println("descripcion actual: " + categoria.getDescripcion());
        System.out.print("nueva descripcion: ");
        String descripcion = sc.nextLine();

        if (!nombre.isBlank()) {
            categoria.setNombre(nombre);
        }

        if (!descripcion.isBlank()) {
            categoria.setDescripcion(descripcion);
        }

        categoriaRepo.guardar(categoria);

        System.out.println("categoria modificada correctamente");
    }

    private static void bajaCategoria() {

        System.out.println("categorias disponibles para eliminar: ");
        listarCategorias();

        System.out.print("id de categoria a eliminar: ");
        Long id = leerLong();

        // guardo en variable para obtener su nombre
        Categoria categoria = categoriaRepo.buscarPorId(id).orElse(null);

        // valido que exista y no este eliminada
        if (categoria == null || categoria.isEliminado()) {
            System.out.println("no existe una categoria activa con ese id");
            return;
        }

        boolean eliminada = categoriaRepo.eliminarLogico(id);

        if (eliminada) { // uso el boolean de eliminar
            System.out.println("categoria " + categoria.getNombre() + ", id: "
                    + categoria.getId() + " eliminada");
        } else {
            System.out.println("no se pudo eliminar la categoria");
        }
    }

    private static void listarCategorias() {
        List<Categoria> categorias = categoriaRepo.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("no hay categorias activas");
            return;
        }

        for (Categoria categoria : categorias) {
            System.out.println(
                    categoria.getId()
                            + " - "
                            + categoria.getNombre()
                            + " - "
                            + categoria.getDescripcion()
            );
        }
    }

// ── productos ─────────────────────────────────────────────────

    private static void altaProducto() {

        // categorias activas
        System.out.println("categorias disponibles: ");
        List<Categoria> categorias = categoriaRepo.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("no hay categorias disponibles");
            return;
        }

        for (Categoria categoria : categorias) { // muestro las opciones
            System.out.println(
                    categoria.getId()
                            + " - "
                            + categoria.getNombre()
            );
        }

        System.out.print("id de categoria: ");
        Long idCategoria = leerLong();

        // busco y valido
        Categoria categoria = categoriaRepo.buscarPorId(idCategoria).orElse(null);
        if (categoria == null || categoria.isEliminado()) {
            System.out.println("categoria no encontrada");
            return;
        }

        // solicito nombre
        System.out.print("nombre: ");
        String nombre = sc.nextLine();

        if (nombre.isBlank()) {
            System.out.println("el nombre no puede estar vacio");
            return;
        }

        // pido descripcion
        System.out.print("descripcion: ");
        String descripcion = sc.nextLine();

        // pido y valido precio
        System.out.print("precio: ");
        Double precio = leerDouble();

        if (precio <= 0) {
            System.out.println("el precio debe ser mayor a 0");
            return;
        }

        // pido y valido stock
        System.out.print("stock: ");
        int stock = leerEntero();

        if (stock < 0) {
            System.out.println("el stock no puede ser negativo");
            return;
        }

        System.out.print("imagen: ");
        String imagen = sc.nextLine();

        System.out.print("disponible s/n: ");
        String disponibleTexto = sc.nextLine();

        boolean disponible = !disponibleTexto.equalsIgnoreCase("n");

        Producto producto = Producto.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .stock(stock)
                .imagen(imagen)
                .disponible(disponible)
                .build();

        Producto productoGuardado = productoRepo.guardarProductoEnCategoria(idCategoria, producto);

        if (productoGuardado == null) {
            System.out.println("no se pudo crear el producto");
            return;
        }

        System.out.println( // muestro el id generado y la categoria asignada
                "producto creado con id: "
                        + productoGuardado.getId()
                        + " en categoria: "
                        + categoria.getNombre()
        );
    }

    private static void modificarProducto() {

        System.out.println("productos disponibles para modificar: ");
        listarProductos();

        System.out.print("id de producto a modificar: ");
        Long id = leerLong();

        // busco por id del input
        Producto producto = productoRepo.buscarPorId(id).orElse(null);

        if (producto == null || producto.isEliminado()) { // valido
            System.out.println("no existe un producto activo con ese id");
            return;
        }

        // inputs para nombre, precio, stock e imagen
        System.out.println("nombre actual: " + producto.getNombre());
        System.out.print("nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.println("descripcion actual: " + producto.getDescripcion());
        System.out.print("nueva descripcion: ");
        String descripcion = sc.nextLine();

        System.out.println("precio actual: " + producto.getPrecio());
        System.out.print("nuevo precio: ");
        String precioTexto = sc.nextLine(); // en string para poder dejar input vacio

        System.out.println("stock actual: " + producto.getStock());
        System.out.print("nuevo stock: ");
        String stockTexto = sc.nextLine();

        System.out.println("imagen actual: " + producto.getImagen());
        System.out.print("nueva imagen: ");
        String imagen = sc.nextLine();

        System.out.println("disponible actual: " + producto.getDisponible());
        System.out.print("nuevo disponible s/n/vacio para mantener: ");
        String disponibleTexto = sc.nextLine();

        // si los campos no estan vacios los actualizo, sino mantiene el valor
        if (!nombre.isBlank()) {
            producto.setNombre(nombre);
        }

        if (!descripcion.isBlank()) {
            producto.setDescripcion(descripcion);
        }

        if (!precioTexto.isBlank()) {
            double precio = Double.parseDouble(precioTexto); // convierto a double

            if (precio <= 0) {
                System.out.println("el precio debe ser mayor a 0");
                return;
            }

            producto.setPrecio(precio);
        }

        if (!stockTexto.isBlank()) {
            int stock = Integer.parseInt(stockTexto);

            if (stock < 0) {
                System.out.println("el stock no puede ser negativo");
                return;
            }

            producto.setStock(stock);
        }

        if (!imagen.isBlank()) {
            producto.setImagen(imagen);
        }

        if (!disponibleTexto.isBlank()) {
            producto.setDisponible(!disponibleTexto.equalsIgnoreCase("n"));
        }

        productoRepo.guardar(producto); // commit

        System.out.println("producto modificado correctamente");
    }

    private static void bajaProducto() {

        // muestro los productos activos antes de pedir el id
        System.out.println("productos disponibles: ");
        listarProductos();

        // pido el id del producto
        System.out.print("id de producto a eliminar: ");
        Long id = leerLong();

        // busco por id
        Producto producto = productoRepo.buscarPorId(id).orElse(null);

        if (producto == null || producto.isEliminado()) { // valido
            System.out.println("no existe un producto activo con ese id");
            return;
        }

        // guardo el valor en una variable para ver si quedo true o false y muestro
        boolean eliminado = productoRepo.eliminarLogico(id);

        if (eliminado) {
            System.out.println("producto " + producto.getNombre() + ", id: "
                    + producto.getId() + " eliminado");
        } else {
            System.out.println("no se pudo eliminar el producto");
        }
    }

    private static void listarProductos() {

        List<Producto> productos = productoRepo.listarActivos();

        if (productos.isEmpty()) {
            System.out.println("no hay productos activos");
            return;
        }

        for (Producto producto : productos) {

            // busco el nombre de categoria con jpql porque producto no tiene categoria
            String nombreCategoria = productoRepo.buscarNombreCategoriaProducto(producto.getId());

            System.out.println(
                    producto.getId()
                            + " - "
                            + producto.getNombre()
                            + " - precio: "
                            + producto.getPrecio()
                            + " - stock: "
                            + producto.getStock()
                            + " - disponible: "
                            + producto.getDisponible()
                            + " - categoria: "
                            + nombreCategoria
            );
        }
    }

    private static void productosPorCategoria() {

        List<Categoria> categorias = categoriaRepo.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("no hay categorias activas");
            return;
        }

        System.out.println("categorias disponibles: ");
        listarCategorias();

        System.out.print("id de categoria: ");
        Long idCategoria = leerLong();

        // busco segun input
        Categoria categoria = categoriaRepo.buscarPorId(idCategoria).orElse(null);

        // valido
        if (categoria == null || categoria.isEliminado()) {
            System.out.println("no existe una categoria activa con ese id");
            return;
        }

        // busco con el jpql
        List<Producto> productos = productoRepo.buscarPorCategoria(idCategoria);

        if (productos.isEmpty()) {
            System.out.println("no hay productos activos en esta categoria");
            return;
        }

        System.out.println("productos de la categoria: " + categoria.getNombre());

        for (Producto producto : productos) {
            System.out.println(
                    producto.getId()
                            + " - "
                            + producto.getNombre()
                            + " - precio: "
                            + producto.getPrecio()
                            + " - stock: "
                            + producto.getStock()
            );
        }
    }

// ── usuarios ─────────────────────────────────────────────────

    private static void altaUsuario() {

        // solicito datos
        System.out.print("nombre: ");
        String nombre = sc.nextLine();

        if (nombre.isBlank()) {
            System.out.println("el nombre no puede estar vacio");
            return;
        }


        System.out.print("apellido: ");
        String apellido = sc.nextLine();

        if (apellido.isBlank()) {
            System.out.println("el apellido no puede estar vacio");
            return;
        }

        System.out.print("mail: ");
        String mail = sc.nextLine();

        if (mail.isBlank()) {
            System.out.println("el mail no puede estar vacio");
            return;
        }

        // busco si ya existe un usuario activo con ese mail
        Optional<Usuario> usuarioExistente = usuarioRepo.buscarPorMail(mail);

        if (usuarioExistente.isPresent()) {
            System.out.println("ya existe un usuario activo con ese mail");
            return;
        }

        System.out.print("celular: ");
        String celular = sc.nextLine();

        // solicito contraseña
        System.out.print("contraseña: ");
        String contrasena = sc.nextLine();

        if (contrasena.isBlank()) {
            System.out.println("la contraseña no puede estar vacia");
            return;
        }

        // seleccion de rol
        System.out.println("seleccione el rol: ");
        System.out.println("1. admin");
        System.out.println("2. usuario");
        System.out.print("opcion: ");
        int opcionRol = leerEntero();
        Rol rol;

        if (opcionRol == 1) {
            rol = Rol.ADMIN;
        } else if (opcionRol == 2) {
            rol = Rol.USUARIO;
        } else {
            System.out.println("rol invalido");
            return;
        }

        // creo el usuario con los datos ingresados
        Usuario usuario = Usuario.builder()
                .nombre(nombre)
                .apellido(apellido)
                .mail(mail)
                .celular(celular)
                .contrasena(contrasena)
                .rol(rol)
                .build();

        // guardo y uso el retorno para mostrar el id generado
        Usuario usuarioGuardado = usuarioRepo.guardar(usuario);

        System.out.println("usuario " + usuarioGuardado.getNombre()
                + " " + usuarioGuardado.getApellido()
                + " creado con id: " + usuarioGuardado.getId());
    }

    private static void modificarUsuario() {

        System.out.println("usuarios disponibles para modificar: ");
        listarUsuarios();

        System.out.print("id de usuario a modificar: ");
        Long id = leerLong();

        // busco por id del input
        Usuario usuario = usuarioRepo.buscarPorId(id).orElse(null);

        if (usuario == null || usuario.isEliminado()) { // valido
            System.out.println("no existe un usuario activo con ese id");
            return;
        }

        // muestro valores actuales y pido nuevos datos
        System.out.println("nombre actual: " + usuario.getNombre());
        System.out.print("nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.println("apellido actual: " + usuario.getApellido());
        System.out.print("nuevo apellido: ");
        String apellido = sc.nextLine();

        System.out.println("mail actual: " + usuario.getMail());
        System.out.print("nuevo mail: ");
        String mail = sc.nextLine();

        System.out.println("celular actual: " + usuario.getCelular());
        System.out.print("nuevo celular: ");
        String celular = sc.nextLine();

        System.out.print("nueva contrasena: ");
        String contrasena = sc.nextLine();

        // si los campos no estan vacios los actualizo, sino mantiene el valor
        if (!nombre.isBlank()) {
            usuario.setNombre(nombre);
        }

        if (!apellido.isBlank()) {
            usuario.setApellido(apellido);
        }

        if (!mail.isBlank()) {

            // busco si el nuevo mail ya esta usado por otro usuario
            Optional<Usuario> usuarioConMail = usuarioRepo.buscarPorMail(mail);

            if (usuarioConMail.isPresent() && !usuarioConMail.get().getId().equals(usuario.getId())) {
                System.out.println("ya existe otro usuario activo con ese mail");
                return;
            }

            usuario.setMail(mail);
        }

        if (!celular.isBlank()) {
            usuario.setCelular(celular);
        }

        if (!contrasena.isBlank()) {
            usuario.setContrasena(contrasena);
        }

        usuarioRepo.guardar(usuario); // commit

        System.out.println("usuario modificado correctamente");
    }

    private static void bajaUsuario() {

        System.out.println("usuarios disponibles para dar de baja: ");
        listarUsuarios();

        System.out.print("id de usuario a suspender: ");
        Long id = leerLong();

        // busco por id
        Usuario usuario = usuarioRepo.buscarPorId(id).orElse(null);

        // valido que exista y no este eliminado
        if (usuario == null || usuario.isEliminado()) {
            System.out.println("no existe un usuario activo con ese id");
            return;
        }

        // uso el boolean de eliminar para saber si se pudo dar de baja
        boolean eliminado = usuarioRepo.eliminarLogico(id);

        if (eliminado) {
            System.out.println("usuario " + usuario.getNombre() + " "
                    + usuario.getApellido() + ", id: "
                    + usuario.getId() + " dado de baja");
        } else {
            System.out.println("no se pudo dar de baja el usuario");
        }
    }

    private static void listarUsuarios() {

        List<Usuario> usuarios = usuarioRepo.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("no hay usuarios activos");
            return;
        }

        for (Usuario usuario : usuarios) {
            System.out.println(
                    usuario.getId()
                            + " - "
                            + usuario.getNombre()
                            + " "
                            + usuario.getApellido()
                            + " - mail: "
                            + usuario.getMail()
                            + " - rol: "
                            + usuario.getRol()
            );
        }
    }

    private static void buscarUsuarioPorMail() {

        System.out.print("mail del usuario a buscar: ");
        String mail = sc.nextLine();

        if (mail.isBlank()) {
            System.out.println("el mail no puede estar vacio");
            return;
        }

        // busco usuario  por mail
        Optional<Usuario> usuarioBusco = usuarioRepo.buscarPorMail(mail);

        // valido si no encontro nada
        if (usuarioBusco.isEmpty()) {
            System.out.println("no existe usuario activo con ese mail");
            return;
        }

        Usuario usuario = usuarioBusco.get(); // si pasa el if agarro el usuario del Optional y lo guardo

        System.out.println("id: " + usuario.getId());
        System.out.println("nombre: " + usuario.getNombre());
        System.out.println("apellido: " + usuario.getApellido());
        System.out.println("mail: " + usuario.getMail());
        System.out.println("celular: " + usuario.getCelular());
        System.out.println("rol: " + usuario.getRol());
    }

// ── pedidos ─────────────────────────────────────────────────

    private static void altaPedido() {

        // valido que haya usuarios
        List<Usuario> usuarios = usuarioRepo.listarActivos();

        if (usuarios.isEmpty()) {
            System.out.println("no hay usuarios activos");
            return;
        }

        System.out.println("usuarios disponibles: ");
        listarUsuarios();

        System.out.print("id de usuario: ");
        Long idUsuario = leerLong();

        Usuario usuario = usuarioRepo.buscarPorId(idUsuario).orElse(null);

        if (usuario == null || usuario.isEliminado()) {
            System.out.println("no existe un usuario activo con ese id");
            return;
        }

        // selecciono forma de pago
        FormaPago formaPago = elegirFormaPago();

        if (formaPago == null) {
            System.out.println("forma de pago invalida");
            return;
        }

        // listas temporales
        List<Long> idsProductos = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();

        String seguir = "s";

        while (seguir.equalsIgnoreCase("s")) {

            System.out.println("productos disponibles: ");
            listarProductos();

            System.out.print("id de producto: ");
            Long idProducto = leerLong();

            Producto producto = productoRepo.buscarPorId(idProducto).orElse(null);

            if (producto == null || producto.isEliminado()) {
                System.out.println("no existe un producto activo con ese id");
                return; // usar continue ?? return corta el run
            }

            if (!Boolean.TRUE.equals(producto.getDisponible())) {
                System.out.println("el producto no esta disponible");
                return; // usar continue ?? return corta el run
            }

            System.out.print("cantidad: ");
            int cantidad = leerEntero();

            if (cantidad <= 0) {
                System.out.println("la cantidad debe ser mayor a 0");
                return; // usar continue ?? return corta el run
            }

            if (producto.getStock() < cantidad) {
                System.out.println("stock insuficiente, stock disponible: " + producto.getStock());
                return;
            }

            // guardo el id y la cantidad en la misma posicion
            idsProductos.add(idProducto);
            cantidades.add(cantidad);

            System.out.print("agregar otro producto s/n: ");
            seguir = sc.nextLine();
        }

        if (idsProductos.isEmpty()) {
            System.out.println("el pedido debe tener al menos un producto");
            return;
        }

        // finalmente guardo
        Pedido pedidoGuardado = pedidoRepo.guardarPedido(idUsuario, formaPago, idsProductos, cantidades);

        if (pedidoGuardado == null) {
            System.out.println("no se pudo crear el pedido");
            return;
        }

        System.out.println("pedido creado con id: " + pedidoGuardado.getId());
        System.out.println("usuario: " + usuario.getNombre() + " " + usuario.getApellido());
        System.out.println("forma de pago: " + formaPago);
        System.out.println("total: " + pedidoGuardado.getTotal());
    }

    private static void cambiarEstadoPedido() {

        System.out.println("pedidos disponibles: ");
        listarPedidos();

        System.out.print("id de pedido: ");
        Long id = leerLong();

        Pedido pedido = pedidoRepo.buscarPorId(id).orElse(null);

        // valido que exista
        if (pedido == null || pedido.isEliminado()) {
            System.out.println("no existe un pedido activo con ese id");
            return;
        }

        System.out.println("estado actual: " + pedido.getEstado());

        // selecciono nuevo estado
        Estado estado = elegirEstado();

        if (estado == null) {
            System.out.println("estado invalido");
            return;
        }

        pedido.setEstado(estado);   // actualizo el estado del pedido
        pedidoRepo.guardar(pedido);   // guardo el pedido modificado

        System.out.println("pedido " + pedido.getId()
                + " actualizado a estado: " + estado);
    }

    private static void bajaPedido() {

        System.out.println("pedidos disponibles para eliminar: ");
        listarPedidos();

        System.out.print("id de pedido a eliminar: ");
        Long id = leerLong();

        Pedido pedido = pedidoRepo.buscarPorId(id).orElse(null);

        // valido que exista
        if (pedido == null || pedido.isEliminado()) {
            System.out.println("no existe un pedido activo con ese id");
            return;
        }

        boolean eliminado = pedidoRepo.eliminarLogico(id); // eliminado a true

        if (eliminado) {
            System.out.println("pedido " + pedido.getId()
                    + " eliminado, total: " + pedido.getTotal());
        } else {
            System.out.println("no se pudo eliminar el pedido");
        }
    }

    private static void listarPedidos() {

        List<Pedido> pedidos = pedidoRepo.listarPedidosActivos();

         /*
    List<Pedido> pedidosOrdenados = pedidos.stream()
            .sorted((p1, p2) -> p2.getFecha().compareTo(p1.getFecha()))
            .toList();
    */

        if (pedidos.isEmpty()) {
            System.out.println("no hay pedidos activos");
            return;
        }

        for (Pedido pedido : pedidos) {

            // busco el nombre del usuario
            String nombreUsuario = pedidoRepo.buscarUsuario(pedido.getId());

            System.out.println(pedido.getId()
                            + " - fecha: " + pedido.getFecha()
                            + " - estado: " + pedido.getEstado()
                            + " - pago: " + pedido.getFormaPago()
                            + " - usuario: " + nombreUsuario
                            + " - total: " + pedido.getTotal()
            );
        }
    }

    private static void pedidosPorUsuario() {

        List<Usuario> usuarios = usuarioRepo.listarActivos(); // lo mismo que altaPedido

        if (usuarios.isEmpty()) {
            System.out.println("no hay usuarios activos");
            return;
        }

        System.out.println("usuarios disponibles: ");
        listarUsuarios();

        System.out.print("id de usuario: ");
        Long idUsuario = leerLong();

        // busco y valido el usuario elegido
        Usuario usuario = usuarioRepo.buscarPorId(idUsuario).orElse(null);

        if (usuario == null || usuario.isEliminado()) {
            System.out.println("no existe un usuario activo con ese id");
            return;
        }

        // busco los pedidos activos del usuario
        List<Pedido> pedidos = pedidoRepo.buscarPorUsuario(idUsuario);

        if (pedidos.isEmpty()) {
            System.out.println("el usuario no tiene pedidos activos");
            return;
        }

        System.out.println("pedidos de usuario: " + usuario.getNombre() + " " + usuario.getApellido());

        for (Pedido pedido : pedidos) {
            System.out.println(pedido.getId()
                            + " - fecha: " + pedido.getFecha()
                            + " - estado: " + pedido.getEstado()
                            + " - pago: " + pedido.getFormaPago()
                            + " - total: " + pedido.getTotal()
            );
        }
    }

    private static void pedidosPorEstado() {

        // selecciono el estado a consultar
        Estado estado = elegirEstado();

        if (estado == null) {
            System.out.println("estado invalido");
            return;
        }

        // busco pedidos activos
        List<Pedido> pedidos = pedidoRepo.buscarPorEstado(estado);

        if (pedidos.isEmpty()) {
            System.out.println("no hay pedidos con ese estado");
            return;
        }

        System.out.println("pedidos con estado: " + estado);

        for (Pedido pedido : pedidos) {

            // busco el nombre del usuario porque pedido no tiene usuario
            String nombreUsuario = pedidoRepo.buscarUsuario(pedido.getId());

            System.out.println(pedido.getId()
                            + " - fecha: " + pedido.getFecha()
                            + " - usuario: " + nombreUsuario
                            + " - total: " + pedido.getTotal()
            );
        }
    }

// ── reportes ─────────────────────────────────────────────────

    private static void totalFacturado() {

        // busco los pedidos terminados
        List<Pedido> pedidos = pedidoRepo.buscarPorEstado(Estado.TERMINADO);

        double total = pedidos.stream()
                .mapToDouble(pedido -> pedido.getTotal() != null ? pedido.getTotal() : 0)
                .sum();  // si el total del pedido es null usa 0

        System.out.println("total facturado: $" + total);
    }

// ── lecturas ── helpers ─────────────────────────────────────────────────

    private static int leerEntero() {
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    private static Long leerLong() {
        Long valor = sc.nextLong();
        sc.nextLine();
        return valor;
    }

    private static Double leerDouble() {
        Double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }

    private static FormaPago elegirFormaPago() {
        System.out.println("formas de pago disponibles: ");
        System.out.println("1. tarjeta");
        System.out.println("2. transferencia");
        System.out.println("3. efectivo");
        System.out.print("opcion: ");

        int opcion = leerEntero();

        return switch (opcion) {
            case 1 -> FormaPago.TARJETA;
            case 2 -> FormaPago.TRANSFERENCIA;
            case 3 -> FormaPago.EFECTIVO;
            default -> null;
        };
    }

    private static Estado elegirEstado() {
        System.out.println("estados disponibles: ");
        System.out.println("1. pendiente");
        System.out.println("2. confirmado");
        System.out.println("3. terminado");
        System.out.println("4. cancelado");
        System.out.print("opcion: ");

        int opcion = leerEntero();

        return switch (opcion) {
            case 1 -> Estado.PENDIENTE;
            case 2 -> Estado.CONFIRMADO;
            case 3 -> Estado.TERMINADO;
            case 4 -> Estado.CANCELADO;
            default -> null;
        };
    }

}
