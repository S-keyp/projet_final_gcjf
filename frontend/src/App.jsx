import { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./App.css";

import Dish from "./pages/Login/Login";
import NotFound from "./pages/NotFound/NotFound";

function App() {
  const [isConnected, setIsConnected] = useState(false);

  useEffect(() => {
    /*
    Mise en commentaire de la récupération du token pour le moment car il n'est pas encore implémenté
    */
    const token = localStorage.getItem("token");
    setIsConnected(!!token);

    /* ----- DEV ----- 
    Met le "isConnected" à true pour simuler un utilisateur connecté
    */
    // setIsConnected(true);
  }, []);

  return (
    <div className="App">
      <Router>
        <Routes>
          {/* Si on n'est pas connecté */}
          {!isConnected && (
            <>
              <Route path="/" element={<Dish />} />
              {/* La route suivante permet de rediriger si on est pas connecter mais elle fait interférence même si on est connecté
            Donc à voir pour plus tard
            */}
              <Route path="*" element={<Dish />} />
            </>
          )}

          {/* Si on est connecté */}
          {isConnected && (
            <>
              <Route path="/" element={<div>Home</div>} />
              <Route path="/dashboard" element={<div>Dashboard</div>} />
              <Route path="/profile" element={<div>Profile</div>} />
              <Route path="*" element={<NotFound />} />
            </>
          )}
        </Routes>
      </Router>
    </div>
  );
}

export default App;
