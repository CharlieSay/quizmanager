function getData(quizId) {
    let url = 'http://localhost:8080/quiz/get/byId?quizId=' + quizId;
    fetch(url)
        .then(response => {
            if (response.ok) {
                response.json().then(body => {
                    loadJsonData(body)
                    window.sessionStorage.setItem('quizId',quizId);
                })
            } else {
                alert('Issue With Retrieving QuizDataRetrieval. Most Likely doesnt exist.');
            }
        })
}

function loadJsonData(body) {
    let jsonObject = body;
    let newQuiz = new QuizDataRetrieval(jsonObject.id, jsonObject.title, jsonObject.description, jsonObject.creationDate, jsonObject.creatorId, jsonObject.amountOfQuestions, jsonObject.questionList)
    document.getElementById('titleText').innerText = newQuiz.title;
    document.getElementById('descText').innerText = newQuiz.description;
    document.getElementById('creationText').innerText = newQuiz.creationDate;
    document.getElementById('amountofquestionsText').innerText = newQuiz.amountOfQuestions;
    populateQuestions(newQuiz);
    document.getElementById('question_list').hidden = false;
    document.getElementById('quiz_list').hidden = true;
}

function populateOptions(question) {
    let amountOfQuestions = question.optionList.length;
    let requiredOptions = ' <ul class="list-group" name="option" hidden>';
    for (let i = 0; i < amountOfQuestions; i++) {
        console.log(question.optionList[i].optionText);
        requiredOptions += question.optionList[i].optionText;
    }
    requiredOptions += '</ul>';
    return requiredOptions;
}

function optionVerifier(boolean) {
    let elementsByName = document.getElementsByName('option');
    for (let x = 0; x < elementsByName.length; x++) {
        let item = elementsByName[x];
        item.hidden = boolean;
    }
}

function options() {
    if (document.getElementsByName('option')[0].hidden.valueOf()) {
        optionVerifier(false);
    } else {
        optionVerifier(true);
    }
}


function populateQuestions(quiz) {
    let length = quiz._questionList.length;
    let requiredHTML = ' <ul class="list-group">';
    for (let i = 0; i < length; i++) {
        let questionHTML = '<li class="list-group-item"><h4>Question ' +
            (i + 1) + '</h4>' + quiz._questionList[i].questionText + '</li>'
        requiredHTML += questionHTML;
        requiredHTML += populateOptions(quiz._questionList[i]);
    }

    requiredHTML += '</ul>'
    document.getElementById('listofquestionsText').innerHTML = requiredHTML;
}

class QuizDataRetrieval {

    constructor(id, title, description, creationDate, creatorId, amountOfQuestions, questionList) {
        this._id = id;
        this._title = title;
        this._description = description;
        this._creationDate = creationDate;
        this._creatorId = creatorId;
        this._amountOfQuestions = amountOfQuestions;

        let array = [];
        for (let i = 0; i < questionList.length; i++) {
            array.push(new Question(questionList[i].questionText, questionList[i].optionList))
        }

        this._questionList = array;
    }

    get id() {
        return this._id;
    }

    get title() {
        return this._title;
    }

    get description() {
        return this._description;
    }

    get creationDate() {
        return this._creationDate;
    }

    get creatorId() {
        return this._creatorId;
    }

    get amountOfQuestions() {
        return this._amountOfQuestions;
    }

    get questionList() {
        return this._questionList;
    }
}

class Question {

    constructor(questionText, optionList) {
        this._questionText = questionText;
        let array = [];
        for (let i = 0; i < optionList.length; i++) {
            array.push(new Option(optionList[i].optionText, i, optionList[i].correct));
        }
        this._optionList = array;
    }

    get questionText() {
        return this._questionText;
    }

    get optionList() {
        return this._optionList;
    }
}

class Option {
    constructor(optionText, optionNumber, correctAnswer) {
        this._optionText = optionText;
        this._optionNumber = optionNumber;
        this._correctAnswer = correctAnswer;
    }


    get optionText() {
        if (this._correctAnswer) {
            return '<li class="list-group-item list-group-item-action list-group-item-success"  name="option">' + numberToCharacter(this._optionNumber) + ': ' + this._optionText + "</li>";
        } else {
            return '<li class="list-group-item"  name="option">' + numberToCharacter(this._optionNumber) + ': ' + this._optionText + "</li>";
        }
    }
}

function numberToCharacter(number) {
    if (number == 0) {
        return 'A'
    } else if (number == 1) {
        return 'B'
    } else if (number == 2) {
        return 'C'
    } else if (number == 3) {
        return 'D'
    } else if (number == 4) {
        return 'E'
    }

}