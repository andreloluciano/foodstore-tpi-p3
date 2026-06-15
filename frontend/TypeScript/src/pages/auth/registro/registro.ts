// Capturamos el formulario
const form = document.getElementById("registroForm") as HTMLFormElement;

// Evento para el submit
form.addEventListener("submit", (e) => {
  e.preventDefault();

  // Capturamos los valores
  const emailInput = document.getElementById("email") as HTMLInputElement;
  const passwordInput = document.getElementById("password") as HTMLInputElement;

  const email = emailInput.value;
  const password = passwordInput.value;

  // crear  usuario
  const newUser = {
    email: email,
    password: password,
    role: "client" // 
  };

  // buscamos usuarios existentes 
  const users = JSON.parse(localStorage.getItem("users") || "[]");

  // pusheamos usuario
  users.push(newUser);

  // guardamos en localStorage
  localStorage.setItem("users", JSON.stringify(users));

  alert("Usuario registrado correctamente");

  location.href = "/src/pages/auth/login/login.html";

  form.reset();
});