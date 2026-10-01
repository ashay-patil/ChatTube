import React from "react";

const RequestResponse = ({ request, response, timestamps }) => {

    const openTimestamp = async (timestamp) => {
        // Open tab immediately to avoid popup blockers
        const newTab = window.open("", "_blank");

        if (!newTab) {
            alert("Please allow pop-ups for this site.");
            return;
        }

        newTab.document.write("Loading video...");

        const token = localStorage.getItem("token");

        try {
            const res = await fetch(
                `http://localhost:8080/api/get-video?videoId=${timestamp.videoId}`,
                {
                    method: "GET",
                    headers: {
                        Authorization: token,
                    },
                }
            );

            if (!res.ok) {
                throw new Error("Failed to fetch video");
            }

            const video = await res.json();

            /*
             * Supadata returns timestamps in milliseconds.
             *
             * Example:
             * 16300 ms = 16.3 seconds
             *
             * YouTube's `t` parameter expects seconds.
             */

            const startTimeInSeconds = Math.floor(
                timestamp.startTime / 1000
            );

            const separator = video.youtubeVideoUrl.includes("?")
                ? "&"
                : "?";

            const youtubeUrl =
                `${video.youtubeVideoUrl}${separator}t=${startTimeInSeconds}s`;

            newTab.location.href = youtubeUrl;

        } catch (error) {
            console.error("Error opening video:", error);
            newTab.close();
        }
    };

    const formatTime = (milliseconds) => {
        // Supadata timestamp → seconds
        const totalSeconds = Math.floor(milliseconds / 1000);

        const hours = Math.floor(totalSeconds / 3600);
        const minutes = Math.floor((totalSeconds % 3600) / 60);
        const seconds = totalSeconds % 60;

        if (hours > 0) {
            return `${String(hours).padStart(2, "0")}:${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;
        }

        return `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;
    };

    return (
        <div className="space-y-4">

            {/* User Question */}
            <div className="flex justify-end">
                <div className="max-w-[75%] rounded-2xl rounded-br-sm bg-red-600 px-5 py-3">
                    <p className="text-sm text-red-100">
                        You
                    </p>

                    <p className="mt-1">
                        {request}
                    </p>
                </div>
            </div>

            {/* AI Response */}
            <div className="flex justify-start">
                <div className="max-w-[75%] rounded-2xl rounded-bl-sm border border-gray-800 bg-gray-900 px-5 py-4">

                    <p className="text-sm font-semibold text-gray-400">
                        ChatTube
                    </p>

                    <p className="mt-2 whitespace-pre-wrap text-gray-200">
                        {response}
                    </p>

                    {/* Video References */}
                    {timestamps && timestamps.length > 0 && (
                        <div className="mt-4 border-t border-gray-800 pt-3">

                            <p className="mb-2 text-xs font-semibold text-gray-500">
                                Video References
                            </p>

                            <div className="flex flex-wrap gap-2">

                                {timestamps.map((timestamp, index) => (
                                    <button
                                        key={index}
                                        onClick={() =>
                                            openTimestamp(timestamp)
                                        }
                                        className="rounded-md bg-gray-800 px-3 py-1.5 text-xs text-gray-300 transition hover:bg-red-600 hover:text-white"
                                    >
                                        {formatTime(timestamp.startTime)}
                                        {" - "}
                                        {formatTime(timestamp.endTime)}
                                    </button>
                                ))}

                            </div>
                        </div>
                    )}

                </div>
            </div>

        </div>
    );
};

export default RequestResponse;