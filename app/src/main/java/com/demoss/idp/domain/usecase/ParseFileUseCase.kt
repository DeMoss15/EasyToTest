package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.base.RxUseCaseCompletable
import com.demoss.idp.util.Constants.EMPTY_LINE
import com.demoss.idp.util.Constants.NEW_QUESTION
import com.demoss.idp.util.Constants.NEW_TEST
import com.demoss.idp.util.Constants.RIGHT_ANSWER
import io.reactivex.Completable
import io.reactivex.rxkotlin.toObservable
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class ParseFileUseCase(private val saveChangesUseCase: SaveChangesUseCase) : RxUseCaseCompletable<ParseFileUseCase.Params>() {

    override fun buildUseCaseObservable(params: Params): Completable = parseStream(params.stream)
            .toObservable()
            .flatMapCompletable {
                saveChangesUseCase.save(it)
            }

    private fun parseStream(stream: InputStream): List<TestModel> {
        val bufferedReader = BufferedReader(InputStreamReader(stream))
        var lineOfText: String? = bufferedReader.readLine()

        val tests: MutableList<TestModel> = mutableListOf()

        var test: TestModel = TempEntitiesFabric.createTempTest()
        var question: QuestionModel = TempEntitiesFabric.createTempQuestion()
        var answer: AnswerModel

        while (lineOfText != null) {

            when {
                lineOfText.startsWith(NEW_TEST) -> {
                    test = TempEntitiesFabric.createTempTest()
                    test.name = lineOfText.substring(lineOfText.indexOf(NEW_TEST) + 1)
                    tests.add(test)
                }
                lineOfText.startsWith(NEW_QUESTION) -> {
                    question = TempEntitiesFabric.createTempQuestion()
                    question.text = lineOfText.substring(lineOfText.indexOf(NEW_QUESTION) + 1)
                    test.questions.add(question)
                }
                lineOfText.startsWith(RIGHT_ANSWER) -> {
                    answer = TempEntitiesFabric.createTempAnswer()
                    answer.isRightAnswer = true
                    answer.text = lineOfText.substring(lineOfText.indexOf(RIGHT_ANSWER) + 1)
                    question.answers.add(answer)
                }
                !lineOfText.startsWith(EMPTY_LINE) -> {
                    answer = TempEntitiesFabric.createTempAnswer()
                    answer.isRightAnswer = true
                    answer.text = lineOfText.substring(lineOfText.indexOf(EMPTY_LINE) + 1)
                    question.answers.add(answer)
                }
            }

            lineOfText = bufferedReader.readLine()
        }

        bufferedReader.close()
        tests.map { TempEntitiesFabric.cleanTempIds(it) }
        return tests
    }

    data class Params(
            val stream: InputStream
    )
}