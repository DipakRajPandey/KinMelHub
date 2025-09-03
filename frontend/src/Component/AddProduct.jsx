import axios from "axios";
import { useState } from "react";
import VenderNavBar from "./VenderNavBar";
import styles from "./addproduct.module.css";
export default function AddProduct({ onClose, onSuccess }) {
  const [formData, setFormData] = useState({
    name: "",
    price: "",
    description: "",
    category: "",
  });
  const [image, setImage] = useState(null);
  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (name === "image") {
      setFormData((prev) => ({ ...prev, image: files[0] }));
    } else {
      setFormData((prev) => ({ ...prev, [name]: value }));
    }
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const data = new FormData();
    const user = JSON.parse(localStorage.getItem("user"));
    console.log("user", user.user.vendor.id);
    // Add vendor ID to form data
    const vendorId = user.user.vendor.id;
    data.append("product", JSON.stringify(formData));
    if (image) data.append("image", image);

    try {
      await axios.post(`http://localhost:9090/addproduct/${vendorId}`, data, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      alert("Product added successfully!");
      setFormData({
        name: "",
        price: "",
        description: "",
        category: "",
      });
      setImage(null);
    } catch (err) {
      alert(
        "Failed to add product: " +
          (err.response?.data?.message || "Unknown error")
      );
    }
  };
  return (
    <>
      <VenderNavBar />
      <div className={styles.conatiner}>
        <div className={styles["modal-containe"]}>
          <h2 className="">Add New Product</h2>
          <form onSubmit={handleSubmit}>
            <label htmlFor="name">Enter product name</label>
            <input
              name="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="Product Name"
              className="w-full mb-2 p-1 border"
              required
            />
            <label htmlFor="price">Enter product price</label>
            <input
              name="price"
              type="number"
              min={10}
              value={formData.price}
              onChange={handleChange}
              placeholder="10"
              className="w-full mb-2 p-1 border"
              required
            />
            <label htmlFor="category">Select Product category</label>
            <select
              name="category"
              value={formData.category}
              onChange={handleChange}
              className="w-full mb-2 p-1 border"
              required
            >
              <option value="">Select Category</option>
              <option value="Electronics">📱 Electronics</option>
              <option value="Fashion">👗 Fashion</option>
              <option value="HomeAndLiving">🏠 Home & Living</option>
              <option value="BeautyAndHealth">💄 Beauty & Health</option>
              <option value="Books">📚 Books & Stationery</option>
              <option value="Toys">🧸 Toys & Baby Products</option>
              <option value="Groceries">🛒 Groceries</option>
              <option value="Automotive">🚗 Automotive</option>
              <option value="Sports">🏋️ Sports & Outdoors</option>
              <option value="Pets">🐶 Pet Supplies</option>
            </select>
            <label htmlFor="description">
              {" "}
              Enter Description of the product
            </label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              placeholder="Description"
              className="w-full mb-2 p-1 border"
              required
            />
            <label htmlFor="image">Upload product image</label>
            <input
              type="file"
              onChange={(e) => setImage(e.target.files[0])}
              className="w-full mb-2"
              accept="image/*"
              required
            />
            <div className="flex justify-end gap-2">
              <button
                type="submit"
                className="bg-green-500 text-black px-3 py-1 rounded"
              >
                Add
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
