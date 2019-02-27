function authenticateUser(username, password) {
    if (username.value.length > 1 || password.value.length > 1) {
        let url = window.location.href + '/authorisation?username=' + username.value + '&password=' + password.value;
        fetch(url)
            .then(response => {
                if (response.ok) {
                    response.json().then(body => {
                        document.getElementById('success-panel').innerHTML = 'Welcome Back <h5>' + username.value + '</h5>';
                        document.getElementById('success-panel').hidden = false;
                        window.sessionStorage.setItem('un', username.value);
                        window.sessionStorage.setItem('uid', parseInt(body.userId));
                        window.sessionStorage.setItem('group', parseInt(body.groupId));
                        window.setTimeout(function() {
                            window.location = './quizPage.html'
                        }, 3000);
                        console.log(body)
                    })
                } else {
                    showErrorMessage('Username and Password do not match.')
                }
            }).catch(response => {
            showErrorMessage('Server Error : ' + response)
        })
    } else {
        showErrorMessage('Please fill out the form fully.')
    }
}

function showErrorMessage(message) {
    document.getElementById('error-panel').innerText = message;
    document.getElementById('error-panel').hidden = false;
}