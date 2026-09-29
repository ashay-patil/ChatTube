import React from 'react'
import { Link } from "react-router-dom";
import RequestResponse from '../Components/RequestResponse';
const Chat = () => {


  return (
    <div>
        <Link to="/chat-sessions">Back</Link>
        Fetch all the ChatHistory for the user with this chatSessionId and store them in a useState "chats" with RequestRespone format <br/>

        Display Videos Component on Left Side. Pass chatSessionId to it. This Component will take care of uploading and fetching all the videos for user with chatSessionId <br/>

        Display all ChatHistory <br/>

        Make a separateComponent for user Typing and pass setChats as prop to that component, this setChats will help to rerender the parent component <br/>
        in that UserQuestion Component, make handleSubmit function, when we get the response from backend then update the chats with new RequestRespone object using setChats prop <br/>

    </div>
  )
}

export default Chat