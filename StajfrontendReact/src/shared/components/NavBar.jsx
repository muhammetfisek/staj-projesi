import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";

import logo from "../../assets/fsk.png";

import { useAuthDispatch, useAuthState } from "../state/context";
import { ProfileImage } from "./ProfileImage";

export function NavBar() {
  const { t } = useTranslation();
  const authState = useAuthState(); // Kullanıcının oturum durumunu almak için kullanılır.
  const dispatch = useAuthDispatch(); // Oturum açma/kapama işlemleri için dispatch fonksiyonu.

  // Kullanıcı oturumu kapatmak istediğinde çağrılan fonksiyon
  const onClickLogout = () => {
    dispatch({ type: "logout-success" }); // Oturumu kapatma işlemi
  };

  return (
    <nav className="navbar navbar-expand bg-body-tertiary shadow-sm">
      <div className="container-fluid d-flex justify-content-between">
        <ul className="navbar-nav d-flex">
          {/* Kullanıcı oturumu yoksa (authState.id === 0) login ve kayıt ol seçenekleri gösterilir */}
          {authState.id === 0 && (
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/Login">
                  {t("login")}
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/kayit">
                  {t("signUp")}
                </Link>
              </li>
            </>
          )}
          {/* Kullanıcı oturumu açıksa (authState.id > 0) profil ve çıkış seçenekleri gösterilir */}
          {authState.id > 0 && (
            <>
              <li className="nav-item">
                <Link className="nav-link" to={`/user/${authState.id}`}>
                  <ProfileImage width={30} image={authState.image} />
                  <span className="ms-3">{authState.ad}</span>
                </Link>
              </li>
              <li className="nav-item">
                <span
                  className="nav-link"
                  role="button"
                  onClick={onClickLogout}
                >
                  Logout
                </span>
              </li>
            </>
          )}
        </ul>

        <Link className="navbar-brand ms-auto d-flex align-items-center" to="/">
          MUHAMMET FİŞEK
          <img src={logo} width={60} alt="Fişek Logo" />
        </Link>
      </div>
    </nav>
  );
}
