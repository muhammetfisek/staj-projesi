import PropTypes from "prop-types";
import pro from "../../assets/profile.png";

export function ProfileImage({ width, tempImage, image }) {
  //Eğer image varsa, özel profil resmini gösterir; yoksa, varsayılan resim (pro) kullanılır.
  const profileImage = image ? `/assets/profile/${image}` : pro;
  return (
    <img
      src={tempImage || profileImage}
      className="rounded-circle shadow-sm"
      width={width}
      height={width}
      onError={({ target }) => {
        target.src = pro;
      }}
    />
  );
}

// PropTypes ile width prop'unun doğrulanması
ProfileImage.propTypes = {
  width: PropTypes.number.isRequired, // width prop'unun sayı ve zorunlu olduğunu belirtiyoruz
  tempImage: PropTypes.func.isRequired,
  image: PropTypes.string.isRequired,
};
