import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import styles from "./registerasuser.module.css";
export default function RegisterAsUser() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    name: "",
    password: "",
    cpassword: "",
    image: null,
  });
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
      })
    );
    formDataToSend.append("image", formData.profile); // formData.image is a File

    try {
      const response = await fetch(
        `${import.meta.env.VITE_API_BASE_URL}register`,
        {
          method: "POST",
          body: formDataToSend,
        }
      );

      if (response.ok) {
        alert("Registered with image successfully");
        navigate("/login");
      } else {
        alert("Failed to register");
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <>
      <div className={styles.container}>
        <form method="post" onSubmit={handleSubmit}>
          <div className="mb-2 ">
            <label htmlFor="email">Enter Gmail </label>
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
            <input
              type="file"
              name="image"
              placeholder="Upload your profile"
              onChange={handleChange}
            />
          </div>

          <button type="submit" className="btn btn-primary w-30 ">
            Register
          </button>
        </form>
        <span className="d-flex mt-3">
          I have account ?
          <NavLink to="/Login" className="ms-1">
            Login
          </NavLink>
        </span>
      </div>
    </>
  );
}
