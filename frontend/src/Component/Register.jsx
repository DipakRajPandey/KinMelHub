import { useState } from "react";
import RegisterAsUser from "./RegisterAsUser";
import RegisterAsVendor from "./RegisterAsVendor";
import styles from "./register.module.css";
export default function Register() {
  const [userType, setUserType] = useState("CUSTOMER");

  const handleToggle = () => {
    setUserType((prev) => (prev === "CUSTOMER" ? "VENDOR" : "CUSTOMER"));
  };

  return (
    <div className={styles.conatiner}>
      <div className="text-center">
        <button className={styles.toggleButton} onClick={handleToggle}>
          Switch to {userType === "CUSTOMER" ? "Vendor" : "Customer"} Register
        </button>
      </div>

      {userType === "CUSTOMER" ? <RegisterAsUser /> : <RegisterAsVendor />}
    </div>
  );
}
