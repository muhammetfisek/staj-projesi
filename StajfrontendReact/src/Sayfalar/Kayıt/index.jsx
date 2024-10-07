import { useEffect, useState, useMemo } from "react";
//import axios from "axios";
import { signUp } from "./api";
//import "./index.css";
import { Input } from "./components/Input";
import { useTranslation } from "react-i18next";
//import { LanguageSelector } from "../../shared/components/LanguageSelector";

export function Kayit() {
  // dışarıdan fonksiyona erişmek için export ettik
  // Ad, e-posta, şifre ve şifre tekrarını saklayacak durum değişkenlerini tanımlıyoruz
  const [ad, setad] = useState(); // useState Fonksiyon bileşenlerinde durum (state) yönetimini sağlar. useState, bir durum değişkeni ve bu değişkeni güncellemek için kullanılan bir fonksiyon döner.
  const [email, setEmail] = useState();
  const [sifre, setsifre] = useState(); // kullanıcının şifresini saklar
  const [sifretekrar, setsifretekrar] = useState();
  const [basariliMesaj, setbasariliMesaj] = useState();
  const [apiProgress, setApiProgress] = useState(false); // butona tıkladıktan sonra bidaha tıklanmaz
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();
  const { t } = useTranslation();
  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordTekrar, setShowPasswordTekrar] = useState(false); // Şifre görünürlüğü durumu

  useEffect(() => {
    setErrors(function (lastErrors) {
      // burada yapılan işlem ad bos olduğunda hata mesajı geldiğinde tekrar ad girildiğinde hata mesajını kaldırır.
      return {
        ...lastErrors,
        ad: undefined,
      };
    });
  }, [ad]);

  useEffect(() => {
    setErrors(function (lastErrors) {
      // aynı şekil email için yapıldı
      return {
        ...lastErrors,
        email: undefined,
      };
    });
  }, [email]);

  useEffect(() => {
    setErrors(function (lastErrors) {
      // aynı şekil sfire için yapıldı
      return {
        ...lastErrors,
        sifre: undefined,
      };
    });
  }, [sifre]);

  const onSubmit = async (event) => {
    event.preventDefault();
    setbasariliMesaj();
    setGeneralError(); // sonra siliyor
    setApiProgress(true);

    try {
      // Kullanıcı kayıt bilgilerini backend'e gönderiyoruz.
      const response = await signUp({
        ad: ad,
        email: email,
        sifre: sifre,
      });
      setbasariliMesaj(response.data.message);
    } catch (axiosError) {
      // Eğer backend'den dönen bir hata varsa ve bu hata 400 status koduna sahipse,
      // doğrulama hatalarını state'e kaydediyoruz.
      if (axiosError.response?.data) {
        if (axiosError.response.data.status === 400) {
          setErrors(axiosError.response.data.validationErrors);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        setGeneralError(t("genericMessage"));
      }
    } finally {
      // API çağrısı tamamlandıktan sonra, API progress durumunu false yapıyoruz
      setApiProgress(false);
    }
  };

  const sifreTekrarError = useMemo(() => {
    //şifre ve şifre tekrar alanlarını karşılaştırdığınız bu kodda, useMemo kullanarak bu karşılaştırmayı yalnızca sifre veya sifretekrar değiştiğinde yapıyoruz. Bu, her render işleminde bu karşılaştırmanın yapılmasını engeller ve performansı artırır.
    if (sifre && sifre !== sifretekrar) {
      return t("passwordMismatch");
    }
    return ""; // Eğer 'sifre' ve 'sifretekrar' değişkenleri eşitse veya 'sifre' tanımlı değilse, boş bir string döner
  }, [sifre, sifretekrar, t]); // Bu hesaplama, yalnızca 'sifre' veya 'sifretekrar' değişkenlerinden biri değiştiğinde yeniden yapılır.

  return (
    <div className="wrapper">
      <div className="login-box">
        <form>
          <div className="h1arka">
            {" "}
            <h1 className="h1">{t("signUp")}</h1>
          </div>
          <Input
            id="ad"
            label={t("username")}
            error={errors.ad}
            onChange={(event) => setad(event.target.value)}
          />
          <Input
            id="email"
            label={t("email")}
            error={errors.email}
            onChange={(event) => setEmail(event.target.value)}
          />
          <div className="input-group mb-1">
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
          <div className="input-group mb-1">
            <Input
              id="sifretekrar"
              label={t("passwordRepeat")}
              error={sifreTekrarError}
              onChange={(event) => setsifretekrar(event.target.value)}
              type={showPasswordTekrar ? "text" : "password"}
              className="form-control"
            />
            <div className="goz">
              <span
                className="input-group-text"
                onClick={() => setShowPasswordTekrar((prev) => !prev)}
                style={{ cursor: "pointer" }}
                aria-label={
                  showPasswordTekrar ? "Hide password" : "Show password"
                }
              >
                {showPassword ? (
                  <i className="bi bi-eye-slash"></i>
                ) : (
                  <i className="bi bi-eye"></i>
                )}
              </span>
            </div>
          </div>
          <div className="success-background-true">
            {basariliMesaj && <div>{basariliMesaj}</div>}
          </div>
          <div className="success-background-false">
            {generalError && <div>{generalError}</div>}
          </div>
          <div>
            <button
              className="button"
              disabled={apiProgress || !sifre || sifre !== sifretekrar}
              onClick={onSubmit}
            >
              {apiProgress && (
                <span
                  className="spinner-border spinner-border-sm"
                  aria-hidden="true"
                ></span>
              )}
              {t("signUp")}
            </button>{" "}
          </div>{" "}
        </form>
      </div>
    </div>
  );
}
