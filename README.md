# QwertyQuiz-V2 - 2020
Created for another school project, this time with Kotlin - taking advantage of TornadoFX for the GUI elements.

Provides functionality for the user to complete various multi-choice quizzes.

Allows user to chose from a selection of quizzes, giving them the option of chosing the length of the quiz and then presents them with a series of questions based on the user's input. Questions and answers are shuffled on each attempt, so every quiz is slightly different. After answering or skipping the questions, the user is provided with the oppotunity to retry any questions before submitting the answers as the final result and displaying a results page, with the result as a percentage and the questions they got wrong listed below as buttons. Clicking one of these buttons will display a popup, containing the question number, the incorrect answer they selected if they got it wrong as well as the correct answer.

Alternatively, the user can choose to create their own quiz instead, in which case the program will present them with a form to fill out.

Quizzes are stored as .qwg files, which are essentially just .txt files however the hope is to stop any accidental changes to the format of the text inside the file, as the user's computer likely won't understand which program to open the file with.
