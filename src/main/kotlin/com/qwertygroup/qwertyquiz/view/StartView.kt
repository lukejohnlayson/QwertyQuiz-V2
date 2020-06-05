package com.qwertygroup.qwertyquiz.view

import com.qwertygroup.qwertyquiz.app.CreateQuizModel
import com.qwertygroup.qwertyquiz.app.QuizModel
import com.qwertygroup.qwertyquiz.app.Styles
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import tornadofx.*
import java.io.File

// Declaring StartView class
class StartView : View("QwertyQuiz") {
    // Resize the view window to fit this views contents
    override fun onDock() {
        currentWindow?.sizeToScene()
    }

    // root window object, using vbox for vertical alignment of controls
    override val root = vbox(alignment = Pos.CENTER) {
        // Gradient Background, using JavaFX syntax
        style = "-fx-background-color: linear-gradient(#0C3340, #174F61);"

        // List of dropdown menu items
        val topics = FXCollections.observableArrayList("Physics", "General Knowledge")
        // String property for selected topic
        val topicSelected = SimpleStringProperty()
        // List of quiz lengths for radiobutton's
        val quizLengthLabels = listOf("Short - 5 Questions", "Medium - 10 Questions", "Long - 15 Questions")
        // Variable for length radiobutton group
        val lengthGroup = ToggleGroup()
        // Variable to hold radiobutton value
        var quizLength = 0
        // Creating instance of QuizModel
        val quiz = QuizModel()
        // Adding css class for background and padding
        addClass(Styles.titleSection)

        // Attempt to create Topics.qwg if it doesn't exist
        val topicsFile = File("resources/Topics.qwg").createNewFile()
        // Find any user created quizzes
        File("resources/Topics.qwg").forEachLine { topics.add(it) }

        // Title label
        label("QwertyQuiz") {
            addClass(Styles.titleHeading)
        }
        // Container for dropdown list, hbox for horizontal alignment of controls
        hbox(10, alignment = Pos.CENTER) {
            // Dropdown list label
            label("Topic") {
                addClass(Styles.topicLengthLabel)
            }
            // Dropdown list
            combobox(topicSelected, topics) {
                addClass(Styles.dropdown)
            }
            // Choosing to add inline styling to dropdown list instead of styles class due to lack of styling
            style {
                padding = box(10.px, 0.px)
            }
        }
        // Label for radiobutton's
        label("Length") {
            // Adding style class
            addClass(Styles.topicLengthLabel)
            // Add extra styling, only for this control
            style {
                padding = box(0.px, 0.px, 5.px, 0.px)
            }
        }
        // Generating radiobutton's from list
        for(length in quizLengthLabels) {
            radiobutton(length, lengthGroup) {
                // Click event for radiobutton's
                action {
                    // Check what value the radio button holds, then set quizLength to appropriate value.
                    when(this@radiobutton.text) {
                        /* quizLength is 1 less than actual number of questions to account for the index of a
                           list being 0*/
                        "Short - 5 Questions" -> quizLength = 4 // i.e: a quizLength of 4 == 5 questions
                        "Medium - 10 Questions" -> quizLength = 9
                        "Long - 15 Questions" -> quizLength = 14
                    }
                }
                // Add styles class to radiobutton's
                addClass(Styles.lengthRadioButton)
            }
        }
        // Button Container
        hbox(20, alignment = Pos.CENTER) {
            // Since there is no equivalent in tornadofx to css's margin, using a container with padding instead to replicate effect.
            style {
                padding = box(20.px, 0.px, 0.px, 0.px)
            }
            // Button to open the dialog to create a new quiz topic
            button("Create Quiz") {
                // Adding css styling class
                addClass(Styles.smallButtonWhite)

                action {
                    // Creating instance of CreateQuizModel
                    val createQuiz = CreateQuizModel()
                    // Initialising questionList
                    createQuiz.initialise()

                    // Creating new Scope
                    val scope = Scope()
                    // Setting QuizModel instance to be in scope
                    setInScope(createQuiz, scope)
                    // Creating new parameter containing the instance of QuizModel
                    val params = find<CreateQuizView>(scope)
                    // Replacing StartView with RadioView, transferring instance of QuizModel as well
                    replaceWith(params)
                }
            }
            // Button to begin quiz
            button("Start Quiz") {
                // Add css class for styling
                addClass(Styles.largeButtonWhite)
                // Click event for start quiz button
                action {
                    // Attempt to get values for quiz length and quiz topic
                    try {
                        // Check if quizLength has a value
                        if(quizLength != 0) {
                            // Set the number of questions in the quiz model to quizLength
                            quiz.numQuestions = quizLength
                        }
                        // Trigger an error if quizLength doesn't have a value
                        else {
                            // Display the error, describing the issue
                            throw Exception("Select a quiz length")
                        }
                        // Check if topicSelected has a value
                        if(topicSelected.value != null) {
                            // Set the quiz topic in the quiz model to the dropdown value
                            quiz.topic = topicSelected.value.toUpperCase()
                        }
                        // Trigger an error if topicSelected doesn't have a value
                        else {
                            // Display the error, describing the issue
                            throw Exception("Select a quiz topic")
                        }
                        // Generating questions based on data collected from user
                        quiz.generateQuestions()

                        // Creating new Scope
                        val scope = Scope()
                        // Setting QuizModel instance to be in scope
                        setInScope(quiz, scope)
                        // Creating new parameter containing the instance of QuizModel
                        val params = find<RadioView>(scope)
                        // Replacing StartView with RadioView, transferring instance of QuizModel as well
                        replaceWith(params)
                    }
                    // Display an error to the user if there are missing values
                    catch(e: Exception) {
                        // Open a dialog window to alert user of the error of their ways
                        alert(javafx.scene.control.Alert.AlertType.ERROR, e.message!!)
                    }
                }
            }
            // Button to quit the program
            button("Quit") {
                // Adding css styling class
                addClass(Styles.smallButtonWhite)
                style {
                    padding = box(5.px, 30.px)
                }
                action {
                    // Close the window
                    close()
                }
            }
        }
    }
}