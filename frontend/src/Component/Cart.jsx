import axios from "axios";
import { useEffect, useState } from "react";
import UserNavBar from "./UserNvaBar";
import styles from "./cart.module.css";
export default function Cart() {
  const [carts, setCarts] = useState([]);
  const [showOrderForm, setShowOrderForm] = useState(true);
  const [address, setAddress] = useState("");
  const [phone, setPhone] = useState("");
  const [orderItemId, setOrderItemId] = useState(null);

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user.user.id;
    console.log(userId);
    if (userId) {
      axios
        .get(`http://localhost:9090/getallcartitembyuserid/${userId}`)
        .then((res) => {
          setCarts(res.data);
          console.log(res.data);
        })
        .catch((err) => {
          console.error(err.response);
        });
    }
  }, []);

  //updating
  const handleQuantityChange = (id, newQuantity) => {
    setCarts((prev) =>
      prev.map((item) =>
        item.id === id ? { ...item, quantity: parseInt(newQuantity) } : item
      )
    );
  };
  const updateItem = async (item) => {
    console.log("produc id ", item.product.id);
    const body = {
      productId: item.product.id,
      quantity: item.quantity,
    };
    console.log("iteam id ", item.id);
    axios
      .put(`http://localhost:9090/updatecart/${item.id}`, body)
      .then(() => {
        alert("Quantity updated successfully");
        window.location.reload();
      })
      .catch((err) => {
        console.error(err);
        alert("Failed to update quantity");
      });
  };
  //delete items form th  cart
  const deleteIteam = (id) => {
    axios
      .delete(`http://localhost:9090/deletecart/${id}`)
      .then(() => {
        alert("Item deleted from cart");
        window.location.reload();
      })
      .catch((err) => {
        console.log(err);
        alert("Failed to delete item from cart");
      });
  };
  // order placing
  const handlePlaceOrder = (cartid, orderitem) => {
    console.log(orderItem);
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user.user.id;

    if (!address || !phone) {
      alert("Please fill all fields");
      return;
    }

    const items = carts.map((item) => ({
      productId: item.product.id,
      quantity: item.quantity,
    }));

    const order = {
      userid: userId,
      address,
      phone,
      items,
    };

    axios
      .post(`http://localhost:9090/addorder/${userId}`, order)
      .then((res) => {
        alert("Order placed successfully!");
        const tomail = "dipakrajpandey31@gmail.com";
        const subject = "Order placed successfully!";
        const emailbody = {
          productname: orderitem.product.name,
          productprice: orderitem.price,
        };
        axios
          .post(
            `http://localhost:9090/sendmail/${tomail}/${subject}`,
            emailbody
          )
          .then((res) => {
            alert("mail send");
          })
          .catch((err) => {
            alert("fail to send mail ");
            console.log(err);
          });
        setShowOrderForm(false);
        setAddress("");
        setPhone("");
        deleteIteam(cartid);
        window.location.reload();
      })
      .catch((err) => {
        console.error(err);
        alert("Failed to place order.");
      });
  };

  return (
    <>
      <UserNavBar />
      <div className={styles.container}>
        {Array.isArray(carts) && carts.length > 0 ? (
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>SN</th>
                <th>Product Name</th>
                <th>Product Price</th>
                <th>Quantity</th>
                <th>Total Price</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {carts.map((item, index) => {
                return (
                  <tr key={item.id}>
                    <td>{index + 1}</td>
                    <td>{item.product.name}</td>
                    <td>{item.product.price}</td>
                    <td>
                      <input
                        type="number"
                        value={item.quantity}
                        min={1}
                        onChange={(e) =>
                          handleQuantityChange(item.id, e.target.value)
                        }
                      />
                    </td>
                    <td>{item.price}</td>
                    <td>
                      <button
                        className="btn btn-secondary btn-sm me-1"
                        onClick={() => updateItem(item)}
                      >
                        Update
                      </button>
                      <button
                        className="btn btn-primary btn-sm me-1"
                        onClick={() => setOrderItemId(item.id)}
                      >
                        Order
                      </button>
                      {orderItemId === item.id && (
                        <div className="mt-4 border p-3">
                          <h4>Place Order</h4>
                          <div className="mb-2">
                            <label>Phone:</label>
                            <input
                              type="text"
                              className="form-control"
                              value={phone}
                              required
                              onChange={(e) => setPhone(e.target.value)}
                            />
                          </div>
                          <div className="mb-2">
                            <label>Address:</label>
                            <input
                              type="text"
                              className="form-control"
                              value={address}
                              min={10}
                              required
                              onChange={(e) => setAddress(e.target.value)}
                            />
                          </div>
                          <button
                            className="btn btn-success"
                            onClick={() => {
                              handlePlaceOrder(item.id, item);
                            }}
                          >
                            Confirm Order
                          </button>
                          <button
                            className="btn btn-secondary ms-2"
                            onClick={() => setOrderItemId(null)}
                          >
                            Cancel
                          </button>
                        </div>
                      )}
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => deleteIteam(item.id)}
                      >
                        Delete
                      </button>
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
