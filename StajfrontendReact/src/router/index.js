import { createBrowserRouter } from "react-router-dom";
import { Home } from "../Sayfalar/Home";
import { Kayit } from "../Sayfalar/Kayıt";
import App from "../App";
import { Activation } from "../Sayfalar/Activation";
import { User } from "../Sayfalar/User";
import { Login } from "../Sayfalar/Login";

export default createBrowserRouter([
  {
    path: "/",
    Component: App,
    children: [
      {
        path: "/", // baska olmayan bir sayfaya gidildiğine hata mesajı yerine anasayfaya atar
        Component: Home,
      },
      {
        path: "/kayit", //Bu yol App bileşenini ekranda gösterir.
        Component: Kayit,
      },
      {
        path: "/activation/:token",
        Component: Activation,
      },
      {
        path: "/user/:id",
        Component: User,
      },
      {
        path: "/login",
        Component: Login,
      },
    ],
  },
]);
