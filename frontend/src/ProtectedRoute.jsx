import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }) => {
    const [loading, setLoading] = useState(true);
    const [authenticated, setAuthenticated] = useState(false);

    useEffect(() => {
        const validateUser = async () => {
            const token = localStorage.getItem("token");

            if (!token) {
                setAuthenticated(false);
                setLoading(false);
                return;
            }

            try {
                const response = await fetch(
                    "http://localhost:8080/auth/get-profile",
                    {
                        method: "GET",
                        headers: {
                            Authorization: token,
                        },
                    }
                );

                if (response.ok) {
                    setAuthenticated(true);
                } else {
                    localStorage.removeItem("token");
                    setAuthenticated(false);
                }

            } catch (error) {
                console.error(error);
                setAuthenticated(false);
            } finally {
                setLoading(false);
            }
        };

        validateUser();
    }, []);

    if (loading) {
        return (
            <div className="flex min-h-screen items-center justify-center bg-gray-950 text-white">
                <p>Checking authentication...</p>
            </div>
        );
    }

    if (!authenticated) {
        return <Navigate to="/login" replace />;
    }

    return children;
};

export default ProtectedRoute;