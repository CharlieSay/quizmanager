function unhideNewQuestionForm() {
    document.getElementById('submitNewQuestion').hidden = false;
    document.getElementById('newQuestionForm').hidden = false;
    document.getElementById('hideNewQuestion').hidden = false;
    document.getElementById('newQuestionButton').hidden = true;
}

function hideNewQuestionForm(){
    document.getElementById('newQuestionForm').hidden = true;
    document.getElementById('submitNewQuestion').hidden = true;
    document.getElementById('hideNewQuestion').hidden = true;
    document.getElementById('newQuestionButton').hidden = false;
}

function addNewQuiz(){
    let title = document.getElementById('new_quiz_title').value;
    let description = document.getElementById('new_quiz_description').value;

    let url = 'http://localhost:8080/quiz/add?'+
        'title=' + title +
        '&description=' + description+
        '&userId=' + window.sessionStorage.getItem('uid');
    fetch(url, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST"
    }) .then(response => {
        console.log(response);
    });
    alert('new quiz added');
    document.getElementById('new_quiz_form').hidden = true;
    document.getElementById('new_quiz_title').value = '';
    document.getElementById('new_quiz_description').value = '';
    getQuizList();
}

function submitNewQuestion() {
    let question = document.getElementById('questionTitle').value;
    let option1 = document.getElementById('Option1Input').value;
    let option2 = document.getElementById('Option2Input').value;
    let option3 = document.getElementById('Option3Input').value;
    let option4 = document.getElementById('Option4Input').value;
    let option5 = document.getElementById('Option5Input').value;

    let url = 'http://localhost:8080/quiz/question/add?' +
        'question=' + question +
        '&option1=' + option1 +
        '&option2=' + option2 +
        '&option3=' + option3 +
        '&option4=' + option4 +
        '&option5=' + option5 +
        '&quizId=' + window.sessionStorage.getItem('quizId');
    fetch(url, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST"
    })
        .then(response => {
           console.log(response);
        });

    document.getElementById('newQuestionForm').hidden = true;
    document.getElementById('newQuestionButton').hidden = false;
    document.getElementById('hideNewQuestionForm').hidden = true;
    document.getElementById('submitNewQuestion').hidden = true;
    document.getElementById('questionTitle').value = '';
    document.getElementById('Option1Input').value = '';
    document.getElementById('Option2Input').value = '';
    document.getElementById('Option3Input').value = '';
    document.getElementById('Option4Input').value = '';
    document.getElementById('Option5Input').value = '';
    getData(window.sessionStorage.getItem('quizId'));
}

function addOption(questionId){
    let questionText = document.getElementById('newOptionBox'+questionId).value;
    let url = 'http://localhost:8080/quiz/question/option/add?' +
            'questionId=' + questionId +
            '&optionText=' + questionText;
    fetch(url, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST"
    })
        .then(response => {
            console.log(response);
        });
    alert('Option added');
    getData(window.sessionStorage.getItem('quizId'));
}

function showAddNewOptionBox(questionId){
    document.getElementById('newQuestionForm'+questionId).hidden = false;
}

function deleteQuestion(questionId){
    let url = 'http://localhost:8080/quiz/question/delete?questionId='+questionId;
    fetch(url,{ method: "DELETE" }).then(response => {
        console.log(response);
    });
    alert('Question Deleted');
    getData(window.sessionStorage.getItem('quizId'));
}

function deleteQuiz(quizId){
    let url = 'http://localhost:8080/quiz/delete?quizId='+quizId;
    fetch(url,{ method: "DELETE" }).then(response => {
        console.log(response);
    });
    alert('Quiz Deleted');
    getQuizList();
}

function deleteOption(optionId){
    let url = 'http://localhost:8080/quiz/question/option/delete?optionId='+optionId;
    fetch(url,{ method: "DELETE" }).then(response => {
        console.log(response);
    });
    alert('Option Deleted');
    getData(window.sessionStorage.getItem('quizId'));
}