import type { Rol } from "./Rol";

export interface IUser {
  password?: string;
  email: string;
  loggedIn: boolean;
  role: Rol;
}
