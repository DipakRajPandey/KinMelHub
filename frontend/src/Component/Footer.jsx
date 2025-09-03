import "./Footer.css";

export default function Footer() {
  return (
    <footer className="footer">
      <div className="footer-top">
        <div>
          <h3>KinmelHub</h3>
          <p>
            Experience hassle-free shopping in Nepal with your favorite brands
            and deals.
          </p>
        </div>
        <div>
          <h4>Top Categories</h4>
          <ul>
            <li>Mobiles</li>
            <li>Laptops</li>
            <li>Home Appliances</li>
            <li>Fashion</li>
            <li>Books</li>
            <li>Groceries</li>
          </ul>
        </div>
        <div>
          <h4>Quick Links</h4>
          <ul>
            <li>
              <a href="/register">Sign Up</a>
            </li>
            <li>
              <a href="/login">Login</a>
            </li>
          </ul>
        </div>
        <div>
          <h4>Follow Us</h4>
          <ul className="social-icons">
            <li>
              <a href="#">Facebook</a>
            </li>
            <li>
              <a href="#">Instagram</a>
            </li>
            <li>
              <a href="#">YouTube</a>
            </li>
          </ul>
        </div>
      </div>
      <div className="footer-bottom">
        <p>
          &copy; {new Date().getFullYear()} KinMelHub | All rights reserved.
        </p>
      </div>
    </footer>
  );
}
