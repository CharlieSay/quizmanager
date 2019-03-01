function getQuizList() {
    document.getElementById('create_new').hidden = false;
    document.getElementById('new_quiz_form').hidden = true;
    let url = 'http://localhost:8080/quiz/get/all';
    fetch(url)
        .then(response => {
            if (response.ok) {
                response.json().then(body => {
                    parseQuizList(body);
                })
            }
        })
}

function parseQuizList(body) {
    document.getElementById('quiz_list').innerHTML = '';
    let startingHTML = '<ul class="list-group">';
    for (let x = 0; x < body.length; x++) {
        if (window.sessionStorage.getItem('group')==1){
            startingHTML += '<div class="row" style="display:inline-block">' +
                '<li class="list-group-item"><div class="col"><h4 onclick="getData(' + body[x].id + ')" id=' + body[x].id + '>'
                + body[x].title + '</h4></div><div class="col">' +
                '<button type="button" onclick="deleteQuiz('+body[x].id+')"class="btn btn-link">Delete Quiz</button></div></li>' +
                '</div>'
        }else{
            startingHTML += '<li class="list-group-item" onclick="getData(' + body[x].id + ')" id=' + body[x].id + '><h4>' + body[x].title + '</h4></li>'
        }
    }
    startingHTML += '</ul>'
    document.getElementById('quiz_list').innerHTML = startingHTML;
    document.getElementById('question_list').hidden = true;
    document.getElementById('quiz_list').hidden = false;
}