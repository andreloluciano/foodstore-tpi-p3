import type { Category } from "../types/category";
import type { Product } from "../types/product";
import type { IUser } from "../types/IUser";
import type { Pedido } from "../types/pedido";

// centralización de los fetch

export const getCategorias = async (): Promise<Category[]> => {
  const respuesta = await fetch("/data/categorias.json");
  return await respuesta.json();
};


export const getProductos = async (): Promise<Product[]> => {
  const respuesta = await fetch("/data/productos.json");
  return await respuesta.json();
};

export const getUsuarios = async (): Promise<IUser[]> => {
  const respuesta = await fetch("/data/usuarios.json");
  return await respuesta.json();
};

export const getPedidos = async (): Promise<Pedido[]> => {
  const respuesta = await fetch("/data/pedidos.json");
  return await respuesta.json();
};