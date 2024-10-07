import {
  useAuthDispatch,
  useAuthState,
} from "../../../../../shared/state/context";
import { deleteUser } from "./api";
import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";

export function DeleteHook() {
  const [apiProgress, setApiProgress] = useState(false);
  const { id } = useAuthState();
  const dispatch = useAuthDispatch();
  const navigate = useNavigate();

  const onClick = useCallback(async () => {
    const result = confirm("Eminsin mi knks ?"); // delete tıklandığında mesaj gösterir
    if (result) {
      // Kullanıcı onayladıysa
      setApiProgress(true); // API işlemi devam ediyor durumunu ayarla
      try {
        await deleteUser(id); // Kullanıcıyı silmek için API çağrısı
        dispatch({ type: "logout-success" }); // Kullanıcı silindikten sonra oturumu kapat
        navigate("/"); // Anasayfaya yönlendir
      } catch (error) {
        console.error("Kullanıcı silinirken bir hata oluştu:", error); // Hata loglama
        alert("Bir hata oluştu, lütfen tekrar deneyin."); // Kullanıcıya hata bildirimi
      } finally {
        // API işlemi tamamlandığında durumu sıfırla
        setApiProgress(false);
      }
    }
  }, [id, dispatch, navigate]);

  return {
    apiProgress,
    onClick,
  };
}
