export default class zomerwekenservice {
    getAllZomerweken() {
        const LOCAL_TOKEN = window.sessionStorage.getItem("myJWT");
        const URL = "http://localhost:8080/restservices/aanmelden/getAllZomerweken";

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

    selectZomerweek() {
        const LOCAL_TOKEN = window.sessionStorage.getItem("myJWT");
        let jsonRequestBody = {
            "zomerWeekNaam": document.getElementById("selectZomerweek").value,
        }

        let fetchOptions = {
            method: "POST",
            body: JSON.stringify(jsonRequestBody),
            headers: {'Content-Type': 'application/json','Authorization': 'Bearer ' + LOCAL_TOKEN}
        }

        return fetch('restservices/aanmelden/selectZomerweken', fetchOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.status);
                }
                return response.json()
                refresh()
            })
            .catch((error) => {
                const STATUS_ALREADY_EXISTS = 409;
                let message = error;

                if (Number(error.message) === STATUS_ALREADY_EXISTS) {
                    message = "An account with that username already exists!";
                }
                throw new Error(message);
            });
    }

    schrijfInFetch() {
        const LOCAL_TOKEN = window.sessionStorage.getItem("myJWT");
        console.log(document.getElementById("plekSelector").value)
        let jsonRequestBody = {
            "gekozenPlek": document.getElementById("plekSelector").value,
            "week" : document.getElementById("selectZomerweek").value
        }

        let fetchOptions = {
            method: "POST",
            body: JSON.stringify(jsonRequestBody),
            headers: {'Content-Type': 'application/json','Authorization': 'Bearer ' + LOCAL_TOKEN}
        }

        fetch('restservices/aanmelden/schrijfInOpPlek', fetchOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.status);
                }
                refresh()
            })
            .catch((error) => {
                let message = error;
                throw new Error(message);
            });
    }


}