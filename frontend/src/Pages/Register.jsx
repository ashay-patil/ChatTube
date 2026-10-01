import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const Register = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        setMessage("");

        try {
            const response = await fetch(
                "http://localhost:8080/auth/register",
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
            const data = await response.text();

            if (response.ok) {
                setMessage(data);

                setUsername("");
                setPassword("");

                // Redirect to login after successful registration
                setTimeout(() => {
                    navigate("/login");
                }, 1000);
            } else {
                setMessage(data || "Registration failed");
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
                        Start chatting with your YouTube videos
                    </p>
                </div>

                {/* Register Card */}
                <div className="rounded-2xl border border-gray-800 bg-gray-900 p-8 shadow-xl">

                    <h1 className="text-2xl font-bold">
                        Create your account
                    </h1>

                    <p className="mt-2 text-sm text-gray-400">
                        Join ChatTube and start using AI with your videos
                    </p>

                    <form onSubmit={handleRegister} className="mt-6 space-y-5">

                        {/* Username */}
                        <div>
                            <label className="mb-2 block text-sm font-medium text-gray-300">
                                Username Or Email
                            </label>

                            <input
                                type="text"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                placeholder="Choose a username"
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
                                placeholder="Create a password"
                                required
                                className="w-full rounded-lg border border-gray-700 bg-gray-950 px-4 py-3 text-white outline-none transition placeholder:text-gray-600 focus:border-red-500 focus:ring-1 focus:ring-red-500"
                            />
                        </div>

                        {/* Message */}
                        {message && (
                            <div className="rounded-lg border border-gray-700 bg-gray-950 px-4 py-3 text-sm text-gray-300">
                                {message}
                            </div>
                        )}

                        {/* Register Button */}
                        <button
                            type="submit"
                            className="w-full rounded-lg bg-red-600 py-3 font-semibold text-white transition hover:bg-red-700"
                        >
                            Create Account
                        </button>

                    </form>

                    {/* Login Option */}
                    <div className="mt-6 border-t border-gray-800 pt-6 text-center">
                        <p className="text-sm text-gray-400">
                            Already have an account?
                        </p>

                        <Link
                            to="/login"
                            className="mt-2 inline-block text-sm font-semibold text-red-500 transition hover:text-red-400"
                        >
                            Login
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

export default Register;