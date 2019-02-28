let updateContent;

function getData(quizId) {
    document.getElementById('new_quiz_form').hidden = true;
    document.getElementById('create_new').hidden = true;
    let url = 'http://localhost:8080/quiz/get/byId?quizId=' + quizId;
    fetch(url)
        .then(response => {
            if (response.ok) {
                response.json().then(body => {
                    loadJsonData(body.value);
                    window.sessionStorage.setItem('quizId', quizId);
                })
            } else {
                bootbox.alert('Issue With Retrieving QuizDataRetrieval. Most Likely doesnt exist.');
            }
        })
}

function loadJsonData(body) {
    let jsonObject = body;
    let newQuiz = new QuizDataRetrieval(jsonObject.id, jsonObject.title, jsonObject.description, jsonObject.creationDate, jsonObject.creatorId, jsonObject.amountOfQuestions, jsonObject.questionList);
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
    let requiredOptions = ' <ul class="list-group" name="option">';
    for (let i = 0; i < amountOfQuestions; i++) {
            requiredOptions += question.optionList[i].optionText;
    }
    requiredOptions += '</ul>';
    return requiredOptions;
}

function editableView(){
    let elementsByName = document.getElementsByName('option');

    for (let i = 0; i<elementsByName.length; i++){
        if (elementsByName[i].getAttribute('contenteditable') === 'true'){
            elementsByName[i].setAttribute('contenteditable','false');
            if (i == 0){
                bootbox.alert('NOW IN NORMAL VIEW \n You can no longer directly edit text');
            }
        }else {
            elementsByName[i].setAttribute('contenteditable', 'true');
            if (i == 0) {
                bootbox.alert('NOW IN EDIT VIEW \n All Question Text & Option Text are now editable by clicking on the text and changing it.');
            }
        }
    }

}

function populateQuestions(quiz) {
    let length = quiz._questionList.length;
    let requiredHTML = ' <ul class="list-group" id="wholeQuestionList">';
    for (let i = 0; i < length; i++) {
        let questionHTML = '<div id="questionId' + quiz._questionList[i].questionId + '">';
        if (window.sessionStorage.getItem('group') == 1) {
            questionHTML +=
                '<div class="row">' +
                '<div class="col">' +
                '<li class="list-group-item">' +
                '<h4>Question ' + (i + 1) + '</h4>' + quiz._questionList[i].questionText +
                '</div> ' +
                '<div class="row">' +
                '<button class="btn btn-link" onclick="deleteQuestion('+quiz._questionList[i].questionId+')">Delete</button>' +
                '<button class="btn btn-link" onclick="showAddNewOptionBox('+quiz._questionList[i].questionId+')">Add New Option</button>' +
                '</div>' +
                '</li>' +
                '</div>'
        } else {
            questionHTML += '<div class="row"><div class="col"<li class="list-group-item"><h4>Question ' +
                (i + 1) + '</h4>' + quiz._questionList[i].questionText +
                '</div><div class="row"></div></div></li>'
        }
        requiredHTML += questionHTML;
        requiredHTML += populateOptions(quiz._questionList[i]);
            requiredHTML += '<div class="form-group" id="newQuestionForm'+quiz._questionList[i].questionId+'" hidden>' +
            '<label>Option</label><input type="text" class="form-control" id="newOptionBox'+quiz._questionList[i].questionId+'" placeholder="New Option">' +
            '<button class="btn btn-primary" onclick="addOption('+quiz._questionList[i].questionId+')">Submit New Option</button>' +
            '</div>';
        requiredHTML += '</div>'
    }

    requiredHTML += '</ul>';
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
            array.push(new Question(questionList[i].questionText, questionList[i].optionList,questionList[i].id))
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

    constructor(questionText, optionList,questionId) {
        this._questionText = questionText;
        this._questionId = questionText;
        let array = [];
        for (let i = 0; i < optionList.length; i++) {
            array.push(new Option(optionList[i].optionText, i, optionList[i].correct, optionList[i].id));
        }
        this._optionList = array;
        this._questionId = questionId;
    }

    get questionText() {
        return this._questionText;
    }

    get optionList() {
        return this._optionList;
    }

    get questionId() {
        return this._questionId;
    }
}

class Option {
    constructor(optionText, optionNumber, correctAnswer, optionId) {
        this._optionText = optionText;
        this._optionNumber = optionNumber;
        this._correctAnswer = correctAnswer;
        this._optionId = optionId;
    }


    get optionText() {
        if (window.sessionStorage.getItem('group') == 1){
            if (this._correctAnswer) {
                return '<li class="list-group-item list-group-item-action list-group-item-success" name="option" id="optionId'+this._optionId+'">' + numberToCharacter(this._optionNumber) + ': ' + this._optionText + '<button class="btn btn-link" onclick="deleteOption('+this._optionId+')">Delete Option</button></li>';

            } else {
                return '<li class="list-group-item"  name="option">' + numberToCharacter(this._optionNumber) + ': ' + this._optionText + '<button class="btn btn-link" onclick="deleteOption('+this._optionId+')">Delete Option</button></li>';
            }
        }else if(window.sessionStorage.getItem('group') == 2){
            if (this._correctAnswer) {
                return '<li class="list-group-item list-group-item-action list-group-item-success" name="option" id="optionId'+this._optionId+'">' + numberToCharacter(this._optionNumber) + ': ' + this._optionText + '</li>';
            } else {
                return '<li class="list-group-item"  name="option">' + numberToCharacter(this._optionNumber) + ': ' + this._optionText + '</li>';
            }
        }else{
            return '<li class="list-group-item"  name="option">' + numberToCharacter(this._optionNumber) + ': ' + this._optionText + '</li>';
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
function listener(){

}