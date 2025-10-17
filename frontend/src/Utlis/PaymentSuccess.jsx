import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import tick from "../../public/Images/ticksign.png";
import Footer from "../Component/Footer";
import UserNvaBar from "../Component/UserNvaBar";
import style from "./paymentsuccess.module.css";
export default function PaymentSuccess() {
  const Navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id");
  const rawPid = searchParams.get("pid");
  const pid = rawPid?.split("?")[0]; // Removes everything after '?'

  useEffect(() => {
    const dataBody = {
      paymentStatus: "PAID",
      orderStatus: "DELIVERED",
    };
    axios
      .put(`http://localhost:9090/updateorder/${id}`, dataBody)
      .then((res) => {})
      .catch((err) => {});
  }, []);
  const [rating, setRating] = useState();
  const [review, setReview] = useState();

  const addReview = (e) => {
    e.preventDefault();
    const sendData = {
      prdouctId: pid,
      rating: rating,
      comment: review,
    };
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.user?.id;
    axios
      .post(`http://localhost:9090/addreview/${userId}`, sendData)
      .then((rsp) => {
        Navigate("/");
        alert("Review added");
        console.log(rsp);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <>
      <UserNvaBar />
      <div className={style.success}>
        <h1 className="text-center">Payment Success</h1>
        <img
          src={tick}
          alt="tick"
          width={80}
          height={80}
          style={{ margin: "20px 0" }}
        />
        <h2 className="text-center">Thank you for your purchase!</h2>
      </div>

      <form onSubmit={addReview}>
        <h2> Rate the Product</h2>
        <label htmlFor="comment">Comment</label>
        <input
          type="text"
          placeholder="your review"
          onChange={(e) => {
            setReview(e.target.value);
          }}
        />
        <label htmlFor="rating">Rate Product</label>
        <input
          type="number"
          min={1}
          max={5}
          onChange={(e) => {
            setRating(e.target.value);
          }}
          value="1"
        />
        <input type="submit" value="Add Review" />
      </form>
      <Footer />
    </>
  );
}
