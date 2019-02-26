function addNewQuestion() {
    document.getElementById('newQuestionForm').hidden = false;
    document.getElementById('newQuestionButton').hidden = true;
    document.getElementById('submitNewQuestion').hidden = false;
}

function submitNewQuestion() {
    let question = document.getElementById('questionTitle').value;
    let option1 = document.getElementById('Option1').value;
    let option2 = document.getElementById('Option2').value;
    let option3 = document.getElementById('Option3').value;
    let option4 = document.getElementById('Option4').value;
    let option5 = document.getElementById('Option5').value;
    console.log(question + option1 + option2 + option3 + option4 + option5);

    let url = 'http://localhost:8080/quiz/addQuestion?' +
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
    document.getElementById('submitNewQuestion').hidden = true;
    document.getElementById('questionTitle').value = '';
    document.getElementById('Option1').value = '';
    document.getElementById('Option2').value = '';
    document.getElementById('Option3').value = '';
    document.getElementById('Option4').value = '';
    document.getElementById('Option5').value = '';
    getData(window.sessionStorage.getItem('quizId'));
}

function deleteQuestion(questionId){
    let url = 'http://localhost:8080/quiz/question/delete?questionId='+questionId;
    fetch(url,{ method: "DELETE" }).then(response => {
        console.log(response);
    });
    alert(questionId);
    getData(window.sessionStorage.getItem('quizId'));
}

function deleteOption(optionId){
    alert('TO DELETE OPTION + ' + optionId)
}