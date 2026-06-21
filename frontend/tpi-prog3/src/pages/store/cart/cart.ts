
import { checkAuhtUser } from "../../../utils/auth";
import type { CartItem } from "../../../types/product";
checkAuhtUser(
  "/src/pages/auth/login/login.html",
  "/src/pages/admin/home/home.html",
  "USUARIO"
);
const contenedor = document.getElementById("cart-container") as HTMLDivElement;
const clearCartButton = document.getElementById("clearCart") as HTMLButtonElement;

// traemos el total
const totalHTML = document.getElementById("total") as HTMLHeadingElement;


// mostrar carrito P1
// función para renderizar el carrito
const renderCarrito = () => {

  // traemos el carrito actualizado cada vez que renderizamos
  const carrito = JSON.parse(localStorage.getItem("cart") || "[]");

  // limpiamos antes de renderizar
  contenedor.innerHTML = "";
  let total = 0; // acumulador

  if (carrito.length === 0) { // si hay carrito vacio

    const mensaje = document.createElement("p");
    mensaje.textContent = "Tu carrito está vacío";

    contenedor.appendChild(mensaje); // aviso carrito vacio

    totalHTML.textContent = "Total: $0";

    return; // corto la funcion
  }

  // si hay productos
  carrito.forEach((producto: CartItem) => { // recorremos carrito // producto: Product ?? preguntar
    const div = document.createElement("div")as HTMLDivElement; // creo un bloque html

    div.innerHTML = ` 
      <h3>${producto.nombre}</h3>
      <p>Precio: $${producto.precio}</p>
      <p>Subtotal: $${producto.precio * producto.quantity}</p> 
    `; // inserto y muestro contenido en el bloque div  // subtotal

    // funcion para aumentar cantidad
const aumentarCantidad = (id: number) => { // recibe el id del producto

  const carrito = JSON.parse(localStorage.getItem("cart") || "[]"); // traigo el carrito de localStorage

  const producto = carrito.find((item: CartItem) => item.id === id); // busca producto que coincida con ese id

  if (producto) { 
    producto.quantity += 1; // si existe cambio cantidad 
  }

  localStorage.setItem("cart", JSON.stringify(carrito)); // guardo carrito actualizado

  renderCarrito();
};


// funcion para disminuir cantidad
const disminuirCantidad = (id: number) => {

  let carrito = JSON.parse(localStorage.getItem("cart") || "[]"); // traigo el carrito de localStorage

  const producto = carrito.find((item: CartItem) => item.id === id); // busca producto que coincida con ese id

  if (producto) {
    producto.quantity -= 1;
  }

  // si llega a 0 se elimina
  carrito = carrito.filter((item: CartItem) => item.quantity > 0);

  localStorage.setItem("cart", JSON.stringify(carrito));

  renderCarrito();
};
    
    // boton disminuir cantidad
    const botonMenos = document.createElement("button");
    botonMenos.textContent = "-";

    botonMenos.addEventListener("click", () => { // agrego evento al click
      disminuirCantidad(producto.id);
    });

    // texto cantidad actual
    const cantidadTexto = document.createElement("span");
    cantidadTexto.textContent = ` ${producto.quantity} `;

    // boton aumentar cantidad
    const botonMas = document.createElement("button");
    botonMas.textContent = "+";

    botonMas.addEventListener("click", () => { // agrego evento al click
      aumentarCantidad(producto.id);
    });

    // muestro los botones en el div
    div.appendChild(botonMenos);
    div.appendChild(cantidadTexto);
    div.appendChild(botonMas);


    // boton eliminar P2
    const button = document.createElement("button"); // creo boton
    button.textContent = "Eliminar"; 

    // cuando hay click, llama a la funcion eliminar
    button.addEventListener("click", () => {
      eliminarProducto(producto.id);
    });

    div.appendChild(button); // agrego el boton al div

    // suma del total
    total += producto.precio * producto.quantity;

    contenedor.appendChild(div);
  });

  // mostramos total
  totalHTML.textContent = "Total: $" + total;
};


// logica eliminar  P3
// funcion para eliminar un producto del carrito
const eliminarProducto = (id: number) => {

  // traigo el carrito actual del localStorage
  let carrito = JSON.parse(localStorage.getItem("cart") || "[]");

  // dejamos todos menos el que tenga ese id
  carrito = carrito.filter((item: CartItem) => item.id !== id);

  // guardamos el carrito actualizado
  localStorage.setItem("cart", JSON.stringify(carrito));

  // renderizo devuelta para actualizar la pantalla
  renderCarrito();
};


// vaciar carrito
function clearCart() {
  localStorage.removeItem("cart"); // borra todo el carrito
  renderCarrito(); // vuelve a mostrar
}

clearCartButton.addEventListener("click", () => {
  clearCart();
});





// ejecuto
renderCarrito();