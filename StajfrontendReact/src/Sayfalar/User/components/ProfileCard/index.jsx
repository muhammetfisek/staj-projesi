import PropTypes from "prop-types";

import { useState } from "react";
import { useAuthState } from "../../../../shared/state/context";
import { ProfileImage } from "../../../../shared/components/ProfileImage";
import { UserEditForm } from "./UserEditForm";
import { UserDeleteButton } from "./UserDeleteButton";

export function ProfileCard({ user }) {
  const [editMode, setEditMode] = useState(false);
  const authState = useAuthState();
  const [tempImage, setTempImage] = useState();

  // Mevcut oturum durumunu alır.
  // Kullanıcı kimliği eşleşiyorsa, oturumdaki kullanıcı adını gösterir;
  //aksi takdirde, diğer kullanıcının adını gösterir.
  const visibleAd = authState.id === user.id ? authState.ad : user.ad;

  return (
    <div className="card">
      <div className="card-header text-center">
        <ProfileImage width={200} tempImage={tempImage} image={user.image} />
      </div>
      <div className="card-body text-center">
        {!editMode && <span className="fs-3 d-block">{visibleAd}</span>}
        {/* Eğer oturum açan kullanıcı ile profildeki kullanıcı aynıysa, düzenleme butonu gösterilir */}
        {!editMode && authState.id === user.id && (
          <div className="d-flex justify-content-center">
            <button
              className="btn btn-primary me-2"
              onClick={() => setEditMode(true)}
            >
              EDIT
            </button>
            <UserDeleteButton />
          </div>
        )}

        {editMode && (
          <UserEditForm setEditMode={setEditMode} setTempImage={setTempImage} />
        )}
      </div>
    </div>
  );
}

ProfileCard.propTypes = {
  user: PropTypes.shape({
    ad: PropTypes.string.isRequired,
    id: PropTypes.number.isRequired,
    image: PropTypes.string.isRequired,
    profilePhotoUrl: PropTypes.string,
  }).isRequired,
};
