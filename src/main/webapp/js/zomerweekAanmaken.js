import LoginService from "./login-service.js";
import zomerweekaanmakenService from "./zomerweekAanmaken-service";


const REGISTREER_ZOMERWEEK = document.forms.registerZomerweek;
let zomeraanmaakService = new zomerweekaanmakenService()
let service = new LoginService();
/**
 * registreer een nieuwe zomerweek
 * @returns {Promise<void>}
 */


if (REGISTREER_ZOMERWEEK) {
    REGISTREER_ZOMERWEEK.addEventListener("submit", function(event) {
        event.preventDefault();
        zomeraanmaakService.registerNewZomerweek();
    })
}

function refresh() {
    // Change front-end to correspond login state
    if (service.isLoggedIn()) {
        placeAllUsedDates()
    }
    else {
        if (document.URL.includes("login.html") ){}
        else    {window.location.replace("login.html");}

    }
}




function placeAllUsedDates() {
    const fetchAllDates = zomeraanmaakService.getAllZomerweekDates().then(response => {
        return response.allDates;
    });

    const handleFetchResult = () => {
        fetchAllDates.then((a) => {
            if(window.location.pathname === "/zomerweekAanmaken.html") {
                let list = document.getElementById("datelist")
                document.getElementById("datelist").innerHTML = "";
                a.forEach((item) => {
                    let li =
                        document.createElement("li");
                    li.innerText = item;
                    list.appendChild(li);
                });
            }
        });
    };

    handleFetchResult();
}

refresh()