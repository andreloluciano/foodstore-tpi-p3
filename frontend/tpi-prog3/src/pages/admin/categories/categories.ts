
import { checkAuhtUser } from "../../../utils/auth";
import { getCategorias } from "../../../utils/api";
import type { Category } from "../../../types/category";

// valido que sea admin
const tienePermiso = checkAuhtUser(
  "/src/pages/auth/login/login.html",
  "/src/pages/store/home/home.html",
  "ADMIN"
);

if (!tienePermiso) {
  throw new Error("sin permiso");
}

// traigo contenedor para mostrar categorias
const contenedorCategorias = document.getElementById(
  "contenedor-categorias"
) as HTMLElement;

// funcion para renderizar categorias
const renderCategorias = (categorias: Category[]) => {
  contenedorCategorias.innerHTML = "";

  if (categorias.length === 0) {
    const mensaje = document.createElement("p");
    mensaje.textContent = "No hay categorias cargadas";

    contenedorCategorias.appendChild(mensaje);

    return;
  }

  contenedorCategorias.innerHTML = `
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Nombre</th>
          <th>Descripcion</th>
          <th>Acciones</th>
        </tr>
      </thead>

      <tbody>
        ${categorias
          .map(
            (categoria) => `
              <tr>
                <td>${categoria.id}</td>
                <td>${categoria.nombre}</td>
                <td>${categoria.descripcion}</td>
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

// funcion para cargar categorias
const cargarCategorias = async () => {
  const categorias = await getCategorias(); // traigo categorias desde el json

  renderCategorias(categorias); // muestro categorias en pantalla
};

// ejecuto
cargarCategorias();