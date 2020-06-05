package com.qwertygroup.qwertyquiz.view

import com.qwertygroup.qwertyquiz.app.QuizModel
import com.qwertygroup.qwertyquiz.app.Styles
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class ReviewView : View("Review Questions") {
    // Get instance of QuizModel from RadioView
    val quiz: QuizModel by inject()

    // Resize the view window to fit this views contents
    override fun onDock() {
        currentWindow?.sizeToScene()
    }

    override val root = vbox(alignment = Pos.CENTER) {
        // ReviewView title label
        label("Review") {
            addClass(Styles.titleHeading)
            style {
                padding = box(0.px, 0.px, 15.px, 0.px)
            }
        }
        // Check if the resultList contains the latest data from the question lists
        if(quiz.freshData) {
            scrollpane {
                vbox(5, alignment = Pos.CENTER) {
                    for(row in quiz.resultList) {
                        hbox(50, alignment = Pos.CENTER) {
                            label("Q${row.questionNumber}") {
                                addClass(Styles.resultDataLabel)
                            }
                            label(row.status) {
                                addClass(Styles.resultDataLabel)
                            }
                            button("Retry") {
                                action {
                                    if(row.status != "Correct") {
                                        // Quiz is not active
                                        quiz.active = false
                                        // Setting the current question number to this rows question number
                                        quiz.currentQuestionNumber = row.questionNumber
                                        // Open the RadioView as a modal window and wait for it to close
                                        RadioView().openModal(block = true)
                                        // Refresh the resultsList to contain latest result data
                                        loadResultsList()
                                        // Reload the view so changes are shown in the GUI
                                        reload()
                                    }
                                }
                                style {
                                    fontWeight = FontWeight.BOLD
                                    if(row.status != "Correct") {
                                        textFill = c("ffffff")
                                        backgroundColor += c("103C4A")
                                    }
                                    else {
                                        textFill = c("092731")
                                        backgroundColor += c("092731")
                                    }
                                    padding = box(5.px, 10.px)
                                    fontSize = 15.px
                                    backgroundRadius += box(1.px)
                                }
                            }
                            addClass(Styles.resultRow)
                        }
                    }
                    style {
                        backgroundColor += c("#113844")
                    }
                }
                // Adding css styling
                addClass(Styles.resultScrollpane)
                style = "-fx-background: #113844;"
            }
        }
        // If it doesn't, then show a load button instead
        else {
            // Container to take up the same amount of space as the results table
            hbox(alignment = Pos.CENTER) {
                // Load button
                button("Load Results") {
                    // Adding css styling class to load button
                    addClass(Styles.smallButtonWhite)
                    action {
                        // Refresh the resultsList to contain latest result data
                        loadResultsList()
                        // Set freshData to be true so result data is shown to user
                        quiz.freshData = true
                        // Reload the view so changes are shown in the GUI
                        reload()
                    }
                }
                // Styling container
                style {
                    minHeight = 300.px
                    minWidth = 300.px
                }
            }
        }
        // Container to give "Submit" button padding
        hbox(alignment = Pos.CENTER) {
            // "Submit" button to confirm that the current question data is final, and to move on to ResultsView
            button("Submit") {
                addClass(Styles.largeButtonWhite)
                action {
                    // Creating new Scope
                    val scope = Scope()
                    // Setting QuizModel instance to be in scope
                    setInScope(quiz, scope)
                    // Creating new parameter containing the instance of QuizModel
                    val params = find<ResultView>(scope)
                    // Replacing this view with the ResultView to finalise results and display to user
                    replaceWith(params)
                }
            }
            style {
                padding = box(25.px, 0.px, 30.px, 0.px)
            }
        }
        // Adding css styling for background color and padding
        addClass(Styles.reviewView)
    }
    
    // Function to clear the resultsList and
    fun loadResultsList() {
        quiz.resultList.clear()

        // Remove duplicate items from lists
        quiz.questionsCorrect = quiz.questionsCorrect.distinct().toMutableList()
        quiz.questionsWrong = (quiz.questionsWrong.distinctBy { it[0] }).toMutableList()
        quiz.questionsSkipped = quiz.questionsSkipped.distinct().toMutableList()

        val incorrectListSize = quiz.questionsWrong.size - 1

        // Add question result lists to resultList
        for(q in quiz.questionsCorrect) {
            quiz.resultList.add(Result(q, "Correct"))
        }
        for(q in quiz.questionsSkipped) {
            quiz.resultList.add(Result(q, "Skipped"))
        }
        for(q in quiz.questionsWrong) {
            quiz.resultList.add(Result(q[0].toInt(), "Wrong"))
        }

        // Sort list
        quiz.resultList.sortBy { it.questionNumber }
    }
    // Redirect function to get QuizModel instance and pass it as a parameter when changing view to Redirect.
    private fun reload() {
        // Creating new Scope
        val scope = Scope()
        // Setting QuizModel instance to be in scope
        setInScope(quiz, scope)
        // Creating new parameter containing the instance of QuizModel
        val params = find<RefreshReview>(scope)
        // Replacing this view with the redirect view, effectively reloading this view
        replaceWith(params)
    }
}

// Class to reload ReviewView
class RefreshReview : View("Quiz Task") {
    private val quiz: QuizModel by inject()

    // When View is loaded...
    override fun onDock() {
        // Creating new Scope
        val scope = Scope()
        // Setting QuizModel instance to be in scope
        setInScope(quiz, scope)
        // Creating new parameter containing the instance of QuizModel
        val params = find<ReviewView>(scope)
        // Replacing StartView with RadioView, transferring instance of QuizModel as well
        replaceWith(params)
    }
    override val root = hbox()
}

// TableView class to be used when creating objects for list of results
class Result(val questionNumber: Int, val status: String)