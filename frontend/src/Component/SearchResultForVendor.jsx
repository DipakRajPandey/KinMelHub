import axios from "axios";
import { useState } from "react";
import { useLocation } from "react-router-dom";
import EditProductModal from "./EditProductModal";
import styles from "./searchresultforvendor.module.css";
import VenderNavBar from "./VenderNavBar";
export default function SearchResult() {
  const location = useLocation();
  const products = location.state || [];
  const [editingProduct, setEditingProduct] = useState(null);
  // deleteing product
  const deleteProduct = (id) => {
    axios
      .delete(`http://localhost:9090/deleteproduct/${id}`)
      .then((res) => {
        console.log(res.data);
        setProducts(products.filter((item) => item.id !== id));
        alert("Product deleted successfully");
      })
      .catch((err) => {
        if (err.response) {
          // The request was made and server responded with error status
          console.error("Error message:", err.response.data.message);
          alert(err.response.data.message);
        } else {
          console.error("Network or other error", err);
        }
      });
  };
  const updateProduct = (item) => {
    setEditingProduct(item);
  };
  return (
    <>
      <VenderNavBar />
      {editingProduct && (
        <EditProductModal
          product={editingProduct}
          onClose={() => setEditingProduct(null)}
          onSuccess={() => {
            setEditingProduct(null);
            setRefresh((prev) => !prev); // trigger reload
          }}
        />
      )}
      {products.length === 0 ? (
        <p className="text-center text-gray-500">
          No products found for this vendor.
        </p>
      ) : (
        products.map((item) => {
          return (
            <div className={styles.product} key={item.id}>
              <div className={styles.image}>
                <img
                  src={item.image}
                  alt="product image"
                  width={100}
                  height={100}
                />
              </div>

              <div className={styles.productInfo}>
                {" "}
                <p>Product Name:{item.name}</p>
                <p>Rs.{item.price}</p>
                <p>Category: {item.category}</p>
                <p>{item.description}</p>
              </div>
              <div className={styles.actions}>
                <button
                  className={styles.editButton}
                  onClick={() => {
                    updateProduct(item);
                  }}
                >
                  Edit
                </button>
                <button
                  className={styles.deleteButton}
                  onClick={() => {
                    deleteProduct(item.id);
                  }}
                >
                  Delete
                </button>
              </div>
            </div>
          );
        })
      )}
    </>
  );
}
