package com.qwertygroup.qwertyquiz.app

import javafx.geometry.Pos
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val largeButtonWhite by cssclass()
        val topicLengthLabel by cssclass()
        val titleHeading by cssclass()
        val lengthRadioButton by cssclass()
        val dropdown by cssclass()
        val answerRadio by cssclass()
        val columnOne by cssclass()
        val topSection by cssclass()
        val questionNumber by cssclass()
        val amountOfQuestions by cssclass()
        val questionDescription by cssclass()
        val questionLabel by cssclass()
        val smallButtonWhite by cssclass()
        val smallButtonBlue by cssclass()
        val titleSection by cssclass()
        val tables by cssclass()
        val tableButton by cssclass()
        val hideButton by cssclass()
        val resultDataLabel by cssclass()
        val resultRow by cssclass()
        val resultScrollpane by cssclass()
        val reviewView by cssclass()
        val resultsTitle by cssclass()
        val resultView by cssclass()
        val resultContainer by cssclass()
        val resultLabel by cssclass()
        val resultDescription by cssclass()
        val resultFlowpane by cssclass()
        val resultButton by cssclass()
        val createTextfield by cssclass()
        val createLabel by cssclass()
        val popUpSubHeader by cssclass()
        val popUpAnswersLabel by cssclass()
        val popUpContainer by cssclass()
        val popUpHeading by cssclass()
        val popUpTitle by cssclass()
    }

    init {
        popUpTitle {
            fontWeight = FontWeight.BOLD
            textFill = c("ffffff")
            fontSize = 40.px
        }
        popUpHeading {
            fontWeight = FontWeight.BOLD
            textFill = c("ffffff")
            fontSize = 20.px
            padding = box(0.px, 0.px, 10.px, 0.px)
        }
        popUpContainer {
            padding = box(15.px, 22.px)
            backgroundColor += c("092731")
            backgroundRadius += box(2.px)
        }
        popUpAnswersLabel {
            fontWeight = FontWeight.BOLD
            textFill = c("ffffff")
            fontSize = 23.px
        }
        popUpSubHeader {
            fontWeight = FontWeight.BOLD
            fontStyle = FontPosture.ITALIC
            textFill = c("ffffff")
            fontSize = 17.px
        }
        createLabel {
            textFill = c("ffffff")
            fontSize = 15.px
            fontWeight = FontWeight.SEMI_BOLD
            fontStyle = FontPosture.ITALIC
        }
        createTextfield {
            backgroundColor += c("ffffff")
            backgroundRadius += box(2.px)
            textFill = c("092731")
            fontWeight = FontWeight.BOLD
        }
        resultButton {
            backgroundRadius += box(2.px)
            padding = box(10.px, 15.px)
            fontWeight = FontWeight.BOLD
            textFill = c("113844")
            fontSize = 20.px
            backgroundColor += c("ffffff")

            and(hover) {
                backgroundColor += c("f2f2f2")
            }

            and(pressed) {
                backgroundColor += c("#092731")
            }
        }
        resultFlowpane {
            maxWidth = 400.px
            spacing = 5.px
            alignment = Pos.CENTER
        }
        resultDescription {
            textFill = c("ffffff")
            fontWeight = FontWeight.SEMI_BOLD
            fontStyle = FontPosture.ITALIC
            fontSize = 15.px
        }
        resultLabel {
            textFill = c("ffffff")
            fontWeight = FontWeight.BOLD
            fontSize = 80.px
        }
        resultContainer {
            backgroundColor += c("#092731")
            backgroundRadius += box(3.px)
            padding = box(10.px, 20.px)
        }
        resultView {
            backgroundColor += c("#113844")
            padding = box(0.px, 20.px)
        }
        resultsTitle {
            textFill = c("ffffff")
            fontSize = 35.px
            fontWeight = FontWeight.BOLD
            padding = box(5.px, 0.px, 0.px, 0.px)
        }
        reviewView {
            backgroundColor += c("#113844")
            padding = box(0.px, 100.px)
            maxHeight = 360.px
        }
        resultScrollpane {
            backgroundColor += c("113844")
            minHeight = 350.px
            maxWidth = 302.px
            minWidth = 302.px
            alignment = Pos.CENTER
        }
        resultRow {
            backgroundColor += c("#092731")
            padding = box(20.px, 10.px)
            backgroundRadius += box(3.px)
        }
        resultDataLabel {
            fontWeight = FontWeight.BOLD
            textFill = c("ffffff")
            fontSize = 20.px
        }
        hideButton {
            backgroundColor += c("113844")
            backgroundRadius += box(0.px)
            textFill = c("#113844")
        }
        titleSection {
            backgroundColor += c("#113844")
            padding = box(30.px, 50.px)
        }
        smallButtonBlue {
            backgroundColor += c("113844")
            backgroundRadius += box(1.5.px)
            textFill = c("#ffffff")
            fontSize = 15.px
            fontWeight = FontWeight.BOLD
            padding = box(5.px, 10.px)

            and(hover) {
                backgroundColor += c("#195466")
            }

            and(pressed) {
                backgroundColor += c("#051114")
            }
        }
        smallButtonWhite {
            backgroundColor += c("ffffff")
            backgroundRadius += box(1.5.px)
            textFill = c("#113844")
            fontSize = 15.px
            fontWeight = FontWeight.BOLD
            padding = box(5.px, 10.px)

            and(hover) {
                backgroundColor += c("f2f2f2")
            }

            and(pressed) {
                backgroundColor += c("#092731")
            }
        }
        questionLabel {
            textFill = c("ffffff")
            fontWeight = FontWeight.BOLD
            fontSize = 19.px
        }
        questionDescription {
            textFill = c("ffffff")
            fontWeight = FontWeight.SEMI_BOLD
            fontStyle = FontPosture.ITALIC
            fontSize = 15.px
            padding = box(0.px, 0.px, 7.px, 0.px)
        }
        amountOfQuestions {
            textFill = c("ffffff")
            fontSize = 25.px
            fontWeight = FontWeight.BOLD
            padding = box(0.px, 0.px, 5.px, 0.px)
        }
        questionNumber {
            textFill = c("ffffff")
            fontSize = 50.px
            fontWeight = FontWeight.BOLD
        }
        topSection {
            padding = box(0.px, 0.px, 25.px, 0.px)
        }
        columnOne {
            backgroundColor += c("#113A47")
            backgroundRadius += box(0.px, 25.px, 25.px, 0.px)
            padding = box(5.px, 20.px, 20.px, 20.px)
        }
        answerRadio {
            fontSize = 18.px
            fontWeight = FontWeight.BOLD
            textFill = c("000000")
        }
        largeButtonWhite {
            fontWeight = FontWeight.BOLD
            textFill = c("113844")
            backgroundColor += c("ffffff")
            padding = box(8.px, 30.px)
            fontSize = 20.px
            backgroundRadius += box(2.px)

            and(hover) {
                backgroundColor += c("f2f2f2")
            }

            and(pressed) {
                backgroundColor += c("#092731")
            }
        }
        topicLengthLabel {
            textFill = c("ffffff")
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
        titleHeading {
            textFill = c("ffffff")
            fontSize = 40.px
            fontWeight = FontWeight.BOLD
        }
        lengthRadioButton {
            textFill = c("ffffff")
            fontSize = 13.px
            fontWeight = FontWeight.BOLD
            padding = box(2.px)
        }
        dropdown {
            backgroundColor += c("ffffff")
            textFill = c("092731")
            fontWeight = FontWeight.BOLD
            backgroundRadius += box(2.px)

            and(hover) {
                backgroundColor += c("f1f1f1")
            }
        }
    }
}