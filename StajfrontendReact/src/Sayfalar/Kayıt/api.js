import http from "../../lib/http";

export function signUp(body) {
  //import etmemiz için export ekledik
  return http.post("/api/v1/users", body);
}
