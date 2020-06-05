package com.qwertygroup.qwertyquiz.view

import com.qwertygroup.qwertyquiz.app.QuizModel
import com.qwertygroup.qwertyquiz.app.Styles
import javafx.geometry.Pos
import tornadofx.*
import kotlin.math.roundToInt

class ResultView : View("QwertyQuiz Results") {
    // Get instance of QuizModel from RadioView
    val quiz: QuizModel by inject()

    // Resize the view window to fit this views contents
    override fun onDock() {
        currentWindow?.sizeToScene()
    }

    override val root = vbox(alignment = Pos.CENTER) {
        // Adding css styling
        addClass(Styles.resultView)
        // Calculating the result as a percentage
        val result = ((quiz.questionsCorrect.size.toDouble() / (quiz.numQuestions + 1)) * 100).roundToInt()

        // List to hold questions the user didn't answer correctly
        val incorrectQuestions = mutableListOf<QuestionResult>()

        // Getting question data from list of questions the user got wrong
        for(w in quiz.questionsWrong) {
            // Create instance of Question containing information required on question
            val question = QuestionResult(w[0].toInt(), "Incorrect",
                    quiz.questionList[w[0].toInt() - 1].correctAnswer, w[1])
            // Add list to list of incorrect questions
            incorrectQuestions.add(question)
        }
        // Getting question data from list of questions user skipped
        for(w in quiz.questionsSkipped) {
            // Create instance of QuestionResult containing information required on question
            val question = QuestionResult(w, "Skipped", quiz.questionList[w - 1].correctAnswer,
                    "")
            // Add list to list of incorrect questions
            incorrectQuestions.add(question)
        }

        // Sorting list of incorrect questions numerically
        incorrectQuestions.sortBy { it.questionNumber }

        label("Results") {
            // Adding css styling
            addClass(Styles.resultsTitle)
        }

        // Container to hold result data
        vbox(10, alignment = Pos.CENTER) {
            // Adding css styling
            addClass(Styles.resultContainer)

            // Displaying result as a percentage
            label("$result%") {
                // Adding css styling
                addClass(Styles.resultLabel)
            }
            // Only show description of questions the user got wrong, and questions the user got wrong if they didn't
            // get a result of 100%
            if(result != 100) {
                // Container for description of incorrect question button group
                vbox {
                    label("Below are the questions you didn't answer correctly.") {
                        // Adding css styling class
                        addClass(Styles.resultDescription)
                    }
                    label("Click on them to see more information - such as the correct answer.") {
                        // Adding css styling class
                        addClass(Styles.resultDescription)
                    }
                }
                flowpane {
                    // Adding css styling class
                    addClass(Styles.resultFlowpane)

                    // Generating buttons using list of incorrect questions
                    for(q in incorrectQuestions) {
                        hbox {
                            button("Q${q.questionNumber}") {
                                // Adding css styling class
                                addClass(Styles.resultButton)

                                action {
                                    // Save the data of this buttons question to the instance of Question in the ViewModel
                                    quiz.popUpQuestion = q
                                    // Open a new PopUpResult window, containing the data of this question
                                    PopUpResult().openWindow()
                                }
                            }
                            // Css styling for spacing
                            style {
                                padding = box(10.px)
                            }
                        }
                    }
                }
            }
        }

        // Action button container
        hbox(15, alignment = Pos.CENTER_RIGHT) {
            // Css styling for spacing
            style {
                padding = box(20.px, 0.px, 15.px, 0.px)
            }
            // Button to quit the program
            button("Quit") {
                // Adding css styling
                addClass(Styles.smallButtonWhite)

                action {
                    // Close the program
                    close()
                }
            }
            // Button to restart the quiz the user just completed
            button("Restart Quiz") {
                // Adding css styling
                addClass(Styles.smallButtonWhite)

                action {
                    // Change freshData boolean so ReviewVIew doesn't show outdated data
                    quiz.freshData = false

                    // Quiz is active again
                    quiz.active = true

                    // Use QuizModel function to reset question counter and question number
                    quiz.restartQuiz()
                    // Creating new Scope
                    val scope = Scope()
                    // Setting QuizModel instance to be in scope
                    setInScope(quiz, scope)
                    // Creating new parameter containing the instance of QuizModel
                    val params = find<RadioView>(scope)
                    // Switch to RadioView to restart the quiz
                    replaceWith(params)
                }
            }
            // Button to go back to the start page so the user can start a different quiz
            button("Back to Start") {
                // Adding css styling
                addClass(Styles.smallButtonWhite)

                action {
                    // Replacing this view with StartView
                    replaceWith<StartView>()
                }
            }
        }
    }
}

// QuestionResult class for storing instances of question data
class QuestionResult(val questionNumber: Int, val resultType: String, val correctAnswer: String, val answerSelected: String)

class PopUpResult: View("View Question") {
    // Getting instance of quiz
    val quiz = ResultView().quiz
    // Getting instance of popUpQuestion
    val question = quiz.popUpQuestion!!

    override val root = vbox(alignment = Pos.CENTER) {
        style {
            backgroundColor += c("113844")
            padding = box(0.px, 20.px, 20.px, 20.px)
        }
        // Container for title label
        hbox(alignment = Pos.BOTTOM_CENTER) {
            // Grammar
            if(question.resultType == "Incorrect") {
                label("You got question ") {
                    // Adding css styling class
                    addClass(Styles.popUpHeading)
                }
                // Question number
                label("${question.questionNumber}") {
                    // Adding css styling class
                    addClass(Styles.popUpTitle)
                }
                label(" incorrect") {
                    // Adding css styling class
                    addClass(Styles.popUpHeading)
                }
            }
            else {
                label("You skipped question ") {
                    // Adding css styling class
                    addClass(Styles.popUpHeading)
                }
                label("${question.questionNumber}") {
                    // Adding css styling class
                    addClass(Styles.popUpTitle)
                }
            }
        }
        // Container for question answers
        vbox(15, alignment = Pos.CENTER) {
            // Adding css styling class
            addClass(Styles.popUpContainer)

            // Container for correct answer
            hbox(alignment = Pos.CENTER) {
                label("The correct answer was ") {
                    // Adding css styling class
                    addClass(Styles.popUpSubHeader)
                }
                hbox {
                    style {
                        padding = box(15.px, 20.px)
                        backgroundColor += c("113844")
                        backgroundRadius += box(2.px)
                    }
                    // Displaying correct answer
                    label(question.correctAnswer) {
                        // Adding css styling class
                        addClass(Styles.popUpAnswersLabel)
                    }
                }
            }
            // If the user didn't skip the question, show the incorrect answer they chose
            if(question.resultType == "Incorrect") {
                // Container for incorrect answer
                hbox(alignment = Pos.CENTER) {
                    label("The answer you selected was ") {
                        // Adding css styling class
                        addClass(Styles.popUpSubHeader)
                    }
                    hbox {
                        style {
                            padding = box(15.px, 20.px)
                            backgroundRadius += box(2.px)
                        }
                        // Displaying incorrect answer
                        label(question.answerSelected) {
                            // Adding css styling class
                            addClass(Styles.popUpAnswersLabel)
                        }
                    }
                }
            }
        }
        
        hbox(alignment = Pos.CENTER) {
            // "Okay" button to close popup window
            button("Okay") {
                // Adding css styling class
                addClass(Styles.smallButtonWhite)

                action {
                    // Close this window
                    close()
                }
            }
            // CSS for spacing
            style {
                padding = box(10.px, 0.px, 0.px, 0.px)
            }
        }
    }
}
