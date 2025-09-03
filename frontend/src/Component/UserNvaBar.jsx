import axios from "axios";
import { useEffect, useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import logo from "../assets/images/logo.jpeg";
import "./popupform.css";
<img src={logo} alt="Logo" style={{ height: "40px" }} />;

export default function Navbar() {
  const [darkMode, setDarkMode] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [showDropdown, setShowDropdown] = useState(false);
  const [user, setUser] = useState(null);
  const navigate = useNavigate();
  const [showPopup, setShowPopup] = useState(false);

  const [formData, setFormData] = useState({
    email: "",
    name: "",
    password: "",
    cpassword: "",
    status: "ACTIVATE",
    image: null,
  });
  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem("user"));
    if (storedUser) {
      setUser(storedUser);
    }
  }, []);

  const toggleTheme = () => {
    setDarkMode(!darkMode);
    document.body.className = !darkMode
      ? "bg-dark text-white"
      : "bg-light text-dark";
  };

  const handleSearch = () => {
    if (searchQuery.trim() === "") {
      alert("Please enter a search query");
      return;
    }
    axios
      .get(`http://localhost:9090/getproductbyname/${searchQuery}`)
      .then((res) => {
        if (res.data.length === 0) {
          alert("No products found");
        } else {
          navigate("/search", { state: res.data });
          console.log(res.data);
        }
      })
      .catch((err) => {
        console.error(err);
        alert("Error fetching products");
      });
  };
  //logout
  const handleLogout = () => {
    localStorage.removeItem("user");
    setShowDropdown(false);
    navigate("/login"); // Change route as per your project
  };
  const handleLogin = () => {
    navigate("/login");
  };
  // updating user profile

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (name === "image") {
      setFormData((prev) => ({ ...prev, profile: files[0] }));
    } else {
      setFormData((prev) => ({ ...prev, [name]: value }));
    }
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
        name: formData.name,
        email: formData.email,
        password: formData.password,
        status: formData.status,
      })
    );
    formDataToSend.append("image", formData.profile); // formData.image is a File
    try {
      const res = await axios.put(
        `http://localhost:9090/update/${user.user.id}`,
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
      {showPopup && (
        <div className="popup-overlay">
          <div className="popup-content">
            <button className="close-btn" onClick={() => setShowPopup(false)}>
              ×
            </button>

            <form method="post" onSubmit={handleSubmit}>
              {/*<div className="mb-2">
                <label>Enter Gmail</label>
                <input
                  type="email"
                  name="email"
                  placeholder="abc12@gmail.com"
                  onChange={handleChange}
                />
              </div> */}

              <div className="mb-2">
                <label>Enter Your Full Name</label>
                <input
                  type="text"
                  name="name"
                  placeholder="Dipak Raj Pandey"
                  onChange={handleChange}
                />
              </div>

              <div className="mb-2">
                <label>Enter Password</label>
                <input
                  type="password"
                  name="password"
                  placeholder="abc@123"
                  onChange={handleChange}
                />
              </div>

              <div className="mb-2">
                <label>Confirm Password</label>
                <input
                  type="password"
                  name="cpassword"
                  placeholder="abc@123"
                  value={formData.cpassword}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label>Status</label>
                <select
                  name="status"
                  id=""
                  onChange={handleChange}
                  value={formData.status}
                >
                  <option value="ACTIVATE">Active</option>
                  <option value="INACTIVE">Inactive</option>
                </select>
              </div>
              <div className="mb-2">
                <label>Set Profile</label>
                <input type="file" name="image" onChange={handleChange} />
              </div>

              <button type="submit" className="btn btn-primary">
                Update
              </button>
            </form>
          </div>
        </div>
      )}

      <nav
        className={`navbar navbar-expand-lg ${
          darkMode ? "navbar-dark bg-dark" : "navbar-light bg-light"
        } px-3`}
      >
        <NavLink className="navbar-brand" to="/">
          <img
            src={logo} // Adjust the path as necessary
            alt="Logo"
            style={{ height: "40px", marginRight: "10px" }}
          />
          KinmelHub
        </NavLink>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div
          className="collapse navbar-collapse justify-content-between"
          id="navbarNav"
        >
          {/* Center Search Input */}
          <div className="d-flex mx-auto">
            <input
              type="text"
              className="form-control me-2"
              placeholder="Search items..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
            <button className="btn btn-outline-primary" onClick={handleSearch}>
              Search
            </button>
          </div>

          {/* Right Menu */}
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <NavLink className="nav-link" to="/">
                Home
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/order">
                Order
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/cart">
                Cart
              </NavLink>
            </li>

            {user ? (
              <li className="nav-item">
                <div className="ml-auto position-relative">
                  <button
                    className="btn btn-light"
                    onClick={() => setShowDropdown(!showDropdown)}
                  >
                    Profile
                  </button>

                  {showDropdown && (
                    <div
                      className="dropdown-menu dropdown-menu-end show position-absolute"
                      style={{ right: 0, top: "100%" }}
                    >
                      <div className="px-3 py-2">
                        <img
                          src={user.user.profile}
                          alt="Profile"
                          className="img-fluid rounded-circle mb-2"
                          style={{ width: "50px", height: "50px" }}
                        />
                        <p>
                          <strong>Name:</strong> {user.user.name}
                        </p>
                        <p>
                          <strong>Email:</strong> {user.user.email}
                        </p>
                        <p>
                          <strong>Role:</strong> {user.user.role}
                        </p>
                        <p>
                          <strong>Status</strong> {user.user.status}
                        </p>
                        <button
                          className="btn btn-danger btn-sm w-100"
                          onClick={handleLogout}
                        >
                          Logout
                        </button>
                        <button
                          className="btn btn-primary btn-sm w-100"
                          onClick={() => {
                            setShowPopup(true);
                          }}
                        >
                          Edit Profile
                        </button>
                      </div>
                    </div>
                  )}
                </div>
              </li>
            ) : (
              <li className="nav-item">
                <button
                  className="btn btn-danger btn-sm w-100"
                  onClick={handleLogin}
                >
                  LogIn
                </button>
              </li>
            )}

            <li className="nav-item">
              <button
                onClick={toggleTheme}
                className={`btn btn-sm ${
                  darkMode ? "btn-light" : "btn-dark"
                } ms-3`}
              >
                {darkMode ? "Light Mode" : "Dark Mode"}
              </button>
            </li>
          </ul>
        </div>
      </nav>
    </>
  );
}
