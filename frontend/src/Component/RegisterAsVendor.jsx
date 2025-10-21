import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import styles from "./registerasuser.module.css";
export default function RegisterAsVendor() {
  const navigate = useNavigate();

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
      })
    );
    formDataToSend.append("profile", formData.profile);
    formDataToSend.append("logo", formData.logo);

    try {
      const res = await fetch(
        `${import.meta.env.VITE_API_BASE_URL}/vendor/register`,
        {
          method: "POST",
          body: formDataToSend,
        }
      );

      if (res.ok) {
        alert("Vendor registered successfully!");
        navigate("/login");
      } else {
        const errorMessage = await res.text();
        alert("Registration failed: " + errorMessage);
      }
    } catch (err) {
      console.error(err);
      alert("An error occurred.");
    }
  };

  return (
    <>
      <div className={styles.container}>
        <form method="post" onSubmit={handleSubmit}>
          <div className="mb-2 ">
            <label htmlFor="gmail">Enter Gmail </label>
            <br />
            <input
              type="email"
              name="email"
              placeholder="abc12@gmail.com"
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-2 ">
            <label htmlFor="fullname">Enter You Full Name</label>
            <br />
            <input
              type="text"
              id="name"
              name="name"
              placeholder="Dipak Raj Pandey"
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-2">
            <label htmlFor="password">Enter password</label>
            <br />
            <input
              type="password"
              name="password"
              placeholder="abc@123"
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-2 ">
            <label htmlFor="cpassword">Conform password</label>
            <br />
            <input
              type="password"
              name="cpassword"
              placeholder="abc@123"
              onChange={handleChange}
              value={formData.cpassword}
              required
            />
          </div>
          <div className="mb-2 ">
            <label htmlFor="profile">Set Profile</label>
            <br />
            <input
              type="file"
              name="profile"
              onChange={handleChange}
              placeholder="Upload your profile"
            />
          </div>
          {/* vendors  details  */}
          <div className="mb-2 ">
            <label htmlFor="storename">Enter Store Name </label>
            <br />
            <input
              type="text"
              name="storename"
              placeholder="Dipak shop"
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-2 ">
            <label htmlFor="address">Enter You Store Address</label>
            <br />
            <input
              type="text"
              id="address"
              name="address"
              placeholder="Kanchanpur-08"
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-2">
            <label htmlFor="contactnumber">Enter Your Contact Number</label>
            <br />
            <input
              type="tel"
              name="contactnumber"
              placeholder="9867901234"
              onChange={handleChange}
              required
              pattern="[0-9]{10}"
              maxLength={10}
              inputMode="numeric"
            />
          </div>

          <div className="mb-2 ">
            <label htmlFor="bio">Enter Bio </label>
            <br />
            <input
              type="text"
              name="bio"
              placeholder="Cool"
              onChange={handleChange}
              value={formData.bio}
            />
          </div>
          <div className="mb-2 ">
            <label htmlFor="logo">Set Logo</label>
            <br />
            <input
              type="file"
              name="logo"
              placeholder="Upload your logo"
              onChange={handleChange}
            />
          </div>

          <button type="submit" className="btn btn-primary w-30 ">
            Register As Vendor
          </button>
        </form>
        <span className="d-flex mt-3">
          I have account ?
          <Link to="/Login" className="ms-1">
            Login
          </Link>
        </span>
      </div>
    </>
  );
}
