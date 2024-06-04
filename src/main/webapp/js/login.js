
/*
* Imports
*/
import LoginService from "./login-service.js";

/*
* Constants & variables
*/

let service = new LoginService();
const LOGIN_FORM = document.forms.login;
const REGISTER_FORM = document.forms.register;
const LOGOUT_BUTTON = document.getElementById("logout");
let USERNAME = "";
let IS_ADMIN = "";

/*
* Functions
*/
/**
 * Check if user is logged in
 * @returns {String}
 */
// Handle the username by fetching it and saving it in a constant
function getUsername() {
    const fetchUsername = service.getUser().then(response => {
        return response.username;
    });

    const handleFetchResult = () => {
        fetchUsername.then((a) => {
            USERNAME = a;
        });
    };

    handleFetchResult();
}

/**
 * Check if user is logged in
 * @returns {void}
 */
function RemoveAdminPages() {
    service.getUser().then(response => {
        if(USERNAME !== "admin"){
            if (document.URL.includes("zomerweekAanmaken.html") || document.URL.includes("gebruikerAanmaken.html")){
                window.location.replace("index.html");
            }
        }
        else{

        }
    });
}



function refresh() {
    // Change front-end to correspond login state
    if (service.isLoggedIn()) {
        getUsername();
        RemoveAdminPages();

        } else {
            if (document.URL.includes("login.html") ){
        }
        else    {window.location.replace("login.html");}

    }
}
/**
 * Register a new account
 * @returns {Promise<void>}
 */
function registerNewAccount() {
    console.log("register is started")
    const USERNAME = document.getElementById("registerUsername");
    const DIPLOMA = document.getElementById("registerDiploma");
    const PASSWORD = document.getElementById("registerPassword");
    console.log(DIPLOMA.options[DIPLOMA.selectedIndex].text)
    let jsonRequestBody = {
        "username": USERNAME.value,
        "diploma": DIPLOMA.options[DIPLOMA.selectedIndex].text,
        "password": PASSWORD.value
    }

    let fetchOptions = {
        method: "POST",
        body: JSON.stringify(jsonRequestBody),
        headers: {'Content-Type': 'application/json'}
    }

    fetch('restservices/account/create', fetchOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error(response.status);
            }
            console.log(response.status)
            USERNAME.value = ""
            PASSWORD.value = ""
            DIPLOMA.value = "I2"
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

/*
* EventListeners
*/

// Register new account
if (REGISTER_FORM) {
    REGISTER_FORM.addEventListener("submit", function(event) {
        event.preventDefault();
        registerNewAccount();
    })
}

// Log users in
if (LOGIN_FORM) {
    LOGIN_FORM.addEventListener('submit', e => {
        e.preventDefault();
        service.login(LOGIN_FORM.username.value, LOGIN_FORM.password.value).then(() => {
        })
    });
}

// Log users out
if (LOGOUT_BUTTON) {
    LOGOUT_BUTTON.addEventListener('click', e => {
        e.preventDefault();
        service.logout().then(() => {
            window.location.replace("login.html");
        });
    });
}

refresh();

// If user token couldn't be verified, log users out
service.getUser().then(user => {
    if (!user) {
        service.logout().then(r => console.log("Automatically logged out"));
        refresh();
    }

})