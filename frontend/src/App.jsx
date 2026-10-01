import React from "react";
import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import SharedLayout from "./Pages/SharedLayout";
import Home from "./Pages/Home";
import ChatSessions from "./Pages/ChatSessions";
import Chat from "./Pages/Chat";
import Login from "./Pages/Login";
import Register from "./Pages/Register";
import ProtectedRoute from "./ProtectedRoute";

const App = () => {
    return (
        <BrowserRouter>
            <Routes>

                {/* Public pages with Navbar + Footer */}
                <Route path="/" element={<SharedLayout />}>

                    <Route
                        index
                        element={<Home />}
                    />

                    <Route
                        path="chat-sessions"
                        element={
                            <ProtectedRoute>
                                <ChatSessions />
                            </ProtectedRoute>
                        }
                    />

                </Route>

                {/* Chat */}
                <Route
                    path="/chat/:chatSessionId"
                    element={
                        <ProtectedRoute>
                            <Chat />
                        </ProtectedRoute>
                    }
                />

                {/* Authentication */}
                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

            </Routes>
        </BrowserRouter>
    );
};

export default App;