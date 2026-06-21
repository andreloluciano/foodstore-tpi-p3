
import { checkAuhtUser } from "../../../utils/auth";
import { getProductos } from "../../../utils/api";
import type { Product, CartItem } from "../../../types/product";

const tienePermiso = checkAuhtUser(
  "/src/pages/auth/login/login.html",
  "/src/pages/admin/home/home.html",
  "USUARIO"
);

if (!tienePermiso) {
  throw new Error("sin permiso");
}

const contenedorDetalle = document.getElementById(
  "detalle-producto"
) as HTMLElement;

// funcion para agregar productos al carrito
const agregarAlCarrito = (producto: Product, cantidad: number) => {
  // traigo carrito del localstg, si no existe creo array vacio
  const carrito: CartItem[] = JSON.parse(localStorage.getItem("cart") || "[]");

  // busco si el producto ya existe
  const existe = carrito.find((item: CartItem) => item.id === producto.id);

  if (existe) {
    // valido que no pase el stock
    if (existe.quantity + cantidad > producto.stock) {
      alert("No hay stock suficiente");
      return;
    }

    existe.quantity += cantidad; // aumento cantidad si existe
  } else {
    carrito.push({ ...producto, quantity: cantidad }); // sino, agrego con cantidad elegida
  }

  localStorage.setItem("cart", JSON.stringify(carrito)); // guardo carrito actualizado el localSt

  alert("Producto agregado al carrito"); // temporal
};

// funcion para mostrar el detalle del producto
const renderDetalle = (producto: Product) => {
  contenedorDetalle.innerHTML = `
    <div class="detalle-card">
      <img src="${producto.imagen}" alt="${producto.nombre}" class="detalle-imagen">

      <div class="detalle-info">
        <p>${producto.categoria.nombre}</p>

        <h2>${producto.nombre}</h2>

        <p class="precio-detalle">$${producto.precio}</p>

        <span class="${producto.disponible ? "badge-disponible" : "badge-no-disponible"}">
          ${producto.disponible ? `Disponible Stock: ${producto.stock}` : "No disponible"}
        </span>

        <p>${producto.descripcion}</p>

        <label for="cantidad">Cantidad:</label>

        <div>
          <button id="restarCantidad">-</button>
          <input id="cantidad" type="number" value="1" min="1" max="${producto.stock}">
          <button id="sumarCantidad">+</button>
        </div>

        <button id="agregarCarrito">Agregar al carrito</button>
      </div>
    </div>
  `;

  const inputCantidad = document.getElementById("cantidad") as HTMLInputElement;
  const buttonRestar = document.getElementById("restarCantidad") as HTMLButtonElement;
  const buttonSumar = document.getElementById("sumarCantidad") as HTMLButtonElement;
  const buttonAgregar = document.getElementById("agregarCarrito") as HTMLButtonElement;

  if (!producto.disponible || producto.stock <= 0) {
    buttonAgregar.textContent = "No disponible";
    buttonAgregar.disabled = true;
    buttonAgregar.classList.add("boton-no-disponible");
  }

  buttonRestar.addEventListener("click", () => {
    const cantidad = Number(inputCantidad.value);

    if (cantidad > 1) {
      inputCantidad.value = String(cantidad - 1);
    }
  });

  buttonSumar.addEventListener("click", () => {
    const cantidad = Number(inputCantidad.value);

    if (cantidad < producto.stock) {
      inputCantidad.value = String(cantidad + 1);
    } else {
      alert("No hay mas stock disponible");
    }
  });

  buttonAgregar.addEventListener("click", () => {
    const cantidad = Number(inputCantidad.value);

    if (cantidad < 1) {
      alert("La cantidad debe ser mayor a 0");
      return;
    }

    if (cantidad > producto.stock) {
      alert("No hay stock suficiente");
      return;
    }

    agregarAlCarrito(producto, cantidad);
  });
};

// cargo producto desde el json usando el id de la url
const initPage = async () => {
  const params = new URLSearchParams(location.search);
  const id = Number(params.get("id"));

  const productos = await getProductos();

  const producto = productos.find((producto) => producto.id === id);

  if (!producto) {
    contenedorDetalle.innerHTML = "<p>No se encontro el producto</p>";
    return;
  }

  renderDetalle(producto);
};

initPage();