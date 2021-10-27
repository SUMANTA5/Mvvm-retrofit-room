package com.sumanta.myapplication.repo
//error handling
sealed class Response<T>(val data: T? = null, val errorManager: String? = null) {

    class Loading<T> : Response<T>()
    class Success<T>(data: T? = null) : Response<T>(data = data)
    class Error<T>(errorManager: String) : Response<T>(errorManager = errorManager)

}

//sealed class Response() {
//    class Loading : Response()
//    class Success(val quoteList: QuoteList) : Response()
//    class Error(val errorManager: String) : Response()
//}

//sealed class Response(val data: QuoteList? = null, val errorManager: String? = null) {
//
//    class Loading : Response()
//    class Success(quoteList: QuoteList) : Response(data = quoteList)
//    class Error(errorManager: String) : Response(errorManager = errorManager)
//
//}

