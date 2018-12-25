package com.demoss.idp.domain.usecase

import com.demoss.idp.domain.model.AnswerModel
import com.demoss.idp.domain.model.QuestionModel
import com.demoss.idp.domain.model.TestModel
import com.demoss.idp.domain.usecase.model.GetTestUseCase
import com.demoss.idp.util.setDefaultSchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class TestSessionUseCase(private val getTestUseCase: GetTestUseCase) {

    private lateinit var test: TestModel
    private var timer: Long = 0L
    private val userAnswers = mutableListOf<AnswerModel>()
    private var timeSpent = ""
    private var isRunning = true

    fun setTestId(testId: Int): Completable {
        resetSession()
        return getTestUseCase.buildUseCaseObservable(GetTestUseCase.Params(testId))
                .map { test = it }
                .ignoreElement()
    }

    private fun resetSession() {
        timer = 0L
        userAnswers.clear()
        timeSpent = ""
        isRunning = true
    }

    // Setup =======================================================================================
    fun getTest(): TestModel = test

    fun setupSession(isShuffled: Boolean, timeInMinutes: Long, numberOfQuestions: Int) {
        if (isShuffled) {
            test.questions.shuffle()
            test.questions.map { it.answers.shuffle() }
        }
        this.timer = timeInMinutes * 60
        test.questions = test.questions.take(numberOfQuestions).toMutableList()
    }

    // Session =====================================================================================
    private val compositeDisposable = CompositeDisposable()
    private lateinit var timeObservable: Observable<String>
    private lateinit var questionsObservable: BehaviorSubject<QuestionModel>

    fun setAnswer(answer: AnswerModel) {
        userAnswers.add(answer)
        if (test.questions.size <= userAnswers.size) {
            questionsObservable.onComplete()
            return
        }
        questionsObservable.onNext(test.questions[userAnswers.size + 1])
    }

    fun subscribeToQuestions(onNextQuestion: (QuestionModel) -> Unit) {
        questionsObservable = BehaviorSubject.create<QuestionModel>()
        compositeDisposable.add(
                questionsObservable.doOnComplete { isRunning = !isRunning }.subscribe { onNextQuestion(it) }
        )
        questionsObservable.onNext(test.questions[0])
    }

    fun runTimer(onTimeOut: () -> Unit, onTick: (String) -> Unit) {
        timeObservable = createTimer()
        compositeDisposable.add(timeObservable.doOnNext(onTick).doOnComplete(onTimeOut).subscribe())
        compositeDisposable.add(timeObservable.takeLast(1).subscribe {
            timeSpent = it
            finishObservables()
        })
    }

    // Results =====================================================================================
    fun getNumberOfAnswers(): Int = userAnswers.size

    fun getNumberOfRightAnswers(): Int = userAnswers.filter { it.isRightAnswer }.size

    fun getTimeSpent(): String = timeSpent

    // Private =====================================================================================
    private fun finishObservables() {
        questionsObservable.onComplete()
        compositeDisposable.clear()
    }

    private fun createTimer(): Observable<String> = PublishSubject
            .interval(1, TimeUnit.SECONDS)
            // todo fix this observable completing
            .takeUntil { !(timer == 0L || (isRunning && it != timer)) } // it takes while expression is false
            .map { time -> "${time / 60}:${time % 60}" }
            .setDefaultSchedulers()
}