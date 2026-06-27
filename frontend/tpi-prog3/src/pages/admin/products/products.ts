import { checkAuhtUser } from "../../../utils/auth";
import { getProductos } from "../../../utils/api";
import type { Product } from "../../../types/product";

// valido que sea admin
const tienePermiso = checkAuhtUser(
  "/src/pages/auth/login/login.html",
  "/src/pages/store/home/home.html",
  "ADMIN"
);

if (!tienePermiso) {
  throw new Error("sin permiso");
}

// traigo contenedor para mostrar productos
const contenedorProductos = document.getElementById(
  "contenedor-productos-admin"
) as HTMLElement;

// funcion para mostrar productos
const renderProductos = (productos: Product[]) => {
  contenedorProductos.innerHTML = "";

  if (productos.length === 0) {
    const mensaje = document.createElement("p");
    mensaje.textContent = "No hay productos cargados";

    contenedorProductos.appendChild(mensaje);

    return;
  }

  contenedorProductos.innerHTML = `
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Imagen</th>
          <th>Nombre</th>
          <th>Descripcion</th>
          <th>Precio</th>
          <th>Categoria</th>
          <th>Stock</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>

      <tbody>
        ${productos
          .map(
            (producto) => `
              <tr>
                <td>${producto.id}</td>
                <td>
                  <img 
                    src="${producto.imagen}" 
                    alt="${producto.nombre}" 
                    class="admin-img"
                  >
                </td>
                <td>${producto.nombre}</td>
                <td>${producto.descripcion}</td>
                <td>$${producto.precio}</td>
                <td>${producto.categoria.nombre}</td>
                <td>${producto.stock}</td>
                <td>${producto.disponible ? "Disponible" : "No disponible"}</td>
                <td>
                  <button>Editar</button>
                  <button>Eliminar</button>
                </td>
              </tr>
            `
          )
          .join("")}
      </tbody>
    </table>
  `;
};

// funcion para cargar productos
const cargarProductos = async () => {
  const productos = await getProductos(); // traigo productos desde el json

  renderProductos(productos); // muestro productos en pantalla
};

// ejecuto
cargarProductos();