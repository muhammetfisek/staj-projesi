import { activateUser } from "./api";
import { useRPApiRequest } from "../../shared/hooks/useRPApiRequest";

export function Activation() {
  const { apiProgress, data, error } = useRPApiRequest("token", activateUser);

  return (
    <>
      {apiProgress && (
        <span className="spinner-border" aria-hidden="true"></span>
      )}
      {data?.message && (
        <div className="alert alert-success"> {data.message}</div>
      )}
      {error && <div className="alert alert-danger"> {error}</div>}
    </>
  );
}
