import { checkAuhtUser, logout } from "../../../utils/auth";
import { getCategorias, getProductos, getPedidos } from "../../../utils/api";


// boton para cerrar sesion
const buttonLogout = document.getElementById(
  "logoutButton"
) as HTMLButtonElement;

// traigo  estadisticas
const contenedorEstadisticas = document.getElementById(
  "contenedor-estadisticas"
) as HTMLElement;

// valido que sea admin
const tienePermiso = checkAuhtUser(
  "/src/pages/auth/login/login.html",
  "/src/pages/store/home/home.html",
  "ADMIN"
);

if (!tienePermiso) {
  throw new Error("sin permiso");
}

// cierro sesion
buttonLogout?.addEventListener("click", () => {
  logout();
});

// cargar estadisticas del panel
const cargarEstadisticas = async () => {
  const categorias = await getCategorias(); // traigo categorias
  const productos = await getProductos(); // traigo productos
  const pedidos = await getPedidos(); // traigo pedidos

  const productosDisponibles = productos.filter(
    (producto) => producto.disponible
  );

  const pedidosPendientes = pedidos.filter(
    (pedido) => pedido.estado === "PENDIENTE"
  );

  const pedidosPreparacion = pedidos.filter(
    (pedido) => pedido.estado === "EN_PREPARACION"
  );

  const pedidosEntregados = pedidos.filter(
    (pedido) => pedido.estado === "ENTREGADO"
  );

  contenedorEstadisticas.innerHTML = `
    <div class="admin-card">
      <h3>Total categorias</h3>
      <p>${categorias.length}</p>
    </div>

    <div class="admin-card">
      <h3>Total productos</h3>
      <p>${productos.length}</p>
    </div>

    <div class="admin-card">
      <h3>Total pedidos</h3>
      <p>${pedidos.length}</p>
    </div>

    <div class="admin-card">
      <h3>Productos disponibles</h3>
      <p>${productosDisponibles.length}</p>
    </div>

    <div class="admin-card">
      <h3>Pedidos pendientes</h3>
      <p>${pedidosPendientes.length}</p>
    </div>

    <div class="admin-card">
      <h3>Pedidos en preparacion</h3>
      <p>${pedidosPreparacion.length}</p>
    </div>

    <div class="admin-card">
      <h3>Pedidos entregados</h3>
      <p>${pedidosEntregados.length}</p>
    </div>
  `;
};

// ejecuto
cargarEstadisticas();