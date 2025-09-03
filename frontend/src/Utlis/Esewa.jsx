import { useParams, useSearchParams } from "react-router-dom";
import { v4 as uuidv4 } from "uuid";
import Footer from "../Component/Footer";
import UserNvaBar from "../Component/UserNvaBar";
import createSignature from "../Esewa/createSignature";
import style from "./esewa.module.css"; // Adjust the path as necessary
export default function Esewa() {
  const [searchParams] = useSearchParams();
  console.log(useParams());
  const id = searchParams.get("uuid");
  const productId = searchParams.get("productid");
  const total_amount = searchParams.get("total_amount");

  const uuid = uuidv4(); // Generate a unique transaction UUID
  // const totalAmount = 12; // Example total amount
  const signature = createSignature(total_amount, uuid); // use total_amount in signature

  return (
    <>
      <UserNvaBar />
      <form
        className={style.form}
        action="https://rc-epay.esewa.com.np/api/epay/main/v2/form"
        method="POST"
      >
        <label htmlFor="Total Price" className={style.label}>
          Total Price
        </label>
        <input
          className={style.input}
          type="text"
          id="amount"
          name="amount"
          value={total_amount}
          readOnly
          required
        />

        <input
          type="text"
          id="tax_amount"
          name="tax_amount"
          value="0"
          readOnly
          required
          hidden
        />
        <input
          type="text"
          id="total_amount"
          name="total_amount"
          value={total_amount}
          readOnly
          required
          hidden
        />
        <input
          type="text"
          id="transaction_uuid"
          name="transaction_uuid"
          value={uuid}
          readOnly
          required
          hidden
        />
        <input
          type="text"
          id="product_code"
          name="product_code"
          value="EPAYTEST"
          readOnly
          required
          hidden
        />
        <input
          type="text"
          id="product_service_charge"
          name="product_service_charge"
          value="0"
          readOnly
          required
          hidden
        />
        <input
          type="text"
          id="product_delivery_charge"
          name="product_delivery_charge"
          value="0"
          readOnly
          required
          hidden
        />
        <input
          type="text"
          id="success_url"
          name="success_url"
          value={`http://localhost:5173/paymentsuccess/?id=${id}&pid=${productId}`}
          readOnly
          required
          hidden
        />
        <input
          type="text"
          id="failure_url"
          name="failure_url"
          value="https://developer.esewa.com.np/failure"
          readOnly
          required
          hidden
        />
        <input
          type="text"
          id="signed_field_names"
          name="signed_field_names"
          value="total_amount,transaction_uuid,product_code"
          readOnly={true}
          required
          hidden
        />
        <input
          type="text"
          id="signature"
          name="signature"
          value={signature}
          readOnly={true}
          required
          hidden
        />
        <input value="Pay Now" type="submit" className={style.btn} />
      </form>
      <Footer />
    </>
  );
}
