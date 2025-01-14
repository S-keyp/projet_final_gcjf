import { useContext, useEffect, useState } from "react";
import { UserContext } from "../../model/utils/context/UserContext";
import { useNavigate } from "react-router-dom";
import loadData from "./../../model/utils/hooks.jsx";

import "./Absense.css";

export default function CreateAbs() {
  const navigate = useNavigate();
  const [dt_debut, setDtDebut] = useState(null);
  const dateAujourdhui = new Date();

  const { postData } = useContext(UserContext);

  const { loadedData } = loadData("http://localhost:8082/api/user");

  const [isFormValid, setIsFormValid] = useState(false);

  useEffect(() => {
    const form = document.querySelector(".abs-form");
    const dtDebutInput = form.querySelector('[name="dt_debut"]');
    const dtFinInput = form.querySelector('[name="dt_fin"]');

    const handleFormChange = () => {
      const isDtDebutValid = dtDebutInput.validity.valid;
      const isDtFinValid =
        dtFinInput.validity.valid && dtFinInput.value >= dtDebutInput.value;
      setIsFormValid(isDtDebutValid && isDtFinValid);
    };

    dtDebutInput.addEventListener("change", handleFormChange);
    dtFinInput.addEventListener("change", handleFormChange);

    return () => {
      dtDebutInput.removeEventListener("change", handleFormChange);
      dtFinInput.removeEventListener("change", handleFormChange);
    };
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();

    // Affiche un le json des données du formulaire
    const formData = new FormData(event.target);
    const data = {};
    formData.forEach((value, key) => {
      data[key] = value;
    });
    data.status = "INITIALE";
    data["user_id"] = loadedData.id;
    postData("http://localhost:8082/api/absence/create", data);
    console.log(data);
    setTimeout(() => {
      navigate("/absence");
    }, 100);
  };

  return (
    <div className="abs">
      <h1>Créer une absence</h1>
      <form className="abs-form" onSubmit={handleSubmit}>
        <label>
          <span>Votre identifiant utilisateur</span>
          <input type="text" name="user_id" value={loadedData.email} readOnly />
        </label>
        <label>
          <span>Type d&apos;absence</span>
          <select name="type">
            <option value="PAID_LEAVE">Congé</option>
            <option value="UNPAID_LEAVE">Congé sans solde</option>
            <option value="RTT_EMPLOYEE">RTT</option>
          </select>
        </label>
        <label>
          <span>Date de début</span>
          <input
            type="date"
            name="dt_debut"
            min={dateAujourdhui.toISOString().split("T")[0]}
            required
            onChange={(e) => setDtDebut(e.target.value)}
          />
        </label>
        <label>
          <span>Date de fin</span>
          <input type="date" name="dt_fin" required min={dt_debut} />
        </label>
        <label>
          <span>Motif</span>
          <input type="text" name="motif" />
        </label>
        <button type="submit" disabled={!isFormValid}>
          Créer
        </button>
      </form>
    </div>
  );
}
