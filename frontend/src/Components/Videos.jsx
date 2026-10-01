import React, { useEffect, useState } from "react";

const Videos = ({ chatSessionId }) => {
    const [videos, setVideos] = useState([]);
    const [youtubeLinks, setYoutubeLinks] = useState("");
    const [loading, setLoading] = useState(true);
    const [uploading, setUploading] = useState(false);

    const token = localStorage.getItem("token");

    const fetchVideos = async () => {
        try {
            const response = await fetch(
                `http://localhost:8080/api/get-videos?chatSessionId=${chatSessionId}`,
                {
                    method: "GET",
                    headers: {
                        Authorization: token,
                    },
                }
            );

            if (!response.ok) {
                throw new Error("Failed to fetch videos");
            }

            const data = await response.json();

            setVideos(data);

        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchVideos();
    }, [chatSessionId]);

    const uploadVideos = async (e) => {
        e.preventDefault();

        const links = youtubeLinks
            .split("\n")
            .map((link) => link.trim())
            .filter((link) => link.length > 0);

        if (links.length === 0) {
            return;
        }

        setUploading(true);

        try {
            const response = await fetch(
                `http://localhost:8080/api/upload-youtube-videos?chatSessionId=${chatSessionId}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: token,
                    },
                    body: JSON.stringify({
                        youtubeLinks: links,
                    }),
                }
            );

            if (!response.ok) {
                throw new Error("Failed to upload videos");
            }

            const message = await response.text();

            console.log(message);

            setYoutubeLinks("");

            // Fetch the updated videos
            await fetchVideos();

        } catch (error) {
            console.error(error);
        } finally {
            setUploading(false);
        }
    };

    return (
        <div className="rounded-xl border border-gray-800 bg-gray-900 p-5">

            <h2 className="mb-4 text-xl font-semibold">
                Videos
            </h2>

            {/* Upload */}
            <form onSubmit={uploadVideos}>

                <textarea
                    value={youtubeLinks}
                    onChange={(e) =>
                        setYoutubeLinks(e.target.value)
                    }
                    placeholder={`Paste YouTube links here...\nOne link per line`}
                    rows={4}
                    className="w-full resize-none rounded-lg border border-gray-700 bg-gray-950 p-3 text-sm text-white outline-none placeholder:text-gray-600 focus:border-red-500"
                />

                <button
                    type="submit"
                    disabled={uploading}
                    className="mt-3 w-full rounded-lg bg-red-600 py-2 font-semibold hover:bg-red-700 disabled:opacity-50"
                >
                    {uploading
                        ? "Processing..."
                        : "Add Videos"}
                </button>

            </form>

            {/* Video list */}
            <div className="mt-6">

                <h3 className="mb-3 text-sm font-semibold text-gray-400">
                    Added Videos
                </h3>

                {loading ? (
                    <p className="text-sm text-gray-500">
                        Loading videos...
                    </p>
                ) : videos.length === 0 ? (
                    <p className="text-sm text-gray-500">
                        No videos added yet.
                    </p>
                ) : (
                    <div className="space-y-3">

                        {videos.map((video) => (
                            <div
                                key={video.id}
                                className="rounded-lg border border-gray-800 bg-gray-950 p-3"
                            >

                                <div className="aspect-video overflow-hidden rounded-md">
                                    <iframe
                                        className="h-full w-full"
                                        src={`https://www.youtube.com/embed/${video.youtubeVideoId}`}
                                        title={video.youtubeVideoId}
                                        allowFullScreen
                                    />
                                </div>

                                <p className="mt-2 truncate text-sm text-gray-400">
                                    {video.youtubeVideoUrl}
                                </p>

                            </div>
                        ))}

                    </div>
                )}

            </div>

        </div>
    );
};

export default Videos;