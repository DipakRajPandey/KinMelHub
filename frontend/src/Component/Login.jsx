import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "./login.css";
export default function Login() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await fetch("http://localhost:9090/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
    });
    if (res.ok) {
      const user = await res.json();
      console.log(user);

      localStorage.setItem("user", JSON.stringify(user));
      if (user.user.role === "VENDOR" && user.user.status === "ACTIVATE") {
        navigate("/vendordashboard");
      } else if (
        user.user.role === "ADMIN" &&
        user.user.status === "ACTIVATE"
      ) {
        navigate("/admindashboard");
      } else if (
        user.user.role === "CUSTOMER" &&
        user.user.status === "ACTIVATE"
      ) {
        navigate("/");
      }
    } else {
      const error = await res.json();

      toast.error(error.message);
    }
  };
  return (
    <>
      <div className="container">
        <form method="post" onSubmit={handleSubmit}>
          <div className="mb-2 ">
            <label htmlFor="email">Enter Gmail </label>
            <br />
            <input
              type="email"
              name="email"
              placeholder="abc12@gmail.com"
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
          <button type="submit" className="btn btn-primary w-30 ">
            Login
          </button>
        </form>
        <span className="d-flex mt-3">
          I do not have account ?
          <Link to="/register" className="ms-1">
            register
          </Link>
        </span>
      </div>
    </>
  );
}
