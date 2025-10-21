import axios from "axios";
import { useEffect, useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import logo from "../assets/images/kinmel.jpeg";
import "./popupform.css";
{
  /* <img src={logo} alt="Logo" style={{ height: "40px" }} />; */
}

export default function VenderNavBar() {
  const [darkMode, setDarkMode] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [showDropdown, setShowDropdown] = useState(false);
  const [user, setUser] = useState({});
  const [showPopup, setShowPopup] = useState(false);
  const navigate = useNavigate();
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
    const user = JSON.parse(localStorage.getItem("user"));
    const vendorId = user?.user?.vendor?.id;
    axios
      .get(
        `${
          import.meta.env.VITE_API_BASE_URL
        }/getproductforvendor/${vendorId}/${searchQuery}`
      )
      .then((res) => {
        if (res.data.length === 0) {
          alert("No products found");
        } else {
          navigate("/searchforvendor", { state: res.data });
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

  //updating
  const [formData, setFormData] = useState({
    email: "",
    name: "",
    password: "",
    cpassword: "",
    profile: null,
    storename: "",
    address: "",
    contactnumber: "",
    bio: "",
    logo: null,
    status: "ACTIVATE",
  });
  const handleChange = (e) => {
    const { name, value, type, files } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "file" ? files[0] : value,
    }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.password !== formData.cpassword) {
      alert("Passwords do not match");
      return;
    }
    const formDataToSend = new FormData();
    formDataToSend.append(
      "vendor",
      JSON.stringify({
        email: formData.email,
        name: formData.name,
        password: formData.password,
        storename: formData.storename,
        address: formData.address,
        contactnumber: formData.contactnumber,
        bio: formData.bio,
        status: formData.status,
      })
    );
    formDataToSend.append("profile", formData.profile);
    formDataToSend.append("logo", formData.logo);

    const user = JSON.parse(localStorage.getItem("user"));
    const vendorId = user?.user?.vendor?.id;
    console.log(formDataToSend.get("vendor"));
    try {
      const res = await fetch(
        `${import.meta.env.VITE_API_BASE_URL}/vendor/updatevendor/${vendorId}`,
        {
          method: "PUT",
          body: formDataToSend,
        }
      );

      if (res.ok) {
        alert("Vendor updated successfully!");
        navigate("/vendorhome");
      } else {
        const errorMessage = await res.text();
        alert("Update  failed: ");
      }
    } catch (err) {
      console.error(err);
      alert("An error occurred.");
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
              {/* <div className="mb-2">
                <label>Enter Gmail</label>
                <input
                  type="email"
                  name="email"
                  placeholder="abc12@gmail.com"
                  onChange={handleChange}
                />
              </div> */}
              <div className="mb-2 ">
                <label htmlFor="fullname">Enter You Full Name</label>
                <input
                  type="text"
                  id="name"
                  name="name"
                  placeholder="Dipak Raj Pandey"
                  onChange={handleChange}
                  value={formData.name}
                />
              </div>
              <div className="mb-2">
                <label htmlFor="password">Enter password</label>
                <input
                  type="password"
                  name="password"
                  placeholder="abc@123"
                  onChange={handleChange}
                  value={formData.password}
                />
              </div>
              <div className="mb-2 ">
                <label htmlFor="cpassword">Conform password</label>
                <input
                  type="password"
                  name="cpassword"
                  placeholder="abc@123"
                  onChange={handleChange}
                  value={formData.cpassword}
                />
              </div>
              <div className="mb-2 ">
                <label htmlFor="profile">Set Profile</label>
                <input
                  type="file"
                  name="profile"
                  onChange={handleChange}
                  placeholder="Upload your profile"
                  accept="image/*"
                />
              </div>
              {/* vendors  details  */}
              <div className="mb-2 ">
                <label htmlFor="storename">Enter Store Name </label>
                <input
                  type="text"
                  name="storename"
                  placeholder="Dipak shop"
                  onChange={handleChange}
                  value={formData.storename}
                />
              </div>
              <div className="mb-2 ">
                <label htmlFor="address">Enter You Store Address</label>
                <input
                  type="text"
                  id="address"
                  name="address"
                  placeholder="Kanchanpur-08"
                  onChange={handleChange}
                  value={formData.address}
                />
              </div>
              <div className="mb-2">
                <label htmlFor="contactnumber">Enter Your Contact Number</label>
                <input
                  type="tel"
                  name="contactnumber"
                  placeholder="9867901234"
                  onChange={handleChange}
                  pattern="[0-9]{10}"
                  maxLength={10}
                  inputMode="numeric"
                  value={formData.contactnumber}
                />
              </div>
              <div className="mb-2 ">
                <label htmlFor="bio">Enter Bio </label>
                <input
                  type="text"
                  name="bio"
                  placeholder="Cool"
                  onChange={handleChange}
                  value={formData.bio}
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
                  <option value="ACTIVATE">ACTIVATE</option>
                  <option value="INACTIVE">INACTIVE</option>
                </select>
              </div>
              <div className="mb-2 ">
                <label htmlFor="logo">Set Logo</label>
                <input
                  type="file"
                  name="logo"
                  placeholder="Upload your logo"
                  onChange={handleChange}
                  accept="image/*"
                />
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
        <NavLink className="navbar-brand" to="/vendordashboard">
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
              className="form-control me-0 custom-search-input"
              placeholder="Search items..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
            <button
              className="btn btn-primary ms-0 custom-search-button"
              onClick={handleSearch}
            >
              Search
            </button>
          </div>

          {/* Right Menu */}
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <NavLink className="nav-link" to="/vendorhome">
                Home
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/vendordashboard">
                Dashboard
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/addproduct">
                Add Product
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/orderreceived">
                Order Received
              </NavLink>
            </li>
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
