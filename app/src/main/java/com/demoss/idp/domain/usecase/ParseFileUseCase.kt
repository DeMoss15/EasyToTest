package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.base.RxUseCaseCompletable
import com.demoss.idp.util.Constants.EMPTY_LINE
import com.demoss.idp.util.Constants.JSON_PREFIX
import com.demoss.idp.util.Constants.NEW_QUESTION
import com.demoss.idp.util.Constants.NEW_TEST
import com.demoss.idp.util.Constants.RIGHT_ANSWER
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

class ParseFileUseCase(
    private val saveChangesUseCase: SaveChangesUseCase,
    private val encryptionUseCase: EncryptionUseCase,
    private val jsonConverterUseCase: JsonConverterUseCase
) : RxUseCaseCompletable<ParseFileUseCase.Params>() {

    override fun execute(observer: DisposableCompletableObserver, params: Params) {
        addDisposable(
            buildUseCaseObservable(params)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

    override fun buildUseCaseObservable(params: Params): Completable = parseStreamToJson(params.stream)
        .flatMapCompletable {
            saveChangesUseCase.save(it)
        }

    private fun parseStreamToJson(stream: InputStream): Observable<TestModel> {
        val bufferedReader = BufferedReader(InputStreamReader(stream))
        var lineOfText: String? = bufferedReader.readLine()

        val tests: MutableList<TestModel> = mutableListOf()

        var test: TestModel = TempEntitiesFabric.createTempTest()
        var question: QuestionModel = TempEntitiesFabric.createTempQuestion()
        var answer: AnswerModel

        if (lineOfText != null && lineOfText.startsWith(JSON_PREFIX)) {
            val stringBuilder = StringBuilder()

            while (lineOfText != null) {
                stringBuilder.append(lineOfText)
                lineOfText = bufferedReader.readLine()
            }
            bufferedReader.close()

            return encryptionUseCase.buildUseCaseObservable(EncryptionUseCase.Params(stringBuilder.toString()))
                .flatMapObservable { decryptedJson ->
                    Observable.fromCallable { jsonConverterUseCase.parse(decryptedJson) }
                }
        }

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
                    answer.isRightAnswer = false
                    answer.text = lineOfText.substring(lineOfText.indexOf(EMPTY_LINE) + 1)
                    question.answers.add(answer)
                }
            }

            lineOfText = bufferedReader.readLine()
        }

        bufferedReader.close()
        tests.map { TempEntitiesFabric.cleanTempIds(it) }
        return tests.toObservable()
    }

    data class Params(
        val stream: InputStream
    )
}