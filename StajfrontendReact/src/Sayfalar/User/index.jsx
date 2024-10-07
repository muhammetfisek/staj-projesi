import { getUser } from "./api";
import { useRPApiRequest } from "../../shared/hooks/useRPApiRequest";
import { ProfileCard } from "./components/ProfileCard";

export function User() {
  // API isteğini yapar ve istek durumu, kullanıcı verisi ve hata bilgilerini döner
  const { apiProgress, data: user, error } = useRPApiRequest("id", getUser);
  return (
    <>
      {apiProgress && (
        <span className="spinner-border" aria-hidden="true"></span>
      )}
      {user && <ProfileCard user={user}></ProfileCard>}
      {error && <div className="alert alert-danger"> {error}</div>}
    </>
  );
}
