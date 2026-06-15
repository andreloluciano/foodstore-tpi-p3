import enums.Estado;
import enums.FormaPago;
import enums.Rol;
import entities.Categoria;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Scanner;
import entities.Categoria;
import repository.CategoriaRepository;
import repository.ProductoRepository;
import util.JPAUtil;


public class Main {

    static Scanner scanner = new Scanner(System.in);
    static CategoriaRepository categoriaRepository = new CategoriaRepository();
    static ProductoRepository productoRepository = new ProductoRepository();

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println("\n----- menu principal -----");
            System.out.println("1. gestion de categorias");
            System.out.println("2. gestion de productos");
            System.out.println("3. reportes");
            System.out.println("4. salir");
            System.out.print("opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    menuCategorias();
                    break;
                case 2:
                    menuProductos();
                    break;
                case 3:
                    menuReportes();
                    break;
                case 4:
                    System.out.println("saliendo");
                    break;
                default:
                    System.out.println("opcion invalida");
            }

        } while (opcion != 4);

        JPAUtil.close();
    }

    public static void menuCategorias() {

        int opcion;

        do {
            System.out.println("\n----- categorias -----");
            System.out.println("1. alta");
            System.out.println("2. modificacion");
            System.out.println("3. eliminar categoria");
            System.out.println("4. listado");
            System.out.println("5. volver");
            System.out.print("opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    altaCategoria();
                    break;
                case 2:
                    modificarCategoria();
                    break;
                case 3:
                    bajaCategoria();
                    break;
                case 4:
                    listarCategorias();
                    break;
                case 5:
                    System.out.println("volviendo al menu principal");
                    break;
                default:
                    System.out.println("opcion invalida");
            }

        } while (opcion != 5);
    }

    public static void menuProductos() {

        int opcion;

        do {
            System.out.println("\n----- productos -----");
            System.out.println("1. alta");
            System.out.println("2. modificacion");
            System.out.println("3. elimnar producto");
            System.out.println("4. listado");
            System.out.println("5. volver");
            System.out.print("opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    altaProducto();
                    break;
                case 2:
                    modificarProducto();
                    break;
                case 3:
                    bajaProducto();
                    break;
                case 4:
                    listarProductos();
                    break;
                case 5:
                    System.out.println("volviendo al menu principal");
                    break;
                default:
                    System.out.println("opcion invalida");
            }

        } while (opcion != 5);
    }

    public static void menuReportes() {

        int opcion;

        do {
            System.out.println("\n----- reportes -----");
            System.out.println("1. productos por categoria");
            System.out.println("2. volver");
            System.out.print("opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    productosPorCategoria();
                    break;
                case 2:
                    System.out.println("volviendo al menu principal");
                    break;
                default:
                    System.out.println("opcion invalida");
            }

        } while (opcion != 2);
    }

    public static void altaCategoria() {
        System.out.print("nombre: ");
        String nombre = scanner.nextLine();

        if (nombre.isBlank()) {
            System.out.println("el nombre no puede estar vacio");
            return;
        }

        System.out.print("descripcion: ");
        String descripcion = scanner.nextLine();

        Categoria categoria = Categoria.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .build(); // elimnado y fecha viene por default

        // categoriaRepository.guardar(categoria);
        // System.out.println("categoria creada");
        Categoria categoriaGuardada = categoriaRepository.guardar(categoria); // guardo en variable para mostrar id
        System.out.println("categoria" + categoriaGuardada.getNombre() +
                " creada con id: " + categoriaGuardada.getId());
    }

    public static void bajaCategoria() {

        System.out.println("categorias disponibles para eliminar: ");
        listarCategorias();
        System.out.print("id de categoria a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        // guardo en variable para obtener su nombre
        Categoria categoria = categoriaRepository.buscarPorId(id).orElse(null);

        // valido que exista y no este eliminada
        if (categoria == null || categoria.isEliminado()) {
            System.out.println("no existe una categoria activa con ese id");
            return;
        }

        boolean eliminada = categoriaRepository.eliminarLogico(id);

        if (eliminada) { // uso el boolean de eliminar
            System.out.println("categoria " + categoria.getNombre() + ", ID: " +
                    categoria.getId() + " eliminada");
        }
    }

    public static void modificarCategoria() {

        System.out.println("categorias disponibles para modificar: ");
        listarCategorias();

        System.out.print("id de categoria a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Categoria categoria = categoriaRepository.buscarPorId(id).orElse(null);

        if (categoria == null || categoria.isEliminado()) {
            System.out.println("no existe una categoria activa con ese id");
            return;
        }

        System.out.println("nombre actual: " + categoria.getNombre());
        System.out.print("nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("descripcion actual: " + categoria.getDescripcion());
        System.out.print("nueva descripcion: ");
        String descripcion = scanner.nextLine();

        if (!nombre.isBlank()) {
            categoria.setNombre(nombre);
        }

        if (!descripcion.isBlank()) {
            categoria.setDescripcion(descripcion);
        }

        categoriaRepository.guardar(categoria);

        System.out.println("categoria modificada correctamente");
    }

    public static void listarCategorias() {
        List<Categoria> categorias = categoriaRepository.listarActivos();

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

    public static void altaProducto() {

        //  categorias activas
        System.out.println("categorias disponibles: ");
        List<Categoria> categorias = categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("no hay categorias disponibles");
            return;
        } // producto en categoria tiene nullable = false

        for (Categoria categoria : categorias) { // muestro las opciones
            System.out.println(
                    categoria.getId()
                            + " - "
                            + categoria.getNombre()
            );
        }

        System.out.print("id de categoria: ");
        Long idCategoria = scanner.nextLong();
        scanner.nextLine(); // pido el id de la categoria

      // busco y valido
        Categoria categoria = categoriaRepository.buscarPorId(idCategoria).orElse(null);
        if (categoria == null || categoria.isEliminado()) {
            System.out.println("categoria no encontrada");
            return;
        }

      // solicito nombre
        System.out.print("nombre: ");
        String nombre = scanner.nextLine();

        if (nombre.isBlank()) {
            System.out.println("el nombre no puede estar vacio");
            return;
        }

        // pido descripcion
        System.out.print("descripcion: ");
        String descripcion = scanner.nextLine();

        // pido y valido precio
        System.out.print("precio: ");
        double precio = scanner.nextDouble();

        if (precio <= 0) {
            System.out.println("el precio debe ser mayor a 0");
            scanner.nextLine();
            return;
        }

        // pido y valido stock
        System.out.print("stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        if (stock < 0) {
            System.out.println("el stock no puede ser negativo");
            return;
        }

        Producto producto = Producto.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .stock(stock)
                .disponible(true)
                .categoria(categoria)
                .build();

        //categoria.agregarProducto(producto); relacion cambiada
        //productoRepository.guardar(producto);
        //System.out.println("producto creado");

        Producto productoGuardado = productoRepository.guardar(producto); // guardo en variable para mostrar id
        System.out.println( // muestro el id generado y la categoria asignada
                "producto creado con id: "
                        + productoGuardado.getId()
                        + " en categoria: "
                        + categoria.getNombre()
        );
    }

    public static void bajaProducto() {

        // muestro los productos activos antes de pedir el id
        System.out.println("productos disponibles: ");
        listarProductos();

        // pido el id del producto
        System.out.print("id de producto a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        // busco por id
        Producto producto = productoRepository.buscarPorId(id).orElse(null);

        if (producto == null || producto.isEliminado()) { // valido
            System.out.println("no existe un producto activo con ese id");
            return;
        }

        // guardo el valor en una variable para ver si quedo true or false y muestro
        boolean eliminado = productoRepository.eliminarLogico(id);

        if (eliminado) {
            System.out.println("producto " + producto.getNombre() + ", ID: " +
                    producto.getId() + " eliminado");
        } else {
            System.out.println("no se pudo eliminar el producto");
        }
    }

    public static void modificarProducto() {

        System.out.println("productos disponibles para modificar: ");
        listarProductos();

        System.out.print("id de producto a modificar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        // busco por id del input
        Producto producto = productoRepository.buscarPorId(id).orElse(null);

        if (producto == null || producto.isEliminado()) { // valido
            System.out.println("no existe un producto activo con ese id");
            return;
        }

        // inputs para nombre, precio y stock
        System.out.println("nombre actual: " + producto.getNombre());
        System.out.print("nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("precio actual: " + producto.getPrecio());
        System.out.print("nuevo precio: ");
        String precioTexto = scanner.nextLine(); // en String para poder dejar input vacio

        System.out.println("stock actual: " + producto.getStock());
        System.out.print("nuevo stock: ");
        String stockTexto = scanner.nextLine();

        // si el nombre, precio y stock  no estan vacios los actualizo, sino mantien el valor
        if (!nombre.isBlank()) {
            producto.setNombre(nombre);
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

        productoRepository.guardar(producto);   // commit

        System.out.println("producto modificado correctamente");
    }

    public static void listarProductos() {

        List<Producto> productos = productoRepository.listarActivos();

        if (productos.isEmpty()) {
            System.out.println("no hay productos activos");
            return;
        }

        for (Producto producto : productos) {

            // ternario para obtener nombre de categoria
            String nombreCategoria = producto.getCategoria() != null
                    ? producto.getCategoria().getNombre()
                    : "sin categoria";

            // error de lazyinitializationexception
            System.out.println(
                    producto.getId()
                            + " - "
                            + producto.getNombre()
                            + " - precio: "
                            + producto.getPrecio()
                            + " - stock: "
                            + producto.getStock()
                            + " - categoria: "
                            + nombreCategoria
            );
        }
    }

    public static void productosPorCategoria() {

        List<Categoria> categorias = categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("no hay categorias activas");
            return;
        }

        System.out.println("categorias disponibles: ");
        listarCategorias();

        System.out.print("id de categoria: ");
        Long idCategoria = scanner.nextLong();
        scanner.nextLine();
        // busco segun input
        Categoria categoria = categoriaRepository.buscarPorId(idCategoria).orElse(null);

        // valido
        if (categoria == null || categoria.isEliminado()) {
            System.out.println("no existe una categoria activa con ese id");
            return;
        }

        // busco con el jpql
        List<Producto> productos = productoRepository.buscarPorCategoria(idCategoria);

        if (productos.isEmpty()) {
            System.out.println("no hay productos activos en esta categoria");
            return;
        }

        System.out.println("productos de la categoria: " + categoria.getNombre());
        for (Producto producto : productos) {
            System.out.println(
                    producto.getId() + " - " + producto.getNombre()
                            + " - precio: " + producto.getPrecio()
                            + " - stock: " + producto.getStock());
        }
    }
}
