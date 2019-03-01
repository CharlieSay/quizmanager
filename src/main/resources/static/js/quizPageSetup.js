function userNameCheck(window) {
    if (window.sessionStorage.length == 0) {
        alert('No user detected, please log-in.');
        window.location = '/'
    } else {
        setUpPageForUser(window.sessionStorage.getItem('group'));
        document.getElementById('wrapper fadeInDown').hidden = false;
    }
}

function setUpPageForUser(groupId) {
    if (groupId === '1') {
        document.getElementById('answerButton').hidden = false;
        document.getElementById('newQuestionForm').disabled = false;
        document.getElementById('create_new').hidden = false;
        document.getElementById('userIdentifier').innerText = 'Edit User';
    } else if (groupId === '2') {
        document.getElementById('newQuestionButton').hidden = true;
        document.getElementById('userIdentifier').innerText = 'View User';

    } else if (groupId === '3') {
        document.getElementById('newQuestionButton').hidden = true;
        document.getElementById('userIdentifier').innerText = 'Restricted User';
    } else {
        bootbox.alert('Something went wrong');
    }
}

function logOut() {
    window.sessionStorage.clear();
    window.location = '/'
}
