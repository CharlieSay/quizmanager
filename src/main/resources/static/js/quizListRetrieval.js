function getQuizList() {
    window.sessionStorage.removeItem('quizId');
    let url = 'http://localhost:8080/quiz/get/all';
    fetch(url)
        .then(response => {
            if (response.ok) {
                response.json().then(body => {
                    parseQuizList(body);
                })
            } else {

            }
        })
}

function parseQuizList(body) {
    document.getElementById('quiz_list').innerHTML = '';
    let startingHTML = '<ul class="list-group">'
    for (let x = 0; x < body.length; x++) {
        startingHTML += '<li class="list-group-item" onclick="getData(' + body[x].id + ')" id=' + body[x].id + '><h4>' + body[x].title + '</h4></li>'
    }
    startingHTML += '</ul>'
    document.getElementById('quiz_list').innerHTML = startingHTML;
    document.getElementById('question_list').hidden = true;
    document.getElementById('quiz_list').hidden = false;
}