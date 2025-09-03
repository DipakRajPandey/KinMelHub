import axios from "axios";
import KhaltiCheckout from "khalti-checkout-web";

export default function KhaltiPayment() {
  const khaltiConfig = {
    publicKey: "test_public_key_f8faa442eba944768a31cc5b0035eaf5",
    productIdentity: "1234",
    productName: "Hamro Product",
    productUrl: "http://localhost:3000/product",
    eventHandler: {
      onSuccess(payload) {
        console.log(payload);
        // Send token to backend for verification
        axios
          .post("http://localhost:9090/api/khalti/verify", {
            token: payload.token,
            amount: payload.amount,
          })
          .then((res) => alert("Payment Verified"))
          .catch((err) => alert("Verification Failed"));
      },
      onError(error) {
        console.error(error);
      },
      onClose() {
        console.log("Widget closed");
      },
    },
    paymentPreference: ["KHALTI"],
  };

  const checkout = new KhaltiCheckout(khaltiConfig);

  return (
    <button onClick={() => checkout.show({ amount: 10 * 100 })}>
      Pay with Khalti
    </button>
  );
}
