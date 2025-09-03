import CryptoJS from "crypto-js";

function createSignature(totalAmt, id) {
  // console.log(totalAmt);
  // console.log(id);
  // console.log(eSewaProductCode);

  const message = `total_amount=${totalAmt},transaction_uuid=${id},product_code=EPAYTEST`;
  const hash = CryptoJS.HmacSHA256(message, "8gBm/:&EnhH.1/q");
  const hashInBase64 = CryptoJS.enc.Base64.stringify(hash);

  return hashInBase64;
}
export default createSignature;
