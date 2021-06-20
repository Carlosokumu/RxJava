import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.fromIterable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread


fun main () {

    flowable()
    single()
    createUsingJust()
    createUsingFromArray()
    Map()
    flatMap()


}
//creating an Observable
fun createUsingJust(){
    Observable.just(Data.employees).subscribe {
        println(it)
    }
}
fun createUsingFromArray(){
    Observable.fromArray(Data.employees).subscribe {
        println(it)
    }
}

   /*
      Emitter Types
  */
//flowable-> works just like normal Observable but supports Backpressure
fun flowable(){
    Flowable.just(Data.employees)
        .subscribe(
            { value -> println("Received: $value") },
            { error -> println("Error: $error") },
            { println("Completed") }
        )
}
//single->it simply returns an error or success
fun single(){
    Single.just(Data.employees)
        .subscribe(
            { v -> println("Value is: $v") },
            { e -> println("Error: $e")}
        )
}
//maybe ->it returns a success or an error or onComplete
fun maybe(){
    Maybe.just(Data.employees)
        .subscribe(
            { value -> println("Received: $value") },
            { error -> println("Error: $error") },
            { println("Completed") }
        )
}
fun compleTable(){
    Completable.create { emitter ->
    emitter.onComplete()
    emitter.onError(Exception())
}
}
/*
     Operators
     Map & Flatmap
 */
fun Map(){
    fromIterable(Data.employees).map {
        it.name=it.name.toUpperCase()
        it
    }.subscribe {
        println(it)
    }
}
/* [  FlatMap->converts an observable into another observable  ]
 */
fun flatMap(){
     Observable.just(Data.employees)
   .subscribeOn(Schedulers.io())
  .flatMap { e ->
    Observable.just(e.forEach {
        it.name=it.name.toUpperCase()
    }).subscribeOn(Schedulers.io())
}
.subscribe { v -> println("Received: $v") }
}
