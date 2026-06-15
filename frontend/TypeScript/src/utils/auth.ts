import type { IUser } from "../types/IUser";
import type { Rol } from "../types/Rol";
import { getUSer, removeUser } from "./localStorage";
import { navigate } from "./navigate";

export const checkAuhtUser = (
  redireccion1: string,
  redireccion2: string,
  rol: Rol
) => {
  console.log("comienzo de checkeo");

  const user = getUSer();

  if (!user) {
    console.log("no existe en local");
    alert("Debes iniciar sesión primero");
    navigate(redireccion1);
    return;
  } else {
    console.log("existe pero no tiene el rol necesario");

    const parseUser: IUser = JSON.parse(user);
    if (parseUser.role !== rol) {
    alert("No tenés permisos para acceder a esta sección");
      navigate(redireccion2);
      return;
    }
  }
};

export const logout = () => {
  removeUser();
  navigate("/src/pages/auth/login/login.html");
};

