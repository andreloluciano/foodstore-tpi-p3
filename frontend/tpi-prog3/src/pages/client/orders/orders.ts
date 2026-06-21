import { checkAuhtUser } from "../../../utils/auth";

checkAuhtUser(
  "/src/pages/auth/login/login.html",
  "/src/pages/admin/home/home.html",
  "USUARIO"
);

console.log("pedidos del cliente");