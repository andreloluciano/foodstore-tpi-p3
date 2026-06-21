import { checkAuhtUser } from "../../../utils/auth";

checkAuhtUser(
  "/src/pages/auth/login/login.html",
  "/src/pages/store/home/home.html",
  "ADMIN"
);

console.log("gestion de productos");