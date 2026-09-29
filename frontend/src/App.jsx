import React from 'react'
import {BrowserRouter , Routes , Route} from 'react-router-dom';
import SharedLayout from './pages/SharedLayout';
import Home from './Pages/Home';
import ChatSessions from './Pages/ChatSessions';
import Chat from './Pages/Chat';
const App = () => {
  return (
    <div>
        <BrowserRouter>
            <Routes>
                <Route path = "/"  element = {<SharedLayout/>}>
                    <Route index element={<Home/>}></Route>
                    <Route path ="/chat-sessions" element = {<ChatSessions/>}></Route>
                </Route>
                <Route path="/chat/:chatSessionId" element = {<Chat/>}></Route>
            </Routes>
        </BrowserRouter>
    </div>
  )
}

export default App