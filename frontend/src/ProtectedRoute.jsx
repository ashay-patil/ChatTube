import React from 'react'

const ProtectedRoute = ({children}) => {

    // validate the user with JWT; if error on validation then navigate to login

  return children;
}

export default ProtectedRoute