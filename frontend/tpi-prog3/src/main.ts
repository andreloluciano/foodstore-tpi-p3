//import { navigate } from "./utils/navigate";

// Traemos la sesión
const userData = localStorage.getItem("userData");

// si no hay sesion pido log in
if (!userData) {
  location.href = "/src/pages/auth/login/login.html";
} else {
  const user = JSON.parse(userData);

  const path = location.pathname;

  // si esta como clientey quiere entrar a admin
if (user.role === "client" && path.includes("/admin/")) {
  alert("No tenés permisos para acceder a la sección de administrador");
  location.href = "/src/pages/client/home/home.html";
}

}
