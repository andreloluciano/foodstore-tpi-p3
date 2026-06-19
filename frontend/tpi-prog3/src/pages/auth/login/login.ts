import type { IUser } from "../../../types/IUser";
import { navigate } from "../../../utils/navigate";

// hardcodeo admin para probar path de ingreso de admin
if (!localStorage.getItem("users")) {
  const admin = [
    {
      email: "admin@admin.com",
      password: "1234",
      role: "admin",
      loggedIn: false
    }
  ];

  localStorage.setItem("users", JSON.stringify(admin));}

const form = document.getElementById("loginForm") as HTMLFormElement;
const inputEmail = document.getElementById("email") as HTMLInputElement;
const inputPassword = document.getElementById("password") as HTMLInputElement;

form.addEventListener("submit", (e: SubmitEvent) => {
  e.preventDefault();

  const email = inputEmail.value;
  const password = inputPassword.value;

  // traer usuarios registrados
  const users: IUser[] = JSON.parse(localStorage.getItem("users") || "[]");

  // busca coincidencia
  const user = users.find((u) => u.email === email && u.password === password);

  if (user) {
    // guarda sesion
    localStorage.setItem("userData", JSON.stringify(user));

    // redirige según rol
    if (user.role === "admin") {
      navigate("/src/pages/admin/home/home.html");
    } else {
      navigate("/src/pages/client/home/home.html");
    }
  } else {
    alert("Email o contraseña incorrectos");
  }

  
});
