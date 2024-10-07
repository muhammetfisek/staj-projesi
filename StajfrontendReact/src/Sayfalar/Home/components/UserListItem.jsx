import PropTypes from "prop-types";
import { Link } from "react-router-dom";
import { ProfileImage } from "../../../shared/components/ProfileImage";

export function UserListItem({ user }) {
  return (
    <Link
      className="list-group-item list-group-item-action"
      to={`/user/${user.id}`} // Kullanıcının id'sine göre URL oluşturur
      style={{ textDecoration: "none" }}
    >
      <ProfileImage width={30} image={user.image} />
      {user.ad}
    </Link>
  );
}

UserListItem.propTypes = {
  user: PropTypes.shape({
    id: PropTypes.number.isRequired,
    ad: PropTypes.string.isRequired,
    profilePhotoUrl: PropTypes.string,
    image: PropTypes.string.isRequired,
  }).isRequired,
};
