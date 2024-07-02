package me.intuit.cat.data.utils.generic

fun handleError(message: String):String {
    return when (message) {
        ConstantsErrorHandler.EXCEPTION_MESSAGE -> {
            //UiText.StringResource(resId = R.string.app_name)
            return ""
        }
        ConstantsErrorHandler.NO_CONNECTION_INTERNET_MESSAGE -> {
            //UiText.StringResource(resId = R.string.app_name)
            return ""
        }
        else -> {
            //UiText.StringResource(resId = R.string.app_name)
            return ""
        }
    }
}

object ConstantsErrorHandler {

    // Message Exception
    const val EXCEPTION_MESSAGE = "ExceptionMessage"
    const val NO_CONNECTION_INTERNET_MESSAGE = "NoConnectionInternetMessage"
    const val NO_DATA_AVAILABLE_IN_DB = "Nodataindb"

    //endregion
}