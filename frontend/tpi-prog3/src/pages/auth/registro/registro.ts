import type { IUser } from "../../../types/IUser";
import { getUsuarios } from "../../../utils/api";

const form = document.getElementById("registroForm") as HTMLFormElement;
const inputEmail = document.getElementById("email") as HTMLInputElement;
const inputPassword = document.getElementById("password") as HTMLInputElement;

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const email = inputEmail.value.trim();
  const password = inputPassword.value.trim();

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

  // valido si ya existe el mail
  const existe = usuarios.some((user) => user.mail === email);

  if (existe) {
    alert("Ya existe un usuario con ese mail");
    return;
  }

  // creo el usuario nuevo con rol usuario
  const nuevoUsuario: IUser = {
    id: usuarios.length + 1,
    nombre: "Usuario",
    apellido: "Nuevo",
    mail: email,
    celular: "",
    rol: "USUARIO",
    password: password,
  };

  usuariosRegistrados.push(nuevoUsuario); // agrego el usuario nuevo

  localStorage.setItem(
    "usuariosRegistrados",
    JSON.stringify(usuariosRegistrados)
  ); // guardo usuarios registrados

  alert("Usuario registrado correctamente");

  location.href = "/src/pages/auth/login/login.html";

  form.reset();
});