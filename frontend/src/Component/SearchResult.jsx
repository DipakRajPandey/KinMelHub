import { NavLink, useLocation } from "react-router-dom";
import styles from "./searchresult.module.css";
export default function SearchResult() {
  const location = useLocation();
  const products = location.state || [];

  return (
    <>
      {" "}
      <h2>Search Results</h2>
      <div className={styles.container}>
        {Array.isArray(products) ? (
          products.map((item) => {
            return (
              <div className={styles.products} key={item.id}>
                <div className="image">
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
                    <p>Product Name:{item.name}</p>
                  </NavLink>
                  <p>Rs.{item.price}</p>
                  <p>Category: {item.category}</p>
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
