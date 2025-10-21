import axios from "axios";
import { useState } from "react";
import styles from "./editproductmodule.module.css";
export default function EditProductModal({ product, onClose, onSuccess }) {
  const [formData, setFormData] = useState({
    name: product.name,
    price: product.price,
    description: product.description,
    category: product.category,
  });
  const [image, setImage] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const data = new FormData();
    data.append("product", JSON.stringify(formData));
    if (image) data.append("image", image);

    try {
      await axios.put(
        `${import.meta.env.VITE_API_BASE_URL}/updateproduct/${product.id}`,
        data,
        {
          headers: { "Content-Type": "multipart/form-data" },
        }
      );
      alert("Product updated!");
      onSuccess();
    } catch (err) {
      alert(
        "Update failed: " + (err.response?.data?.message || "Unknown error")
      );
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <span className={styles.closeButton} onClick={onClose}>
          ×
        </span>
        <div className={styles.modalHeader}>Edit Product</div>
        <form onSubmit={handleSubmit}>
          <div className={styles.formGroup}>
            <label htmlFor="name">Enter product name</label>
            <input
              name="name"
              value={formData.name}
              onChange={handleChange}
              className="w-full mb-2 p-1 border"
              placeholder="Name"
            />
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="price">Enter product price</label>
            <input
              name="price"
              type="number"
              value={formData.price}
              onChange={handleChange}
              className="w-full mb-2 p-1 border"
              placeholder="Price"
            />
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="category">Select Product category</label>
            <select
              name="category"
              value={formData.category}
              onChange={handleChange}
              className="w-full mb-2 p-1 border"
              placeholder="Category"
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
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="description">
              {" "}
              Enter Description of the product
            </label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              className="w-full mb-2 p-1 border"
              placeholder="Description"
            />
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="image">Upload product image</label>
            <input
              type="file"
              onChange={(e) => setImage(e.target.files[0])}
              className="w-full mb-2"
            />
          </div>

          <div className={styles.modalActions}>
            <button
              className={`${styles.btn} ${styles.btnUpdate}`}
              type="submit"
            >
              Update
            </button>
            <button
              className={`${styles.btn} ${styles.btnCancel}`}
              onClick={onClose}
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
