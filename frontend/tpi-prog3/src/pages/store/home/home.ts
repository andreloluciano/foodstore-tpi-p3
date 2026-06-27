
import { getProductos, getCategorias } from "../../../utils/api";
import type { Product, CartItem } from "../../../types/product";
import type { Category } from "../../../types/category";
import { checkAuhtUser, logout } from "../../../utils/auth";
const inputBuscar = document.getElementById("buscar") as HTMLInputElement;
const contenedor = document.getElementById("contenedor-productos") as HTMLDivElement; // contenedor
const listaCategorias = document.getElementById("lista-categorias") as HTMLUListElement; //importo categorias
const contadorProductos = document.getElementById("contador-productos") as HTMLParagraphElement; // contador de productos
const selectOrdenar = document.getElementById("ordenar") as HTMLSelectElement;
const logoutButton = document.getElementById("logoutButton") as HTMLButtonElement;
const mensajeToast = document.getElementById("mensaje-toast") as HTMLDivElement;
// valido que sea usuario
const tienePermiso = checkAuhtUser(
  "/src/pages/auth/login/login.html",
  "/src/pages/admin/home/home.html",
  "USUARIO"
);

if (!tienePermiso) {
  throw new Error("sin permiso");
}

// cierro sesion
logoutButton.addEventListener("click", () => {
  logout();
});

let productos: Product[] = [];
let categorias: Category[] = [];
let productosMostrados: Product[] = [];

// funcion para agregar productos al carrito
const agregarAlCarrito = (producto: Product) => { 
  // traigo carrito del localstg, si no existe creo array vacio
  const carrito: CartItem[] = JSON.parse(localStorage.getItem("cart") || "[]");

  // busco si el producto ya existe
  const existe = carrito.find((item: CartItem) => item.id === producto.id);

  if (existe) {
    // valido que no supere el stock
    if (existe.quantity >= producto.stock) {
      mostrarMensaje("No hay mas stock disponible");
      return;
    }

    existe.quantity += 1; // aumento cantidad si existe
  } else {
    // valido si no hay stock
    if (producto.stock <= 0) {
      mostrarMensaje("No hay stock disponible");
      return;
    }

    carrito.push({ ...producto, quantity: 1 }); // sino, agrego con cantidad 1
  }

  localStorage.setItem("cart", JSON.stringify(carrito)); // guardo carrito actualizado el localSt

  mostrarMensaje("Producto agregado al carrito"); // temporal
};

// funcion para mostrar mensaje temporal
const mostrarMensaje = (mensaje: string) => {
  mensajeToast.textContent = mensaje;
  mensajeToast.classList.add("mostrar-toast");

  setTimeout(() => {
    mensajeToast.classList.remove("mostrar-toast");
  }, 2000);
};

// funcion render, recorre los productos y los mete dentr odel contenedor
const renderProductos = (productos: Product[]) => { 
  productosMostrados = productos; // guardo los productos que se estan mostrando
  contenedor.innerHTML = ""; // limpia antes de renderizar
  contadorProductos.textContent = `Productos encontrados: ${productos.length}`; // contador

  // si no hay productos para mostrar muestro mensaje
  if (productos.length === 0) {
    const mensaje = document.createElement("p"); // creo un <p>
    mensaje.textContent = "No se encontraron productos"; // aplico texto al <p>

    contenedor.appendChild(mensaje);
    return; // corto la funcion
  }

  productos.forEach((producto) => { // cambio el producto.nombre para que sea un link a su detalle
    const card = document.createElement("div"); // creo contenedor para cada producto
    card.innerHTML = `
     <img src="${producto.imagen}" alt="${producto.nombre}" class="producto-imagen"> 
      <h3>
  <a href="/src/pages/store/productDetail/productDetail.html?id=${producto.id}" class="link-detalle">
    ${producto.nombre}
  </a>
</h3>
      <p>${producto.descripcion}</p>
      <p>Precio: $${producto.precio}</p>
       <span class="${producto.disponible ? "badge-disponible" : "badge-no-disponible"}"> 
    ${producto.disponible ? "Disponible" : "No disponible"}
  </span>
    `;

    

    // boton para agregar el producto
const button = document.createElement("button");

if (producto.disponible) { // chequeo si el producto está disponible, sino bloqueo el boton
  button.textContent = "Agregar al carrito";

  button.addEventListener("click", () => {
    agregarAlCarrito(producto); // con click llamo a la funcion 
  });
} else {
  button.textContent = "No disponible";
  button.disabled = true;
  button.classList.add("boton-no-disponible");
}

    card.appendChild(button); // agrego el boton a la card

    contenedor.appendChild(card); // meto producto(card) dentro del contenedor
  });
};

// mostrar categorias en pantalla
const renderCategorias = () => {
  listaCategorias.innerHTML = ""; // limpio antes de renderizar
 
  const liTodos = document.createElement("li");  // opcion para mostrar todos los productos 
  liTodos.textContent = "Todos";
  liTodos.addEventListener("click", () => { // evento para mostrar Todos
    renderProductos(productos);
  });

  listaCategorias.appendChild(liTodos);

  // recorro categorias y muestro
  categorias.forEach((categoria) => {
    const li = document.createElement("li");
    li.textContent = categoria.nombre;

    // evento de click para filtrar productos por categoria
    li.addEventListener("click", () => {
      const productosFiltrados = productos.filter((producto) => // filtra solo con productos de esa categoria
        producto.categoria.nombre === categoria.nombre
      );

      renderProductos(productosFiltrados); // renderizo con productos filtrados
    });

    listaCategorias.appendChild(li);
  });
};

// funcion busqueda
inputBuscar.addEventListener("input", () => { // listener para cuando el usuario escribe

  const texto = inputBuscar.value.toLowerCase(); // capturo texto ingresado

  const productosFiltrados = productos.filter((producto) => // filtro productos que coincidan con el texto ingresado
    producto.nombre.toLowerCase().includes(texto)
  );

  // renderizo resultados
  renderProductos(productosFiltrados);
});

// funcion para ordenar productos
const ordenarProductos = (productos: Product[]) => {
  const orden = selectOrdenar.value;

  const productosOrdenados = [...productos]; // copio array para no modificar el original

  if (orden === "nombre-asc") {
    productosOrdenados.sort((a, b) => a.nombre.localeCompare(b.nombre));
  }

  if (orden === "nombre-desc") {
    productosOrdenados.sort((a, b) => b.nombre.localeCompare(a.nombre));
  }

  if (orden === "precio-asc") {
    productosOrdenados.sort((a, b) => a.precio - b.precio);
  }

  if (orden === "precio-desc") {
    productosOrdenados.sort((a, b) => b.precio - a.precio);
  }

  return productosOrdenados;
};

// evento para ordenar productos
selectOrdenar.addEventListener("change", () => {
  const productosOrdenados = ordenarProductos(productosMostrados);
  renderProductos(productosOrdenados);
});

// cargo productos y categorias desde los json
const initPage = async () => {
  productos = await getProductos();
  categorias = await getCategorias();

  // render inicial
  renderProductos(productos); 
  renderCategorias();
};



initPage();

//console.log(contenedor) // testeo para ver productos