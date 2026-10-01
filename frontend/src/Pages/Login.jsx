import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setMessage("");

        try {
            const response = await fetch(
                "http://localhost:8080/generate-token",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        username,
                        password,
                    }),
                }
            );

            if (!response.ok) {
                setMessage("Invalid username or password");
                return;
            }

            const token = response.headers.get("Authorization");

            if (token) {
                localStorage.setItem("token", token);
                navigate("/");
            } else {
                setMessage("Login successful, but token was not received");
            }
        } catch (error) {
            console.error(error);
            setMessage("Unable to connect to server");
        }
    };

    return (
        <div className="flex min-h-screen items-center justify-center bg-gray-950 px-4 text-white">

            <div className="w-full max-w-md">

                {/* Logo */}
                <div className="mb-8 text-center">
                    <Link to="/" className="text-4xl font-bold">
                        Chat<span className="text-red-500">Tube</span>
                    </Link>

                    <p className="mt-2 text-gray-400">
                        Chat with your YouTube videos using AI
                    </p>
                </div>

                {/* Login Card */}
                <div className="rounded-2xl border border-gray-800 bg-gray-900 p-8 shadow-xl">

                    <h1 className="text-2xl font-bold">
                        Welcome back
                    </h1>

                    <p className="mt-2 text-sm text-gray-400">
                        Login to continue to ChatTube
                    </p>

                    <form onSubmit={handleLogin} className="mt-6 space-y-5">

                        {/* Username */}
                        <div>
                            <label className="mb-2 block text-sm font-medium text-gray-300">
                                Username
                            </label>

                            <input
                                type="text"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                placeholder="Enter your username"
                                required
                                className="w-full rounded-lg border border-gray-700 bg-gray-950 px-4 py-3 text-white outline-none transition placeholder:text-gray-600 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                            />
                        </div>

                        {/* Password */}
                        <div>
                            <label className="mb-2 block text-sm font-medium text-gray-300">
                                Password
                            </label>

                            <input
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                placeholder="Enter your password"
                                required
                                className="w-full rounded-lg border border-gray-700 bg-gray-950 px-4 py-3 text-white outline-none transition placeholder:text-gray-600 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                            />
                        </div>

                        {/* Error Message */}
                        {message && (
                            <div className="rounded-lg border border-red-900 bg-red-950/50 px-4 py-3 text-sm text-red-400">
                                {message}
                            </div>
                        )}

                        {/* Login Button */}
                        <button
                            type="submit"
                            className="w-full rounded-lg bg-red-600 py-3 font-semibold text-white transition hover:bg-red-700"
                        >
                            Login
                        </button>

                    </form>

                    {/* Create Account */}
                    <div className="mt-6 border-t border-gray-800 pt-6 text-center">
                        <p className="text-sm text-gray-400">
                            Don't have an account?
                        </p>

                        <Link
                            to="/register"
                            className="mt-2 inline-block text-sm font-semibold text-red-500 transition hover:text-red-400"
                        >
                            Create an account
                        </Link>
                    </div>

                </div>

                {/* Back to Home */}
                <div className="mt-6 text-center">
                    <Link
                        to="/"
                        className="text-sm text-gray-500 transition hover:text-gray-300"
                    >
                        ← Back to Home
                    </Link>
                </div>

            </div>
        </div>
    );
};

export default Login;