import axios from "axios";
import { i18nInstance } from "../locales";
import { loadToken, storeToken } from "../shared/state/storage";
const http = axios.create();

let authToken = loadToken(); // Uygulamanın başında, mevcut token'ı yükleyerek authToken değişkenine atar.

export function setToken(token) {
  authToken = token; // authToken değişkenini yeni token ile günceller.
  storeToken(token); // Token'ı kalıcı bir şekilde saklar.
}
/**
 * Axios isteklerine bir "interceptor" ekler.
 * Her istek yapılmadan önce, bu kod çalışır ve isteklerin başlıklarını düzenler.
 */
http.interceptors.request.use((config) => {
  config.headers["Accept-Language"] = i18nInstance.language;
  if (authToken) {
    // Eğer bir yetkilendirme token'ı mevcutsa, istek başlığına Authorization ekler
    config.headers["Authorization"] = `${authToken.prefix} ${authToken.token}`;
  }
  return config;
});
export default http; // HTTP modülünü dışa aktarır, böylece diğer dosyalarda kullanılabilir.
