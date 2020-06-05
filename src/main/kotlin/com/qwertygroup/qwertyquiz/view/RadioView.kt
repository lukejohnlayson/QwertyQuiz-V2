package com.qwertygroup.qwertyquiz.view

import com.qwertygroup.qwertyquiz.app.QuizModel
import com.qwertygroup.qwertyquiz.app.Styles
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import tornadofx.*

class RadioView : View("QwertyQuiz") {
    // Get instance of QuizModel from StartView
    private val quiz: QuizModel by inject()
    // Get instance of Question
    val question = quiz.questionList[quiz.currentQuestionNumber - 1]
    // Variable to hold currently selected answer
    var answerSelected = ""
    // Radiobutton group for answer radio-buttons
    private val answerGroup = ToggleGroup()
    // List to hold question answers
    private val answers = mutableListOf<String>()

    // Resize the window to fit to view contents
    override fun onDock() {
        currentWindow?.sizeToScene()
    }

    // root window object, aligning columns horizontally
    override val root = hbox {
        // Creating new Scope
        val scope = Scope()
        // Setting QuizModel instance to be in scope
        setInScope(quiz, scope)
        // Creating new parameter containing the instance of QuizModel
        val paramsReview = find<ReviewView>(scope)

        // Column One, containing question, question number and restart & skip buttons. Using a border-pane as container
        // so as to align restart & skip buttons properly
        borderpane {
            // Adding css class for styling column one
            addClass(Styles.columnOne)

            // Top section, containing question data.
            top = vbox {
                // Adding css class to style top section
                addClass(Styles.topSection)

                // Container to align question number contents horizontally
                hbox(10, alignment = Pos.BOTTOM_LEFT) {
                    // Question number
                    label("Q${quiz.currentQuestionNumber}") {
                        // Adding css class to style question number label
                        addClass(Styles.questionNumber)
                    }
                    // Total questions label
                    label("of ${quiz.numQuestions + 1}") {
                        // Adding css class to style total questions label
                        addClass(Styles.amountOfQuestions)
                    }

                    // Adding padding between question number labels and actual question
                    style {
                        padding = box(0.px, 0.px, 12.px, 0.px)
                    }
                }
                // Question Description Label
                label(question.questionDescription) {
                    // Adding css class to style question description
                    addClass(Styles.questionDescription)
                }
                // Primary Question Label
                label(question.primaryQuestion) {
                    // Adding css class to style question label
                    addClass(Styles.questionLabel)
                }
            }

            // Check if the quiz is currently active, and display the relevant buttons.
            if(quiz.active) {
                // Display "Restart" and "Skip" buttons if the user is currently attempting the quiz.
                left = hbox(alignment = Pos.BOTTOM_LEFT) {
                    button("Restart Quiz") {
                        action {
                            // Use QuizModel function to reset question counter and question number
                            quiz.restartQuiz()
                            // Reload the view with the new data
                            reload()
                        }

                        // Adding css class to style button
                        addClass(Styles.smallButtonWhite)
                    }
                }

                right = hbox(alignment = Pos.BOTTOM_RIGHT) {
                    // Skip Question Button
                    button("Skip") {
                        action {
                            // Add current question to list of skipped questions
                            quiz.questionsSkipped.add(quiz.currentQuestionNumber)

                            // Check if user has reached the end of the quiz
                            if(quiz.currentQuestionNumber != quiz.numQuestions + 1) {
                                // Increase question counter
                                quiz.currentQuestionNumber++
                                // Reload view
                                reload()
                            }
                            else {
                                replaceWith(paramsReview)
                            }
                        }

                        // Adding css class to style button
                        addClass(Styles.smallButtonWhite)
                    }
                }
            }
            else {
                // Display only the "Cancel" button if the user is retrying the question after the quiz attempt
                left = hbox(alignment = Pos.BOTTOM_LEFT) {
                    button("Cancel") {
                        action {
                            close()
                        }

                        // Adding css class to style button
                        addClass(Styles.smallButtonWhite)
                    }
                }
            }
        }
        // Column Two, containing answer selections and "next" button
        vbox(13) {
            // Adding padding around the edges
            style {
                padding = box(20.px, 20.px, 20.px, 20.px)
            }

            // Getting answers from list of questions
            answers.add(question.correctAnswer)
            for(x in 0..2) {
                answers.add(question.incorrectAnswers[x])
            }
            // Shuffling answers
            answers.shuffle()

            // Creating a radiobutton for every answer
            for(x in answers) {
                radiobutton(x, answerGroup) {
                    action {
                        // Setting answerSelected to the value of the radiobutton when it is clicked
                        answerSelected = this@radiobutton.text
                    }
                    // Adding css class to style radio-buttons
                    addClass(Styles.answerRadio)
                }
            }

            // Check if the quiz is currently active, and display the relevant buttons.
            if(quiz.active) {
                // If the user is currently attempting the quiz, show the "Next" button
                hbox(alignment = Pos.BOTTOM_LEFT) {
                    button("Next") {
                        action {
                            // Checking if the answer selected was correct
                            if(answerSelected == "") {
                                // If there is no answer selected show an error dialog box
                                alert(javafx.scene.control.Alert.AlertType.ERROR, "Oops, you forgot to select " +
                                        "an answer. Select an answer or press skip.")
                            }
                            else {
                                if(answerSelected == question.correctAnswer) {
                                    // Add current question number to list of correct questions
                                    quiz.questionsCorrect.add(quiz.currentQuestionNumber)
                                }
                                else {
                                    // Use QuizModel function to add current question and answer user selected
                                    // to list of questions that the user got wrong
                                    quiz.questionsWrong.add(listOf("${quiz.currentQuestionNumber}", answerSelected))
                                }
                                // Check if user has reached the end of the quiz
                                if (quiz.currentQuestionNumber != quiz.numQuestions + 1) {
                                    // Increase question counter
                                    quiz.currentQuestionNumber++
                                    // Reload view
                                    reload()
                                } else {
                                    // Quiz is no longer active
                                    quiz.active = false
                                    // Change to ReviewView
                                    replaceWith(paramsReview)
                                }
                            }
                        }

                        // Adding css class to style "next" button
                        addClass(Styles.smallButtonBlue)
                    }
                }
            }
            else {
                // If the user is retrying a question after the quiz has finished, display the "Finish" button.
                hbox(alignment = Pos.BOTTOM_RIGHT) {
                    // Check if answer was correct, place question number in appropriate list and change to next question
                    button("Finish") {
                        action {
                            // Checking if an answer was selected
                            if(answerSelected == "") {
                                // If there is no answer selected show an error dialog box
                                alert(javafx.scene.control.Alert.AlertType.ERROR, "Oops, you forgot to select " +
                                        "an answer. Select an answer or press skip.")
                            }
                            else {
                                // Remove the previous result of this question from the lists in the instance of QuizModel
                                if(quiz.questionsSkipped.contains(quiz.currentQuestionNumber)) {
                                    quiz.questionsSkipped.remove(quiz.currentQuestionNumber)
                                }
                                else {
                                    // Variables to store position of previous results as index numbers
                                    var previousResultLocation: Int = 0
                                    var i = 0
                                    // Loop through each item in list of incorrect questions
                                    for(question in quiz.questionsWrong) {
                                        // Check if the result is the previous result for this question
                                        if(question[0].toInt() == quiz.currentQuestionNumber) {
                                            // Store the location of the previous result
                                            previousResultLocation = i
                                        }
                                        // Increase the list index counter
                                        i++
                                    }
                                    // Remove the previous question result from list of incorrect questions
                                    quiz.questionsWrong.removeAt(previousResultLocation)
                                }
                                
                                // Checking if the answer is correct
                                if(answerSelected == question.correctAnswer) {
                                    quiz.questionsCorrect.add(quiz.currentQuestionNumber)
                                    quiz.questionsCorrect.sort()
                                }
                                else {
                                    quiz.questionsWrong.add(listOf("${quiz.currentQuestionNumber}", answerSelected))
                                    quiz.questionsWrong.sortBy { it[0] }
                                }
                                // Close the window
                                close()
                            }
                        }

                        // Adding css class to style "next" button
                        addClass(Styles.smallButtonBlue)
                    }
                }
            }
        }
    }

    // Redirect function to get QuizModel instance and pass it as a parameter when changing view to Redirect.
    private fun reload() {
        // Creating new parameter containing the instance of QuizModel
        val params = find<RefreshRadio>(scope)
        // Replacing this view with the redirect view, effectively reloading this view
        replaceWith(params)
    }
}

// Class to reload view
class RefreshRadio : View("QwertyQuiz") {
    private val quiz: QuizModel by inject()

    // When View is loaded...
    override fun onDock() {
        // Creating new Scope
        val scope = Scope()
        // Setting QuizModel instance to be in scope
        setInScope(quiz, scope)
        // Creating new parameter containing the instance of QuizModel
        val params = find<RadioView>(scope)
        // Replacing StartView with RadioView, transferring instance of QuizModel as well
        replaceWith(params)
    }
    override val root = hbox()
}