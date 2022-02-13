export const userService = {
    authHeader,
    signup,
    login,
    logout
};

const apiUrl='http://localhost:8080';

function authHeader() {
    // return authorization header with basic auth credentials
    let user = JSON.parse(localStorage.getItem('user'));
    
    if (user && user.authdata) {
        return { 'Authorization': 'Basic ' + user.authdata };
    } else {
        return {};
    }
};

function authorization() {
    // return authorization header with basic auth credentials
    let user = JSON.parse(localStorage.getItem('user'));
    
    if (user && user.authdata) {
       // return { 'Basic ' + user.authdata + '' };
    } else {
        return {};
    }
};

function signup (username, password, name, email, roles) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password, name, email, roles})
    };
    console.log(requestOptions.body);
    return fetch(`${apiUrl}/newuser`, requestOptions)
    .then(response => {if(response.ok) {return response.json();}
    else { return Promise.reject(response);}
}
    ) 
    .then(data => { console.log(data);
        return data; 
    })
   .catch(async err=>{console.log(err); let er=''; await err.text().then(text=>{
    er=text; console.log(er+"errr");
    return Promise.reject(er)})});
}   

function login(username, password) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    };
    
    return fetch(`${apiUrl}/auth`, requestOptions)
        .then(handleResponse)
        .then(user => {
            // login successful if there's a user in the response
            if (user) {
                // store user details and basic auth credentials in local storage 
                // to keep user logged in between page refreshes
                user.authdata = window.btoa(username + ':' + password);
                localStorage.setItem('user', JSON.stringify(user));
            }
            return user;
        });
}

function logout() {
    localStorage.removeItem('user');
}

function handleResponse(response) { 
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        console.log("321"+data);
        console.log("322"+text);
        console.log("320"+JSON.parse(text));
        if (!response.ok) {
            if (response.status === 401) {
                // auto logout if 401 response returned from api
                logout();
                //window.location.reload(true);
            }
            const error = (data && data.message) || response.statusText;
            console.log("325"+(data && data.message));
            console.log("323"+error);
            return Promise.reject(error);
        }
        return data;
    });
}