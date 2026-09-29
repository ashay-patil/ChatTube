import React from 'react'
import {BrowserRouter , Routes , Route} from 'react-router-dom';
import SharedLayout from './pages/SharedLayout';
import Home from './Pages/Home';
import ChatSessions from './Pages/ChatSessions';
import Chat from './Pages/Chat';
import Login from './Pages/Login';
import Register from './Pages/Register';
const App = () => {
  return (
    <div>
        <BrowserRouter>
            <Routes>
                <Route path = "/"  element = {<SharedLayout/>}>
                    <Route index element={<Home/>}></Route>
                    <Route path ="/chat-sessions" element = {
                        <ProtectedRoute>
                            <ChatSessions/>
                        </ProtectedRoute>
                    }></Route> 
                </Route>
                <Route path ="/chat/:chatSessionId" element = {
                        <ProtectedRoute>
                            <Chat/>
                        </ProtectedRoute>
                    }></Route>

                <Route path='/login' element={<Login/>}></Route>
                <Route path='/register' element={<Register/>}></Route>
            </Routes>
        </BrowserRouter>
    </div>
  )
}

export default App