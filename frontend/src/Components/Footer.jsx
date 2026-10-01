import React from "react";
import { Link } from "react-router-dom";

const Footer = () => {
    return (
        <footer className="border-t border-gray-800 bg-gray-950 text-gray-400">
            <div className="mx-auto flex max-w-7xl flex-col items-center justify-between gap-4 px-6 py-8 md:flex-row">

                {/* Logo / Name */}
                <div>
                    <Link
                        to="/"
                        className="text-xl font-bold text-white"
                    >
                        Chat<span className="text-red-500">Tube</span>
                    </Link>

                    <p className="mt-1 text-sm">
                        Chat with your YouTube videos using AI.
                    </p>
                </div>

                {/* Links */}
                <div className="flex gap-6 text-sm">
                    <Link
                        to="/"
                        className="transition hover:text-white"
                    >
                        Home
                    </Link>

                    <Link
                        to="/chat-sessions"
                        className="transition hover:text-white"
                    >
                        Chat Sessions
                    </Link>
                </div>

            </div>

            {/* Bottom */}
            <div className="border-t border-gray-800 py-4 text-center text-sm">
                © {new Date().getFullYear()} ChatTube. All rights reserved.
            </div>
        </footer>
    );
};

export default Footer;