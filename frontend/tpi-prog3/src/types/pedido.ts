import type { Product } from "./product";
import type { IUser } from "./IUser";

export interface DetallePedido {
  cantidad: number;
  subtotal: number;
  producto: Product;
}

export interface Pedido {
  id: number;
  fecha: string;
  estado: string;
  total: number;
  formaPago: string;
  detalles: DetallePedido[];
  usuarioDto: Omit<IUser, "password">;
}