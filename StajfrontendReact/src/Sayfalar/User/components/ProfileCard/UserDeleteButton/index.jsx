import { DeleteHook } from "./DeleteHook";

export function UserDeleteButton() {
  const { apiProgress, onClick } = DeleteHook();
  return (
    <button
      className="btn btn-danger"
      disabled={apiProgress} // apiProgress'e bağlı olarak butonu devre dışı bırak
      onClick={onClick}
    >
      {apiProgress ? "Deleting..." : "DELETE"}{" "}
      {/* Yükleme durumuna göre metin değişimi */}
    </button>
  );
}
