import http from "../../../lib/http";

// backend kısmına request atıyoruz
// Belirtilen sayfa numarasıyla backend'e HTTP GET isteği gönderiyoruz,
// sayfalama için 'page' parametresi ve sayfa başına 3 kullanıcı alıyoruz.
export function loadUsers(page = 0) {
  return http.get("/api/v1/users", { params: { page, size: 3 } }); // API'ye page ve size parametreleri ile istek atılıyor.
}
