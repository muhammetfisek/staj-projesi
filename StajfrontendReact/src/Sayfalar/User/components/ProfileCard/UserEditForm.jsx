import {
  useAuthDispatch,
  useAuthState,
} from "../../../../shared/state/context";
import PropTypes from "prop-types";
import { Input } from "../../../Kayıt/components/Input";
import { useTranslation } from "react-i18next";
import { updateUser } from "./api";
import { useState } from "react";

export function UserEditForm({ setEditMode, setTempImage }) {
  const authState = useAuthState(); // Mevcut oturum durumunu alır.
  const { t } = useTranslation();
  const [newAd, setNewAd] = useState(authState.ad);
  const [apiProgress, setApiProgress] = useState(false);
  const [errors, setErrors] = useState({}); // Doğrulama hatalarını saklar.
  const [generalError, setGeneralError] = useState(); // Genel hataları saklar (örneğin, ağ hatası).
  const dispatch = useAuthDispatch();
  const [newImage, setNewImage] = useState();

  // Kullanıcı bir girdi değişikliği yaptığında,
  // setNewAd ile yeni değeri ayarlar ve setErrors ile
  // mevcut hata mesajlarını temizler.
  const onChangeAd = (event) => {
    setNewAd(event.target.value);
    setErrors(function (lastErrors) {
      return {
        ...lastErrors,
        ad: undefined,
      };
    });
  };

  // Kullanıcı bir profil resmi seçtiğinde çağrılır.
  const onSelectImage = (event) => {
    setErrors(function (lastErrors) {
      return {
        // Mevcut hataları sıfırlar, resim ile ilgili hatayı temizler
        ...lastErrors,
        image: undefined,
      };
    }); // Eğer hiçbir dosya seçilmediyse, işlemi sonlandırır
    if (event.target.files.length < 1) return;
    const file = event.target.files[0]; // Seçilen ilk dosyayı alır
    const fileReader = new FileReader();
    fileReader.onloadend = () => {
      const data = fileReader.result; // Okunan dosya verisini alır (base64 formatında)
      setNewImage(data); // Yeni resmi ayarlar
      setTempImage(data); // Geçici olarak gösterilecek resmi ayarlar
    }; // Dosyayı base64 formatında okur
    fileReader.readAsDataURL(file);
  };

  // Düzenleme işlemi sırasında "İptal" butonuna basıldığında,
  // düzenlemeyi kapatıp kullanıcı adı alanını önceki haline geri döndürür.
  const onClickCancel = () => {
    setEditMode(false);
    setNewAd(authState.ad);
    setNewImage();
    setTempImage();
  };

  // "Kaydet" butonuna basıldığında, API yüklenme durumunu başlatır,
  // mevcut hataları ve genel hata durumunu temizler.
  const onClickSave = async () => {
    setApiProgress(true);
    setErrors({});
    setGeneralError();

    try {
      // Kullanıcı bilgilerini günceller ve başarılı olursa ilgili eylemi dispatch eder.
      const { data } = await updateUser(authState.id, {
        ad: newAd,
        image: newImage,
      });
      dispatch({
        type: "user-update-success",
        data: { ad: data.ad, image: data.image },
      });
      setEditMode(false); // Düzenleme modunu kapatır.
    } catch (axiosError) {
      // Eğer bir hata oluşursa:
      if (axiosError.response?.data) {
        // Hata backend'den geldiyse:
        if (axiosError.response.data.status === 400) {
          // Doğrulama hatalarını ayarlar.
          setErrors(axiosError.response.data.validationErrors);
        } else {
          // Diğer genel hata mesajlarını ayarlar.
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        // Ağ hatası gibi genel bir hata mesajı ayarlar.
        setGeneralError(t("genericMessage"));
      }
    } finally {
      // API işleminden bağımsız olarak, yüklenme durumunu kapatır.
      setApiProgress(false);
    }
  };

  return (
    <>
      <Input
        label={t("username")}
        defaultValue={authState.ad}
        onChange={onChangeAd}
        error={errors.ad}
      />
      <Input
        label={t("Profile Image")}
        type="file"
        onChange={onSelectImage}
        error={errors.image}
      />
      {generalError && <div>{generalError}</div>}
      <button
        className="btn btn-danger"
        onClick={onClickSave}
        disabled={apiProgress} // apiProgress'e göre butonu devre dışı bırak
      >
        {apiProgress ? t("loading") : "Save"}{" "}
        {/* Yükleniyorsa "loading" yazısını göster */}
      </button>
      <div className="d-inline m-2"></div>
      <button className="btn btn-secondary" onClick={onClickCancel}>
        Cancel
      </button>
    </>
  );
}

// PropTypes ile prop doğrulama
UserEditForm.propTypes = {
  setEditMode: PropTypes.func.isRequired, // setEditMode fonksiyon olmalı ve gerekli
  setTempImage: PropTypes.func.isRequired, // setTempImage fonksiyon olmalı ve gerekli
};
