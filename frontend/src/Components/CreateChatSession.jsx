import React, { useState } from "react";

const CreateChatSession = ({ setChatSessions }) => {
    const [sessionName, setSessionName] = useState("");
    const [loading, setLoading] = useState(false);

    const createChatSession = async (e) => {
        e.preventDefault();

        if (!sessionName.trim()) {
            return;
        }

        const token = localStorage.getItem("token");

        setLoading(true);

        try {
            const response = await fetch(
                "http://localhost:8080/chat-session/create-chat-session",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: token,
                    },
                    body: JSON.stringify({
                        sessionName: sessionName.trim(),
                    }),
                }
            );

            if (!response.ok) {
                throw new Error("Failed to create chat session");
            }

            const newSession = await response.json();

            // Add newly created session to existing sessions
            setChatSessions((prevSessions) => [
                ...prevSessions,
                newSession,
            ]);

            setSessionName("");

        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <form
            onSubmit={createChatSession}
            className="flex gap-2"
        >
            <input
                type="text"
                placeholder="Session name"
                value={sessionName}
                onChange={(e) => setSessionName(e.target.value)}
                className="rounded-lg border border-gray-700 bg-gray-900 px-4 py-2 text-white outline-none placeholder:text-gray-500 focus:border-red-500"
            />

            <button
                type="submit"
                disabled={loading}
                className="rounded-lg bg-red-600 px-4 py-2 font-semibold hover:bg-red-700 disabled:opacity-50"
            >
                {loading ? "Creating..." : "Create"}
            </button>
        </form>
    );
};

export default CreateChatSession;