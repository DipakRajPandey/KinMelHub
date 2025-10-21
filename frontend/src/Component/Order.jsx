import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import UserNavBar from "./UserNvaBar";
import styles from "./order.module.css";

export default function () {
  const [orders, setOrders] = useState([]);
  const navigate = useNavigate();
  //fetching orders
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user.user.id;

    if (userId) {
      axios
        .get(`${import.meta.env.VITE_API_BASE_URL}/getorderbyuserid/${userId}`)
        .then((res) => {
          setOrders(res.data);
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, []);

  // handling quantity change
  const handleQuantityChange = (id, newQuantity) => {
    setOrders((prev) =>
      prev.map((item) =>
        item.id === id ? { ...item, quantity: parseInt(newQuantity) } : item
      )
    );
  };

  //deleting orders
  const deleteIteam = (id) => {
    axios
      .delete(`${import.meta.env.VITE_API_BASE_URL}/deleteorder/${id}`)
      .then(() => {
        alert("Item deleted from Order List");
        window.location.reload();
      })
      .catch((err) => {
        console.log(err);
        alert("Failed to delete item from cart");
      });
  };

  return (
    <>
      {" "}
      <UserNavBar />
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
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {orders
                .filter((item) => item.orderstatus !== "CANCELED")
                .map((item, index) => {
                  const productid = item.items.map((i) => i.product.id);
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

                      <td>
                        {item.paymentstatus !== "PAID" ? (
                          <>
                            {/* {item.orderstatus !== "ONTHEWAY" && (
                              <button className="btn btn-danger btn-sm">
                                Update
                              </button>
                            )} */}
                            <Link
                              id="payment-button"
                              className="btn btn-primary btn-sm me-1"
                              to={`/esewa?total_amount=${item.totalprice}&uuid=${item.id}&productid=${productid}`}
                            >
                              {" "}
                              Pay with Esewa
                            </Link>

                            {item.orderstatus !== "ONTHEWAY" && (
                              <button
                                className="btn btn-danger btn-sm"
                                onClick={() => deleteIteam(item.id)}
                              >
                                Delete
                              </button>
                            )}
                          </>
                        ) : (
                          <p>Payment Completed !! Happy Shopping</p>
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

        {/* <KhaltiPayment /> */}
      </div>
    </>
  );
}
