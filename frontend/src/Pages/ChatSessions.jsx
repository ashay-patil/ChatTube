import React from 'react'
import { Link } from "react-router-dom";
const ChatSessions = () => {
  return (
    <div>
        ChatSessions <br/><br/>

        Fetch all chat Sessions the user and save then in a chatSessions useState. <br/>

        make a CreateChatSession Component and pass the setChatSessions function. In that Component call the create chat session api and in return you will get a chatSession Object and then update the chatSessions using setChatSessions.
        <br/>
        <Link to="/chat/001">Chat-001</Link>
    </div>
  )
}

export default ChatSessions