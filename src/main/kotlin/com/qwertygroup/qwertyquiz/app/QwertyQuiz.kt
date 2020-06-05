package com.qwertygroup.qwertyquiz.app

import com.qwertygroup.qwertyquiz.view.QuestionResult
import com.qwertygroup.qwertyquiz.view.StartView
import com.qwertygroup.qwertyquiz.view.Result
import tornadofx.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class MyApp: App(StartView::class, Styles::class)

// Declaring QuizModel class
class QuizModel: ViewModel() {
    // Initialising QuizModel properties
    val questionList: MutableList<Question> = mutableListOf()
    // numQuestions will be 1 less than the actual amount of questions, to account for list index starting at 0
    var numQuestions = 0
    // Lists to hold question result data
    var questionsCorrect: MutableList<Int> = mutableListOf()
    var questionsWrong: MutableList<List<String>> = mutableListOf()
    var questionsSkipped: MutableList<Int> = mutableListOf()
    // Variable to hold topic value
    var topic: String = ""
    // Question counter to keep track of current question
    var currentQuestionNumber: Int = 0
    // Boolean for ReviewView when reloading
    var freshData = false
    // List to hold results
    val resultList = mutableListOf<Result>()
    // Current instance of QuestionResult for PopUp view
    var popUpQuestion: QuestionResult? = null
    // List to hold all questions
    private val allQuestions: MutableList<Question> = mutableListOf()
    // Variable to determine current state of program, for RadioView to use when deciding which buttons to display
    var active = true

    // Declaring QuizModel function used to generate questions from text file
    fun generateQuestions() {
        var line = 0 // keeps track of which line number to use when reading questions from text file
        // Getting all the questions from the relevant text document
        for(i in 0..14) {
            // Creating temporary list to hold question data
            val question = mutableListOf<String>()
            // Using to for loop to read the 6 lines of each question, adding it to a list
            for(q in 0..5) {
                question.add(File("resources/${topic}.qwg").readLines()[line + q])
            }
            // Adding the list created to the main question list
            allQuestions.add(Question(question[0], question[1], question[2], listOf(question[3],
                    question[4], question[5])))
            line += 6 // adding 6 to line number to move to next question, as each question is 6 lines long.
        }
        // Shuffling Questions
        allQuestions.shuffle()

        // Take the amount of questions required for the specified length of the quiz from the shuffled list of all
        // questions
        for(i in 0..numQuestions) {
            questionList.add(allQuestions[i])
        }

        // Setting current questions to first question
        currentQuestionNumber = 1
    }
    // Declaring QuizModel function used for restarting the quiz, but keeping the same order of questions.
    fun restartQuiz() {
        // Reset question counter
        currentQuestionNumber = 1

        // Reset question results
        questionsCorrect.clear()
        questionsWrong.clear()
        questionsSkipped.clear()
    }
}

// Question object for easy management of question list
class Question(val questionDescription: String, val primaryQuestion: String, val correctAnswer: String,
               val incorrectAnswers: List<String>)

// CreateQuizModel object for when user is creating a new quiz
class CreateQuizModel: ViewModel() {
    // Variable to hold value of quiz name (topic)
    var topic = ""
    // List to hold quiz questions
    val questionList: MutableList<Question> = mutableListOf()
    // Question counter. Will be one less than actual question to account for list indexes starting at 0
    var currentQuestionNumber = 0

    // Function to fill questionList
    fun initialise() {
        for(q in 0..15) {
            questionList.add(Question("", "", "", listOf("", "", "")))
        }
    }

    // Function used when user submits
    fun nextQuestion(newTopic: String, question: Question) {
        // Add instance of question to question list
        questionList[currentQuestionNumber] = question
        // Update topic name
        topic = newTopic

        // Check if that was the last question
        if(currentQuestionNumber == 14) {
            // Create a new file to hold quiz data
            val createFile = File("resources/${topic.toUpperCase()}.qwg").createNewFile()

            // Make sure the file doesn't already exist
            if(createFile) {
                // Add the quiz topic to topic file
                Files.write(Paths.get("resources/Topics.qwg"), ("${topic}\n").toByteArray(),
                        StandardOpenOption.APPEND)

                // Write the question data to the newly created quiz file
                for(q in questionList) {
                    // Write question and correct answer to file
                    Files.write(Paths.get("resources/${topic.toUpperCase()}.qwg"), ("${q.questionDescription}\n" +
                            "${q.primaryQuestion}\n" +
                            "${q.correctAnswer}\n").toByteArray(), StandardOpenOption.APPEND)
                    // Write incorrect answers to file
                    for(x in q.incorrectAnswers) {
                        Files.write(Paths.get("resources/${topic.toUpperCase()}.qwg"), ("${x}\n").toByteArray(),
                                StandardOpenOption.APPEND)
                    }
                }
                // Increase the question counter
                currentQuestionNumber++
            }
            else {
                // This will only happen is the user has already created a quiz with the same name
                alert(javafx.scene.control.Alert.AlertType.ERROR, "Hmm, it seems there's already a quiz " +
                        "with the same name. Try using a different name.")
            }
        }
        else {
            // Increase the question counter
            currentQuestionNumber++
        }
    }
}