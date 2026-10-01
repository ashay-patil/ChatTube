import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import CreateChatSession from "../Components/CreateChatSession";

const ChatSessions = () => {
    const [chatSessions, setChatSessions] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchChatSessions = async () => {
        const token = localStorage.getItem("token");

        try {
            const response = await fetch(
                "http://localhost:8080/chat-session/get-chat-sessions",
                {
                    method: "GET",
                    headers: {
                        Authorization: token,
                    },
                }
            );

            if (!response.ok) {
                throw new Error("Failed to fetch chat sessions");
            }

            const data = await response.json();

            setChatSessions(data);
        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchChatSessions();
    }, []);

    return (
        <div className="min-h-screen bg-gray-950 px-6 py-10 text-white">
            <div className="mx-auto max-w-5xl">

                <div className="mb-8 flex items-center justify-between">
                    <div>
                        <h1 className="text-3xl font-bold">
                            Chat Sessions
                        </h1>

                        <p className="mt-2 text-gray-400">
                            Continue your conversations or create a new one.
                        </p>
                    </div>

                    <CreateChatSession
                        setChatSessions={setChatSessions}
                    />
                </div>

                {loading ? (
                    <p className="text-gray-400">
                        Loading chat sessions...
                    </p>
                ) : chatSessions.length === 0 ? (
                    <div className="rounded-xl border border-gray-800 bg-gray-900 p-10 text-center">
                        <h2 className="text-xl font-semibold">
                            No chat sessions yet
                        </h2>

                        <p className="mt-2 text-gray-400">
                            Create a chat session to start chatting with
                            your YouTube videos.
                        </p>
                    </div>
                ) : (
                    <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                        {chatSessions.map((session) => (
                            <Link
                                key={session.id}
                                to={`/chat/${session.id}`}
                                className="rounded-xl border border-gray-800 bg-gray-900 p-5 transition hover:border-red-500 hover:bg-gray-800"
                            >
                                <h2 className="text-lg font-semibold">
                                    {session.sessionName}
                                </h2>

                                <p className="mt-2 text-sm text-gray-500">
                                    Open conversation →
                                </p>
                            </Link>
                        ))}
                    </div>
                )}

            </div>
        </div>
    );
};

export default ChatSessions;