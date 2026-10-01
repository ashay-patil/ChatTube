import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Videos from "../Components/Videos";
import RequestResponse from "../Components/RequestResponse";

const Chat = () => {
    const { chatSessionId } = useParams();
    const navigate = useNavigate();

    const [chatHistory, setChatHistory] = useState([]);
    const [question, setQuestion] = useState("");
    const [loading, setLoading] = useState(false);

    const token = localStorage.getItem("token");

    useEffect(() => {
        const fetchChatHistory = async () => {
            try {
                const response = await fetch(
                    `http://localhost:8080/api/get-all-chats?chatSessionId=${chatSessionId}`,
                    {
                        method: "GET",
                        headers: {
                            Authorization: token,
                        },
                    }
                );

                if (!response.ok) {
                    throw new Error("Failed to fetch chat history");
                }

                const data = await response.json();

                setChatHistory(data);

            } catch (error) {
                console.error(error);
            }
        };

        fetchChatHistory();
    }, [chatSessionId, token]);

    const askQuestion = async (e) => {
        e.preventDefault();

        if (!question.trim()) {
            return;
        }

        setLoading(true);

        try {
            const response = await fetch(
                `http://localhost:8080/api/ask-question?chatSessionId=${chatSessionId}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: token,
                    },
                    body: JSON.stringify(question),
                }
            );

            if (!response.ok) {
                throw new Error("Failed to get response");
            }

            const data = await response.json();

            const newChat = {
                id: Date.now().toString(),
                userQuestion: question,
                LLMResponse: data.response,
                timestamps: data.timestamps,
            };

            setChatHistory((prev) => [
                ...prev,
                newChat,
            ]);

            setQuestion("");

        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-gray-950 text-white">

            <div className="mx-auto max-w-7xl px-6 py-8">

                {/* Back button */}
                <button
                    onClick={() => navigate("/chat-sessions")}
                    className="mb-6 inline-flex items-center gap-2 rounded-lg border border-gray-700 bg-gray-900 px-4 py-2 text-sm font-medium text-gray-300 transition hover:border-red-500 hover:text-white"
                >
                    ← Back to Chat Sessions
                </button>

                {/* Main content */}
                <div className="flex gap-6">

                    {/* Left side - Videos */}
                    <div className="w-1/3">
                        <Videos chatSessionId={chatSessionId} />
                    </div>

                    {/* Right side - Chat */}
                    <div className="flex w-2/3 flex-col">

                        <h1 className="mb-6 text-2xl font-bold">
                            Chat
                        </h1>

                        {/* Chat history */}
                        <div className="mb-6 flex-1 space-y-5">

                            {chatHistory.length === 0 ? (
                                <div className="rounded-xl border border-gray-800 bg-gray-900 p-8 text-center text-gray-400">
                                    Ask a question about your videos.
                                </div>
                            ) : (
                                chatHistory.map((chat) => (
                                    <RequestResponse
                                        key={chat.id}
                                        request={chat.userQuestion}
                                        response={chat.LLMResponse}
                                        timestamps={chat.timestamps}
                                    />
                                ))
                            )}

                        </div>

                        {/* Question input */}
                        <form
                            onSubmit={askQuestion}
                            className="flex gap-3"
                        >
                            <input
                                type="text"
                                value={question}
                                onChange={(e) =>
                                    setQuestion(e.target.value)
                                }
                                placeholder="Ask something about your videos..."
                                className="flex-1 rounded-lg border border-gray-700 bg-gray-900 px-4 py-3 text-white outline-none placeholder:text-gray-500 focus:border-red-500"
                            />

                            <button
                                type="submit"
                                disabled={loading}
                                className="rounded-lg bg-red-600 px-6 py-3 font-semibold hover:bg-red-700 disabled:opacity-50"
                            >
                                {loading ? "Thinking..." : "Ask"}
                            </button>
                        </form>

                    </div>

                </div>

            </div>

        </div>
    );
};

export default Chat;