import axios from "axios";
import { useEffect, useState } from "react";
import AdminNavBar from "./AdminNavBar";
import "./admindashboard.css";
export default function AdminDashboard() {
  const [dashboardData, setDashboardData] = useState(null);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    const fetchDashboard = async () => {
      const user = JSON.parse(localStorage.getItem("user"));
      console.log("User data:", user);

      try {
        const res = await axios.get(
          `${import.meta.env.VITE_API_BASE_URL}/admindata`
        );
        setDashboardData(res.data);
      } catch (error) {
        console.error("Error fetching Admin dashboard:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboard();
  }, []);
  if (loading) return <p className="text-center mt-4">Loading...</p>;
  if (!dashboardData) return <p className="text-center mt-4">No data found</p>;
  return (
    <>
      <AdminNavBar />
      <div className="dashboard-container">
        <div className="dashboard-card">
          <div className="dashboard-title">Total Users</div>
          <div className="dashboard-value">{dashboardData.totaluser ?? 0}</div>
        </div>
        <div className="dashboard-card">
          <div className="dashboard-title">Vendors</div>
          <div className="dashboard-value">{dashboardData.vendor ?? 0}</div>
        </div>
        <div className="dashboard-card">
          <div className="dashboard-title">Customers</div>
          <div className="dashboard-value">{dashboardData.customer ?? 0}</div>
        </div>
        <div className="dashboard-card">
          <div className="dashboard-title">Total Products</div>
          <div className="dashboard-value">
            {dashboardData.totalproduct ?? 0}
          </div>
        </div>
        <div className="dashboard-card">
          <div className="dashboard-title">Total Orders</div>
          <div className="dashboard-value">{dashboardData.totalorder ?? 0}</div>
        </div>
        <div className="dashboard-card">
          <div className="dashboard-title">Total Earnings</div>
          <div className="dashboard-value">
            Rs. {dashboardData.totalsal?.toLocaleString() ?? 0}
          </div>
        </div>
      </div>
    </>
  );
}
