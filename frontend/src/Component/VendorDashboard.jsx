import axios from "axios";
import { useEffect, useState } from "react";
import VenderNavBar from "./VenderNavBar";
import "./vendordashboard.css";
const VendorDashboard = ({ vendorId }) => {
  const [dashboardData, setDashboardData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDashboard = async () => {
      const user = JSON.parse(localStorage.getItem("user"));
      console.log("User data:", user);
      const vendorId = user.user.vendor.id;
      console.log("Vendor ID:", vendorId);
      try {
        const res = await axios.get(
          `http://localhost:9090/getdata/${vendorId}`
        );
        setDashboardData(res.data);
      } catch (error) {
        console.error("Error fetching dashboard:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboard();
  }, [vendorId]);

  if (loading) return <p className="text-center mt-4">Loading...</p>;
  if (!dashboardData) return <p className="text-center mt-4">No data found</p>;

  return (
    <>
      <VenderNavBar />
      <div className="dashboard-container">
        <div className="dashboard-card">
          <div className="dashboard-title">Pending Orders</div>
          <div className="dashboard-value">
            {" "}
            {dashboardData.pendingOrder ?? 0}
          </div>
        </div>
      </div>
      <div className="dashboard-container">
        <div className="dashboard-card">
          <div className="dashboard-title">Delivered Orders</div>
          <div className="dashboard-value">
            {" "}
            {dashboardData.deliveredOrder ?? 0}
          </div>
        </div>
      </div>

      <div className="dashboard-container">
        <div className="dashboard-card">
          <div className="dashboard-title">On the Way Orders</div>
          <div className="dashboard-value">
            {" "}
            {dashboardData.deliveredOrder ?? 0}
          </div>
        </div>
      </div>

      <div className="dashboard-container">
        <div className="dashboard-card">
          <div className="dashboard-title">Canceled Orders</div>
          <div className="dashboard-value">
            {" "}
            {dashboardData.canceledOrder ?? 0}
          </div>
        </div>
      </div>

      <div className="dashboard-container">
        <div className="dashboard-card">
          <div className="dashboard-title">Total Orders</div>
          <div className="dashboard-value">
            {" "}
            {dashboardData.totalorder ?? 0}
          </div>
        </div>
      </div>
      <div className="dashboard-container">
        <div className="dashboard-card">
          <div className="dashboard-title">Pending Earnings</div>
          <div className="dashboard-value">
            {" "}
            Rs. {dashboardData.totalPendingEarning ?? 0}
          </div>
        </div>
      </div>

      <div className="dashboard-container">
        <div className="dashboard-card">
          <div className="dashboard-title">Total Earnings</div>
          <div className="dashboard-value">
            {" "}
            Rs. {dashboardData.totalEarning ?? 0}
          </div>
        </div>
      </div>

      <div className="dashboard-container">
        <div className="dashboard-card">
          <div className="dashboard-title">Available Products</div>
          <div className="dashboard-value">
            {" "}
            {dashboardData.availableProduct ?? 0}
          </div>
        </div>
      </div>
    </>
  );
};

export default VendorDashboard;
