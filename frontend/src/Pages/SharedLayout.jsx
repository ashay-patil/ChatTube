import React from "react";
import { Outlet } from "react-router-dom";
import Navbar from "../Components/Navbar";
import Footer from "../Components/Footer";

const SharedLayout = () => {
    return (
        <div className="flex min-h-screen flex-col bg-gray-950">

            {/* Navbar */}
            <Navbar />

            {/* Page Content */}
            <main className="flex-1">
                <Outlet />
            </main>

            {/* Footer */}
            <Footer />

        </div>
    );
};

export default SharedLayout;