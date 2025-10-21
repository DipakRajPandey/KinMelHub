import axios from "axios";
import { useEffect, useState } from "react";
import AdminNavBar from "./AdminNavBar";
import styles from "./order.module.css";
import "./popupform.css";
export default function AdminOrder() {
  const [orders, setOrders] = useState([]);
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user.user.id;

    if (userId) {
      axios
        .get(`${import.meta.env.VITE_API_BASE_URL}/getallorders`)
        .then((res) => {
          setOrders(res.data);
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, []);
  return (
    <>
      <AdminNavBar />
      <div className={styles.container}>
        {Array.isArray(orders) ? (
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>SN</th>
                <th>Product Name</th>
                <th>Product Price</th>
                <th> Quantity</th>
                <th>Total Price</th>
                <th>Ordertatus</th>
                <th>PaymentStatus</th>
                <th>OrderDate</th>
                <th>Delivery Date</th>
                <th>Address</th>
                <th>Phone</th>
              </tr>
            </thead>
            <tbody>
              {orders
                .filter((item) => item.orderstatus !== "CANCELED")
                .map((item, index) => {
                  return (
                    <tr key={item.id}>
                      <td>{index + 1}</td>
                      <td>{item.items.map((i) => i.product.name)}</td>
                      <th>{item.items.map((i) => i.product.price)}</th>
                      <td>{item.items.map((i) => i.quantity)}</td>
                      <td>{item.totalprice}</td>
                      <td>{item.orderstatus}</td>
                      <td>{item.paymentstatus}</td>
                      <td>{item.orderdate}</td>
                      <td>{item.deliverydate}</td>
                      <td>{item.address}</td>
                      <td>{item.phone}</td>
                    </tr>
                  );
                })}
            </tbody>
          </table>
        ) : (
          <p>Loading or no products available</p>
        )}
      </div>
    </>
  );
}
