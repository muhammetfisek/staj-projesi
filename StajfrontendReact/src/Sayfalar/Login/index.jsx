import { useEffect, useState } from "react";
//import axios from "axios";
import { useTranslation } from "react-i18next";
import { Input } from "../Kayıt/components/Input";
import { login } from "./api";
import { useNavigate } from "react-router-dom";
import { useAuthDispatch } from "../../shared/state/context";

export function Login() {
  const [email, setEmail] = useState(); // Kullanıcının e-posta adresini saklar
  const [sifre, setsifre] = useState(); // Kullanıcının şifresini saklar
  const [apiProgress, setApiProgress] = useState(false); // API çağrısı sırasında ilerleme durumunu kontrol eder, böylece butona birden fazla kez tıklanamaz.
  const [errors, setErrors] = useState({}); // Doğrulama hatalarını saklar.
  const [generalError, setGeneralError] = useState(); // Genel hataları saklar (örneğin, ağ hatası).
  const { t } = useTranslation(); // Çeviri fonksiyonunu kullanarak çoklu dil desteği sağlar.
  const navigate = useNavigate(); // Kullanıcıyı farklı sayfalara yönlendirmek için kullanılır.
  const dispatch = useAuthDispatch(); // Oturum açma işlemi başarılı olduğunda durumu güncellemek için dispatch fonksiyonu.
  const [showPassword, setShowPassword] = useState(false); // Şifre görünürlüğü durumu

  // E-posta adresi değiştiğinde e-posta doğrulama hatasını temizler
  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        email: undefined, // E-posta hatası sıfırlanır.
      };
    });
  }, [email]); // 'email' her değiştiğinde bu effect çalışır.

  // Şifre alanı değiştiğinde şifre doğrulama hatasını temizler
  useEffect(() => {
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        sifre: undefined, // Şifre hatası sıfırlanır.
      };
    });
  }, [sifre]); // 'sifre' her değiştiğinde bu effect çalışır.

  // Form gönderildiğinde çağrılır
  const onSubmit = async (event) => {
    event.preventDefault(); // Sayfanın yeniden yüklenmesini engeller
    setGeneralError(); // Genel hataları sıfırlar
    setApiProgress(true); // API işleminin başladığını işaret eder

    try {
      const response = await login({ email, sifre }); // login API'sine e-posta ve şifre ile istek yapar
      dispatch({ type: "login-success", data: response.data }); // Giriş başarılıysa oturum durumu güncellenir
      navigate("/"); // Kullanıcı ana sayfaya yönlendirilir
    } catch (axiosError) {
      // Backend'den dönen bir hata varsa, hataları kontrol eder
      if (axiosError.response?.data) {
        if (axiosError.response.data.status === 400) {
          setErrors(axiosError.response.data.validationErrors); // Doğrulama hatalarını set eder
        } else {
          setGeneralError(axiosError.response.data.message); // Diğer genel hatalar
        }
      } else {
        setGeneralError(t("genericMessage")); // Ağ hatası gibi genel bir hata mesajı
      }
    } finally {
      setApiProgress(false); // API işlemi tamamlandıktan sonra butonun yeniden tıklanabilmesi için durumu sıfırlar
    }
  };

  return (
    <div className="wrapper">
      <div className="login-box">
        <form>
          <div className="h1arka">
            <h1 className="h1">{t("login")}</h1>
          </div>
          <Input
            id="email"
            label={t("email")}
            error={errors.email}
            onChange={(event) => setEmail(event.target.value)}
          />
          <div className="input-group mb-3">
            <Input
              id="sifre"
              label={t("password")}
              error={errors.sifre}
              onChange={(event) => setsifre(event.target.value)}
              type={showPassword ? "text" : "password"}
              className="form-control"
            />
            <div className="goz">
              <span
                className="input-group-text"
                onClick={() => setShowPassword((prev) => !prev)}
                style={{ cursor: "pointer" }}
                aria-label={showPassword ? "Hide password" : "Show password"}
              >
                {showPassword ? (
                  <i className="bi bi-eye-slash"></i> // Göz kapalı
                ) : (
                  <i className="bi bi-eye"></i> // Göz açık
                )}
              </span>
            </div>
          </div>

          <div className="success-background-false">
            {generalError && <div>{generalError}</div>}
          </div>
          <div>
            <button
              className="button"
              disabled={apiProgress || !sifre}
              onClick={onSubmit}
            >
              {apiProgress && (
                <span
                  className="spinner-border spinner-border-sm"
                  aria-hidden="true"
                ></span>
              )}
              {t("login")}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
