import LoginService from "./login-service.js";
import Zomerwekenservice from "./zomerweken-service.js";

let zomerweekservice = new Zomerwekenservice();
let service = new LoginService();
const SELECTZOMERWEEK = document.forms.getdePlekken;
const INSCHRIJVEN = document.forms.schrijfIn;




function placeAllZomerweken() {
    const fetchAllDates = zomerweekservice.getAllZomerweken().then(response => {
        console.log(response)
        return response.allNames;
    });

    const handleFetchResult = () => {
        fetchAllDates.then((a) => {
            if(window.location.pathname === "/zomerweken.html") {
                let select = document.getElementById("selectZomerweek")
                document.getElementById("selectZomerweek").innerHTML = "";
                a.forEach((item) => {
                    let option =
                        document.createElement("option");
                    option.innerText = item;
                    select.appendChild(option);
                });
            }
        });
    };

    handleFetchResult();
}

if (SELECTZOMERWEEK) {
    SELECTZOMERWEEK.addEventListener("submit", function(event) {
        event.preventDefault();
        placePlaces();
    })
}

if (INSCHRIJVEN) {
    INSCHRIJVEN.addEventListener("submit", function(event) {
        event.preventDefault();
        zomerweekservice.schrijfInFetch()
        refresh()
    })
}


function placePlaces() {
    const fetchAllPlaces = zomerweekservice.selectZomerweek().then(response => {
        return response.allPlekken;
    });

    const handleFetchResult = () => {
        fetchAllPlaces.then((a) => {
            if(window.location.pathname === "/zomerweken.html") {
                let select = document.getElementById("plekSelector")
                document.getElementById("plekSelector").innerHTML = "";
                a.forEach((item) => {
                    if(item.I2 !== undefined){
                        let option =
                            document.createElement("option");
                        if(item.I2 === null){
                            option.innerText = "I2 plek: Geen inschrijving bekend";
                        }
                        else {
                            option.innerText = "I2 plek: " + item.I2;
                        }
                        select.appendChild(option);
                    }
                    else if(item.I3 !== undefined){
                        let option =
                            document.createElement("option");
                        if(item.I3 === null){
                            option.innerText = "I3 plek: Geen inschrijving bekend";
                        }
                        else {
                            option.innerText = "I3 plek: " + item.I3;
                        }
                        select.appendChild(option);
                    }
                    else if(item.I4 !== undefined){
                        let option =
                            document.createElement("option");
                        if(item.I4 === null){
                            option.innerText = "I4 plek: Geen inschrijving bekend";
                        }
                        else {
                            option.innerText = "I4 plek: " + item.I4;
                        }
                        select.appendChild(option);
                    }
                    else if(item.O !== undefined){
                        let option =
                            document.createElement("option");
                        if(item.O === null){
                            option.innerText = "O plek: Geen inschrijving bekend";
                        }
                        else {
                            option.innerText = "O plek: " + item.O;
                        }
                        select.appendChild(option);
                    }
                });
            }
        });
    };

    handleFetchResult();
}




function refresh() {
    // Change front-end to correspond login state
    if (service.isLoggedIn()) {
        placeAllZomerweken()
    }
    else {
        if (document.URL.includes("login.html") ){}
        else    {window.location.replace("login.html");}

    }
}

refresh()