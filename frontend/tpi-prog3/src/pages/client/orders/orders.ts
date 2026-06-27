import { checkAuhtUser } from "../../../utils/auth";
import { getPedidos } from "../../../utils/api";
import type { Pedido } from "../../../types/pedido";
import type { IUser } from "../../../types/IUser";

// valido que el usuario pueda entrar a esta pagina
const tienePermiso = checkAuhtUser(
  "/src/pages/auth/login/login.html",
  "/src/pages/admin/home/home.html",
  "USUARIO"
);

if (!tienePermiso) {
  throw new Error("sin permiso");
}

// traigo el contenedor donde voy a mostrar los pedidos
const contenedorPedidos = document.getElementById(
  "contenedor-pedidos"
) as HTMLElement;

// funcion para mostrar el estado mas prolijo
const textoEstado = (estado: string) => {
  if (estado === "ENTREGADO") {
    return "Entregado";
  }

  if (estado === "EN_PREPARACION") {
    return "En preparacion";
  }

  if (estado === "PENDIENTE") {
    return "Pendiente";
  }

  return estado;
};

// funcion para renderizar pedidos
const renderPedidos = (pedidos: Pedido[]) => {
  contenedorPedidos.innerHTML = "";

  // si no hay pedidos muestro mensaje
  if (pedidos.length === 0) {
    const mensaje = document.createElement("p");
    mensaje.textContent = "No tenes pedidos realizados";

    contenedorPedidos.appendChild(mensaje);

    return;
  }

  // recorro pedidos y creo una tarjeta por cada uno
  pedidos.forEach((pedido) => {
    const div = document.createElement("div");
    div.classList.add("pedido-card");

    // muestro los primeros 3 productos del pedido
    const productos = pedido.detalles
      .slice(0, 3)
      .map((detalle) => detalle.producto.nombre)
      .join(", ");

    div.innerHTML = `
      <h3>Pedido #${pedido.id}</h3>
      <p>Fecha: ${pedido.fecha}</p>
      <p>Estado: ${textoEstado(pedido.estado)}</p>
      <p>Productos: ${productos}</p>
      <p>Total: $${pedido.total}</p>
    `;

    contenedorPedidos.appendChild(div);
  });
};

// funcion para cargar pedidos del usuario
const cargarPedidos = async () => {
  const userData = localStorage.getItem("userData");

  if (!userData) {
    return;
  }

  const usuario: IUser = JSON.parse(userData);

  const pedidosJson = await getPedidos(); // traigo pedidos desde el json

  const pedidosConfirmados: Pedido[] = JSON.parse(
    localStorage.getItem("pedidosConfirmados") || "[]"
  ); // traigo pedidos confirmados desde localstorage

  const pedidos = [...pedidosJson, ...pedidosConfirmados]; // junto pedidos del json y localstorage

  const pedidosUsuario = pedidos.filter(
    (pedido) => pedido.usuarioDto.id === usuario.id
  ); // filtro pedidos del usuario logueado

  renderPedidos(pedidosUsuario); // muestro pedidos en pantalla
};

// ejecuto
cargarPedidos();