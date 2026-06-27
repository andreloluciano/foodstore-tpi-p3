import { checkAuhtUser } from "../../../utils/auth";
import { getPedidos } from "../../../utils/api";
import type { Pedido } from "../../../types/pedido";

// valido que sea admin
const tienePermiso = checkAuhtUser(
  "/src/pages/auth/login/login.html",
  "/src/pages/store/home/home.html",
  "ADMIN"
);

if (!tienePermiso) {
  throw new Error("sin permiso");
}

// traigo contenedor para mostrar pedidos
const contenedorPedidos = document.getElementById(
  "contenedor-pedidos-admin"
) as HTMLElement;

// traigo select para filtrar por estado
const filtroEstado = document.getElementById(
  "filtroEstado"
) as HTMLSelectElement;

// para mostrar el estado 
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

// funcion para cambiar estado de pedido
const cambiarEstadoPedido = (idPedido: number, nuevoEstado: string) => {
  const estadosGuardados = JSON.parse(
    localStorage.getItem("estadosPedidos") || "{}"
  );
  estadosGuardados[idPedido] = nuevoEstado;
  localStorage.setItem("estadosPedidos", JSON.stringify(estadosGuardados));
  alert("Estado actualizado correctamente");
  cargarPedidos(); // recargo pedidos actualizados
};

// renderizar pedidos
const renderPedidos = (pedidos: Pedido[]) => {
  contenedorPedidos.innerHTML = ""; // limpio antes de renderizar

  // si no hay pedidos muestro mensaje
  if (pedidos.length === 0) {
    const mensaje = document.createElement("p");
    mensaje.textContent = "No hay pedidos para mostrar";
    contenedorPedidos.appendChild(mensaje);
    return;
  }
  // recorro pedidos y creo una tarjeta por cada uno
  pedidos.forEach((pedido) => {
    const div = document.createElement("div");
    div.classList.add("pedido-card");
    // calculo cantidad total de productos del pedido
    const cantidadProductos = pedido.detalles.reduce(
      (acumulador, detalle) => acumulador + detalle.cantidad,
      0
    );
    // cargo los datos del pedido en la tarjeta
    div.innerHTML = `
      <h3>Pedido #${pedido.id}</h3>
      <p>Cliente: ${pedido.usuarioDto.nombre} ${pedido.usuarioDto.apellido}</p>
      <p>Fecha: ${pedido.fecha}</p>
      <p>Estado actual: ${textoEstado(pedido.estado)}</p>
      <p>Cantidad de productos: ${cantidadProductos}</p>
      <p>Total: $${pedido.total}</p>

      <label for="estado-${pedido.id}">Cambiar estado:</label>
      <select id="estado-${pedido.id}">
        <option value="PENDIENTE" ${pedido.estado === "PENDIENTE" ? "selected" : ""}>Pendiente</option>
        <option value="EN_PREPARACION" ${pedido.estado === "EN_PREPARACION" ? "selected" : ""}>En preparacion</option>
        <option value="ENTREGADO" ${pedido.estado === "ENTREGADO" ? "selected" : ""}>Entregado</option>
      </select>

      <button class="boton-actualizar-estado" data-id="${pedido.id}">
        Actualizar estado
      </button>
    `;
    contenedorPedidos.appendChild(div); // agrego tarjeta al contenedor
    // traigo boton para actualizar estado
    const botonActualizar = div.querySelector(
      ".boton-actualizar-estado"
    ) as HTMLButtonElement;
    // evento para probar el cambio de estado
botonActualizar.addEventListener("click", () => {
  const selectEstado = document.getElementById(
    `estado-${pedido.id}`
  ) as HTMLSelectElement;

  cambiarEstadoPedido(pedido.id, selectEstado.value);
});
  });
};
// funcion para cargar pedidos
const cargarPedidos = async () => {
  const pedidosJson = await getPedidos(); // traigo pedidos desde el json

  const pedidosConfirmados: Pedido[] = JSON.parse(
    localStorage.getItem("pedidosConfirmados") || "[]"
  ); // traigo pedidos confirmados desde localstorage

const pedidos = [...pedidosJson, ...pedidosConfirmados]; // junto pedidos del json y localstorage

const pedidosConEstados = aplicarEstadosGuardados(pedidos); // aplico estados modificados

const estadoSeleccionado = filtroEstado.value;

// filtro pedidos si se selecciona un estado
const pedidosFiltrados = estadoSeleccionado
  ? pedidosConEstados.filter((pedido) => pedido.estado === estadoSeleccionado)
  : pedidosConEstados;

  // ordeno pedidos mas recientes primero
  pedidosFiltrados.sort(
    (a, b) => new Date(b.fecha).getTime() - new Date(a.fecha).getTime()
  );

  renderPedidos(pedidosFiltrados); // muestro pedidos en pantalla
};

// evento para filtrar por estado
filtroEstado.addEventListener("change", () => {
  cargarPedidos();
});

cargarPedidos();