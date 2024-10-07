import { createContext, useContext, useEffect, useReducer } from "react";
import PropTypes from "prop-types";
import { loadAuthState, storeAuthState } from "./storage";
import { setToken } from "../../lib/http";

export const AuthContext = createContext(); // Oturum durumunu paylaşacak context oluşturuluyor.
export const AuthDispatchContext = createContext(); // Dispatch fonksiyonunu paylaşacak context oluşturuluyor.
export function useAuthState() {
  // Oturum durumunu kullanmak için özel hook
  return useContext(AuthContext);
}

export function useAuthDispatch() {
  // Dispatch fonksiyonunu kullanmak için özel hook
  return useContext(AuthDispatchContext);
}
const authRecuder = (authState, action) => {
  // Oturum durumunu yönetmek için reducer fonksiyonu
  switch (action.type) {
    case "login-success": // Giriş başarılıysa, gelen token'ı saklar ve kullanıcı bilgilerini döner.
      setToken(action.data.token);
      return action.data.user; // Başarılı girişte kullanıcı bilgilerini günceller.
    case "logout-success":
      // Çıkış başarılıysa, token'ı temizler ve kullanıcıyı sıfırlar.
      setToken(); // Token'ı sıfırlar (undefined yapar).
      return { id: 0 }; // Kullanıcı kimliğini sıfırlar.
    case "user-update-success":
      // Kullanıcı güncelleme başarılıysa, yeni kullanıcı bilgileriyle authState'i günceller.
      return {
        ...authState, // Mevcut durumu korur.
        ad: action.data.ad, // Kullanıcının adını günceller.
        image: action.data.image,
      };
    default:
      // Bilinmeyen bir action type ile karşılaşılırsa hata fırlatılır.
      throw new Error(`unknown action: ${action.type}`);
  }
};
export function AuthenticationContext({ children }) {
  const [authState, dispatch] = useReducer(authRecuder, loadAuthState());
  // useReducer ile oturum durumunu yönetiyoruz, başlangıç durumu depolanan durumdan yükleniyor.
  useEffect(() => {
    storeAuthState(authState); // Oturum durumu değiştiğinde, yeni durumu yerel depolamaya kaydeder.
  }, [authState]); // authState her değiştiğinde bu efekt çalışır.

  return (
    // Oturum durumu ve dispatch fonksiyonunu context sağlayıcılarıyla tüm uygulamaya dağıtıyoruz.
    <AuthContext.Provider value={authState}>
      <AuthDispatchContext.Provider value={dispatch}>
        {children}
      </AuthDispatchContext.Provider>
    </AuthContext.Provider>
  );
}

AuthenticationContext.propTypes = {
  children: PropTypes.node.isRequired,
};
