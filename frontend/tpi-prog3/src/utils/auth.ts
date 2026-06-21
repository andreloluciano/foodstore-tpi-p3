import type { IUser } from "../types/IUser";
import type { Rol } from "../types/Rol";
import { getUSer, removeUser } from "./localStorage";
import { navigate } from "./navigate";

export const checkAuhtUser = (
  redireccion1: string,
  redireccion2: string,
  rol: Rol
): boolean => {
  const user = getUSer();

  if (!user) {
    alert("Debes iniciar sesion primero");
    navigate(redireccion1);
    return false;
  }

  const parseUser: IUser = JSON.parse(user);

  if (parseUser.rol !== rol) {
    alert("No tenes permisos para acceder a esta seccion");
    navigate(redireccion2);
    return false;
  }

  return true;
};

export const logout = () => {
  removeUser();
  navigate("/src/pages/auth/login/login.html");
};