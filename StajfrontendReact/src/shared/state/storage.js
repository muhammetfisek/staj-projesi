// Oturum durumunu yerel depolamaya (localStorage) kaydeden fonksiyon.
export function storeAuthState(auth) {
  localStorage.setItem("auth", JSON.stringify(auth)); // 'auth' verisini JSON formatında localStorage'a kaydediyoruz.
}

// Yerel depolamadan oturum durumunu yükleyen fonksiyon.
export function loadAuthState() {
  const defaultState = { id: 0 }; // Oturum açılmamış kullanıcı için varsayılan durum (id: 0).
  const AuthStateInStorage = localStorage.getItem("auth"); // Yerel depolamadan 'auth' verisini alıyoruz.

  if (!AuthStateInStorage) return defaultState; // Eğer veri yoksa varsayılan durumu döndür.

  try {
    return JSON.parse(AuthStateInStorage); // Veriyi JSON formatından dönüştürerek döndürüyoruz.
  } catch {
    return defaultState; // Eğer bir hata olursa (örneğin veri bozuksa), varsayılan durumu döndür.
  }
}
export function storeToken(token) {
  if (token) {
    // Eğer geçerli bir token varsa, bunu JSON formatında localStorage'a kaydeder.
    localStorage.setItem("token", JSON.stringify(token));
  } else {
    // Eğer token geçersiz ya da yoksa, localStorage'dan token'ı siler.
    localStorage.removeItem("token");
  }
}

//Bu fonksiyon, localStorage'dan token'ı alır. Token varsa,
// onu JSON formatına çevirip geri döner.
// Token yoksa veya JSON parsing hatası oluşursa null döner.
export function loadToken() {
  const tokenInString = localStorage.getItem("token");
  if (!tokenInString) return null;
  try {
    return JSON.parse(tokenInString);
  } catch {
    return null;
  }
}
