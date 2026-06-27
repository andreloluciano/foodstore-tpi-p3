import { checkAuhtUser, logout } from "../../../utils/auth";
import { getCategorias, getProductos, getPedidos } from "../../../utils/api";
import type { Pedido } from "../../../types/pedido";



// boton para cerrar sesion
const buttonLogout = document.getElementById(
  "logoutButton"
) as HTMLButtonElement;

// funcion para aplicar estados guardados en localstorage
const aplicarEstadosGuardados = (pedidos: Pedido[]) => {
  const estadosGuardados = JSON.parse(
    localStorage.getItem("estadosPedidos") || "{}"
  );

  return pedidos.map((pedido) => {
    const estadoGuardado = estadosGuardados[pedido.id];

    if (estadoGuardado) {
      return {
        ...pedido,
        estado: estadoGuardado,
      };
    }

    return pedido;
  });
};

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

// funcion para cargar estadisticas del panel
const cargarEstadisticas = async () => {
  const categorias = await getCategorias(); // traigo categorias
  const productos = await getProductos(); // traigo productos
  const pedidosJson = await getPedidos(); // traigo pedidos desde el json

  const pedidosConfirmados: Pedido[] = JSON.parse(
    localStorage.getItem("pedidosConfirmados") || "[]"
  ); // traigo pedidos confirmados desde localstorage

  const pedidos = [...pedidosJson, ...pedidosConfirmados]; // junto pedidos del json y localstorage

  const pedidosConEstados = aplicarEstadosGuardados(pedidos); // aplico estados modificados

  const productosDisponibles = productos.filter(
    (producto) => producto.disponible
  );

  const pedidosPendientes = pedidosConEstados.filter(
    (pedido) => pedido.estado === "PENDIENTE"
  );

  const pedidosPreparacion = pedidosConEstados.filter(
    (pedido) => pedido.estado === "EN_PREPARACION"
  );

  const pedidosEntregados = pedidosConEstados.filter(
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
      <p>${pedidosConEstados.length}</p>
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