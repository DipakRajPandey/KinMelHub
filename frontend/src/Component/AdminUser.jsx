import axios from "axios";
import { useEffect, useState } from "react";
import AdminNavBar from "./AdminNavBar";
import styles from "./order.module.css";
import "./popupform.css";

export default function AdminUser() {
  const [orders, setOrders] = useState([]);
  const [showPopup, setShowPopup] = useState(false);
  const [selectedUserId, setSelectedUserId] = useState(null);

  const [formData, setFormData] = useState({
    status: "ACTIVATE",
  });
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user.user.id;
    console.log("User data:", user);
    if (userId) {
      axios
        .get(`${import.meta.env.VITE_API_BASE_URL}/alluser`)
        .then((res) => {
          setOrders(res.data);
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, []);
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({ ...prev, [name]: value }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.password !== formData.cpassword) {
      alert("Passwords do not match");
      return;
    }
    const formDataToSend = new FormData();
    formDataToSend.append(
      "user",
      JSON.stringify({
        status: formData.status,
      })
    );

    try {
      const res = await axios.put(
        `${import.meta.env.VITE_API_BASE_URL}/update/${selectedUserId}`,
        formDataToSend,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      alert("Profile updated successfully!");
    } catch (err) {
      console.error(err);
      alert("Update failed.");
    }
  };
  return (
    <>
      <AdminNavBar />
      {showPopup && (
        <div className="popup-overlay">
          <div className="popup-content">
            <button className="close-btn" onClick={() => setShowPopup(false)}>
              ×
            </button>

            <form method="post" onSubmit={handleSubmit}>
              <div>
                <label>Status</label>
                <select
                  name="status"
                  id=""
                  onChange={handleChange}
                  value={formData.status}
                >
                  <option value="ACTIVATE">Active</option>
                  <option value="BAN">Ban</option>
                </select>
              </div>

              <button type="submit" className="btn btn-primary">
                Update
              </button>
            </form>
          </div>
        </div>
      )}
      <div className={styles.ontainer}>
        {Array.isArray(orders) ? (
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>SN</th>
                <th>User Email</th>
                <th>User Name</th>
                <th> Profile</th>
                <th>Status</th>
                <th> Role</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((user, index) => {
                return (
                  <tr key={user.id}>
                    <td>{index + 1}</td>

                    <td>{user.email}</td>
                    <td>{user.name}</td>
                    <td>
                      <img
                        src={user.profile}
                        alt="User Profile"
                        width={110}
                        height={119}
                      />
                    </td>
                    <td>{user.status}</td>
                    <td>{user.role}</td>
                    <td>
                      <button
                        className="btn btn-primary btn-sm me-1"
                        onClick={() => {
                          setSelectedUserId(user.id);
                          setShowPopup(true);
                        }}
                      >
                        Update
                      </button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        ) : (
          <p>Loading or no products available</p>
        )}
      </div>
    </>
  );
}
