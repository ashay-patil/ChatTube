import React from "react";
import { useNavigate } from "react-router-dom";

const Home = () => {
    const navigate = useNavigate();

    return (
        <div className="min-h-[calc(100vh-64px)] bg-gray-950 text-white">
            
            {/* Hero Section */}
            <section className="flex flex-col items-center justify-center px-6 py-24 text-center">
                
                <div className="mb-6 rounded-full border border-gray-700 bg-gray-900 px-4 py-2 text-sm text-gray-300">
                    🎥 AI-powered YouTube Assistant
                </div>

                <h1 className="max-w-4xl text-5xl font-bold leading-tight md:text-6xl">
                    Chat with your{" "}
                    <span className="text-red-500">YouTube Videos</span>
                </h1>

                <p className="mt-6 max-w-2xl text-lg text-gray-400">
                    Add YouTube videos and ask questions about their content.
                    ChatTube uses AI and RAG to give you answers based on the
                    videos you provide.
                </p>

                {/* CTA */}
                <div className="mt-10 flex gap-4">
                    <button
                        onClick={() => navigate("/chat-sessions")}
                        className="rounded-lg bg-red-600 px-6 py-3 font-semibold transition hover:bg-red-700"
                    >
                        Start Chatting
                    </button>

                    <button
                        onClick={() => navigate("/chat-sessions")}
                        className="rounded-lg border border-gray-700 bg-gray-900 px-6 py-3 font-semibold transition hover:bg-gray-800"
                    >
                        View Sessions
                    </button>
                </div>
            </section>

            {/* Features */}
            <section className="mx-auto grid max-w-5xl grid-cols-1 gap-6 px-6 pb-20 md:grid-cols-3">

                <div className="rounded-xl border border-gray-800 bg-gray-900 p-6">
                    <div className="mb-4 text-3xl">🎥</div>
                    <h3 className="text-xl font-semibold">
                        Add YouTube Videos
                    </h3>
                    <p className="mt-2 text-gray-400">
                        Add one or multiple YouTube videos to create your
                        personalized knowledge base.
                    </p>
                </div>

                <div className="rounded-xl border border-gray-800 bg-gray-900 p-6">
                    <div className="mb-4 text-3xl">🧠</div>
                    <h3 className="text-xl font-semibold">
                        Ask Questions
                    </h3>
                    <p className="mt-2 text-gray-400">
                        Ask questions in natural language and get answers
                        using the content of your videos.
                    </p>
                </div>

                <div className="rounded-xl border border-gray-800 bg-gray-900 p-6">
                    <div className="mb-4 text-3xl">⚡</div>
                    <h3 className="text-xl font-semibold">
                        RAG-Powered Answers
                    </h3>
                    <p className="mt-2 text-gray-400">
                        Relevant video content is retrieved before generating
                        an answer with the AI model.
                    </p>
                </div>

            </section>

            {/* How it works */}
            <section className="border-t border-gray-800 bg-gray-900 px-6 py-20">
                <div className="mx-auto max-w-5xl text-center">

                    <h2 className="text-3xl font-bold">
                        How ChatTube Works
                    </h2>

                    <p className="mt-3 text-gray-400">
                        From YouTube video to AI-powered conversation.
                    </p>

                    <div className="mt-12 grid grid-cols-1 gap-8 md:grid-cols-4">

                        <div>
                            <div className="mx-auto flex h-12 w-12 items-center justify-center rounded-full bg-red-600 font-bold">
                                1
                            </div>
                            <h3 className="mt-4 font-semibold">
                                Add Videos
                            </h3>
                            <p className="mt-2 text-sm text-gray-400">
                                Provide YouTube video links.
                            </p>
                        </div>

                        <div>
                            <div className="mx-auto flex h-12 w-12 items-center justify-center rounded-full bg-red-600 font-bold">
                                2
                            </div>
                            <h3 className="mt-4 font-semibold">
                                Process Content
                            </h3>
                            <p className="mt-2 text-sm text-gray-400">
                                Video transcripts are processed and stored.
                            </p>
                        </div>

                        <div>
                            <div className="mx-auto flex h-12 w-12 items-center justify-center rounded-full bg-red-600 font-bold">
                                3
                            </div>
                            <h3 className="mt-4 font-semibold">
                                Ask Questions
                            </h3>
                            <p className="mt-2 text-sm text-gray-400">
                                Ask anything about the videos.
                            </p>
                        </div>

                        <div>
                            <div className="mx-auto flex h-12 w-12 items-center justify-center rounded-full bg-red-600 font-bold">
                                4
                            </div>
                            <h3 className="mt-4 font-semibold">
                                Get Answers
                            </h3>
                            <p className="mt-2 text-sm text-gray-400">
                                Relevant content is retrieved to generate an answer.
                            </p>
                        </div>

                    </div>
                </div>
            </section>

        </div>
    );
};

export default Home;