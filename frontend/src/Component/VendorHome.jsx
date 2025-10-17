import axios from "axios";
import { useEffect, useState } from "react";
import EditProductModal from "./EditProductModal";
import VenderNavBar from "./VenderNavBar";
import styles from "./vendorHome.module.css?used";

export default function VendorHome() {
  const [editingProduct, setEditingProduct] = useState(null);
  const [refresh, setRefresh] = useState(false);
  const [products, setProducts] = useState([]);
  useEffect(() => {
    const fetchDashboard = async () => {
      const user = JSON.parse(localStorage.getItem("user"));
      console.log("User data:", user);
      const storename = user.user.vendor.storename;
      console.log("Store Name:", storename);
      axios
        .get(`http://localhost:9090/getproductbystorename/${storename}`)
        .then((res) => {
          setProducts(res.data);
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
          alert("Something went wrong during fetching products");
        });
    };
    fetchDashboard();
  }, [refresh]);
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

  // Render products
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
      <div className={styles.vendorContainer}>
        {products.length === 0 ? (
          <p className={styles.emptyMessage}>
            No products found for this vendor.
          </p>
        ) : (
          products.map((item) => {
            return (
              <div className={styles.productCard} key={item.id}>
                <div className={styles.productImage}>
                  <img src={item.image} alt="product image" />
                </div>

                <div className={styles.productDetails}>
                  {" "}
                  <p>Product Name:{item.name}</p>
                  <p>Rs.{item.price}</p>
                  <p>Category: {item.category}</p>
                  <p>{item.description}</p>
                </div>
                <div className={styles.actions}>
                  <button
                    className={`${styles.button} ${styles.editButton}`}
                    onClick={() => {
                      updateProduct(item);
                    }}
                  >
                    Edit
                  </button>
                  <button
                    className={`${styles.button} ${styles.deleteButton}`}
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
      </div>
    </>
  );
}
