import axios from "axios";
import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import styles from "./index.module.css";
import UserNavBar from "./UserNvaBar";
export default function Index() {
  const [products, setProducts] = useState([]);
  useEffect(() => {
    axios
      .get("http://localhost:9090/allproduct")
      .then((res) => {
        setProducts(res.data);
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  //

  return (
    <>
      <UserNavBar />
      <div className={styles.container}>
        {Array.isArray(products) ? (
          products.map((item) => {
            return (
              <div className={styles.product} key={item.id}>
                <div className={styles.image}>
                  <NavLink to={`/productdetails/${item.id}`}>
                    <img
                      src={item.image}
                      alt="product image"
                      width={100}
                      height={100}
                    />
                  </NavLink>
                </div>

                <div>
                  {" "}
                  <NavLink to={`/productdetails/${item.id}`}>
                    <h4 className=" ">{item.name}</h4>
                  </NavLink>
                  <p>
                    <strong>Rs. </strong>
                    {item.price}
                  </p>
                  <p>
                    {" "}
                    <strong>Category: </strong> {item.category}
                  </p>
                  <p>{item.description}</p>
                </div>
              </div>
            );
          })
        ) : (
          <p>Loading or no products available</p>
        )}
      </div>
    </>
  );
}
