import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import UserNavBar from "./UserNvaBar";
import styles from "./productdetails.module.css";
export default function ProductDetails() {
  const { id } = useParams();
  const [product, setProduct] = useState({});
  const [showOrderForm, setShowOrderForm] = useState(false);
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [quantity, setQuantity] = useState(1);
  const navigator = useNavigate();
  useEffect(() => {
    axios
      .get(`${import.meta.env.VITE_API_BASE_URL}/getproductbyid/${id}`)
      .then((res) => {
        console.log(res.data);
        setProduct(res.data);
      })
      .catch((err) => {
        console.error(err);
        alert("Failed to fetch product details");
      });
  }, [id]);
  const handlePlaceOrder = () => {
    const user = JSON.parse(localStorage.getItem("user"));

    const userId = user?.user?.id;

    if (!userId || !address || !phone) {
      alert("Please login and fill all fields");
      return;
    }

    const order = {
      userid: userId,
      address,
      phone,
      items: [
        {
          productId: product.id,
          quantity: quantity,
        },
      ],
    };

    axios
      .post(`${import.meta.env.VITE_API_BASE_URL}/addorder/${userId}`, order)
      .then((res) => {
        alert("Order placed successfully!");
        setShowOrderForm(false);
        setAddress("");
        setPhone("");
        navigator("/order");
        window.location.reload(); // optional
      })
      .catch((err) => {
        console.error(err);
        alert("Failed to place order.");
      });
  };
  //Add to cart
  const handleaddToCart = () => {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.user?.id;
    if (!userId) {
      alert("Please login to add items to cart");
      return;
    }
    const cart = {
      items: [
        {
          productId: product.id,
          quantity: quantity,
        },
      ],
    };
    axios
      .post(`${import.meta.env.VITE_API_BASE_URL}/addcart/${userId}`, cart)
      .then((res) => {
        alert("Item added to cart successfully!");
        setQuantity(1); // Reset quantity after adding to cart
        navigator("/cart");
      })
      .catch((err) => {
        console.error(err);
        alert("Failed to add item to cart.");
      });
  };
  // Fetch reviews
  const [reviews, setReviews] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchReviews = async (pageNumber = 0) => {
    const res = await axios.get(
      `${
        import.meta.env.VITE_API_BASE_URL
      }/getreviewbyproductid/${id}?page=${pageNumber}&size=5`
    );
    try {
      setReviews(res.data.content);
      console.log(res.data.content);
      setTotalPages(res.data.totalPages);
      setPage(res.data.number);
    } catch (err) {
      console.error("Failed to fetch reviews", err);
      setReviews([]);
    }
  };

  useEffect(() => {
    fetchReviews();
  }, [id]);
  if (!product) {
    return <div>Product not found...</div>;
  }
  return (
    <>
      <UserNavBar />
      <div className={styles.container}>
        <div className={styles.productWrapper}>
          <div className={styles.imageContainer}>
            <img
              src={product.image}
              alt={product.name}
              width={300}
              height={300}
            />
          </div>
          <div className={styles.detailsContainer}>
            <h2>Product Name :{product.name}</h2>
            <p>
              {" "}
              <strong>Price</strong> Rs.{product.price}
            </p>
            <p>
              {" "}
              <strong>Category</strong>: {product.category}
            </p>
            <p>
              {" "}
              <strong>Description </strong>: {product.description}
            </p>
            <input
              className={styles.quantityInput}
              type="number"
              value={quantity}
              min={1}
              onChange={(e) => setQuantity(parseInt(e.target.value))}
            />
            <div className={styles.buttonGroup}>
              <button
                className={`${styles.btn} ${styles["btn-primary"]}`}
                onClick={handleaddToCart}
              >
                Add to Cart
              </button>
              <button
                className={`${styles.btn} ${styles["btn-primary"]}`}
                onClick={() => setShowOrderForm(true)}
              >
                Order Now
              </button>
            </div>
            {showOrderForm && (
              <div className={styles.orderForm}>
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
                    required
                    onChange={(e) => setAddress(e.target.value)}
                  />
                </div>
                <button
                  className="btn btn-success"
                  onClick={() => {
                    handlePlaceOrder();
                  }}
                >
                  Confirm Order
                </button>
                <button
                  className="btn btn-secondary ms-2"
                  onClick={() => setShowOrderForm(false)}
                >
                  Cancel
                </button>
              </div>
            )}
          </div>
          {/* comment and rating section */}
          <div className={styles.reviewsSection}>
            <h2 className="text-xl font-bold mb-2">Product Reviews</h2>
            {reviews.length === 0 ? (
              <p>No reviews yet.</p>
            ) : (
              reviews.map((r, i) => (
                <div key={i} className="border-bottom py-2">
                  <p className="fw-bold">{r.user.name}</p>
                  {r.user.status === "ACTIVATE" ? (
                    <p className="text-success">Status: Active</p>
                  ) : (
                    <p className="text-danger">Status: Inactive</p>
                  )}

                  <p>Rating: ⭐{r.rating}</p>
                  <p>{r.comment}</p>
                </div>
              ))
            )}

            <div className="mt-4 flex gap-2">
              <button
                onClick={() => fetchReviews(page - 1)}
                disabled={page === 0}
              >
                Previous
              </button>
              <span>
                Page {page + 1} of {totalPages}
              </span>
              <button
                onClick={() => fetchReviews(page + 1)}
                disabled={page + 1 === totalPages}
              >
                Next
              </button>
            </div>
          </div>
          <div className={styles.vendorCard}>
            {!product.vendor ? (
              <p>Loading vendor info...</p>
            ) : (
              <div>
                <h3>Vendor Information</h3>
                <img src={product.vendor.logo} alt="Profile" />
                <p>
                  <strong>Bio</strong> {product.vendor.bio}
                </p>
                <p>
                  <strong>Name:</strong> {product.vendor.storename}
                </p>
                <p>
                  <strong>Contact number:</strong>{" "}
                  {product.vendor.contactnumber}
                </p>
                <p>
                  <strong>Address:</strong> {product.vendor.address}
                </p>
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
