import axios from "axios";
import { useEffect, useState } from "react";
import styles from "./order.module.css";
import VenderNavBar from "./VenderNavBar";
export default function OrderReceived() {
  const [orders, setOrders] = useState([]);
  const [statusUpdates, setStatusUpdates] = useState({});
  const [refresh, setRefresh] = useState(false);
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    const vendorId = user.user.vendor.id;

    if (vendorId) {
      axios
        .get(`http://localhost:9090/totalorder/${vendorId}`)
        .then((res) => {
          setOrders(res.data);
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [refresh]);
  //
  const updateOrder = (orderId) => {
    const updated = statusUpdates[orderId];
    console.log(orderId);
    const body = {
      orderStatus: updated?.orderstatus || "PENDING",
      paymentStatus: updated?.paymentstatus || "PENDING",
    };

    axios
      .put(`http://localhost:9090/updateorder/${orderId}`, body)
      .then((res) => {
        alert("Order updated successfully");
        setStatusUpdates((prev) => ({
          ...prev,
          [orderId]: body,
        }));
        setRefresh((prev) => !prev);
      })
      .catch((err) => {
        console.error(err);
        alert("Failed to update order");
      });
  };

  return (
    <>
      <VenderNavBar />
      <div className={styles.container}>
        {Array.isArray(orders) ? (
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>SN</th>
                <th>Product Name</th>
                <th>Product Price</th>
                <th>TotalQuantity</th>
                <th>Total Price</th>
                <th>Ordertatus</th>
                <th>PaymentStatus</th>
                <th>OrderDate</th>
                <th>Delivery Date</th>
                <th>Address</th>
                <th>Phone</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((item, index) => {
                return (
                  <tr key={item.id}>
                    <td>{index + 1}</td>
                    <td>{item.items.map((i) => i.product.name)}</td>
                    <td>{item.items.map((i) => i.product.price)}</td>
                    <td>{item.items.map((i) => i.quantity)} </td>
                    <td>{item.totalprice}</td>
                    <td>
                      <select
                        value={
                          statusUpdates[item.id]?.orderstatus ??
                          item.orderstatus ??
                          "PENDING"
                        }
                        onChange={(e) =>
                          setStatusUpdates((prev) => ({
                            ...prev,
                            [item.id]: {
                              ...prev[item.id],
                              orderstatus: e.target.value,
                            },
                          }))
                        }
                      >
                        <option value="PENDING">PENDING</option>
                        <option value="SHIPPED">SHIPPED</option>
                        <option value="DELIVERED">DELIVERED</option>
                        <option value="CANCELED">CANCELED</option>
                      </select>
                    </td>
                    <td>
                      <select
                        value={
                          statusUpdates[item.id]?.paymentstatus ??
                          item.paymentstatus ??
                          "PENDING"
                        }
                        onChange={(e) =>
                          setStatusUpdates((prev) => ({
                            ...prev,
                            [item.id]: {
                              ...prev[item.id],
                              paymentstatus: e.target.value,
                            },
                          }))
                        }
                      >
                        <option value="PENDING">PENDING</option>
                        <option value="PAID">PAID</option>
                        <option value="REFUND">REFUND</option>
                      </select>
                    </td>
                    <td>{item.orderdate}</td>
                    <td>{item.deliverydate}</td>
                    <td>{item.address}</td>
                    <td>{item.phone}</td>

                    <td>
                      {item.paymentstatus !== "PAID" ||
                      item.orderstatus !== "DELIVERED" ? (
                        <button
                          className="btn btn-primary btn-sm"
                          onClick={() => updateOrder(item.id)}
                        >
                          Update
                        </button>
                      ) : (
                        <p>Payment Completed </p>
                      )}
                    </td>
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
