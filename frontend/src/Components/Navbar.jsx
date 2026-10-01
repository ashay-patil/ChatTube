import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const Navbar = () => {
    const [username, setUsername] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProfile = async () => {
            const token = localStorage.getItem("token");

            if (!token) {
                setUsername(null);
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

                if (!response.ok) {
                    localStorage.removeItem("token");
                    setUsername(null);
                    return;
                }

                const user = await response.json();
                setUsername(user.username);

            } catch (error) {
                console.error("Error fetching profile:", error);
                setUsername(null);
            }
        };

        fetchProfile();
    }, []);

    return (
        <nav className="border-b border-gray-800 bg-gray-950 px-6 py-4 text-white">

            <div className="mx-auto flex max-w-7xl items-center justify-between">

                {/* Left side */}
                <div className="flex items-center gap-8">

                    {/* Logo */}
                    <Link
                        to="/"
                        className="text-2xl font-bold"
                    >
                        Chat<span className="text-red-500">Tube</span>
                    </Link>

                    {/* Navigation */}
                    <div className="flex items-center gap-6">

                        <Link
                            to="/"
                            className="text-gray-300 transition hover:text-white"
                        >
                            Home
                        </Link>

                        <Link
                            to="/chat-sessions"
                            className="text-gray-300 transition hover:text-white"
                        >
                            Chat Sessions
                        </Link>

                    </div>
                </div>

                {/* Right side */}
                <div>
                    {username ? (
                        <span className="text-gray-300">
                            Welcome,{" "}
                            <strong className="text-white">
                                {username}
                            </strong>
                        </span>
                    ) : (
                        <button
                            onClick={() => navigate("/login")}
                            className="rounded-lg bg-red-600 px-4 py-2 font-semibold text-white transition hover:bg-red-700"
                        >
                            Login
                        </button>
                    )}
                </div>

            </div>

        </nav>
    );
};

export default Navbar;