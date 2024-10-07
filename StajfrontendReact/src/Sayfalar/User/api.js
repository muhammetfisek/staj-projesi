import http from "../../lib/http";

export function getUser(id) {
  // Belirtilen kullanıcı ID'siyle API'ye GET isteği yapar
  return http.get(`/api/v1/users/${id}`);
}
