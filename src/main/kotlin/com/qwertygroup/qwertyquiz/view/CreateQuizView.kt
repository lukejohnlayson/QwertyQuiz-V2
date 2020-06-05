package com.qwertygroup.qwertyquiz.view

import com.qwertygroup.qwertyquiz.app.CreateQuizModel
import com.qwertygroup.qwertyquiz.app.Question
import com.qwertygroup.qwertyquiz.app.Styles
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.TextField
import tornadofx.*

class CreateQuizView : View("Create New Quiz") {
    // Getting  instance of CreateQuizModel
    private val createQuiz: CreateQuizModel by inject()

    // Resize the window to fit to view contents
    override fun onDock() {
        currentWindow?.sizeToScene()

        if(createQuiz.currentQuestionNumber > 14) {
            replaceWith<StartView>()
        }
    }

    override val root = vbox(10, alignment = Pos.CENTER) {
        // Creating new Scope
        val scope = Scope()
        // Setting QuizModel instance to be in scope
        setInScope(createQuiz, scope)
        // Text fields
        var quizTopic: TextField? = null
        var questionDescription: TextField? = null
        var primaryQuestion: TextField? = null
        var correctAnswer: TextField? = null
        var incorrectAnswer1: TextField? = null
        var incorrectAnswer2: TextField? = null
        var incorrectAnswer3: TextField? = null
        // Variable to hold path to corresponding instance of Question
        val question = createQuiz.questionList[createQuiz.currentQuestionNumber]

        style {
            backgroundColor += c("113844")
            padding = box(20.px)
        }
        label("Create a New Quiz") {
            // Adding css styling class
            addClass(Styles.titleHeading)
        }
        // Container for topic textfield
        hbox(10, alignment = Pos.CENTER) {
            label("Enter the name of the quiz") {
                // Adding css styling class
                addClass(Styles.createLabel)
            }
            // Text field for quiz topic
            textfield(createQuiz.topic) {
                // Adding css styling class
                addClass(Styles.createTextfield)

                // Initialising textfield
                quizTopic = this@textfield
            }
        }
        // Container for question form
        vbox(10, alignment = Pos.CENTER) {
            style {
                backgroundColor += c("092731")
                backgroundRadius += box(2.px)
                padding = box(15.px)
            }
            label("Question ${createQuiz.currentQuestionNumber + 1} of 15") {
                addClass(Styles.topicLengthLabel)
            }
            label("Enter the description of the question:") {
                // Adding css styling class
                addClass(Styles.createLabel)
            }
            textfield(question.questionDescription) {
                // Adding css styling class
                addClass(Styles.createTextfield)

                // Initialising textfield
                questionDescription = this@textfield
            }
            label("Enter the question itself:") {
                // Adding css styling class
                addClass(Styles.createLabel)
            }
            textfield(question.primaryQuestion) {
                // Adding css styling class
                addClass(Styles.createTextfield)

                // Initialising textfield
                primaryQuestion = this@textfield
            }
            // Container for answer textfields
            hbox(10, alignment = Pos.CENTER) {
                vbox(10, alignment = Pos.CENTER) {
                    label("Enter the correct answer:") {
                        // Adding css styling class
                        addClass(Styles.createLabel)
                    }
                    textfield(question.correctAnswer) {
                        // Adding css styling class
                        addClass(Styles.createTextfield)

                        // Initialising textfield
                        correctAnswer = this@textfield
                    }
                    label("Enter one of the wrong answers:") {
                        // Adding css styling class
                        addClass(Styles.createLabel)
                    }
                    textfield(question.incorrectAnswers[0]) {
                        // Adding css styling class
                        addClass(Styles.createTextfield)

                        // Initialising textfield
                        incorrectAnswer1 = this@textfield
                    }
                }
                vbox(10, alignment = Pos.CENTER) {
                    label("Enter one of the wrong answers:") {
                        // Adding css styling class
                        addClass(Styles.createLabel)
                    }
                    textfield(question.incorrectAnswers[1]) {
                        // Adding css styling class
                        addClass(Styles.createTextfield)

                        // Initialising textfield
                        incorrectAnswer2 = this@textfield
                    }
                    label("Enter one of the wrong answers:") {
                        // Adding css styling class
                        addClass(Styles.createLabel)
                    }
                    textfield(question.incorrectAnswers[2]) {
                        // Adding css styling class
                        addClass(Styles.createTextfield)

                        // Initialising textfield
                        incorrectAnswer3 = this@textfield
                    }
                }
            }
            // Container for buttons
            borderpane {
                // Css styling for spacing
                style {
                    padding = box(15.px, 0.px, 0.px, 0.px)
                }

                // Variable to hold value of "next" button text
                var nextText = "Next"
                if(createQuiz.currentQuestionNumber == 14) {
                    nextText = "Finish"
                }

                // Button to cancel creating a new quiz and switch back to StartView
                left = button("Cancel") {
                    // Adding css styling class
                    addClass(Styles.smallButtonWhite)

                    action {
                        // Going back to the StartView without saving the instance of CreateQuizModel
                        replaceWith<StartView>()
                    }
                }

                // Buttons to navigate the question forms
                right = hbox(8, alignment = Pos.CENTER) {
                    // Button to go to the previous question form
                    button("Previous") {
                        // Adding css styling class
                        addClass(Styles.smallButtonWhite)

                        action {
                            // Roll back question counter
                            createQuiz.currentQuestionNumber--

                            // Creating new parameter containing the instance of QuizModel
                            val params = find<RefreshCreateQuiz>(scope)
                            // Replacing StartView with RadioView, transferring instance of QuizModel as well
                            replaceWith(params)
                        }
                    }
                    // Button to move onto the next question form, or finish creating the quiz
                    button(nextText) {
                        // Adding css styling class
                        addClass(Styles.smallButtonWhite)

                        action {
                            // Making sure the user has filled out all the text field's
                            if(quizTopic!!.text != "" && questionDescription!!.text != ""
                                    && primaryQuestion!!.text != "" && correctAnswer!!.text != ""
                                    && incorrectAnswer1!!.text != "" && incorrectAnswer2!!.text != ""
                                    && incorrectAnswer3!!.text != "") {

                                // Creating instance of Question containing question data
                                val question = Question(questionDescription!!.text, primaryQuestion!!.text,
                                        correctAnswer!!.text, listOf(incorrectAnswer1!!.text,
                                        incorrectAnswer2!!.text, incorrectAnswer3!!.text))

                                // Saving topic and instance of Question to questionList inside instance of createQuiz
                                createQuiz.nextQuestion(quizTopic!!.text, question)

                                // Creating new parameter containing the instance of QuizModel
                                val params = find<RefreshCreateQuiz>(scope)
                                // Replacing StartView with RadioView, transferring instance of QuizModel as well
                                replaceWith(params)
                            }
                            else {
                                // Display an error if there is missing content
                                Alert(javafx.scene.control.Alert.AlertType.ERROR, "Opps, you forgot to " +
                                        "fill in one of the textfields.")
                            }
                        }
                    }
                }
            }
        }
    }
}

// Class to reload view
class RefreshCreateQuiz: View("Create New Quiz") {
    private val createQuiz: CreateQuizModel by inject()

    // When View is loaded...
    override fun onDock() {
        // Creating new Scope
        val scope = Scope()
        // Setting QuizModel instance to be in scope
        setInScope(createQuiz, scope)
        // Creating new parameter containing the instance of QuizModel
        val params = find<CreateQuizView>(scope)
        // Replacing StartView with RadioView, transferring instance of QuizModel as well
        replaceWith(params)
    }
    override val root = hbox()
}
