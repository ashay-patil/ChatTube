import React from "react";

const UserQuestion = ({ question }) => {
    return (
        <div className="flex justify-end">
            <div className="max-w-[75%] rounded-2xl rounded-br-sm bg-red-600 px-5 py-3">
                <p className="text-sm text-red-100">
                    You
                </p>

                <p className="mt-1">
                    {question}
                </p>
            </div>
        </div>
    );
};

export default UserQuestion;