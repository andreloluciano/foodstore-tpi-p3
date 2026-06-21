import { getUsuarios } from "../../../utils/api";
import type { IUser } from "../../../types/IUser";

const loginForm = document.getElementById("loginForm") as HTMLFormElement;
const inputEmail = document.getElementById("email") as HTMLInputElement;
const inputPassword = document.getElementById("password") as HTMLInputElement;

loginForm.addEventListener("submit", async (event) => {
  event.preventDefault(); // evito que recargue la pagina

  const email = inputEmail.value.trim(); // tomo el mail ingresado
  const password = inputPassword.value.trim(); // tomo la contraseña ingresada

  // valido campos vacios
  if (!email || !password) {
    alert("Completa todos los campos");
    return;
  }

  const usuariosJson = await getUsuarios(); // traigo usuarios desde el json

// traigo usuarios registrados en localstorage
const usuariosRegistrados: IUser[] = JSON.parse(
  localStorage.getItem("usuariosRegistrados") || "[]"
);

const usuarios = [...usuariosJson, ...usuariosRegistrados]; // junto usuarios del json y localstorage

  

  // busco usuario con mail y contraseña
  const user = usuarios.find(
    (u) => u.mail === email && u.password === password
  );

  // si no encuentra usuario muestro error
  if (!user) {
    alert("Mail o contraseña incorrectos");
    return;
  }

 localStorage.setItem("userData", JSON.stringify(user)); // guardo usuario en localStorage

  // redireccion segun rol
  if (user.rol === "ADMIN") {
    window.location.href = "/src/pages/admin/home/home.html";
  } else {
    window.location.href = "/src/pages/store/home/home.html";
  }
});
