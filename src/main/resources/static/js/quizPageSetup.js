function userNameCheck(window) {
    if (window.sessionStorage.length == 0) {
        alert('No user detected, please log-in.')
        window.location = '/'
    } else {
        setUpPageForUser(window.sessionStorage.getItem('group'))
        document.getElementById('wrapper fadeInDown').hidden = false;
    }
}

function setUpPageForUser(groupId) {
    if (groupId === '1') {
        document.getElementById('editButton').disabled = false;
        document.getElementById('answerButton').disabled = false;
    } else if (groupId === '2') {
        document.getElementById('answerButton').disabled = false;
    } else if (groupId === '3') {

    } else {
        alert('Something went wrong');
    }
}

function logOut() {
    window.sessionStorage.clear();
    window.location = '/'
}
