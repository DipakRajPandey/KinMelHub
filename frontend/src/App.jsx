import { Route, Routes } from "react-router-dom";
import "./App.css";
import AddProduct from "./Component/AddProduct";
import AddToCart from "./Component/AddToCart";
import AdminDashboard from "./Component/AdminDashboard";
import AdminNavBar from "./Component/AdminNavBar";
import AdminOrder from "./Component/AdminOrder";
import AdminUser from "./Component/AdminUser";
import Cart from "./Component/Cart";
import Footer from "./Component/Footer";
import Index from "./Component/Index";
import Login from "./Component/Login";
import Order from "./Component/Order";
import OrderReceived from "./Component/OrderReceived";
import ProductDetails from "./Component/ProductDetails";
import Register from "./Component/Register";
import RegisterAsUser from "./Component/RegisterAsUser";
import RegisterAsVendor from "./Component/RegisterAsVendor";
import RearchResult from "./Component/SearchResult";
import SearchResultForVendor from "./Component/SearchResultForVendor";
import VendorDashboard from "./Component/VendorDashboard";
import VendorHome from "./Component/VendorHome";
import PrivateRoute from "./PrivateRoute";
import Esewa from "./Utlis/Esewa";
import PaymentSuccess from "./Utlis/PaymentSuccess";
function App() {
  const user = localStorage.getItem("user");
  return (
    <>
      <Routes>
        <Route path="/" element={<Index />}></Route>
        <Route path="/Login" element={<Login />}></Route>
        <Route path="/register" element={<Register />}>
          {" "}
        </Route>
        <Route path="/registerasuser" element={<RegisterAsUser />}></Route>
        <Route path="/registerasvendor" element={<RegisterAsVendor />}></Route>

        <Route
          path="*"
          element={<h1 className="text-center text-2xl">Page Not Found</h1>}
        ></Route>

        <Route path="/footer" element={<Footer />}></Route>

        <Route element={<PrivateRoute />}>
          <Route
            path="/productdetails/:id"
            element={<ProductDetails />}
          ></Route>
          <Route path="/search" element={<RearchResult />}></Route>
          <Route
            path="/searchforvendor"
            element={<SearchResultForVendor />}
          ></Route>
          <Route path="/adminnavbar" element={<AdminNavBar />}></Route>
          <Route path="/admindashboard" element={<AdminDashboard />}></Route>
          <Route path="/adminorder" element={<AdminOrder />}></Route>
          <Route path="/adminuser" element={<AdminUser />}></Route>
          <Route path="/orderreceived" element={<OrderReceived />}>
            {" "}
          </Route>
          <Route path="/vendordashboard" element={<VendorDashboard />}></Route>
          <Route path="/cart" element={<Cart />}></Route>
          <Route path="/addtocart" element={<AddToCart />}></Route>
          <Route path="/order" element={<Order />}></Route>
          <Route path="/vendorhome" element={<VendorHome />}></Route>
          <Route path="/addproduct" element={<AddProduct />}></Route>

          <Route path="/esewa" element={<Esewa />}></Route>
          {/* <Route path="/paymentsuccess"></Route> */}
          <Route path="/paymentsuccess/" element={<PaymentSuccess />}></Route>
        </Route>
      </Routes>
    </>
  );
}

export default App;
