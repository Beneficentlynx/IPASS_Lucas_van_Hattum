export default class zomerweekaanmakenService {
    registerNewZomerweek() {
        const LOCAL_TOKEN = window.sessionStorage.getItem("myJWT");
        const NAAM = document.getElementById("ZomerweekNaam");
        const BEGINDATUM = document.getElementById("ZomerweekBegindatum");
        const EINDDATUM = document.getElementById("ZomerweekEinddatum");
        const NAAMHI = document.getElementById("naamHI");
        const HOEVEELHEIDI2 = document.getElementById("hoeveelheidI2");
        const HOEVEELHEIDI3 = document.getElementById("hoeveelheidI3");
        const HOEVEELHEIDI4 = document.getElementById("hoeveelheidI4");
        const HOEVEELHEIDO = document.getElementById("hoeveelheidO");
        let ERROR = false
        const bd = new Date(BEGINDATUM.value)
        const ed = new Date(EINDDATUM.value)
        if (bd > ed) {
            ERROR = true
            document.getElementById("errorMessage").innerHTML = "De begindatum moet voor de einddatum zijn";
        }
        if (bd < new Date()) {
            ERROR = true
            document.getElementById("errorMessage").innerHTML = "De week moet in de toekomst zijn";
        }

        if(!ERROR) {
            document.getElementById("errorMessage").innerHTML = "";
            let jsonRequestBody = {
                "zomerweekNaam": NAAM.value,
                "beginDatum": BEGINDATUM.value,
                "eindDatum": EINDDATUM.value,
                "naamHI": NAAMHI.value,
                "I2": HOEVEELHEIDI2.value,
                "I3": HOEVEELHEIDI3.value,
                "I4": HOEVEELHEIDI4.value,
                "O": HOEVEELHEIDO.value
            }
            let fetchOptions = {
                method: "POST",
                body: JSON.stringify(jsonRequestBody),
                headers: {'Content-Type': 'application/json', 'Authorization': 'Bearer ' + LOCAL_TOKEN}
            }

            fetch('restservices/zomerweek/create', fetchOptions)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(response.status);
                    }
                    NAAM.value = ""
                    NAAMHI.value = ""
                    EINDDATUM.value = new Date().toISOString().split('T')[0]
                    BEGINDATUM.value = new Date().toISOString().split('T')[0]
                    HOEVEELHEIDI2.value = ""
                    HOEVEELHEIDI3.value = ""
                    HOEVEELHEIDI4.value = ""
                    HOEVEELHEIDO.value = ""
                    refresh()
                })
                .catch((error) => {
                    const STATUS_ALREADY_EXISTS = 409;
                    let message = error;

                    if (Number(error.message) === 409) {
                        document.getElementById("errorMessage").innerHTML = "Er bestaat al een zomerweek met die naam";
                    }
                    throw new Error(message);
                });

        }
    }

    getAllZomerweekDates() {
        const LOCAL_TOKEN = window.sessionStorage.getItem("myJWT");
        const URL = "http://localhost:8080/restservices/zomerweek/getAllDates";

        let fetchOptions = {
            method: "GET",
            headers: {
                'Authorization': 'Bearer ' + LOCAL_TOKEN

            }
        }
        return fetch(URL, fetchOptions)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else throw `(${response.status}) Could not get all dates`
            }).catch(error => console.log(error));
    }

}