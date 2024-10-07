export function Input(prop) {
  const { id, label, error, onChange, type, defaultValue } = prop;

  return (
    <div className="text">
      <div className="input-box">
        <label htmlFor={id} style={{ display: "none" }}>
          {label}
        </label>
        <input
          id={id}
          onChange={onChange}
          type={type}
          defaultValue={defaultValue}
          required
          placeholder={label}
          className={
            error ? "form-control is-invalid " : "form-control" // errror varsa ekler yoksa onu diğerini ekler
          }
        />
      </div>
      <div>{error}</div>
    </div>
  );
}
