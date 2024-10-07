import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
export function useRPApiRequest(param, httpFunction) {
  const params = useParams();
  const pathParam = params[param];

  const [apiProgress, setApiProgress] = useState();
  const [data, setData] = useState();
  const [error, setError] = useState();

  useEffect(() => {
    async function sendRequest() {
      // Kullanıcıyı aktif hale getiren asenkron fonksiyon
      setApiProgress(true);
      try {
        // Kullanıcıyı aktif hale getirmek için API çağrısı yapar ve yanıtı bekler
        const response = await httpFunction(pathParam);
        setData(response.data); // Başarılı yanıt mesajını ayarlar
      } catch (axiosError) {
        setError(axiosError.response.data.message);
      } finally {
        // API çağrısı tamamlandığında ilerleme durumunu kapatır
        setApiProgress(false);
      }
    } // Komponent yüklendiğinde veya token değiştiğinde activate fonksiyonunu çağırır
    sendRequest();
  }, [pathParam, httpFunction]);
  return { apiProgress, data, error };
}
